package fix.scala213

import scalafix.v1._

import scala.meta._

object Any2StringAdd {
  val plusString = SymbolMatcher.exact(
    "scala/Predef.any2stringadd#`+`().",
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
      case plusString(Term.ApplyInfix(lhs, _, _, _)) => addToString(lhs)
    }.asPatch
  }

  private def addToString(term: Term) = term match {
    case _: Term.Name | _: Term.Select | _: Term.Block => Patch.addRight(term, ".toString")
    case _ => Patch.addLeft(term, "(") + Patch.addRight(term, ").toString")
  }
}
