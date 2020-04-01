package impl

import scalafix.v1._

import scala.meta._

abstract class CollectingTraverser extends Traverser {
  final protected val globalImports = new GlobalImports
  final protected var patch         = Patch.empty;

  final def run(tree: Tree): Patch = { apply(tree); patch }
}
