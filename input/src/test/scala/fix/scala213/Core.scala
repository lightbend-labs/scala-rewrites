/*
rule = fix.scala213.Core
*/
package fix.scala213

import scala.compat.Platform
import scala.compat.Platform.{EOL, arraycopy, currentTime}

object Core {
  def eol1 = "Hello World!" + EOL
  def eol2 = s"Hello World!$EOL"
  def elo2b = s"Hello World!${EOL}"
  def eol3 = "Hello World!" + Platform.EOL
  def eol4 = s"Hello World!${Platform.EOL}"
  def eol5 = "Hello World!" + scala.compat.Platform.EOL
  def eol6 = s"Hello World!${scala.compat.Platform.EOL}"

  def currentTimeMillis1 = currentTime
  def currentTimeMillis2 = Platform.currentTime
  def currentTimeMillis3 = scala.compat.Platform.currentTime
  def currentTimeMillis4 = s"now: $currentTime"

  def arrayCopy1() = arraycopy(null, 0, null, 0, 0)
  def arrayCopy2() = Platform.arraycopy(null, 0, null, 0, 0)
  def arrayCopy3() = scala.compat.Platform.arraycopy(null, 0, null, 0, 0)

  def arrow1: PartialFunction[Any, String] = {
    case 0      => "zero"
    case 1       ⇒ "one"
    case 2 ⇒       "two"
    case n: Int  ⇒ "ginormous"
    case "⇒"    => "?"
  }
  def arrow2(f: Int ⇒ String) = f(1)
  def arrow3 = {
    import scala.{PartialFunction ⇒ ?=>}
    def f: Int ?=> String = {
      case 1 => "one"
    }
  }
  def arrow4 = Some(1).map(x ⇒ x.toString)

  def singleArrow1: (Int, Int) = 1 → 1
  def singleArrow2: (Int, Int) = 1.→(1)

  def symbolLiteral0 = Symbol("foo")
  def symbolLiteral1 = 'foo
}
