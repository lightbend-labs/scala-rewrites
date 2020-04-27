package fix.scala213

trait Matchers {
  class AnyShouldWrapper[T] {
    def shouldBe[U](right: U) = ???
  }
}

trait ExplicitNonNullaryApplyInfixSpec extends Matchers {
  // we name the defs corresponding to ApplyInfix(lhs, op, tags, args)
  def lhs(): AnyShouldWrapper[Int]
  def arg() = ""

  lhs() shouldBe arg()
  lhs() shouldBe[String] arg()
  lhs() shouldBe arg()
  lhs() shouldBe arg()
  lhs() shouldBe arg()

  lhs() shouldBe (arg())
}
