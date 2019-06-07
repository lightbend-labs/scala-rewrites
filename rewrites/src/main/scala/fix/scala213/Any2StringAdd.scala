package fix.scala213

import scalafix.v1._

import scala.meta._

object Any2StringAdd {
  val any2stringadd = Symbol("scala/Predef.any2stringadd().")
}

final class Any2StringAdd extends SemanticRule("fix.scala213.Any2StringAdd") {
  import Any2StringAdd._

  override def fix(implicit doc: SemanticDocument): Patch = {
    doc.tree.collect {
      case Term.ApplyInfix(lhs, Term.Name("+"), _, List(_)) if
          lhs.synthetic.collect {
            case ApplyTree(TypeApplyTree(IdTree(info), _), _) => info.symbol
          }.contains(any2stringadd)
        =>
        Patch.addRight(lhs, ".toString")
    }.asPatch
  }
}
