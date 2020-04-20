
package fix.scala213

abstract class Any2StringAdd {
  // Strings: leave as-is, both literal strings and non-literal
  def s = "bob"
  def str1 = s + s
  def str2 = s + "bob"
  def str3 = "bob" + s
  def str4 = "bob" + "fred"

  // Non-strings: wrap with String.valueOf
  def n: Any = null
  def nil = String.valueOf(Nil) + s
  def none = String.valueOf(None) + s
  def Null = String.valueOf(n) + s

  // Non-string, generic type: wrap with String.valueOf
  type A
  def x: A
  def generic = String.valueOf(x) + "bob"

  // Numeric primitives: add toString
  def byte = 1.toByte
  def short = 1.toShort
  def char = 'a'
  def int = 1
  def long = 1L
  def float = 1.0F
  def double = 1.0
  //
  def byte1 = "" + byte + s
  def byte2 = byte + byte
  def short1 = "" + short + s
  def short2 = short + short
  def char1 = "" + char + s
  def char2 = char + char
  def int1 = "" + int + s
  def int2 = int + int
  def long1 = "" + long + s
  def long2 = long + long
  def float1 = "" + float + s
  def float2 = float + float
  def double1 = "" + double + s
  def double2 = double + double

  // Boolean: wrap with String.valueOf (there's no + on Boolean, AFAICT)
  def bool = true
  def bool1 = String.valueOf(bool) + s

  // Scala's Unit primitive: wrap with String.valueOf (there's no + on Unit, AFAICT)
  def unit = ()
  def unit1 = String.valueOf(unit) + s

  // Custom value types: wrap with String.valueOf (adding toString would be preferable)
  import Any2StringAdd.Name
  //val name: Name = null // type mismatch; found: Null(null), required: Name
  val name = new Name(null)
  def name1 = String.valueOf(name) + s

  // With infix operators, make sure to use parens
  def parens1 = String.valueOf(Nil ++ Nil) + s
  def parens2 = "" + (int + int) + s
  def parens3 = String.valueOf({Nil ++ Nil}) + s
}

object Any2StringAdd {
  final class Name(val value: String) extends AnyVal { override def toString = value }
}
