# Scalafix Rewrites for Scala

## To develop the rule

```
sbt ~tests/test
# edit rewrites/src/main/scala/fix/Scala213.scala
```

## To run the rule

0. Publish the rule: `publishLocal`
1. Add sbt-scalafix: `addSbtPlugin("ch.epfl.scala" % "sbt-scalafix" % "0.9.5")`
2. Add SemanticDB: `addCompilerPlugin(scalafixSemanticdb)`
3. Configure Semantic DB: `scalacOptions ++= List("-Yrangepos", "-P:semanticdb:synthetics:on")`
4. Run the rule: `scalafix dependency:Scala213@org.scala-lang:scala-rewrites:0.1.0-SNAPSHOT`
5. Run the rule on test sources: `Test/scalafix dependency:Scala213@org.scala-lang:scala-rewrites:0.1.0-SNAPSHOT`
