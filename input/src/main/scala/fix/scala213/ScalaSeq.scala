/*
rule = fix.scala213.ScalaSeq
fix.scala213.ScalaSeq = {
  paramType = "scala.collection.Seq",
  paramImport = "",
  otherType = "scala.collection.immutable.Seq",
  otherImport = ""
}
*/
package fix.scala213

class ScalaSeq {
  def f(a: Seq[Int], b: List[Seq[Int]]): Seq[Any] = List(a, b)
}
