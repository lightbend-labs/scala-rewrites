package fix.scala213

import scala.compat.Platform

object Core {
  def eol1 = "Hello World!" + System.lineSeparator
  def eol2 = s"Hello World!${System.lineSeparator}"
  def elo2b = s"Hello World!${{System.lineSeparator}}"
  def eol3 = "Hello World!" + System.lineSeparator
  def eol4 = s"Hello World!${System.lineSeparator}"
  def eol5 = "Hello World!" + System.lineSeparator
  def eol6 = s"Hello World!${System.lineSeparator}"

  def currentTimeMillis1 = System.currentTimeMillis
  def currentTimeMillis2 = System.currentTimeMillis
  def currentTimeMillis3 = System.currentTimeMillis
  def currentTimeMillis4 = s"now: ${System.currentTimeMillis}"

  def arrayCopy1() = System.arraycopy(null, 0, null, 0, 0)
  def arrayCopy2() = System.arraycopy(null, 0, null, 0, 0)
  def arrayCopy3() = System.arraycopy(null, 0, null, 0, 0)

  def arrow1: PartialFunction[Any, String] = {
    case 0      => "zero"
    case 1       => "one"
    case 2 =>       "two"
    case n: Int  => "ginormous"
    case "⇒"    => "?"
  }
  def arrow2(f: Int => String) = f(1)
  def arrow3 = {
    import scala.{PartialFunction => ?=>}
    def f: Int ?=> String = {
      case 1 => "one"
    }
  }
  def arrow4 = Some(1).map(x => x.toString)

  def singleArrow1: (Int, Int) = 1 -> 1
  def singleArrow2: (Int, Int) = 1.->(1)

  def symbolLiteral0 = Symbol("foo")
  def symbolLiteral1 = Symbol("foo")
}
