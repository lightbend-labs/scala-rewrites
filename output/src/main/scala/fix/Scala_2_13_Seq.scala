package fix

class Scala_2_13_Seq {
  def f(a: scala.collection.Seq[Int], b: List[scala.collection.Seq[Int]]): scala.collection.immutable.Seq[Any] = List(a, b)
}
