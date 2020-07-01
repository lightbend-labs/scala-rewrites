/*
rule = fix.scala213.NullaryHashHash
*/
package fix.scala213

object NullaryHashHash {
  ("": Any).##
  ("": AnyRef).##
  ("": Object).##
  ("": String).##
  "".##
}
