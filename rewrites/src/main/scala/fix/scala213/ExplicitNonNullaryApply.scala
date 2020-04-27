package fix.scala213

import scala.PartialFunction.{ cond, condOpt }
import scala.collection.mutable
import scala.util.control.Exception.nonFatalCatch

import metaconfig.Configured

import scala.meta._
import scala.meta.internal.pc.ScalafixGlobal

import scalafix.v1._
import scalafix.internal.rule.CompilerException
import scalafix.internal.v1.LazyValue

final class ExplicitNonNullaryApply(global: LazyValue[ScalafixGlobal])
    extends SemanticRule("fix.scala213.ExplicitNonNullaryApply")
{
  def this() = this(LazyValue.later(() => ScalafixGlobal.newCompiler(Nil, Nil, Map.empty)))

  override def fix(implicit doc: SemanticDocument): Patch = {
    try unsafeFix() catch {
      case _: CompilerException =>
        shutdownCompiler()
        global.restart()
        try unsafeFix() catch {
          case _: CompilerException => Patch.empty /* ignore compiler crashes */
        }
    }
  }

  private def unsafeFix()(implicit doc: SemanticDocument) = {
    lazy val power = new impl.Power(global.value)
    val handled = mutable.Set.empty[Name]

    def fix(tree: Tree, meth: Term, noTypeArgs: Boolean, noArgs: Boolean) = {
      for {
        name <- termName(meth)
        if handled.add(name) && name.value != "##" // fast-track https://github.com/scala/scala/pull/8814
        if noArgs
        if name.isReference
        if !cond(name.parent) {
          case Some(Term.ApplyInfix(_, `name`, _, _)) => true
        }
        if !tree.parent.exists(_.is[Term.Eta])
        info <- Workaround1104.symbol(name).info
        if !power.isJavaDefined(name) // !info.isJava
        if cond(info.signature) {
          case MethodSignature(_, List(Nil, _*), _) => true
          case ClassSignature(_, _, _, decls) if tree.isInstanceOf[Term.ApplyType] =>
            decls.exists { decl =>
              decl.displayName == "apply" &&
                cond(decl.signature) {
                  case MethodSignature(_, List(Nil, _*), _) => true
                }
            }
        }
      } yield {
        val tok =
          if (noTypeArgs) Workaround1104.lastToken(name)
          else tree.tokens.last
        val right = Patch.addRight(tok, "()")
        name.parent match {
          // scalameta:trees don't have PostfixSelect like
          // scala.tools.nsc.ast.Trees.PostfixSelect
          // so we have to check if Token.Dot is existed
          case Some(Term.Select(qual, `name`)) =>
            val qualLast = qual.tokens.last
            val nameHead = name.tokens.head
            val tokens = doc.tokenList
            val sliced = tokens.slice(tokens.next(qualLast), nameHead)
            if (sliced.exists(_.is[Token.Dot])) {
              right
            } else {
              Patch.removeTokens(tokens.trailingSpaces(qualLast)) +
                Patch.addLeft(nameHead, ".") +
                right
            }
          case _ => right
        }
      }
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

  override def withConfiguration(config: Configuration) =
    Configured.ok(new ExplicitNonNullaryApply(LazyValue.later { () =>
      ScalafixGlobal.newCompiler(config.scalacClasspath, config.scalacOptions, Map.empty)
    }))

  override def afterComplete() = shutdownCompiler()
  def shutdownCompiler() = for (g <- global) nonFatalCatch { g.askShutdown(); g.close() }
}
