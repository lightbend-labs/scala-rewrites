package impl

import scalafix.v1._

import scala.collection.mutable.ListBuffer
import scala.meta._

abstract class CollectingTraverser extends Traverser {
  final protected val patches = ListBuffer.empty[Patch]
  final protected val globalImports = new GlobalImports

  private def run(tree: Tree): List[Patch] = {
    patches.clear()
    apply(tree)
    patches.toList
  }

  final def rewrite(tree: Tree): Patch = run(tree).asPatch + globalImports.patch
}
