package impl

import scalafix.lint.Diagnostic
import scalafix.patch.Patch

/** Represents a fix that can be turned into a rewrite or lint patch. */
case class RewriteAndDiagnostic(rewrite: Patch, message: Diagnostic) {
  def lint: Patch = Patch.lint(message)
}
