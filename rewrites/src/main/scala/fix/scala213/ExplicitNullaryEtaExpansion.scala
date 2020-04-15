package fix.scala213

import scala.meta._

import scalafix.v1._

final class ExplicitNullaryEtaExpansion extends SemanticRule("fix.scala213.ExplicitNullaryEtaExpansion") {
  override def fix(implicit doc: SemanticDocument) = {
    doc.tree.collect { case eta: Term.Eta =>
      eta.expr.symbol.info.map(_.signature).collect {
        case MethodSignature(_, Nil, _) =>
          val etaSuffix = eta.tokens.takeRight(eta.pos.end - eta.expr.pos.end) // the " _" after eta's expr
          Patch.addLeft(eta, "() => ") + Patch.removeTokens(etaSuffix)
      }.asPatch
    }.asPatch
  }
}
