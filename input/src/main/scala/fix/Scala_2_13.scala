/*
rule = Scala_2_13
*/
package fix

import scala.Console._
import scala.collection.mutable
import scala.compat.Platform
import scala.compat.Platform.{ EOL, arraycopy, currentTime }
import scala.sys.{ env, error, exit, props, runtime }

object Scala_2_13 {
  def eol1 = "Hello World!" + EOL
  def eol2 = s"Hello World!$EOL"
  def eol3 = "Hello World!" + Platform.EOL
  def eol4 = s"Hello World!${Platform.EOL}"
  def eol5 = "Hello World!" + scala.compat.Platform.EOL
  def eol6 = s"Hello World!${scala.compat.Platform.EOL}"

  def currentTimeMillis1 = currentTime
  def currentTimeMillis2 = Platform.currentTime
  def currentTimeMillis3 = scala.compat.Platform.currentTime

  def arrayCopy1() = arraycopy(null, 0, null, 0, 0)
  def arrayCopy2() = Platform.arraycopy(null, 0, null, 0, 0)
  def arrayCopy3() = scala.compat.Platform.arraycopy(null, 0, null, 0, 0)

  def error1 = error("foo")
  def error2 = sys.error("foo")
  def error3 = scala.sys.error("foo")

  def exit1: Nothing = sys.exit()
  def exit2: Nothing = exit()
  def exit3: Nothing = sys.exit(1)

  def sysProps1: String = sys.props("foo")
  def sysProps2: Option[String] = sys.props.get("foo")
  def sysProps3: mutable.Map[String, String] = sys.props
  def sysProps4: String = props("foo")
  def sysProps5: Option[String] = props.get("foo")
  def sysProps6: mutable.Map[String, String] = props

  def sysEnv1: String = sys.env("FOO")
  def sysEnv2: Option[String] = sys.env.get("FOO")
  def sysEnv3: Map[String, String] = sys.env
  def sysEnv4: String = env("FOO")
  def sysEnv5: Option[String] = env.get("FOO")
  def sysEnv6: Map[String, String] = env

  def sysRuntime1: Runtime = sys.runtime
  def sysRuntime2: Runtime = runtime

  def sysAddShutdownHook1(body: => Unit): Unit =
    sys.addShutdownHook(body)

  def ansiColorBlack: String      = Console.BLACK
  def ansiColorRed: String        = Console.RED
  def ansiColorGreen: String      = Console.GREEN
  def ansiColorYellow: String     = Console.YELLOW
  def ansiColorBlue: String       = Console.BLUE
  def ansiColorMagenta: String    = Console.MAGENTA
  def ansiColorCyan: String       = Console.CYAN
  def ansiColorWhite: String      = Console.WHITE
  def ansiColorBlackB: String     = Console.BLACK_B
  def ansiColorRedB: String       = Console.RED_B
  def ansiColorGreenB: String     = Console.GREEN_B
  def ansiColorYellowB: String    = Console.YELLOW_B
  def ansiColorBlueB: String      = Console.BLUE_B
  def ansiColorMagentaB: String   = Console.MAGENTA_B
  def ansiColorCyanB: String      = Console.CYAN_B
  def ansiColorWhiteB: String     = Console.WHITE_B
  def ansiColorReset: String      = Console.RESET
  def ansiColorBold: String       = Console.BOLD
  def ansiColorUnderlined: String = Console.UNDERLINED
  def ansiColorBlink: String      = Console.BLINK
  def ansiColorReversed: String   = Console.REVERSED
  def ansiColorInvisible: String  = Console.INVISIBLE

  def ansiColorBlack2: String      = BLACK
  def ansiColorRed2: String        = RED
  def ansiColorGreen2: String      = GREEN
  def ansiColorYellow2: String     = YELLOW
  def ansiColorBlue2: String       = BLUE
  def ansiColorMagenta2: String    = MAGENTA
  def ansiColorCyan2: String       = CYAN
  def ansiColorWhite2: String      = WHITE
  def ansiColorBlackB2: String     = BLACK_B
  def ansiColorRedB2: String       = RED_B
  def ansiColorGreenB2: String     = GREEN_B
  def ansiColorYellowB2: String    = YELLOW_B
  def ansiColorBlueB2: String      = BLUE_B
  def ansiColorMagentaB2: String   = MAGENTA_B
  def ansiColorCyanB2: String      = CYAN_B
  def ansiColorWhiteB2: String     = WHITE_B
  def ansiColorReset2: String      = RESET
  def ansiColorBold2: String       = BOLD
  def ansiColorUnderlined2: String = UNDERLINED
  def ansiColorBlink2: String      = BLINK
  def ansiColorReversed2: String   = REVERSED
  def ansiColorInvisible2: String  = INVISIBLE

  def ansiColorBlack3: String      = s"[$BLACK]"
  def ansiColorRed3: String        = s"[$RED]"
  def ansiColorGreen3: String      = s"[$GREEN]"
  def ansiColorYellow3: String     = s"[$YELLOW]"
  def ansiColorBlue3: String       = s"[$BLUE]"
  def ansiColorMagenta3: String    = s"[$MAGENTA]"
  def ansiColorCyan3: String       = s"[$CYAN]"
  def ansiColorWhite3: String      = s"[$WHITE]"
  def ansiColorBlackB3: String     = s"[$BLACK_B]"
  def ansiColorRedB3: String       = s"[$RED_B]"
  def ansiColorGreenB3: String     = s"[$GREEN_B]"
  def ansiColorYellowB3: String    = s"[$YELLOW_B]"
  def ansiColorBlueB3: String      = s"[$BLUE_B]"
  def ansiColorMagentaB3: String   = s"[$MAGENTA_B]"
  def ansiColorCyanB3: String      = s"[$CYAN_B]"
  def ansiColorWhiteB3: String     = s"[$WHITE_B]"
  def ansiColorReset3: String      = s"[$RESET]"
  def ansiColorBold3: String       = s"[$BOLD]"
  def ansiColorUnderlined3: String = s"[$UNDERLINED]"
  def ansiColorBlink3: String      = s"[$BLINK]"
  def ansiColorReversed3: String   = s"[$REVERSED]"
  def ansiColorInvisible3: String  = s"[$INVISIBLE]"

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
}
