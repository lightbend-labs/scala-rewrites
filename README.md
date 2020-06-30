# CAUTIONS!
Current released Scalafix supports ExplicitNonNullaryApply [ONLY in Scala 2.12](https://github.com/scala/scala-rewrites/issues/31). 
Scalafix will support 2.13 and 2.11 with cross-building ExplicitNonNullaryApply rule against them.  

# Scalafix Rewrites for Scala

## To develop the rule

```
sbt ~tests/test
# edit rewrites/src/main/scala/fix/Scala213.scala
```

## To run the rule

0. Publish the rule: `publishLocal`
1. Add sbt-scalafix: `addSbtPlugin("ch.epfl.scala" % "sbt-scalafix" % "0.9.17")`
2. Add SemanticDB: `addCompilerPlugin(scalafixSemanticdb)`
3. Configure Semantic DB: `scalacOptions ++= List("-Yrangepos", "-P:semanticdb:synthetics:on")`
4. Run a rule you want: `scalafix dependency:RULE_NAME@org.scala-lang:scala-rewrites:0.1.0-SNAPSHOT`
   - For example, you can run Varargs rule by: `scalafix dependency:fix.scala213.Varargs@org.scala-lang:scala-rewrites:0.1.0-SNAPSHOT` 
5. Run the rule on test sources: `Test/scalafix dependency:RULE_NAME@org.scala-lang:scala-rewrites:0.1.0-SNAPSHOT`
