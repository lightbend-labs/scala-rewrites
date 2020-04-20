import _root_.scalafix.sbt.BuildInfo.scalafixVersion
val scalaBV = "2.12.11"

inThisBuild(List(
  organization := "org.scala-lang",
  licenses := List("Apache-2.0" -> url("https://www.apache.org/licenses/LICENSE-2.0")),
  developers := List(Developer("", "", "", url("https://github.com/scala/scala-rewrites/graphs/contributors"))),
  homepage := Some(url("https://github.com/scala/scala-rewrites")),
  scalaVersion := "2.12.12-bin-085b6cf",
  resolvers += "scala-ea-builds" at "https://scala-ci.typesafe.com/artifactory/scala-integration",
))

skip in publish := true

val rewrites = project.settings(
  moduleName := "scala-rewrites",
  libraryDependencies += "ch.epfl.scala" %% "scalafix-core" % scalafixVersion,
  libraryDependencies += "org.scalameta" %% "scalameta"     % "4.3.8",
)

val input = project.settings(
  libraryDependencies += compilerPlugin("org.scalameta" % s"semanticdb-scalac_$scalaBV" % "4.3.8"),
  scalacOptions ++= List("-Yrangepos", "-P:semanticdb:synthetics:on"),
  skip in publish := true,
)

val output = project.settings(skip in publish := true)

val output213 = output.withId("output213").settings(
  target := (target.value / "../target-2.13").getCanonicalFile,
  scalaVersion := "2.13.0",
)

val tests = project.dependsOn(rewrites).enablePlugins(ScalafixTestkitPlugin).settings(
  skip in publish := true,
  libraryDependencies += "ch.epfl.scala" % s"scalafix-testkit_$scalaBV" % scalafixVersion % Test excludeAll(ExclusionRule("org.scalameta"), ExclusionRule("ch.epfl.scala")),
  libraryDependencies += "ch.epfl.scala" % s"scalafix-reflect_$scalaBV" % scalafixVersion % Test excludeAll(ExclusionRule("org.scalameta"), ExclusionRule("ch.epfl.scala")),
  compile in Compile := (compile in Compile).dependsOn(compile in (input, Compile)).value,
  scalafixTestkitOutputSourceDirectories := (sourceDirectories in (output, Compile)).value,
  scalafixTestkitInputSourceDirectories := (sourceDirectories in (input, Compile)).value,
  scalafixTestkitInputClasspath := (fullClasspath in (input, Compile)).value,
)
