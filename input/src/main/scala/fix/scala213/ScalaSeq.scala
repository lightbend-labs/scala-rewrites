/*
rule = fix.scala213.ScalaSeq
fix.scala213.ScalaSeq = {
  paramType = "sc.Seq",
  paramImport = "scala.{collection => sc}",
  otherType = "sci.Seq",
  otherImport = "scala.collection.{immutable => sci}"
}
*/
package fix.scala213

class ScalaSeq {
  def f(a: Seq[Int], b: List[Seq[Int]]): Seq[Any] = List(a, b)
}
