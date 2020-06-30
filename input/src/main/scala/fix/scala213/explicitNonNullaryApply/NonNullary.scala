/*
rule = fix.scala213.ExplicitNonNullaryApply
*/
package fix.scala213.explicitNonNullaryApply

class NonNullary {
  meth
  meth()

  self.meth
  self.meth()

  qual.meth
  qual.meth()

  qual().meth
  qual().meth()


  meth _


  ???.getClass()
  ???.getClass

  any.getClass()
  any.getClass

  ref.getClass()
  ref.getClass

  obj.getClass()
  obj.getClass

  str.getClass()
  str.getClass


  ???.hashCode()
  ???.hashCode

  any.hashCode()
  any.hashCode

  ref.hashCode()
  ref.hashCode

  obj.hashCode()
  obj.hashCode

  str.hashCode()
  str.hashCode


  cm.run()
  cm.run
  cp.run()
  cp.run
}
