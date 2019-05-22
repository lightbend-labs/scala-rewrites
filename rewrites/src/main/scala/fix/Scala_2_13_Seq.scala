package fix

import impl.Traversals.SeqTraverser
import scalafix.v1._

final class Scala_2_13_Seq extends SemanticRule("fix.Scala_2_13_Seq") {
  override def fix(implicit doc: SemanticDocument): Patch =
    new SeqTraverser(doc).rewrite(doc.tree)
}
