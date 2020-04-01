package impl

import scala.PartialFunction.cond
import scala.collection.mutable

import scala.meta._

import scalafix.Patch

abstract class ReplacingTraverser extends CollectingTraverser {
  type Replacer = PartialFunction[Tree, Patch]

  private val replaced = mutable.Set.empty[Tree] // replaced trees, so skip children

  def replacers: List[Replacer]

  final override def apply(tree: Tree) = {
    for (replacer <- replacers)
      replacer.runWith(patch += _)(tree)
    if (!replaced.contains(tree))
      super.apply(tree)
  }

  final protected def replaceTree(from: Tree, to0: String): Patch = {
    def interpolating = from.parent.exists(_.isInstanceOf[Term.Interpolate])
    def toParses      = cond(to0.parse[Term]) { case Parsed.Success(_: Term.Name) => true }
    val to = from match {
      case _: Term.Name if interpolating && !toParses => s"{$to0}"
      case _                                          => to0
    }
    replaced += from
    Patch.replaceTree(from, to)
  }
}
