package fix.scala213

import scalafix.v1._

import scala.meta._

object Any2StringAdd {
  val any2stringaddPlus = SymbolMatcher.exact("scala/Predef.any2stringadd#`+`().")
}

final class Any2StringAdd extends SemanticRule("fix.scala213.Any2StringAdd") {
  import Any2StringAdd._

  override def fix(implicit doc: SemanticDocument): Patch = {
    doc.tree.collect {
      case any2stringaddPlus(Term.ApplyInfix(lhs, _, _, _)) =>
        lhs match {
          case _: Term.Name | _: Term.Select | _: Term.Block => Patch.addRight(lhs, ".toString")
          case _ => Patch.addLeft(lhs, "(") + Patch.addRight(lhs, ").toString")
        }
    }.asPatch
  }
}
