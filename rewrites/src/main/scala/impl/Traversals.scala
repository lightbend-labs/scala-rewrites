package impl

import scalafix.v1._

import scala.collection.mutable.ListBuffer
import scala.meta._


object Traversals {
  abstract class CollectingTraverser(doc: SemanticDocument) extends Traverser{
    protected implicit val theDoc: SemanticDocument = doc

    protected val result = ListBuffer.empty[RewriteAndDiagnostic]

    private def run(tree: Tree): List[RewriteAndDiagnostic] = {
      result.clear()
      apply(tree)
      result.toList
    }

    def rewrite(tree: Tree): Patch = run(tree).map(_.rewrite).asPatch

    def lint(tree: Tree): Patch = run(tree).map(_.lint).asPatch
  }

  class SeqTraverser(doc: SemanticDocument) extends CollectingTraverser(doc) {
    private val scalaSeq = SymbolMatcher.exact("scala/package.Seq#")

    private var inParam = false

    override def apply(tree: Tree): Unit = tree match {
      case p: Term.Param =>
        inParam = true
        apply(p.decltpe)
        inParam = false
        apply(p.default)

      case scalaSeq(Type.Apply(t, _)) =>
        val diag = Diagnostic("scalaSeq", "scala.Seq is an alias for immutable.Seq in 2.13, no longer collection.Seq", t.pos)
        val sub = if (inParam) "scala.collection.Seq" else "scala.collection.immutable.Seq"
        result += RewriteAndDiagnostic(Patch.replaceTree(t, sub), diag)

      case _ => super.apply(tree)
    }
  }
}
