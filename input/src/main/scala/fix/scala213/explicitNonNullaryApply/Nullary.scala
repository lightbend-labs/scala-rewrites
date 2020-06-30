/*
rule = fix.scala213.ExplicitNonNullaryApply
*/
package fix.scala213.explicitNonNullaryApply

class Nullary {
  prop
  self.prop
  qual.prop
  qual().prop

  ???.##
  any.##
  ref.##
  obj.##
  str.##

  ???.asInstanceOf[Int]
  any.asInstanceOf[Int]
  ref.asInstanceOf[Int]
  obj.asInstanceOf[Int]
  str.asInstanceOf[Int]

  ???.isInstanceOf[Int]
  any.isInstanceOf[Int]
  ref.isInstanceOf[Int]
  obj.isInstanceOf[Int]
  str.isInstanceOf[Int]
}
