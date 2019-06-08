package impl

import scalafix.v1._

import scala.collection.mutable
import scala.meta._
import scala.meta.parsers.Parsed.Success

final class Substitutions(implicit doc: SemanticDocument) {
  type Substitution = PartialFunction[Tree, Patch]

  /** Trees will be replaced by one of the patches. Their children are not visited by the [[SubstitutingTraverser]]. */
  private val substituted = mutable.Set.empty[Tree]

  private val globalImports = new GlobalImports

  private class SubstitutingTraverser(sub: Substitution) extends Traverser {
    val patches = mutable.ListBuffer.empty[Patch]
    override def apply(tree: Tree): Unit = {
      if (sub.isDefinedAt(tree)) patches += sub(tree)
      if (!substituted(tree)) super.apply(tree)
    }
  }

  private def substitutionsPF(subs: List[Substitution]): Substitution = {
    case t if subs.exists(_.isDefinedAt(t)) =>
      subs.flatMap(_.lift.apply(t)).asPatch
  }

  private def run(tree: Tree, subs: List[Substitution]): Patch = {
    val traverser = new SubstitutingTraverser(substitutionsPF(subs))
    traverser.apply(tree)
    traverser.patches.asPatch
  }

  def rewrite(tree: Tree, subs: List[Substitution]): Patch = run(tree, subs) + globalImports.patch

  // Symbols

  private val EOL         = SymbolMatcher.exact("scala/compat/Platform.EOL.")
  private val currentTime = SymbolMatcher.exact("scala/compat/Platform.currentTime().")
  private val arraycopy   = SymbolMatcher.exact("scala/compat/Platform.arraycopy().")

  private val deprecatedConsoleReadBoolean = SymbolMatcher.exact("scala/DeprecatedConsole#readBoolean().")
  private val deprecatedConsoleReadByte    = SymbolMatcher.exact("scala/DeprecatedConsole#readByte().")
  private val deprecatedConsoleReadChar    = SymbolMatcher.exact("scala/DeprecatedConsole#readChar().")
  private val deprecatedConsoleReadDouble  = SymbolMatcher.exact("scala/DeprecatedConsole#readDouble().")
  private val deprecatedConsoleReadFloat   = SymbolMatcher.exact("scala/DeprecatedConsole#readFloat().")
  private val deprecatedConsoleReadInt     = SymbolMatcher.exact("scala/DeprecatedConsole#readInt().")
  private val deprecatedConsoleReadLine    = SymbolMatcher.exact("scala/DeprecatedConsole#readLine().")
  private val deprecatedConsoleReadLine1   = SymbolMatcher.exact("scala/DeprecatedConsole#readLine(+1).")
  private val deprecatedConsoleReadLong    = SymbolMatcher.exact("scala/DeprecatedConsole#readLong().")
  private val deprecatedConsoleReadShort   = SymbolMatcher.exact("scala/DeprecatedConsole#readShort().")
  private val deprecatedConsoleReadf       = SymbolMatcher.exact("scala/DeprecatedConsole#readf().")
  private val deprecatedConsoleReadf1      = SymbolMatcher.exact("scala/DeprecatedConsole#readf1().")
  private val deprecatedConsoleReadf2      = SymbolMatcher.exact("scala/DeprecatedConsole#readf2().")
  private val deprecatedConsoleReadf3      = SymbolMatcher.exact("scala/DeprecatedConsole#readf3().")

  private val arrowAssoc = SymbolMatcher.exact("scala/Predef.ArrowAssoc#`→`().")

  // Rewrites

  private def replaceTokens(t: Tree, orig: String, repl: String): Patch = {
    t.tokens.collect {
      case tok if tok.text == orig => Patch.replaceToken(tok, repl)
    }.asPatch
  }

  private def replaceTree(from: Tree, to: String): Patch = {
    def toIsName = PartialFunction.cond(to.parse[Term]) { case Success(_: Term.Name) => true }
    val toQ = from match {
      case _: Term.Name if from.parent.exists(_.isInstanceOf[Term.Interpolate]) && !toIsName =>
        s"{$to}"
      case _ =>
        to
    }
    substituted += from
    Patch.replaceTree(from, toQ)
  }

  val platfromEOL: Substitution = {
    case EOL(i: Importee) => Patch.removeImportee(i)
    case EOL(t: Term)     => replaceTree(t, "System.lineSeparator")
  }

  val platformCurrentTime: Substitution = {
    case currentTime(i: Importee) => Patch.removeImportee(i)
    case currentTime(t: Term)     => replaceTree(t, "System.currentTimeMillis")
  }

  val platformArraycopy: Substitution = {
    case arraycopy(i: Importee)      => Patch.removeImportee(i)
    case arraycopy(Term.Apply(t, _)) => replaceTree(t, "System.arraycopy")
  }

  private def stdInReplace(tree: Tree, name: String): Patch = {
    globalImports.add(importer"scala.io.StdIn")
    replaceTree(tree, s"StdIn.$name")
  }
  val consoleRead: Substitution = {
    case deprecatedConsoleReadBoolean(Term.Apply(t, _)) => stdInReplace(t, "readBoolean")
    case deprecatedConsoleReadByte(   Term.Apply(t, _)) => stdInReplace(t, "readByte")
    case deprecatedConsoleReadChar(   Term.Apply(t, _)) => stdInReplace(t, "readChar")
    case deprecatedConsoleReadDouble( Term.Apply(t, _)) => stdInReplace(t, "readDouble")
    case deprecatedConsoleReadFloat(  Term.Apply(t, _)) => stdInReplace(t, "readFloat")
    case deprecatedConsoleReadInt(    Term.Apply(t, _)) => stdInReplace(t, "readInt")
    case deprecatedConsoleReadLine(   Term.Apply(t, _)) => stdInReplace(t, "readLine")
    case deprecatedConsoleReadLine1(  Term.Apply(t, _)) => stdInReplace(t, "readLine")
    case deprecatedConsoleReadLong(   Term.Apply(t, _)) => stdInReplace(t, "readLong")
    case deprecatedConsoleReadShort(  Term.Apply(t, _)) => stdInReplace(t, "readShort")
    case deprecatedConsoleReadf(      Term.Apply(t, _)) => stdInReplace(t, "readf")
    case deprecatedConsoleReadf1(     Term.Apply(t, _)) => stdInReplace(t, "readf1")
    case deprecatedConsoleReadf2(     Term.Apply(t, _)) => stdInReplace(t, "readf2")
    case deprecatedConsoleReadf3(     Term.Apply(t, _)) => stdInReplace(t, "readf3")
  }

  val unicodeArrows: Substitution = {
    case t: Case                  => replaceTokens(t, "⇒", "=>")
    case t: Type.Function         => replaceTokens(t, "⇒", "=>")
    case t: Term.Function         => replaceTokens(t, "⇒", "=>")
    case t: Importee              => replaceTokens(t, "⇒", "=>")
    case arrowAssoc(t: Term.Name) => replaceTree(t, "->")
  }

  val symbolLiteral: Substitution = {
    case t @ Lit.Symbol(sym) => replaceTree(t, s"""Symbol("${sym.name}")""")
  }
}
