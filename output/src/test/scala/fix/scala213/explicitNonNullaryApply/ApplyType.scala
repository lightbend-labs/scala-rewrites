
package fix.scala213.explicitNonNullaryApply

class ApplyType {
  object Props   { def apply[A]()    = this }
  object Props2  { def apply[A, B]() = this }
  object Nullary { def apply[A]      = this }

  Props.apply[Int]() // fix
  Props[Int]()       // fix
  Props[Int]()     // keep

  Props2.apply[Int, String]() // fix
  Props2[Int, String]()       // fix

  Nullary[Int] // keep
}
