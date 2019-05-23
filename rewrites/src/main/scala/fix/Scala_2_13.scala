package fix

import impl.Substitutions
import scalafix.v1._

final class Scala_2_13 extends SemanticRule("fix.Scala_2_13") {
  override def fix(implicit doc: SemanticDocument): Patch = {
    val subs = new Substitutions
    val toRun = {
      import subs._
      List(platfromEOL, platformCurrentTime, platformArraycopy, consoleRead, unicodeArrows, symbolLiteral)
    }
    subs.rewrite(doc.tree, toRun)
  }
}
