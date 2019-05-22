package fix.scala213

import impl.Traversals.SeqTraverser
import metaconfig.Configured
import scalafix.v1._

final class ScalaSeq(val config: ScalaSeqConfig) extends SemanticRule("fix.scala213.ScalaSeq") {
  def this() = this(ScalaSeqConfig())

  override def withConfiguration(config: Configuration): Configured[Rule] = {
    // the following don't seem to work:
    //  - getOrElse("fix.scala213.ScalaSeq")
    //  - getOrElse("fix", "scala213", "ScalaSeq")
    config.conf.dynamic.fix.scala213.asConf.get
      .getOrElse("ScalaSeq")(this.config)
      .map(new ScalaSeq(_))
  }

  override def fix(implicit doc: SemanticDocument): Patch =
    new SeqTraverser(doc, config).rewrite(doc.tree)
}
