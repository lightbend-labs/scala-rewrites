package fix.scala213

import scala.PartialFunction.{ cond, condOpt }
import scala.collection.mutable

import scala.meta._

import scalafix.v1._

final class ExplicitNonNullaryApply extends SemanticRule("fix.scala213.ExplicitNonNullaryApply") {
  override def fix(implicit doc: SemanticDocument) = {
    val handled = mutable.Set.empty[Name]

    def fix(tree: Tree, meth: Term, noTypeArgs: Boolean, noArgs: Boolean) = {
      for {
        name <- termName(meth)
        if handled.add(name)
        if noArgs
        if name.isReference
        if !name.parent.exists(_.is[Term.ApplyInfix])
        info <- name.symbol.info
        if !info.isJava
        if cond(info.signature) { case MethodSignature(_, List(Nil), _) => true }
      } yield Patch.addRight(if (noTypeArgs) name else tree, "()")
    }.asPatch

    doc.tree.collect {
      case t @ q"$meth[..$targs](...$args)" => fix(t, meth, targs.isEmpty, args.isEmpty)
      case t @ q"$meth(...$args)"           => fix(t, meth, true,          args.isEmpty)
    }.asPatch
  }

  private def termName(term: Term): Option[Name] = condOpt(term) {
    case name: Term.Name                 => name
    case Term.Select(_, name: Term.Name) => name
    case Type.Select(_, name: Type.Name) => name
  }
}
