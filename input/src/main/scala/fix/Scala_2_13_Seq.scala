/*
rule = fix.Scala_2_13_Seq
*/
package fix

class Scala_2_13_Seq {
  def f(a: Seq[Int], b: List[Seq[Int]]): Seq[Any] = List(a, b)
}
