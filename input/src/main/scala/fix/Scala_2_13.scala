/*
rule = fix.Scala_2_13
*/
package fix

import scala.compat.Platform
import scala.compat.Platform.{ EOL, arraycopy, currentTime }

object Scala_2_13 {
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

  def consoleReadBoolean                        = Console.readBoolean()
  def consoleReadByte                           = Console.readByte()
  def consoleReadChar                           = Console.readChar()
  def consoleReadDouble                         = Console.readDouble()
  def consoleReadFloat                          = Console.readFloat()
  def consoleReadInt                            = Console.readInt()
  def consoleReadLine                           = Console.readLine()
  def consoleReadLine(text: String, args: Any*) = Console.readLine(text, args: _*)
  def consoleReadLong                           = Console.readLong()
  def consoleReadShort                          = Console.readShort()
  def consoleReadf(format: String)              = Console.readf(format)
  def consoleReadf1(format: String)             = Console.readf1(format)
  def consoleReadf2(format: String)             = Console.readf2(format)
  def consoleReadf3(format: String)             = Console.readf3(format)

  def arrow1: PartialFunction[Any, String] = {
    case 0      => "zero"
    case 1       ⇒ "one"
    case 2 ⇒       "two"
    case n: Int  ⇒ "ginormous"
    case "⇒"    => "?"
  }
  def arrow2(f: Int ⇒ String) = f(1)
  def arrow3 = {
    import scala.{ PartialFunction ⇒ ?=> }
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
