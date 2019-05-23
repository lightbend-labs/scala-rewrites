package impl

import scalafix.v1._

import scala.collection.mutable
import scala.meta._
import scala.meta.parsers.Parsed.Success

class Substitutions(implicit doc: SemanticDocument) {
  type Substitution = PartialFunction[Tree, RewriteAndDiagnostic]

  /** Trees will be replaced by one of the patches. Their children are not visited by the [[SubstitutingTraverser]] */
  private val substituted = mutable.Set.empty[Tree]

  private class SubstitutingTraverser(sub: PartialFunction[Tree, Patch]) extends Traverser {
    val patches = mutable.ListBuffer.empty[Patch]
    override def apply(tree: Tree): Unit = {
      if (sub.isDefinedAt(tree)) patches += sub(tree)
      if (!substituted(tree)) super.apply(tree)
    }
  }

  private def substitutionsPF(subs: List[Substitution], toPatch: RewriteAndDiagnostic => Patch): PartialFunction[Tree, Patch] = {
    case t if subs.exists(_.isDefinedAt(t)) =>
      subs.flatMap(_.lift.apply(t).map(toPatch)).asPatch
  }

  private def run(tree: Tree, subs: List[Substitution], toPatch: RewriteAndDiagnostic => Patch): Patch = {
    val traverser = new SubstitutingTraverser(substitutionsPF(subs, toPatch))
    traverser.apply(tree)
    traverser.patches.asPatch
  }

  def rewrite(tree: Tree, subs: List[Substitution]): Patch = run(tree, subs, _.rewrite)
  def lint(tree: Tree, subs: List[Substitution]): Patch = run(tree, subs, _.lint)

  // Symbols

  private val EOL        = SymbolMatcher.exact("scala/compat/Platform.EOL.")
  private val arrowAssoc = SymbolMatcher.exact("scala/Predef.ArrowAssoc#`→`().")

  // Rewrites

  private def replaceTokens(t: Tree, orig: String, repl: String, message: Position => Diagnostic): RewriteAndDiagnostic = {
    var first: Token = null
    val patches = t.tokens.collect {
      case tok if tok.text == orig =>
        if (first == null) first = tok
        Patch.replaceToken(tok, repl)
    }
    if (first == null) RewriteAndDiagnostic.empty
    else RewriteAndDiagnostic(
      patches.asPatch,
      message(first.pos))
  }

  private def replaceTree(from: Tree, to: String, message: Position => Diagnostic): RewriteAndDiagnostic = {
    def toIsName = to.parse[Term] match {
      case Success(_: Term.Name) => true
      case _ => false
    }
    val toQ = from match {
      case _: Term.Name if from.parent.exists(_.isInstanceOf[Term.Interpolate]) && !toIsName =>
        s"{$to}"
      case _ =>
        to
    }
    substituted += from
    RewriteAndDiagnostic(Patch.replaceTree(from, toQ), message(from.pos))
  }

  private val platformEOLDiag = Diagnostic("EOL", "scala.compat.Platform is deprecated in Scala 2.13", _: Position)
  val platfromEOL: Substitution = {
    case EOL(i: Importee) => RewriteAndDiagnostic(Patch.removeImportee(i), platformEOLDiag(i.pos))
    case EOL(t: Term)     => replaceTree(t, "System.lineSeparator", platformEOLDiag)
  }

  private val unicodeDoubleArrowDiag = Diagnostic("unicodeDoubleArrow", "Unicode arrows are deprecated in Scala 2.13", _: Position)
  val unicodeArrows: Substitution = {
    case t: Case                  => replaceTokens(t, "⇒", "=>", unicodeDoubleArrowDiag)
    case t: Type.Function         => replaceTokens(t, "⇒", "=>", unicodeDoubleArrowDiag)
    case t: Term.Function         => replaceTokens(t, "⇒", "=>", unicodeDoubleArrowDiag)
    case t: Importee              => replaceTokens(t, "⇒", "=>", unicodeDoubleArrowDiag)
    case arrowAssoc(t: Term.Name) => replaceTree(t, "->", Diagnostic("unicodeArrow", "Unicode arrows are deprecated in Scala 2.13", _))
  }

  val symbolLiteral: Substitution = {
    case t @ Lit.Symbol(sym) => replaceTree(t, s"""Symbol("${sym.name}")""", Diagnostic("symbolLiteral", "Symbol literals are deprecated in Scala 2.13", _))
  }
}
