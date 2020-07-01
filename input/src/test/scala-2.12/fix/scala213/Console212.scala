/*
rule = fix.scala213.Core
*/
package fix.scala213

object Console212 {
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
