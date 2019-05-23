package impl

import fix.scala213.ScalaSeqConfig
import scalafix.v1._

import scala.collection.mutable.ListBuffer
import scala.meta._


object Traversals {
  abstract class CollectingTraverser(doc: SemanticDocument) extends Traverser {
    protected implicit val theDoc: SemanticDocument = doc

    protected val result = ListBuffer.empty[RewriteAndDiagnostic]

    protected val globalImports = new GlobalImports

    private def run(tree: Tree): List[RewriteAndDiagnostic] = {
      result.clear()
      apply(tree)
      result.toList
    }

    def rewrite(tree: Tree): Patch = run(tree).map(_.rewrite).asPatch + globalImports.patch

    def lint(tree: Tree): Patch = run(tree).map(_.lint).asPatch
  }

  class SeqTraverser(doc: SemanticDocument, config: ScalaSeqConfig) extends CollectingTraverser(doc) {
    private val scalaSeq = SymbolMatcher.exact("scala/package.Seq#")

    private var inParam = false

    val paramImport: Option[Importer] =
      if (config.paramImport.isEmpty) None
      else Some(config.paramImport.parse[Importer].get)

    val otherImport: Option[Importer] =
      if (config.otherImport.isEmpty) None
      else Some(config.otherImport.parse[Importer].get)

    override def apply(tree: Tree): Unit = tree match {
      case p: Term.Param =>
        inParam = true
        apply(p.decltpe)
        inParam = false
        apply(p.default)

      case scalaSeq(Type.Apply(t, _)) =>
        val diag = Diagnostic("scalaSeq", "scala.Seq is an alias for immutable.Seq in 2.13, no longer collection.Seq", t.pos)
        val sub = if (inParam) {
          paramImport.foreach(globalImports.add)
          config.paramType
        } else {
          otherImport.foreach(globalImports.add)
          config.otherType
        }
        result += RewriteAndDiagnostic(Patch.replaceTree(t, sub), diag)

      case _ => super.apply(tree)
    }
  }
}
