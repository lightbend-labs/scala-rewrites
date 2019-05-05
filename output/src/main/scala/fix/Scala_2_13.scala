package fix

import scala.Console._
import scala.collection.mutable
import scala.compat.Platform
import scala.collection.JavaConverters._
import scala.io.{ AnsiColor, StdIn }

object Scala_2_13 {
  def eol1 = "Hello World!" + System.lineSeparator
  def eol2 = s"Hello World!${System.lineSeparator}"
  def eol3 = "Hello World!" + System.lineSeparator
  def eol4 = s"Hello World!${System.lineSeparator}"
  def eol5 = "Hello World!" + System.lineSeparator
  def eol6 = s"Hello World!${System.lineSeparator}"

  def currentTimeMillis1 = System.currentTimeMillis
  def currentTimeMillis2 = System.currentTimeMillis
  def currentTimeMillis3 = System.currentTimeMillis

  def arrayCopy1() = System.arraycopy(null, 0, null, 0, 0)
  def arrayCopy2() = System.arraycopy(null, 0, null, 0, 0)
  def arrayCopy3() = System.arraycopy(null, 0, null, 0, 0)

  def error1 = throw new RuntimeException("foo")
  def error2 = throw new RuntimeException("foo")
  def error3 = throw new RuntimeException("foo")

  def exit1: Nothing = { System.exit(0); throw new Throwable }
  def exit2: Nothing = { System.exit(0); throw new Throwable }
  def exit3: Nothing = { System.exit(1); throw new Throwable }

  def sysProps1: String = System.getProperty("foo")
  def sysProps2: Option[String] = Option(System.getProperty("foo"))
  def sysProps3: mutable.Map[String, String] = System.getProperties.asScala
  def sysProps4: String = System.getProperty("foo")
  def sysProps5: Option[String] = Option(System.getProperty("foo"))
  def sysProps6: mutable.Map[String, String] = System.getProperties.asScala

  def sysEnv1: String = System.getenv.asScala.apply("FOO")
  def sysEnv2: Option[String] = Option(System.getenv("FOO"))
  def sysEnv3: Map[String, String] = System.getenv.asScala.toMap
  def sysEnv4: String = System.getenv.asScala.apply("FOO")
  def sysEnv5: Option[String] = Option(System.getenv("FOO"))
  def sysEnv6: Map[String, String] = System.getenv.asScala.toMap

  def sysRuntime1: Runtime = Runtime.getRuntime
  def sysRuntime2: Runtime = Runtime.getRuntime

  def sysAddShutdownHook1(body: => Unit): Unit =
    Runtime.getRuntime.addShutdownHook(new Thread(() => (body)))

  def ansiColorBlack: String      = AnsiColor.BLACK
  def ansiColorRed: String        = AnsiColor.RED
  def ansiColorGreen: String      = AnsiColor.GREEN
  def ansiColorYellow: String     = AnsiColor.YELLOW
  def ansiColorBlue: String       = AnsiColor.BLUE
  def ansiColorMagenta: String    = AnsiColor.MAGENTA
  def ansiColorCyan: String       = AnsiColor.CYAN
  def ansiColorWhite: String      = AnsiColor.WHITE
  def ansiColorBlackB: String     = AnsiColor.BLACK_B
  def ansiColorRedB: String       = AnsiColor.RED_B
  def ansiColorGreenB: String     = AnsiColor.GREEN_B
  def ansiColorYellowB: String    = AnsiColor.YELLOW_B
  def ansiColorBlueB: String      = AnsiColor.BLUE_B
  def ansiColorMagentaB: String   = AnsiColor.MAGENTA_B
  def ansiColorCyanB: String      = AnsiColor.CYAN_B
  def ansiColorWhiteB: String     = AnsiColor.WHITE_B
  def ansiColorReset: String      = AnsiColor.RESET
  def ansiColorBold: String       = AnsiColor.BOLD
  def ansiColorUnderlined: String = AnsiColor.UNDERLINED
  def ansiColorBlink: String      = AnsiColor.BLINK
  def ansiColorReversed: String   = AnsiColor.REVERSED
  def ansiColorInvisible: String  = AnsiColor.INVISIBLE

