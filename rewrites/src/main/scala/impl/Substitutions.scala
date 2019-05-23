package impl

import fix.GlobalImports
import scalafix.v1._

import scala.collection.mutable
import scala.meta._
import scala.meta.parsers.Parsed.Success

class Substitutions(implicit doc: SemanticDocument) {
  type Substitution = PartialFunction[Tree, RewriteAndDiagnostic]

  /** Trees will be replaced by one of the patches. Their children are not visited by the [[SubstitutingTraverser]] */
  private val substituted = mutable.Set.empty[Tree]

  val globalImports = new GlobalImports

  private class SubstitutingTraverser(sub: PartialFunction[Tree, Patch]) extends Traverser {
    val patches = mutable.ListBuffer.empty[Patch]
    override def apply(tree: Tree): Unit = {
      if (sub.isDefinedAt(tree)) patches += sub(tree)
      if (!substituted(tree)) super.apply(tree)
    }
  }

  private def substitutionsPF(subs: List[Substitution], toPatch: RewriteAndDiagnostic => Patch): PartialFunction[Tree, Patch] = {
    case t if subs.exists(_.isDefinedAt(t)) =>
      subs.flatMap(_.lift.apply(t).map(toPatch)).asPatch
  }

  private def run(tree: Tree, subs: List[Substitution], toPatch: RewriteAndDiagnostic => Patch): Patch = {
    val traverser = new SubstitutingTraverser(substitutionsPF(subs, toPatch))
    traverser.apply(tree)
    traverser.patches.asPatch + globalImports.patch
  }

  def rewrite(tree: Tree, subs: List[Substitution]): Patch = run(tree, subs, _.rewrite)
  def lint(tree: Tree, subs: List[Substitution]): Patch = run(tree, subs, _.lint)

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

  private def replaceTokens(t: Tree, orig: String, repl: String, message: Position => Diagnostic): RewriteAndDiagnostic = {
    var first: Token = null
    val patches = t.tokens.collect {
      case tok if tok.text == orig =>
        if (first == null) first = tok
        Patch.replaceToken(tok, repl)
    }
    if (first == null) RewriteAndDiagnostic.empty
    else RewriteAndDiagnostic(
      patches.asPatch,
      message(first.pos))
  }

  private def replaceTree(from: Tree, to: String, message: Position => Diagnostic): RewriteAndDiagnostic = {
    def toIsName = to.parse[Term] match {
      case Success(_: Term.Name) => true
      case _ => false
    }
    val toQ = from match {
      case _: Term.Name if from.parent.exists(_.isInstanceOf[Term.Interpolate]) && !toIsName =>
        s"{$to}"
      case _ =>
        to
    }
    substituted += from
    RewriteAndDiagnostic(Patch.replaceTree(from, toQ), message(from.pos))
  }

  private val platformDiag = Diagnostic("platform", "scala.compat.Platform is deprecated in Scala 2.13", _: Position)
  val platfromEOL: Substitution = {
    case EOL(i: Importee) => RewriteAndDiagnostic(Patch.removeImportee(i), platformDiag(i.pos))
    case EOL(t: Term)     => replaceTree(t, "System.lineSeparator", platformDiag)
  }

  val platformCurrentTime: Substitution = {
    case currentTime(i: Importee) => RewriteAndDiagnostic(Patch.removeImportee(i), platformDiag(i.pos))
    case currentTime(t: Term) => replaceTree(t, "System.currentTimeMillis", platformDiag)
  }

  val platformArraycopy: Substitution = {
    case arraycopy(i: Importee)      => RewriteAndDiagnostic(Patch.removeImportee(i), platformDiag(i.pos))
    case arraycopy(Term.Apply(t, _)) => replaceTree(t, "System.arraycopy", platformDiag)
  }

  private val consoleReadDiag = Diagnostic("consoleRead", "Console.read methods are removed in Scala 2.13", _: Position)
  private def stdInReplace(tree: Tree, name: String): RewriteAndDiagnostic = {
    globalImports.add(importer"scala.io.StdIn")
    replaceTree(tree, s"StdIn.$name", consoleReadDiag)
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

  private val unicodeDoubleArrowDiag = Diagnostic("unicodeDoubleArrow", "Unicode arrows are deprecated in Scala 2.13", _: Position)
  val unicodeArrows: Substitution = {
    case t: Case                  => replaceTokens(t, "⇒", "=>", unicodeDoubleArrowDiag)
    case t: Type.Function         => replaceTokens(t, "⇒", "=>", unicodeDoubleArrowDiag)
    case t: Term.Function         => replaceTokens(t, "⇒", "=>", unicodeDoubleArrowDiag)
    case t: Importee              => replaceTokens(t, "⇒", "=>", unicodeDoubleArrowDiag)
    case arrowAssoc(t: Term.Name) => replaceTree(t, "->", Diagnostic("unicodeArrow", "Unicode arrows are deprecated in Scala 2.13", _))
  }

  val symbolLiteral: Substitution = {
    case t @ Lit.Symbol(sym) => replaceTree(t, s"""Symbol("${sym.name}")""", Diagnostic("symbolLiteral", "Symbol literals are deprecated in Scala 2.13", _))
  }
}
