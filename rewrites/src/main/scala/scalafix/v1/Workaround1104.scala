package scalafix.v1

import scala.meta._

/** workaround for:
 * + https://github.com/scalameta/scalameta/issues/1083
 * + https://github.com/scalacenter/scalafix/issues/1104 */
object Workaround1104 {
  /** Same as [[scalafix.v1.XtensionTreeScalafix.symbol]]
   * but because of scalameta/scalameta#1083,
   * sometimes `XtensionTreeScalafix.symbol` return `Symbol.None``
   * In that case, we retry getting symbols at pos `name.pos`` */
  def symbol(name: Name)(implicit doc: SemanticDocument): Symbol =
    doc.internal.symbol(name) match {
      case Symbol.None if needWorkaround(name) =>
        doc.internal.symbols(name.pos)
          .toStream.headOption // Discard multi symbols
          .getOrElse(Symbol.None)
      case sym => sym
    }

  def needWorkaround(name: Name): Boolean = {
    val s = name.syntax
    s.startsWith("(") && s.endsWith(")") && s != name.value
  }

  /** @return if the token `name` instead of `)` for `(name)` */
  def lastToken(name: Name): Token = {
    val tokens = name.tokens
    if (needWorkaround(name)) tokens(tokens.length - 2)
    else tokens.last
  }
}
