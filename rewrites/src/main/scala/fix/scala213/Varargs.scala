package fix.scala213

import scala.meta._

import scalafix.v1._

import impl.SignatureMatcher

object Varargs {
  val scSeq = SignatureMatcher.exact("scala/collection/Seq#")
}; import Varargs._

final class Varargs extends SemanticRule("fix.scala213.Varargs") {
  override def fix(implicit doc: SemanticDocument): Patch =
    doc.tree.collect {
      case Term.Repeated(scSeq(expr)) => Patch.addRight(expr, ".toSeq")
    }.asPatch
}
