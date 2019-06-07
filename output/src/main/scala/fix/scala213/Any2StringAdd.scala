package fix.scala213

abstract class Any2StringAdd {
  // Strings: leave as-is, both literal strings and non-literal
  def s = "bob"
  def str1 = s + s
  def str2 = s + "bob"
  def str3 = "bob" + s
  def str4 = "bob" + "fred"

  // Non-strings: add toString
  def nil = Nil.toString + s

  // Non-string, generic type: add toString
  type A
  def x: A
  def generic = x.toString + "bob"

  // Primitives: add toString
  def unit = ()
  def bool = true
  def byte = 1.toByte
  def short = 1.toShort
  def char = 'a'
  def int = 1
  def long = 1L
  def float = 1.0F
  def double = 1.0
  //
  def unit1 = unit.toString + s
  def bool1 = bool.toString + s
  def byte1 = byte.toString + s
  def byte2 = byte + byte
  def short1 = short.toString + s
  def short2 = short + short
  def char1 = char.toString + s
  def char2 = char + char
  def int1 = int.toString + s
  def int2 = int + int
  def long1 = long.toString + s
  def long2 = long + long
  def float1 = float.toString + s
  def float2 = float + float
  def double1 = double.toString + s
  def double2 = double + double

  // With infix operators, make sure to use parens
  def parens1 = (Nil ++ Nil).toString + s
  def parens2 = (int + int).toString + s
  def parens3 = {Nil ++ Nil}.toString + s
}
