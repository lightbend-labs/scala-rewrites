package fix.scala213

import scala.{collection => sc}
import scala.collection.{immutable => sci}
class ScalaSeq {
  def f(a: sc.Seq[Int], b: List[sc.Seq[Int]]): sci.Seq[Any] = List(a, b)
}
