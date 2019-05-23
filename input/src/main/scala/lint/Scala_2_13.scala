/*
rule = lint.Scala_2_13
*/
package lint

import scala.compat.Platform
import scala.compat.Platform.{ EOL, arraycopy, currentTime } // assert: lint.Scala_2_13.platform

object Scala_2_13 {
  def eol1 = "Hello World!" + EOL // assert: lint.Scala_2_13.platform
  def eol2 = s"Hello World!$EOL" // assert: lint.Scala_2_13.platform
  def elo2b = s"Hello World!${EOL}" // assert: lint.Scala_2_13.platform
  def eol3 = "Hello World!" + Platform.EOL // assert: lint.Scala_2_13.platform
  def eol4 = s"Hello World!${Platform.EOL}" // assert: lint.Scala_2_13.platform
  def eol5 = "Hello World!" + scala.compat.Platform.EOL // assert: lint.Scala_2_13.platform
  def eol6 = s"Hello World!${scala.compat.Platform.EOL}" // assert: lint.Scala_2_13.platform

  def currentTimeMillis1 = currentTime // assert: lint.Scala_2_13.platform
  def currentTimeMillis2 = Platform.currentTime // assert: lint.Scala_2_13.platform
  def currentTimeMillis3 = scala.compat.Platform.currentTime // assert: lint.Scala_2_13.platform
  def currentTimeMillis4 = s"now: $currentTime" // assert: lint.Scala_2_13.platform

  def arrayCopy1() = arraycopy(null, 0, null, 0, 0) // assert: lint.Scala_2_13.platform
  def arrayCopy2() = Platform.arraycopy(null, 0, null, 0, 0) // assert: lint.Scala_2_13.platform
  def arrayCopy3() = scala.compat.Platform.arraycopy(null, 0, null, 0, 0) // assert: lint.Scala_2_13.platform

  def consoleReadBoolean                        = Console.readBoolean() // assert: lint.Scala_2_13.consoleRead
  def consoleReadByte                           = Console.readByte() // assert: lint.Scala_2_13.consoleRead
  def consoleReadChar                           = Console.readChar() // assert: lint.Scala_2_13.consoleRead
  def consoleReadDouble                         = Console.readDouble() // assert: lint.Scala_2_13.consoleRead
  def consoleReadFloat                          = Console.readFloat() // assert: lint.Scala_2_13.consoleRead
  def consoleReadInt                            = Console.readInt() // assert: lint.Scala_2_13.consoleRead
  def consoleReadLine                           = Console.readLine() // assert: lint.Scala_2_13.consoleRead
  def consoleReadLine(text: String, args: Any*) = Console.readLine(text, args: _*) // assert: lint.Scala_2_13.consoleRead
  def consoleReadLong                           = Console.readLong() // assert: lint.Scala_2_13.consoleRead
  def consoleReadShort                          = Console.readShort() // assert: lint.Scala_2_13.consoleRead
  def consoleReadf(format: String)              = Console.readf(format) // assert: lint.Scala_2_13.consoleRead
  def consoleReadf1(format: String)             = Console.readf1(format) // assert: lint.Scala_2_13.consoleRead
  def consoleReadf2(format: String)             = Console.readf2(format) // assert: lint.Scala_2_13.consoleRead
  def consoleReadf3(format: String)             = Console.readf3(format) // assert: lint.Scala_2_13.consoleRead

  def arrow1: PartialFunction[Any, String] = {
    case 0      => "zero"
    case 1       ⇒ "one" // assert: lint.Scala_2_13.unicodeDoubleArrow
    case 2 ⇒       "two" // assert: lint.Scala_2_13.unicodeDoubleArrow
    case n: Int  ⇒ "ginormous" // assert: lint.Scala_2_13.unicodeDoubleArrow
    case "⇒"    => "?"
  }
  def arrow2(f: Int ⇒ String) = f(1) // assert: lint.Scala_2_13.unicodeDoubleArrow
  def arrow3 = {
    import scala.{ PartialFunction ⇒ ?=> } // assert: lint.Scala_2_13.unicodeDoubleArrow
    def f: Int ?=> String = {
      case 1 => "one"
    }
  }
  def arrow4 = Some(1).map(x ⇒ x.toString) // assert: lint.Scala_2_13.unicodeDoubleArrow

  def singleArrow1: (Int, Int) = 1 → 1 // assert: lint.Scala_2_13.unicodeArrow
  def singleArrow2: (Int, Int) = 1.→(1) // assert: lint.Scala_2_13.unicodeArrow

  def symbolLiteral0 = Symbol("foo")
  def symbolLiteral1 = 'foo // assert: lint.Scala_2_13.symbolLiteral
}
