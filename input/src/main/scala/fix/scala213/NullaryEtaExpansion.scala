/*
rule = fix.scala213.ExplicitNullaryEtaExpansion
*/
package fix.scala213

class NullaryEtaExpansion {
  val str1         = ""
  def prop         = ""
  def meth()       = ""
  def void(x: Any) = ""

  def def_str1 = str1 _ // rewrite
  def def_prop = prop _ // rewrite
  def def_meth = meth _ // leave
  def def_idty = void _ // leave

  val val_str1 = str1 _ // rewrite
  val val_prop = prop _ // rewrite
  val val_meth = meth _ // leave
  val val_idty = void _ // leave

  var var_str1 = str1 _ // rewrite
  var var_prop = prop _ // rewrite
  var var_meth = meth _ // leave
  var var_idty = void _ // leave

  lazy val lzy_str1 = str1 _ // rewrite
  lazy val lzy_prop = prop _ // rewrite
  lazy val lzy_meth = meth _ // leave
  lazy val lzy_idty = void _ // leave
}
