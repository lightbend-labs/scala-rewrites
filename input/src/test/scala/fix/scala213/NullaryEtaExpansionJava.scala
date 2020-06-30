/*
rule = fix.scala213.ExplicitNullaryEtaExpansion
*/
package fix.scala213

class NullaryEtaExpansionJava {
  class ToS1 { override      def toString(): String = "" }
  class ToS2 { override      def toString: String   = "" }
  class ToS3 { override      val toString: String   = "" }
  class ToS4 { override lazy val toString: String   = "" }

  val toS1 = new ToS1
  val toS2 = new ToS2
  val toS3 = new ToS3
  val toS4 = new ToS4

       val val_toS1 = toS1.toString _
       var var_toS1 = toS1.toString _
       def def_toS1 = toS1.toString _
  lazy val lzy_toS1 = toS1.toString _

       val val_toS2 = toS2.toString _
       var var_toS2 = toS2.toString _
       def def_toS2 = toS2.toString _
  lazy val lzy_toS2 = toS2.toString _

       val val_toS3 = toS3.toString _
       var var_toS3 = toS3.toString _
       def def_toS3 = toS3.toString _
  lazy val lzy_toS3 = toS3.toString _

       val val_toS4 = toS4.toString _
       var var_toS4 = toS4.toString _
       def def_toS4 = toS4.toString _
  lazy val lzy_toS4 = toS4.toString _
}
