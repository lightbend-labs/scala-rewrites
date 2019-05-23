/*
rule = Varargs
*/
package fix

class Varargs {
  def foo(xs: scala.collection.Seq[String]) = List(xs: _*)
  def bar = List(List(1,2,3): _*)
  def baz = List(scala.Seq(1,2,3): _*)
}
