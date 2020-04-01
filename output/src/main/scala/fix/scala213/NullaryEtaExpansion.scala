package fix.scala213

class NullaryEtaExpansion {
  val str1         = ""
  def prop         = ""
  def meth()       = ""
  def void(x: Any) = ""

  def def_str1 = () => str1 // rewrite
  def def_prop = () => prop // rewrite
  def def_meth = meth _ // leave
  def def_idty = void _ // leave

  val val_str1 = () => str1 // rewrite
  val val_prop = () => prop // rewrite
  val val_meth = meth _ // leave
  val val_idty = void _ // leave

  var var_str1 = () => str1 // rewrite
  var var_prop = () => prop // rewrite
  var var_meth = meth _ // leave
  var var_idty = void _ // leave

  lazy val lzy_str1 = () => str1 // rewrite
  lazy val lzy_prop = () => prop // rewrite
  lazy val lzy_meth = meth _ // leave
  lazy val lzy_idty = void _ // leave
}