  def ansiColorBlack2: String      = AnsiColor.BLACK
  def ansiColorRed2: String        = AnsiColor.RED
  def ansiColorGreen2: String      = AnsiColor.GREEN
  def ansiColorYellow2: String     = AnsiColor.YELLOW
  def ansiColorBlue2: String       = AnsiColor.BLUE
  def ansiColorMagenta2: String    = AnsiColor.MAGENTA
  def ansiColorCyan2: String       = AnsiColor.CYAN
  def ansiColorWhite2: String      = AnsiColor.WHITE
  def ansiColorBlackB2: String     = AnsiColor.BLACK_B
  def ansiColorRedB2: String       = AnsiColor.RED_B
  def ansiColorGreenB2: String     = AnsiColor.GREEN_B
  def ansiColorYellowB2: String    = AnsiColor.YELLOW_B
  def ansiColorBlueB2: String      = AnsiColor.BLUE_B
  def ansiColorMagentaB2: String   = AnsiColor.MAGENTA_B
  def ansiColorCyanB2: String      = AnsiColor.CYAN_B
  def ansiColorWhiteB2: String     = AnsiColor.WHITE_B
  def ansiColorReset2: String      = AnsiColor.RESET
  def ansiColorBold2: String       = AnsiColor.BOLD
  def ansiColorUnderlined2: String = AnsiColor.UNDERLINED
  def ansiColorBlink2: String      = AnsiColor.BLINK
  def ansiColorReversed2: String   = AnsiColor.REVERSED
  def ansiColorInvisible2: String  = AnsiColor.INVISIBLE

  def ansiColorBlack3: String      = s"[${AnsiColor.BLACK}]"
  def ansiColorRed3: String        = s"[${AnsiColor.RED}]"
  def ansiColorGreen3: String      = s"[${AnsiColor.GREEN}]"
  def ansiColorYellow3: String     = s"[${AnsiColor.YELLOW}]"
  def ansiColorBlue3: String       = s"[${AnsiColor.BLUE}]"
  def ansiColorMagenta3: String    = s"[${AnsiColor.MAGENTA}]"
  def ansiColorCyan3: String       = s"[${AnsiColor.CYAN}]"
  def ansiColorWhite3: String      = s"[${AnsiColor.WHITE}]"
  def ansiColorBlackB3: String     = s"[${AnsiColor.BLACK_B}]"
  def ansiColorRedB3: String       = s"[${AnsiColor.RED_B}]"
  def ansiColorGreenB3: String     = s"[${AnsiColor.GREEN_B}]"
  def ansiColorYellowB3: String    = s"[${AnsiColor.YELLOW_B}]"
  def ansiColorBlueB3: String      = s"[${AnsiColor.BLUE_B}]"
  def ansiColorMagentaB3: String   = s"[${AnsiColor.MAGENTA_B}]"
  def ansiColorCyanB3: String      = s"[${AnsiColor.CYAN_B}]"
  def ansiColorWhiteB3: String     = s"[${AnsiColor.WHITE_B}]"
  def ansiColorReset3: String      = s"[${AnsiColor.RESET}]"
  def ansiColorBold3: String       = s"[${AnsiColor.BOLD}]"
  def ansiColorUnderlined3: String = s"[${AnsiColor.UNDERLINED}]"
  def ansiColorBlink3: String      = s"[${AnsiColor.BLINK}]"
  def ansiColorReversed3: String   = s"[${AnsiColor.REVERSED}]"
  def ansiColorInvisible3: String  = s"[${AnsiColor.INVISIBLE}]"

  def consoleReadBoolean                        = StdIn.readBoolean()
  def consoleReadByte                           = StdIn.readByte()
  def consoleReadChar                           = StdIn.readChar()
  def consoleReadDouble                         = StdIn.readDouble()
  def consoleReadFloat                          = StdIn.readFloat()
  def consoleReadInt                            = StdIn.readInt()
  def consoleReadLine                           = StdIn.readLine()
  def consoleReadLine(text: String, args: Any*) = StdIn.readLine(text, args: _*)
  def consoleReadLong                           = StdIn.readLong()
  def consoleReadShort                          = StdIn.readShort()
  def consoleReadf(format: String)              = StdIn.readf(format)
  def consoleReadf1(format: String)             = StdIn.readf1(format)
  def consoleReadf2(format: String)             = StdIn.readf2(format)
  def consoleReadf3(format: String)             = StdIn.readf3(format)

  def arrow1: PartialFunction[Int, String] = {
    case 0          => "zero"
    case 1           => "one"
    case 2 =>           "two"
    case n if n > 10 => "ginormous"
  }
  def arrow2(f: Int => String) = f(1)
  def arrow3 = {
    import scala.{ PartialFunction => ?=> }
    def f: Int ?=> String = {
      case 1 => "one"
    }
  }
}
