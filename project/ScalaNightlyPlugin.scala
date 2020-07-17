import sbt._, Keys._

object ScalaNightlyPlugin extends AutoPlugin {
  val BinCompatV = """(\d+)\.(\d+)\.(\d+)(-\w+)??(-bin-.*)?""".r
  val isScalaNightly = sys.env.get("TRAVIS_SCALA_VERSION").contains("2.12.next")
  def ifNightly(ss: SettingsDefinition*) = if (isScalaNightly) Def.settings(ss: _*) else Nil

  override def buildSettings = ifNightly(
    resolvers += "scala-integration" at "https://scala-ci.typesafe.com/artifactory/scala-integration/",
  )

  def bootstrapSettings = ifNightly(
    scalaVersion := {
      val props = new java.util.Properties
      val url1 = url("https://raw.github.com/scala/community-build/2.12.x/nightly.properties")
      sbt.io.Using.urlInputStream(url1)(props.load(_))
      val sv = props.getProperty("nightly").ensuring(_ != null)
      sLog.value.info(s"Using Scala nightly version $sv")
      sv
    }
  )

  override def projectSettings = ifNightly(
    scalaVersion := ((LocalRootProject / scalaVersion).value match {
    //case BinCompatV(x, y, z, _, _) => s"$x.$y.${z.toInt - 1}" // Restore when the 2.12 CB is bumped
      case BinCompatV(x, y, z, _, _) => s"$x.$y.$z"
      case sv                        => sv
    }),
    scalaInstance := {
      val si = (LocalRootProject / scalaInstance).value
      new sbt.internal.inc.ScalaInstance(
        scalaVersion.value,
        si.loader, si.loaderLibraryOnly,
        si.libraryJars, si.compilerJar, si.allJars,
        si.explicitActual
      )
    }
  )
}
