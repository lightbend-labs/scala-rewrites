package fix.scala213

import scalafix.v1._

import scala.meta._

object Any2StringAdd {
  val any2stringaddPlusString = SymbolMatcher.exact("scala/Predef.any2stringadd#`+`().")
  val primitivePlusString = SymbolMatcher.exact(
    "scala/Byte#`+`().",
    "scala/Short#`+`().",
    "scala/Char#`+`().",
    "scala/Int#`+`().",
    "scala/Long#`+`().",
    "scala/Float#`+`().",
    "scala/Double#`+`().",
  )
}

final class Any2StringAdd extends SemanticRule("fix.scala213.Any2StringAdd") {
  import Any2StringAdd._

  override def fix(implicit doc: SemanticDocument): Patch = {
    doc.tree.collect {
      case any2stringaddPlusString(Term.ApplyInfix(lhs, _, _, _)) => wrapStringValueOf(lhs)
      case primitivePlusString(Term.ApplyInfix(lhs, _, _, _)) => blankStringPlus(lhs)
    }.asPatch
  }

  private def wrapStringValueOf(term: Term) =
    Patch.addLeft(term, "String.valueOf(") + Patch.addRight(term, ")")

  private def blankStringPlus(term: Term) = term match {
    case _: Term.Name | _: Term.Select | _: Term.Block => Patch.addLeft(term, """"" + """)
    case _ => Patch.addLeft(term, """"" + (""") + Patch.addRight(term, ")")
  }
}
