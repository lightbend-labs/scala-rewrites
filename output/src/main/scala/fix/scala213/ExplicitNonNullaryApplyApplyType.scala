package fix.scala213

class ExplicitNonNullaryApplyApplyType {

  object Props {
    def apply[A]() = ???
  }

  object Props2 {
    def apply[A, B]() = ???
  }

  object Nullary {
    def apply[A] = ???
    def foo() = ???
  }

  Props.apply[Int]() // fix
  Props[Int]() // fix
  Props[Int]() // keep

  Props2.apply[Int, String]() // fix
  Props2[Int, String]() // fix

  Nullary[Int] // keep
}
