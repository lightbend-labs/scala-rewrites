/*
rule = Scala_2_13
*/
package fix

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
