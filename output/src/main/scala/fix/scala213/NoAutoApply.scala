package fix.scala213

object NoAutoApply {
  object buz {
    override def toString(): String = ""
    def empty[T]() = List.empty[T]
  }
  val x: Iterator[Int] = ???
  def foo() = println(1)
  def bar = 32
  foo()
  println(foo())
  foo()
  bar
  x.next()
  class buz() {
    def qux() = List(1)
  }
  new buz().qux().toIterator.next()
  new java.util.ArrayList[Int]().toString
  val builder = List.newBuilder[Int]
  builder.result()
  builder result ()
  builder.result()
  fix.scala213.NoAutoApply.buz.empty[String]()
  var opt: Option[() => Int] = None
  opt = None
  println(1.toString())
  println(buz.toString()) // not inserted
  List(builder) map (_.result())
  builder.##
  def lzy(f: => Int) = {
    var k = f _
    k = () => 3
  }
}
