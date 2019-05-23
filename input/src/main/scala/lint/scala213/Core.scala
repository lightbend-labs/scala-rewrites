/*
rule = lint.scala213.Core
*/
package lint.scala213

import scala.compat.Platform
import scala.compat.Platform.{EOL, arraycopy, currentTime} // assert: lint.scala213.Core.platform

object Core {
  def eol1 = "Hello World!" + EOL // assert: lint.scala213.Core.platform
  def eol2 = s"Hello World!$EOL" // assert: lint.scala213.Core.platform
  def elo2b = s"Hello World!${EOL}" // assert: lint.scala213.Core.platform
  def eol3 = "Hello World!" + Platform.EOL // assert: lint.scala213.Core.platform
  def eol4 = s"Hello World!${Platform.EOL}" // assert: lint.scala213.Core.platform
  def eol5 = "Hello World!" + scala.compat.Platform.EOL // assert: lint.scala213.Core.platform
  def eol6 = s"Hello World!${scala.compat.Platform.EOL}" // assert: lint.scala213.Core.platform

  def currentTimeMillis1 = currentTime // assert: lint.scala213.Core.platform
  def currentTimeMillis2 = Platform.currentTime // assert: lint.scala213.Core.platform
  def currentTimeMillis3 = scala.compat.Platform.currentTime // assert: lint.scala213.Core.platform
  def currentTimeMillis4 = s"now: $currentTime" // assert: lint.scala213.Core.platform

  def arrayCopy1() = arraycopy(null, 0, null, 0, 0) // assert: lint.scala213.Core.platform
  def arrayCopy2() = Platform.arraycopy(null, 0, null, 0, 0) // assert: lint.scala213.Core.platform
  def arrayCopy3() = scala.compat.Platform.arraycopy(null, 0, null, 0, 0) // assert: lint.scala213.Core.platform

  def consoleReadBoolean                        = Console.readBoolean() // assert: lint.scala213.Core.consoleRead
  def consoleReadByte                           = Console.readByte() // assert: lint.scala213.Core.consoleRead
  def consoleReadChar                           = Console.readChar() // assert: lint.scala213.Core.consoleRead
  def consoleReadDouble                         = Console.readDouble() // assert: lint.scala213.Core.consoleRead
  def consoleReadFloat                          = Console.readFloat() // assert: lint.scala213.Core.consoleRead
  def consoleReadInt                            = Console.readInt() // assert: lint.scala213.Core.consoleRead
  def consoleReadLine                           = Console.readLine() // assert: lint.scala213.Core.consoleRead
  def consoleReadLine(text: String, args: Any*) = Console.readLine(text, args: _*) // assert: lint.scala213.Core.consoleRead
  def consoleReadLong                           = Console.readLong() // assert: lint.scala213.Core.consoleRead
  def consoleReadShort                          = Console.readShort() // assert: lint.scala213.Core.consoleRead
  def consoleReadf(format: String)              = Console.readf(format) // assert: lint.scala213.Core.consoleRead
  def consoleReadf1(format: String)             = Console.readf1(format) // assert: lint.scala213.Core.consoleRead
  def consoleReadf2(format: String)             = Console.readf2(format) // assert: lint.scala213.Core.consoleRead
  def consoleReadf3(format: String)             = Console.readf3(format) // assert: lint.scala213.Core.consoleRead

  def arrow1: PartialFunction[Any, String] = {
    case 0      => "zero"
    case 1       ⇒ "one" // assert: lint.scala213.Core.unicodeDoubleArrow
    case 2 ⇒       "two" // assert: lint.scala213.Core.unicodeDoubleArrow
    case n: Int  ⇒ "ginormous" // assert: lint.scala213.Core.unicodeDoubleArrow
    case "⇒"    => "?"
  }
  def arrow2(f: Int ⇒ String) = f(1) // assert: lint.scala213.Core.unicodeDoubleArrow
  def arrow3 = {
    import scala.{PartialFunction ⇒ ?=>} // assert: lint.scala213.Core.unicodeDoubleArrow
    def f: Int ?=> String = {
      case 1 => "one"
    }
  }
  def arrow4 = Some(1).map(x ⇒ x.toString) // assert: lint.scala213.Core.unicodeDoubleArrow

  def singleArrow1: (Int, Int) = 1 → 1 // assert: lint.scala213.Core.unicodeArrow
  def singleArrow2: (Int, Int) = 1.→(1) // assert: lint.scala213.Core.unicodeArrow

  def symbolLiteral0 = Symbol("foo")
  def symbolLiteral1 = 'foo // assert: lint.scala213.Core.symbolLiteral
}
