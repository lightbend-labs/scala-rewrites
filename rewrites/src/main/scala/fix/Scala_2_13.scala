package fix

import scalafix.v1._
import scala.annotation.tailrec
import scala.meta._

final class Scala_2_13 extends SemanticRule("Scala_2_13") {
  override def fix(implicit doc: SemanticDocument): Patch = {
    val EOL = SymbolMatcher.exact("scala/compat/Platform.EOL.")
    val currentTime = SymbolMatcher.exact("scala/compat/Platform.currentTime().")
    val arraycopy = SymbolMatcher.exact("scala/compat/Platform.arraycopy().")
    val sysError = SymbolMatcher.exact("scala/sys/package.error().")
    val sysExit = SymbolMatcher.exact("scala/sys/package.exit(+1).")
    val sysExit0 = SymbolMatcher.exact("scala/sys/package.exit().")
    val sysProps = SymbolMatcher.exact("scala/sys/package.props().")
    val sysPropsGet = SymbolMatcher.exact("scala/sys/SystemProperties#get().")
    val sysEnv = SymbolMatcher.exact("scala/sys/package.env().")
    val mapLikeGet = SymbolMatcher.exact("scala/collection/MapLike#get().")
    val sysRuntime = SymbolMatcher.exact("scala/sys/package.runtime().")
    val sysAddShutdownHook = SymbolMatcher.exact("scala/sys/package.addShutdownHook().")
    val console = SymbolMatcher.exact("scala/Console.")
    val ansiColorBlack      = new TermSelectOrName(console, "scala/io/AnsiColor#BLACK.")
    val ansiColorRed        = new TermSelectOrName(console, "scala/io/AnsiColor#RED.")
    val ansiColorGreen      = new TermSelectOrName(console, "scala/io/AnsiColor#GREEN.")
    val ansiColorYellow     = new TermSelectOrName(console, "scala/io/AnsiColor#YELLOW.")
    val ansiColorBlue       = new TermSelectOrName(console, "scala/io/AnsiColor#BLUE.")
    val ansiColorMagenta    = new TermSelectOrName(console, "scala/io/AnsiColor#MAGENTA.")
    val ansiColorCyan       = new TermSelectOrName(console, "scala/io/AnsiColor#CYAN.")
    val ansiColorWhite      = new TermSelectOrName(console, "scala/io/AnsiColor#WHITE.")
    val ansiColorBlackB     = new TermSelectOrName(console, "scala/io/AnsiColor#BLACK_B.")
    val ansiColorRedB       = new TermSelectOrName(console, "scala/io/AnsiColor#RED_B.")
    val ansiColorGreenB     = new TermSelectOrName(console, "scala/io/AnsiColor#GREEN_B.")
    val ansiColorYellowB    = new TermSelectOrName(console, "scala/io/AnsiColor#YELLOW_B.")
    val ansiColorBlueB      = new TermSelectOrName(console, "scala/io/AnsiColor#BLUE_B.")
    val ansiColorMagentaB   = new TermSelectOrName(console, "scala/io/AnsiColor#MAGENTA_B.")
    val ansiColorCyanB      = new TermSelectOrName(console, "scala/io/AnsiColor#CYAN_B.")
    val ansiColorWhiteB     = new TermSelectOrName(console, "scala/io/AnsiColor#WHITE_B.")
    val ansiColorReset      = new TermSelectOrName(console, "scala/io/AnsiColor#RESET.")
    val ansiColorBold       = new TermSelectOrName(console, "scala/io/AnsiColor#BOLD.")
    val ansiColorUnderlined = new TermSelectOrName(console, "scala/io/AnsiColor#UNDERLINED.")
    val ansiColorBlink      = new TermSelectOrName(console, "scala/io/AnsiColor#BLINK.")
    val ansiColorReversed   = new TermSelectOrName(console, "scala/io/AnsiColor#REVERSED.")
    val ansiColorInvisible  = new TermSelectOrName(console, "scala/io/AnsiColor#INVISIBLE.")

    val deprecatedConsoleReadBoolean = SymbolMatcher.exact("scala/DeprecatedConsole#readBoolean().")
    val deprecatedConsoleReadByte    = SymbolMatcher.exact("scala/DeprecatedConsole#readByte().")
    val deprecatedConsoleReadChar    = SymbolMatcher.exact("scala/DeprecatedConsole#readChar().")
    val deprecatedConsoleReadDouble  = SymbolMatcher.exact("scala/DeprecatedConsole#readDouble().")
    val deprecatedConsoleReadFloat   = SymbolMatcher.exact("scala/DeprecatedConsole#readFloat().")
    val deprecatedConsoleReadInt     = SymbolMatcher.exact("scala/DeprecatedConsole#readInt().")
    val deprecatedConsoleReadLine    = SymbolMatcher.exact("scala/DeprecatedConsole#readLine().")
    val deprecatedConsoleReadLine1   = SymbolMatcher.exact("scala/DeprecatedConsole#readLine(+1).")
    val deprecatedConsoleReadLong    = SymbolMatcher.exact("scala/DeprecatedConsole#readLong().")
    val deprecatedConsoleReadShort   = SymbolMatcher.exact("scala/DeprecatedConsole#readShort().")
    val deprecatedConsoleReadf       = SymbolMatcher.exact("scala/DeprecatedConsole#readf().")
    val deprecatedConsoleReadf1      = SymbolMatcher.exact("scala/DeprecatedConsole#readf1().")
    val deprecatedConsoleReadf2      = SymbolMatcher.exact("scala/DeprecatedConsole#readf2().")
    val deprecatedConsoleReadf3      = SymbolMatcher.exact("scala/DeprecatedConsole#readf3().")

    val handled = scala.collection.mutable.Set.empty[Tree]

    val globalImports = scala.collection.mutable.Set.empty[String]
    def addGlobalImport(importer: Importer) = {
      if (globalImports.add(importer.structure))
        Patch.addGlobalImport(importer)
      else
        Patch.empty
    }

    def fixI(interpolating: Boolean): PartialFunction[Tree, Patch] = {
      case Term.Interpolate(_, _, args) => args.collect(fixI(true)).asPatch

      case EOL(i: Importee) => Patch.removeImportee(i)
      case EOL(t: Term) =>
        recordTermHandled(t)
        val replacement = t match {
          case _: Term.Name if interpolating => "{System.lineSeparator}"
          case _                             => "System.lineSeparator"
        }
        Patch.replaceTree(t, replacement)

      case currentTime(i: Importee) => Patch.removeImportee(i)
      case currentTime(t: Term) =>
        recordTermHandled(t)
        Patch.replaceTree(t, "System.currentTimeMillis")

      case arraycopy(i: Importee) => Patch.removeImportee(i)
      case arraycopy(Term.Apply(t, _)) =>
        recordTermHandled(t)
        Patch.replaceTree(t, "System.arraycopy")

      case sysError(i: Importee) => Patch.removeImportee(i)
      case sysError(Term.Apply(t, _)) =>
        recordTermHandled(t)
        Patch.replaceTree(t, "throw new RuntimeException")

      case sysExit(i: Importee) => Patch.removeImportee(i)
      case sysExit(out @ Term.Apply(t, _)) =>
        recordTermHandled(t)
        Patch.replaceTree(t, "{ System.exit") + Patch.addRight(out, "; throw new Throwable }")
      case sysExit0(t: Term.Apply) =>
        recordTermHandled(t)
        Patch.replaceTree(t, "{ System.exit(0); throw new Throwable }")

      case sysProps(i: Importee) => Patch.removeImportee(i)
      case sysProps(Term.Apply(t, _)) =>
        recordTermHandled(t)
        Patch.replaceTree(t, "System.getProperty")
      case sysProps(t: Term) =>
        recordTermHandled(t)
        Patch.replaceTree(t, "System.getProperties.asScala") +
          addGlobalImport(importer"scala.collection.JavaConverters._")
      case sysPropsGet(out @ Term.Apply(t @ Term.Select(sysProps(in), _), _)) =>
        recordTermHandled(in)
        Patch.replaceTree(t, "Option(System.getProperty") + Patch.addRight(out, ")")

      case sysEnv(i: Importee) => Patch.removeImportee(i)
      case sysEnv(Term.Apply(t, _)) =>
        recordTermHandled(t)
        Patch.replaceTree(t, "System.getenv.asScala.apply") +
          addGlobalImport(importer"scala.collection.JavaConverters._")
      case sysEnv(t: Term) =>
        recordTermHandled(t)
        Patch.replaceTree(t, "System.getenv.asScala.toMap") +
          addGlobalImport(importer"scala.collection.JavaConverters._")
      case mapLikeGet(out @ Term.Apply(t @ Term.Select(sysEnv(in), _), _)) =>
        recordTermHandled(in)
        Patch.replaceTree(t, "Option(System.getenv") + Patch.addRight(out, ")")

      case sysRuntime(i: Importee) => Patch.removeImportee(i)
      case sysRuntime(t: Term) =>
        recordTermHandled(t)
        Patch.replaceTree(t, "Runtime.getRuntime")

      case sysAddShutdownHook(i: Importee) => Patch.removeImportee(i)
      case sysAddShutdownHook(out @ Term.Apply(t, _)) =>
        recordTermHandled(t)
        Patch.replaceTree(t, "Runtime.getRuntime.addShutdownHook(new Thread(() => ") +
          Patch.addRight(out, "))")

      case ansiColorBlack(t)      => colourReplace(interpolating, t, "BLACK")
      case ansiColorRed(t)        => colourReplace(interpolating, t, "RED")
      case ansiColorGreen(t)      => colourReplace(interpolating, t, "GREEN")
      case ansiColorYellow(t)     => colourReplace(interpolating, t, "YELLOW")
      case ansiColorBlue(t)       => colourReplace(interpolating, t, "BLUE")
      case ansiColorMagenta(t)    => colourReplace(interpolating, t, "MAGENTA")
      case ansiColorCyan(t)       => colourReplace(interpolating, t, "CYAN")
      case ansiColorWhite(t)      => colourReplace(interpolating, t, "WHITE")
      case ansiColorBlackB(t)     => colourReplace(interpolating, t, "BLACK_B")
      case ansiColorRedB(t)       => colourReplace(interpolating, t, "RED_B")
      case ansiColorGreenB(t)     => colourReplace(interpolating, t, "GREEN_B")
      case ansiColorYellowB(t)    => colourReplace(interpolating, t, "YELLOW_B")
      case ansiColorBlueB(t)      => colourReplace(interpolating, t, "BLUE_B")
      case ansiColorMagentaB(t)   => colourReplace(interpolating, t, "MAGENTA_B")
      case ansiColorCyanB(t)      => colourReplace(interpolating, t, "CYAN_B")
      case ansiColorWhiteB(t)     => colourReplace(interpolating, t, "WHITE_B")
      case ansiColorReset(t)      => colourReplace(interpolating, t, "RESET")
      case ansiColorBold(t)       => colourReplace(interpolating, t, "BOLD")
      case ansiColorUnderlined(t) => colourReplace(interpolating, t, "UNDERLINED")
      case ansiColorBlink(t)      => colourReplace(interpolating, t, "BLINK")
      case ansiColorReversed(t)   => colourReplace(interpolating, t, "REVERSED")
      case ansiColorInvisible(t)  => colourReplace(interpolating, t, "INVISIBLE")

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

      case t: Case => {
        t.tokens.collect {
          case t: Token.RightArrow if t.text == "⇒" => Patch.replaceToken(t, "=>")
        }.asPatch
      }
      case t: Type.Function => {
        t.tokens.collect {
          case t: Token.RightArrow if t.text == "⇒" => Patch.replaceToken(t, "=>")
        }.asPatch
      }
      case t: Importee => {
        t.tokens.collect {
          case t: Token.RightArrow if t.text == "⇒" => Patch.replaceToken(t, "=>")
        }.asPatch
      }

      case t @ Lit.Symbol(sym) => {
        Patch.replaceTree(t, s"""Symbol("${sym.name}")""")
      }
    }

    def colourReplace(interpolating: Boolean, tree: Tree, name: String) = {
      recordTermHandled(tree)
      val replacement = tree match {
        case _: Term.Name if interpolating => s"{AnsiColor.$name}"
        case _                             => s"AnsiColor.$name"
      }
      Patch.replaceTree(tree, replacement) + addGlobalImport(importer"scala.io.AnsiColor")
    }

    def stdInReplace(tree: Tree, name: String) = {
      recordTermHandled(tree)
      Patch.replaceTree(tree, s"StdIn.$name") + addGlobalImport(importer"scala.io.StdIn")
    }

    @tailrec def recordTermHandled(t: Tree): Unit = {
      handled += t
      t match {
        case Term.Select(_, n) => recordTermHandled(n)
        case _                 => ()
      }
    }

    doc.tree.collect(new Combined({ case t if !handled(t) => t }, fixI(false))).asPatch
  }
}

