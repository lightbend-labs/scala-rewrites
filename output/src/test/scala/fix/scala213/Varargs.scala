package fix.scala213

class Varargs {
  def foo(xs: scala.collection.Seq[String]) = List(xs.toSeq: _*)
  def bar = List(List(1,2,3): _*)
  def baz = List(scala.Seq(1,2,3): _*)
  // TODO
  // def biz = List(scala.collection.mutable.ArrayBuffer(1,2): _*)
}
