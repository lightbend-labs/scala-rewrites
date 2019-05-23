package lint

import impl.Substitutions
import scalafix.v1._

class Scala_2_13 extends SemanticRule("lint.Scala_2_13") {
  override def fix(implicit doc: SemanticDocument): Patch = {
    val subs = new Substitutions
    val toRun = {
      import subs._
      List(platfromEOL, unicodeArrows, symbolLiteral)
    }
    subs.lint(doc.tree, toRun)
  }
}
