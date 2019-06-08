package fix.scala213

import impl.CollectingTraverser
import metaconfig.generic.Surface
import metaconfig.{ConfDecoder, Configured}
import scalafix.v1._
import scala.meta._

final class ScalaSeq(val config: ScalaSeqConfig) extends SemanticRule("fix.scala213.ScalaSeq") {
  def this() = this(ScalaSeqConfig())

  override def withConfiguration(config: Configuration): Configured[Rule] = {
    // the following don't seem to work:
    //  - getOrElse("fix.scala213.ScalaSeq")
    //  - getOrElse("fix", "scala213", "ScalaSeq")
    config.conf.dynamic.fix.scala213.asConf
      .andThen(_.getOrElse("ScalaSeq")(this.config))
      .map(new ScalaSeq(_))
  }

  override def fix(implicit doc: SemanticDocument): Patch =
    new ScalaSeq.Traverser(config).rewrite(doc.tree)
}

object ScalaSeq {
  private val scalaSeq = SymbolMatcher.exact("scala/package.Seq#")

  final class Traverser(config: ScalaSeqConfig)(implicit doc: SemanticDocument)
      extends CollectingTraverser
  {
    private var inParam = false

    private def toImporter(s: String) = if (s.isEmpty) None else Some(s.parse[Importer].get)

    val paramImport: Option[Importer] = toImporter(config.paramImport)
    val otherImport: Option[Importer] = toImporter(config.otherImport)

    override def apply(tree: Tree): Unit = tree match {
      case p: Term.Param =>
        inParam = true
        apply(p.decltpe)
        inParam = false
        apply(p.default)

      case scalaSeq(Type.Apply(t, _)) =>
        val sub = if (inParam) {
          paramImport.foreach(globalImports.add)
          config.paramType
        } else {
          otherImport.foreach(globalImports.add)
          config.otherType
        }
        patches += Patch.replaceTree(t, sub)

      case _ => super.apply(tree)
    }
  }
}

final case class ScalaSeqConfig(
    paramType: String = "collection.Seq",
    paramImport: String = "scala.collection",
    otherType: String = "immutable.Seq",
    otherImport: String = "scala.collection.immutable",
)

object ScalaSeqConfig {
  implicit val surface: Surface[ScalaSeqConfig] = metaconfig.generic.deriveSurface[ScalaSeqConfig]
  implicit val decoder: ConfDecoder[ScalaSeqConfig] = metaconfig.generic.deriveDecoder(new ScalaSeqConfig())
}
