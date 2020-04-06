package fix.scala213

import scala.meta._

import scalafix.v1._

final class NullaryHashHash extends SemanticRule("fix.scala213.NullaryHashHash") {
  private val HashHash = SymbolMatcher.exact("scala/Any#`##`().", "java/lang/Object#`##`().")

  override def fix(implicit doc: SemanticDocument) = {
    doc.tree.collect {
      case expr @ Term.Apply(HashHash(fun), Nil) =>
        Patch.removeTokens(expr.tokens.takeRight(expr.pos.end - fun.pos.end))
    }.asPatch
  }
}
