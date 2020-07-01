/*
rule = fix.scala213.Varargs
*/
package fix.scala213

class Varargs212 {
  def foo(xs: scala.collection.Seq[String]) = List(xs: _*)
}
