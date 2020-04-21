package fix.scala213

class ExplicitNonNullaryApplyOver {
  class Meth { def d() = "" }
  class Prop { def d   = "" }

  class Meth2Prop extends Meth { override def d   = "" }
  class Prop2Meth extends Prop { override def d() = "" }

  val meth2prop = new Meth2Prop
  val prop2meth = new Prop2Meth

  meth2prop.d()
  meth2prop.d() // <- should call with parens
  prop2meth.d()
  prop2meth.d() // <- should call with parens
}
