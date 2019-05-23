package impl

import scalafix.lint.Diagnostic
import scalafix.patch.Patch

import scala.meta._

/** Represents a fix that can be turned into a rewrite or lint patch. */
case class RewriteAndDiagnostic(rewrite: Patch, message: Diagnostic) {
  def lint: Patch = if (isEmpty) Patch.empty else Patch.lint(message)
  def isEmpty: Boolean = this eq RewriteAndDiagnostic.empty
}

object RewriteAndDiagnostic {
  val empty = RewriteAndDiagnostic(Patch.empty, Diagnostic("", "", Position.None))
}
