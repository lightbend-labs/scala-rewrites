# Scalafix Rewrites for Scala

## How to run the rewrites

Add the `sbt-scalafix` sbt plugin, with the SemanticDB compiler plugin enabled ([official docs][1]):

```scala
// project/plugins.sbt
addSbtPlugin("ch.epfl.scala" % "sbt-scalafix" % "0.11.0")
```

```scala
// build.sbt
inThisBuild(List(
  semanticdbEnabled := true,
  semanticdbOptions += "-P:semanticdb:synthetics:on", // make sure to add this
  semanticdbVersion := scalafixSemanticdb.revision,
  scalafixScalaBinaryVersion := CrossVersion.binaryScalaVersion(scalaVersion.value),
))
```

Then run the desired rewrite(s) ([official docs][2]), in sbt:

```scala
> scalafixAll dependency:fix.scala213.ExplicitNonNullaryApply@org.scala-lang:scala-rewrites:<version>
```

You can also add the following to your `build.sbt`:

```scala
ThisBuild / scalafixDependencies += "org.scala-lang" %% "scala-rewrites" % "<version>"
```

and then:

```scala
> scalafixAll fix.scala213.ExplicitNonNullaryApply
```

[1]: https://scalacenter.github.io/scalafix/docs/users/installation.html
[2]: https://scalacenter.github.io/scalafix/docs/rules/external-rules.html

## To develop/contribute to any of the rewrites

```
sbt ~tests/test
# edit rewrites/src/main/scala/...
```
