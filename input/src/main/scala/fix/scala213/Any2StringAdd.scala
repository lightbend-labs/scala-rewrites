/*
rule = fix.scala213.Any2StringAdd
*/
package fix.scala213

abstract class Any2StringAdd {
  // Strings: leave as-is, both literal strings and non-literal
  def s = "bob"
  def str1 = s + s
  def str2 = s + "bob"
  def str3 = "bob" + s
  def str4 = "bob" + "fred"

  // Non-strings: add toString
  def nil = Nil + s

  // Non-string, generic type: add toString
  type A
  def x: A
  def generic = x + "bob"

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
  def unit1 = unit + s
  def bool1 = bool + s
  def byte1 = byte + s              // TODO
  def short1 = short + s            // TODO
  def char1 = char + s              // TODO
  def int1 = int + s                // TODO
  def long1 = long + s              // TODO
  def float1 = float + s            // TODO
  def double1 = double + s          // TODO

  // With infix operators, make sure to use parens
  def parens1 = Nil ++ Nil + s      // TODO
  def parens2 = int + int + s       // TODO
}
