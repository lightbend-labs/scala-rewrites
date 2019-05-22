package fix.scala213

import metaconfig.ConfDecoder
import metaconfig.generic.Surface

case class ScalaSeqConfig(
    paramType: String = "collection.Seq",
    paramImport: String = "scala.collection",
    otherType: String = "immutable.Seq",
    otherImport: String = "scala.collection.immutable")

object ScalaSeqConfig {
  implicit val surface: Surface[ScalaSeqConfig] = metaconfig.generic.deriveSurface[ScalaSeqConfig]
  implicit val decoder: ConfDecoder[ScalaSeqConfig] = metaconfig.generic.deriveDecoder(new ScalaSeqConfig())
}
