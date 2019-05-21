/*
rule = lint.Scala_2_13
*/
package lint

class Scala_2_13 {
  def arrow1: PartialFunction[Any, String] = {
    case 0      => "zero"
    case 1       ⇒ "one" // assert: lint.Scala_2_13.unicodeDoubleArrow
    case 2 ⇒       "two" // assert: lint.Scala_2_13.unicodeDoubleArrow
    case n: Int  ⇒ "ginormous" // assert: lint.Scala_2_13.unicodeDoubleArrow
    case "⇒"    => "?"
  }
  def arrow2(f: Int ⇒ String) = f(1) // assert: lint.Scala_2_13.unicodeDoubleArrow
  def arrow3 = {
    import scala.{ PartialFunction ⇒ ?=> } // assert: lint.Scala_2_13.unicodeDoubleArrow
    def f: Int ?=> String = {
      case 1 => "one"
    }
  }
  def arrow4 = Some(1).map(x ⇒ x.toString) // assert: lint.Scala_2_13.unicodeDoubleArrow

  def singleArrow1: (Int, Int) = 1 → 1 // assert: lint.Scala_2_13.unicodeArrow
  def singleArrow2: (Int, Int) = 1.→(1) // assert: lint.Scala_2_13.unicodeArrow

  def symbolLiteral0 = Symbol("foo")
  def symbolLiteral1 = 'foo // assert: lint.Scala_2_13.symbolLiteral
}
