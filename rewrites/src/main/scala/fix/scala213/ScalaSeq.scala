package fix.scala213

import metaconfig.generic.Surface
import metaconfig.{ConfDecoder, Configured}

import scala.meta._

import scalafix.v1._

final class ScalaSeq(val config: ScalaSeq.Config) extends SemanticRule("fix.scala213.ScalaSeq") {
  def this() = this(ScalaSeq.Config())
  import ScalaSeq._

  override def withConfiguration(config: Configuration): Configured[Rule] = {
    // the following don't seem to work:
    //  - getOrElse("fix.scala213.ScalaSeq")
    //  - getOrElse("fix", "scala213", "ScalaSeq")
    config.conf.dynamic.fix.scala213.asConf
      .andThen(_.getOrElse("ScalaSeq")(this.config))
      .map(new ScalaSeq(_))
  }

  override def fix(implicit doc: SemanticDocument) = new impl.CollectingTraverser {
    private var inParam = false

    private val paramImport: Option[Importer] = toImporter(config.paramImport)
    private val otherImport: Option[Importer] = toImporter(config.otherImport)
    private def toImporter(s: String)         = if (s.isEmpty) None else Some(s.parse[Importer].get)

    override def apply(tree: Tree): Unit = tree match {
      case p: Term.Param =>
        inParam = true
        apply(p.decltpe)
        inParam = false
        apply(p.default)

      case scalaSeq(Type.Apply(t, _)) =>
        val sub = if (inParam) {
          paramImport.foreach(i => patch += globalImports.add(i))
          config.paramType
        } else {
          otherImport.foreach(i => patch += globalImports.add(i))
          config.otherType
        }
        patch += Patch.replaceTree(t, sub)

      case _ =>
        super.apply(tree)
    }
  }.run(doc.tree)
}

object ScalaSeq {
  private val scalaSeq = SymbolMatcher.exact("scala/package.Seq#")

  final case class Config(
      paramType: String   = "collection.Seq",
      otherType: String   = "immutable.Seq",
      paramImport: String = "scala.collection",
      otherImport: String = "scala.collection.immutable",
  )

  object Config {
    implicit val surface: Surface[Config]     = metaconfig.generic.deriveSurface[Config]
    implicit val decoder: ConfDecoder[Config] = metaconfig.generic.deriveDecoder(new Config())
  }
}
