/*
rule = fix.scala213.ExplicitNonNullaryApply
*/
package fix.scala213

class ExplicitNonNullaryApply {
  val enna = new ExplicitNonNullaryApply()

  def prop            = ""
  def meth()          = ""

  def propNullAs[A]   = null.asInstanceOf[A]
  def methNullAs[A]() = null.asInstanceOf[A]

  def id[A](x: A)     = x


  def def_prop_p = prop
  def def_meth_p = meth
  def def_meth_m = meth()

  def def_propNullAs_p    = propNullAs
  def def_propNullAs_p_ta = propNullAs[String]
  def def_methNullAs_p    = methNullAs
  def def_methNullAs_p_ta = methNullAs[String]
  def def_methNullAs_m    = methNullAs()
  def def_methNullAs_m_ta = methNullAs[String]()

  def def_id    = id("")
  def def_id_ta = id[String]("")


  def def_this_prop_p    = this.prop
  def def_this_meth_p    = this.meth
  def def_this_meth_m    = this.meth()
  def def_this_meth_m_in = this meth ()

  def def_this_propNullAs_p       = this.propNullAs
  def def_this_propNullAs_p_ta    = this.propNullAs[String]
  def def_this_methNullAs_p       = this.methNullAs
  def def_this_methNullAs_p_ta    = this.methNullAs[String]
  def def_this_methNullAs_m       = this.methNullAs()
  def def_this_methNullAs_m_ta    = this.methNullAs[String]()
  def def_this_methNullAs_m_in    = this methNullAs         ()
  def def_this_methNullAs_m_in_ta = this methNullAs[String] ()

  def def_this_id_m       = this.id("")
  def def_this_id_m_ta    = this.id[String]("")
  def def_this_id_m_in    = this id ""
  def def_this_id_m_in_ta = this id[String] ""


  def def_enna_prop_p    = enna.prop
  def def_enna_meth_p    = enna.meth
  def def_enna_meth_m    = enna.meth()
  def def_enna_meth_m_in = enna meth ()

  def def_enna_propNullAs_p       = enna.propNullAs
  def def_enna_propNullAs_p_ta    = enna.propNullAs[String]
  def def_enna_methNullAs_p       = enna.methNullAs
  def def_enna_methNullAs_p_ta    = enna.methNullAs[String]
  def def_enna_methNullAs_m       = enna.methNullAs()
  def def_enna_methNullAs_m_ta    = enna.methNullAs[String]()
  def def_enna_methNullAs_m_in    = enna methNullAs         ()
  def def_enna_methNullAs_m_in_ta = enna methNullAs[String] ()

  def def_enna_id_m       = enna.id("")
  def def_enna_id_m_ta    = enna.id[String]("")
  def def_enna_id_m_in    = enna id ""
  def def_enna_id_m_in_ta = enna id[String] ""


  def def_this_enna_prop_p    = this.enna.prop
  def def_this_enna_meth_p    = this.enna.meth
  def def_this_enna_meth_m    = this.enna.meth()
  def def_this_enna_meth_m_in = this.enna meth ()

  def def_this_enna_propNullAs_p       = this.enna.propNullAs
  def def_this_enna_propNullAs_p_ta    = this.enna.propNullAs[String]
  def def_this_enna_methNullAs_p       = this.enna.methNullAs
  def def_this_enna_methNullAs_p_ta    = this.enna.methNullAs[String]
  def def_this_enna_methNullAs_m       = this.enna.methNullAs()
  def def_this_enna_methNullAs_m_ta    = this.enna.methNullAs[String]()
  def def_this_enna_methNullAs_m_in    = this.enna methNullAs         ()
  def def_this_enna_methNullAs_m_in_ta = this.enna methNullAs[String] ()

  def def_this_enna_id_m       = this.enna.id("")
  def def_this_enna_id_m_ta    = this.enna.id[String]("")
  def def_this_enna_id_m_in    = this.enna id ""
  def def_this_enna_id_m_in_ta = this.enna id[String] ""


  def def_enna_this_prop_p    = ExplicitNonNullaryApply.this.prop
  def def_enna_this_meth_p    = ExplicitNonNullaryApply.this.meth
  def def_enna_this_meth_m    = ExplicitNonNullaryApply.this.meth()
  def def_enna_this_meth_m_in = ExplicitNonNullaryApply.this meth ()

  def def_enna_this_propNullAs_p       = ExplicitNonNullaryApply.this.propNullAs
  def def_enna_this_propNullAs_p_ta    = ExplicitNonNullaryApply.this.propNullAs[String]
  def def_enna_this_methNullAs_p       = ExplicitNonNullaryApply.this.methNullAs
  def def_enna_this_methNullAs_p_ta    = ExplicitNonNullaryApply.this.methNullAs[String]
  def def_enna_this_methNullAs_m       = ExplicitNonNullaryApply.this.methNullAs()
  def def_enna_this_methNullAs_m_ta    = ExplicitNonNullaryApply.this.methNullAs[String]()
  def def_enna_this_methNullAs_m_in    = ExplicitNonNullaryApply.this methNullAs         ()
  def def_enna_this_methNullAs_m_in_ta = ExplicitNonNullaryApply.this methNullAs[String] ()

  def def_enna_this_id_m       = ExplicitNonNullaryApply.this.id("")
  def def_enna_this_id_m_ta    = ExplicitNonNullaryApply.this.id[String]("")
  def def_enna_this_id_m_in    = ExplicitNonNullaryApply.this id ""
  def def_enna_this_id_m_in_ta = ExplicitNonNullaryApply.this id[String] ""


  def def_enna_enna_prop_p    = enna.enna.prop
  def def_enna_enna_meth_p    = enna.enna.meth
  def def_enna_enna_meth_m    = enna.enna.meth()
  def def_enna_enna_meth_m_in = enna.enna meth ()

  def def_enna_enna_propNullAs_p       = enna.enna.propNullAs
  def def_enna_enna_propNullAs_p_ta    = enna.enna.propNullAs[String]
  def def_enna_enna_methNullAs_p       = enna.enna.methNullAs
  def def_enna_enna_methNullAs_p_ta    = enna.enna.methNullAs[String]
  def def_enna_enna_methNullAs_m       = enna.enna.methNullAs()
  def def_enna_enna_methNullAs_m_ta    = enna.enna.methNullAs[String]()
  def def_enna_enna_methNullAs_m_in    = enna.enna methNullAs         ()
  def def_enna_enna_methNullAs_m_in_ta = enna.enna methNullAs[String] ()

  def def_enna_enna_id_m       = enna.enna.id("")
  def def_enna_enna_id_m_ta    = enna.enna.id[String]("")
  def def_enna_enna_id_m_in    = enna.enna id ""
  def def_enna_enna_id_m_in_ta = enna.enna id[String] ""
}
