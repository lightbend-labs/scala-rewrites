package impl

import scalafix.v1._

import scala.meta._

object Substitutions {
  type Substitution = PartialFunction[Tree, List[RewriteAndDiagnostic]]

  private def substitutionsPF(subs: List[Substitution]): PartialFunction[Tree, List[RewriteAndDiagnostic]] = {
    case t if subs.exists(_.isDefinedAt(t)) =>
      subs.flatMap(_.applyOrElse(t, (_: Tree) => Nil))
  }

  def rewritePF(subs: List[Substitution]): PartialFunction[Tree, Patch] =
    substitutionsPF(subs).andThen(_.map(_.rewrite).asPatch)

  def lintPF(subs: List[Substitution]): PartialFunction[Tree, Patch] =
    substitutionsPF(subs).andThen(_.map(_.lint).asPatch)

  // Symbols

  private val arrowAssoc: SymbolMatcher = SymbolMatcher.exact("scala/Predef.ArrowAssoc#`→`().")

  // Rewrites

  private def replaceToken(t: Tree, orig: String, repl: String, id: String, message: String): List[RewriteAndDiagnostic] = {
    t.tokens.collect {
      case tok if tok.text == orig => RewriteAndDiagnostic(
        Patch.replaceToken(tok, repl),
        Diagnostic(id, message, tok.pos))
    }.toList
  }

  private def replaceTree(from: Tree, to: String, id: String, message: String): List[RewriteAndDiagnostic] = {
    List(RewriteAndDiagnostic(Patch.replaceTree(from, to), Diagnostic(id, message, from.pos)))
  }

  def unicodeArrows(implicit doc: SemanticDocument): Substitution = {
    case t: Case                  => replaceToken(t, "⇒", "=>", "unicodeDoubleArrow", "Unicode arrows are deprecated in Scala 2.13")
    case t: Type.Function         => replaceToken(t, "⇒", "=>", "unicodeDoubleArrow", "Unicode arrows are deprecated in Scala 2.13")
    case t: Term.Function         => replaceToken(t, "⇒", "=>", "unicodeDoubleArrow", "Unicode arrows are deprecated in Scala 2.13")
    case t: Importee              => replaceToken(t, "⇒", "=>", "unicodeDoubleArrow", "Unicode arrows are deprecated in Scala 2.13")
    case arrowAssoc(t: Term.Name) => replaceToken(t, "→", "->", "unicodeArrow", "Unicode arrows are deprecated in Scala 2.13")
  }

  def symbolLiteral(implicit doc: SemanticDocument): Substitution = {
    case t @ Lit.Symbol(sym) => replaceTree(t, s"""Symbol("${sym.name}")""", "symbolLiteral", "Symbol literals are deprecated in Scala 2.13")
  }
}
