package impl

import scala.meta._

import scalafix.v1._

object SignatureMatcher {
  def exact(symbols: String*): SignatureMatcher =
    new SignatureMatcher(SymbolMatcher.exact(symbols: _*))
}

final class SignatureMatcher(symbolMatcher: SymbolMatcher) {
  def unapply(tree: Tree)(implicit sdoc: SemanticDocument): Option[Tree] =
    tree.symbol.info.map(_.signature).collect {
      case ValueSignature(TypeRef(_, symbolMatcher(_), _)) => tree
    }
}
