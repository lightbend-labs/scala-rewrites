package impl

import scala.meta._
import scala.meta.internal.pc.ScalafixGlobal
import scala.meta.internal.proxy.GlobalProxy

import scalafix.v1._
import scalafix.internal.rule.CompilerException

final class Power(g: ScalafixGlobal)(implicit doc: SemanticDocument) {
  private lazy val unit = g.newCompilationUnit(doc.input.text, doc.input.syntax)

  def isJavaDefined(t: Tree): Boolean =
    try isJavaDefinedUnsafe(gsymbol(t)) catch {
      case e: Throwable => throw CompilerException(e)
    }

  private def isJavaDefinedUnsafe(meth: g.Symbol) = {
    def test(sym: g.Symbol) = sym.isJavaDefined || sym.owner == g.definitions.AnyClass
    test(meth) || meth.overrides.exists(test)
  }

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
