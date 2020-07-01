# CAUTIONS!
Current released Scalafix supports ExplicitNonNullaryApply [ONLY in Scala 2.12](https://github.com/scala/scala-rewrites/issues/31). 
Scalafix will support 2.13 and 2.11 with cross-building ExplicitNonNullaryApply rule against them.  

# Scalafix Rewrites for Scala

## To develop a rewrite

```
sbt ~tests/test
# edit rewrites/src/main/scala/...
```

## To run the rewrites

1. [Install Scalafix](https://scalacenter.github.io/scalafix/docs/users/installation.html)
2. Enable synthetics in SemanticDB; e.g. `semanticdbOptions += "-P:semanticdb:synthetics:on"` in sbt 1.3+
3. Run the [external rule](https://scalacenter.github.io/scalafix/docs/rules/external-rules.html), e.g.:
  * `scalafix dependency:ExplicitNonNullaryApply@org.scala-lang:scala-rewrites:VERSION`
  * `Test/scalafix dependency:ExplicitNonNullaryApply@org.scala-lang:scala-rewrites:VERSION`
