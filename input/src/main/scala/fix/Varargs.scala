/*
rule = Varargs
*/
package fix

class Varargs {
  def foo(xs: scala.collection.Seq[String]) = List(xs: _*)
}
