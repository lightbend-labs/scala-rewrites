import _root_.scalafix.sbt.BuildInfo._
import sbt.librarymanagement.Configurations.CompilerPlugin

def scalametaVersion = "4.5.13"

inThisBuild(List(
  organization := "org.scala-lang",
  licenses := List("Apache-2.0" -> url("https://www.apache.org/licenses/LICENSE-2.0")),
  developers := List(Developer("", "", "", url("https://github.com/scala/scala-rewrites/graphs/contributors"))),
  homepage := Some(url("https://github.com/scala/scala-rewrites")),
  scalaVersion := (sys.env.get("CI_SCALA_VERSION") match {
    case Some("2.13")      => scala213
    case Some("2.12")      => scala212
    case Some("2.12.next") => scala212 // and then overriden by ScalaNightlyPlugin
    case None              => scala212
    case tsv               => sys.error(s"Unknown CI_SCALA_VERSION $tsv")
  }),
  scalacOptions ++= List("-deprecation", "-feature", "-language:_", "-unchecked", "-Xlint"),
  crossScalaVersions := Seq(scala212, scala213),
  publish / skip := true,
))

val rewrites = project.enablePlugins(ScalaNightlyPlugin).settings(
  moduleName := "scala-rewrites",
  libraryDependencies += "ch.epfl.scala" %% "scalafix-rules" % scalafixVersion,
  publish / skip := false,
)

val input = project.enablePlugins(ScalaNightlyPlugin).settings(
  scalacOptions ++= List("-Yrangepos", "-P:semanticdb:synthetics:on"),
  libraryDependencies += "org.scalameta" % "semanticdb-scalac" % scalametaVersion % CompilerPlugin cross CrossVersion.patch,
)

val output = project

// This project is used to verify that the output code actually compiles with scala 2.13
val output213 = output.withId("output213").settings(
  target := file(s"${target.value.getPath}-2.13"),
  scalaVersion := scala213,
  crossScalaVersions := Seq(scalaVersion.value),
)

val tests = project.dependsOn(rewrites).enablePlugins(ScalaNightlyPlugin, ScalafixTestkitPlugin).settings(
  libraryDependencies += "ch.epfl.scala" % "scalafix-testkit" % scalafixVersion % Test cross CrossVersion.patch,
  Compile / compile := (Compile / compile).dependsOn(input / Test / compile).value,
  scalafixTestkitInputClasspath          := ( input / Test / fullClasspath).value,
  scalafixTestkitInputSourceDirectories  := ( input / Test / sourceDirectories).value,
  scalafixTestkitOutputSourceDirectories := (output / Test / sourceDirectories).value,
  ScalaNightlyPlugin.ifNightly(Test / fork := true),
)

ScalaNightlyPlugin.bootstrapSettings
