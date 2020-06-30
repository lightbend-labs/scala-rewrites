/*
rule = fix.scala213.ExplicitNonNullaryApply
*/
package fix.scala213.explicitNonNullaryApply

// ExplicitNonNullaryApply, when methods are overridden
class Override {
  class Meth { def m2p() = "" }
  class Prop { def p2m   = "" }

  class Meth2Prop extends Meth { override def m2p   = "" }
  class Prop2Meth extends Prop { override def p2m() = "" }

  val meth2prop = new Meth2Prop
  val prop2meth = new Prop2Meth

  meth2prop.m2p()
  meth2prop.m2p // <- should call with parens

  prop2meth.p2m()
  prop2meth.p2m // <- should call with parens
}
