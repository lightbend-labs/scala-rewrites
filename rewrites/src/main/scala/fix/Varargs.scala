package fix

import scalafix.v1._

import scala.meta._

object Varargs {
  val scSeq = new SignatureMatcher(SymbolMatcher.exact("scala/collection/Seq#"))
}

final class Varargs extends SemanticRule("Varargs") {
  import Varargs._

  override def fix(implicit doc: SemanticDocument): Patch = {
    doc.tree.collect {
      case Term.Repeated(scSeq(expr)) => Patch.addRight(expr, ".toSeq")
    }.asPatch
  }
}

final class SignatureMatcher(symbolMatcher: SymbolMatcher) {
  def unapply(tree: Tree)(implicit sdoc: SemanticDocument): Option[Tree] =
    tree.symbol.info.map(_.signature).collect {
      case ValueSignature(TypeRef(_, symbolMatcher(_), _)) => tree
    }
}