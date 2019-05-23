package fix.scala213

import impl.Substitutions
import scalafix.v1._

final class Core extends SemanticRule("fix.scala213.Core") {
  override def fix(implicit doc: SemanticDocument): Patch = {
    val subs = new Substitutions
    val toRun = {
      import subs._
      List(platfromEOL, platformCurrentTime, platformArraycopy, consoleRead, unicodeArrows, symbolLiteral)
    }
    subs.rewrite(doc.tree, toRun)
  }
}
