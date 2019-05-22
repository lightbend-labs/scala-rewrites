package fix.scala213

class ScalaSeq {
  def f(a: scala.collection.Seq[Int], b: List[scala.collection.Seq[Int]]): scala.collection.immutable.Seq[Any] = List(a, b)
}
