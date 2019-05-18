package fix

class Varargs {
  def foo(xs: scala.collection.Seq[String]) = List(xs.toSeq: _*)
}
