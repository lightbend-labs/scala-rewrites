package lint

import impl.Substitutions._
import scalafix.v1._

import scala.meta._

class Scala_2_13 extends SemanticRule("lint.Scala_2_13") {
  override def fix(implicit doc: SemanticDocument): Patch = {
    val substitutions: PartialFunction[Tree, Patch] = lintPF(List(
      unicodeArrows, symbolLiteral))

    doc.tree.collect(substitutions).asPatch
  }
}
