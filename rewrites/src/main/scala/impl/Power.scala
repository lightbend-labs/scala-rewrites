package impl

import scala.meta._
import scala.meta.internal.pc.ScalafixGlobal
import scala.meta.internal.proxy.GlobalProxy

import scalafix.v1._
import scalafix.internal.rule.CompilerException

/** Necessary b/c Scalafix/Scalameta don't maintain any overriding info. */
// From scalafix-rules/src/main/scala/scalafix/internal/rule/CompilerTypePrinter.scala#L55
// in scalacenter/scalafix (at db93793c2a8f80d0e7bbcacef729dc5c2fa7ceb8).
final class Power(g: ScalafixGlobal)(implicit doc: SemanticDocument) {
  private lazy val unit = g.newCompilationUnit(doc.input.text, doc.input.syntax)

  def isJavaDefined(t: Tree): Boolean = try {
    val meth = gsymbol(t)
    isJavaDefined1(meth) || meth.overrides.exists(isJavaDefined1)
  } catch {
    case e: Throwable => throw CompilerException(e)
  }

  private def isJavaDefined1(sym: g.Symbol) =
    sym.isJavaDefined || sym.owner == g.definitions.AnyClass

  private def gsymbol(t: Tree): g.Symbol = {
    GlobalProxy.typedTreeAt(g, unit.position(t.pos.start))

    val sym = g
      .inverseSemanticdbSymbols(t.symbol.value)
      .find(t.symbol.value == g.semanticdbSymbol(_))
      .getOrElse(g.NoSymbol)

    if (sym.info.exists(g.definitions.NothingTpe == _))
      sym.overrides.lastOption.getOrElse(sym)
    else sym
  }
}
