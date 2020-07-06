package impl

import scala.meta._
import scala.meta.internal.pc.ScalafixGlobal

import scalafix.v1._
import scalafix.internal.rule.CompilerException

/** Necessary b/c Scalafix/Scalameta don't maintain any overriding info. */
// From scalafix-rules/src/main/scala/scalafix/internal/rule/CompilerTypePrinter.scala#L55
// in scalacenter/scalafix (at db93793c2a8f80d0e7bbcacef729dc5c2fa7ceb8).
final class Power(g: ScalafixGlobal)(implicit doc: SemanticDocument) {
  def isJavaDefined(t: Tree): Boolean = try {
    gsymbol(t).overrideChain.exists(sym => sym.isJavaDefined || sym.owner == g.definitions.AnyClass)
  } catch {
    case e: Throwable => throw CompilerException(e)
  }

  private def gsymbol(t: Tree): g.Symbol = {
    val sSym = t.symbol
    val gSymbols = g.inverseSemanticdbSymbols(sSym.value)
    gSymbols.find(gSym => g.semanticdbSymbol(gSym) == sSym.value).getOrElse(g.NoSymbol)
  }
}