private class TermSelectOrName(qual: SymbolMatcher, name: String) {
  val nameSymbolMatcher = SymbolMatcher.exact(name)

  final def unapply(term: Term)(implicit sdoc: SemanticDocument): Option[Term] = term match {
    case nameSymbolMatcher(t @ Term.Select(qual(_), _)) => Some(t)
    case nameSymbolMatcher(t @ Term.Name(_))            => Some(t)
    case _                                              => None
  }
}

private object Combined {
  private[this] val mockDefaultFunc: Any => Any = _ => mockDefaultFunc
  private def mockDefaultFunction[B] = mockDefaultFunc.asInstanceOf[Any => B]
  private def fallbackOccurred[B](x: B) = mockDefaultFunc eq x.asInstanceOf[AnyRef]
}

private final class Combined[-A, B, +C] (pf: PartialFunction[A, B], k: PartialFunction[B, C]) extends PartialFunction[A, C] {
  import Combined._

  def isDefinedAt(x: A): Boolean = {
    val b = pf.applyOrElse(x, mockDefaultFunction)
    !fallbackOccurred(b) || k.isDefinedAt(b)
  }

  def apply(x: A): C = k(pf(x))

  override def applyOrElse[A1 <: A, C1 >: C](x: A1, default: A1 => C1): C1 = {
    val pfv = pf.applyOrElse(x, mockDefaultFunction)
    if (fallbackOccurred(pfv)) default(x) else k.applyOrElse(pfv, (_: B) => default(x))
  }
}
