import _root_.scalafix.sbt.BuildInfo._
import sbt.librarymanagement.Configurations.CompilerPlugin

def scala213 = "2.13.3"
def scalametaVersion = "4.3.17"

inThisBuild(List(
  organization := "org.scala-lang",
  licenses := List("Apache-2.0" -> url("https://www.apache.org/licenses/LICENSE-2.0")),
  developers := List(Developer("", "", "", url("https://github.com/scala/scala-rewrites/graphs/contributors"))),
  homepage := Some(url("https://github.com/scala/scala-rewrites")),
  scalaVersion := scala212,
  skip in publish := true,
))

val rewrites = project.enablePlugins(ScalaNightlyPlugin).settings(
  moduleName := "scala-rewrites",
  libraryDependencies += "ch.epfl.scala" %% "scalafix-rules" % scalafixVersion,
  skip in publish := false,
)

val input = project.enablePlugins(ScalaNightlyPlugin).settings(
  scalacOptions ++= List("-Yrangepos", "-P:semanticdb:synthetics:on"),
  libraryDependencies += "org.scalameta" % "semanticdb-scalac" % scalametaVersion % CompilerPlugin cross CrossVersion.patch,
)

val output    = project
val output213 = output.withId("output213").settings(
  target := file(s"${target.value.getPath}-2.13"),
  scalaVersion := scala213,
)

val tests = project.dependsOn(rewrites).enablePlugins(ScalaNightlyPlugin, ScalafixTestkitPlugin).settings(
  libraryDependencies += "ch.epfl.scala" % "scalafix-testkit" % scalafixVersion % Test cross CrossVersion.patch,
  compile in Compile := (compile in Compile).dependsOn(compile in (input, Compile)).value,
  scalafixTestkitOutputSourceDirectories := (sourceDirectories in (output, Compile)).value,
  scalafixTestkitInputSourceDirectories := (sourceDirectories in (input, Compile)).value,
  scalafixTestkitInputClasspath := (fullClasspath in (input, Compile)).value,
  ScalaNightlyPlugin.ifNightly(Test / fork := true),
)

ScalaNightlyPlugin.bootstrapSettings
