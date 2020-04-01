package fix.scala213

import scala.meta._

import scalafix.v1._

final class Core extends SemanticRule("fix.scala213.Core") {
  import Core._

  override def fix(implicit doc: SemanticDocument) = new impl.ReplacingTraverser {
    val platformEOL: Replacer = {
      case EOL(i: Importee) => Patch.removeImportee(i)
      case EOL(t: Term)     => replaceTree(t, "System.lineSeparator")
    }

    val platformCurrentTime: Replacer = {
      case currentTime(i: Importee) => Patch.removeImportee(i)
      case currentTime(t: Term)     => replaceTree(t, "System.currentTimeMillis")
    }

    val platformArraycopy: Replacer = {
      case arraycopy(i: Importee)      => Patch.removeImportee(i)
      case arraycopy(Term.Apply(t, _)) => replaceTree(t, "System.arraycopy")
    }

    val consoleRead: Replacer = {
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

    private def stdInReplace(tree: Tree, name: String): Patch =
      replaceTree(tree, s"StdIn.$name") + globalImports.add(importer"scala.io.StdIn")

    val unicodeArrows: Replacer = {
      case t: Case                  => replaceTokens(t, "⇒", "=>")
      case t: Type.Function         => replaceTokens(t, "⇒", "=>")
      case t: Term.Function         => replaceTokens(t, "⇒", "=>")
      case t: Importee              => replaceTokens(t, "⇒", "=>")
      case arrowAssoc(t: Term.Name) => replaceTree(t, "->")
    }

    val symbolLiteral: Replacer = {
      case t @ Lit.Symbol(sym) => replaceTree(t, s"""Symbol("${sym.name}")""")
    }

    val replacers = List(
      platformEOL,
      platformCurrentTime,
      platformArraycopy,
      consoleRead,
      unicodeArrows,
      symbolLiteral,
    )
  }.run(doc.tree)
}

object Core {
  private def replaceTokens(t: Tree, from: String, to: String): Patch =
    t.tokens.collect { case tok if tok.text == from => Patch.replaceToken(tok, to) }.asPatch

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
}
