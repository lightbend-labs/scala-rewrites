package fix.scala213

class ExplicitNonNullaryApply {
  def multiParamsListTest = {
    def await()(implicit time: Int) = ???
    implicit val time: Int = 1
    await() // fix
    await() // keep
  }

  val enna = new ExplicitNonNullaryApply()

  def prop            = ""
  def meth()          = ""

  def id[A](x: A)     = x


  def def_prop_p = prop
  def def_meth_p = meth()
  def def_meth_m = meth()

  def def_id    = id("")
  def def_id_ta = id[String]("")


  def def_this_prop_p    = this.prop
  def def_this_meth_p    = this.meth()
  def def_this_meth_m    = this.meth()
//def def_this_meth_m_in = this meth ()

  def def_this_id_m       = this.id("")
  def def_this_id_m_ta    = this.id[String]("")
  def def_this_id_m_in    = this id ""
  def def_this_id_m_in_ta = this id[String] ""


  def def_enna_prop_p    = enna.prop
  def def_enna_meth_p    = enna.meth()
  def def_enna_meth_m    = enna.meth()
//def def_enna_meth_m_in = enna meth ()

  def def_enna_id_m       = enna.id("")
  def def_enna_id_m_ta    = enna.id[String]("")
  def def_enna_id_m_in    = enna id ""
  def def_enna_id_m_in_ta = enna id[String] ""


  def def_this_enna_prop_p    = this.enna.prop
  def def_this_enna_meth_p    = this.enna.meth()
  def def_this_enna_meth_m    = this.enna.meth()
//def def_this_enna_meth_m_in = this.enna meth ()

  def def_this_enna_id_m       = this.enna.id("")
  def def_this_enna_id_m_ta    = this.enna.id[String]("")
  def def_this_enna_id_m_in    = this.enna id ""
  def def_this_enna_id_m_in_ta = this.enna id[String] ""


  def def_enna_this_prop_p    = ExplicitNonNullaryApply.this.prop
  def def_enna_this_meth_p    = ExplicitNonNullaryApply.this.meth()
  def def_enna_this_meth_m    = ExplicitNonNullaryApply.this.meth()
//def def_enna_this_meth_m_in = ExplicitNonNullaryApply.this meth ()

  def def_enna_this_id_m       = ExplicitNonNullaryApply.this.id("")
  def def_enna_this_id_m_ta    = ExplicitNonNullaryApply.this.id[String]("")
  def def_enna_this_id_m_in    = ExplicitNonNullaryApply.this id ""
  def def_enna_this_id_m_in_ta = ExplicitNonNullaryApply.this id[String] ""


  def def_enna_enna_prop_p    = enna.enna.prop
  def def_enna_enna_meth_p    = enna.enna.meth()
  def def_enna_enna_meth_m    = enna.enna.meth()
//def def_enna_enna_meth_m_in = enna.enna meth ()

  def def_enna_enna_id_m       = enna.enna.id("")
  def def_enna_enna_id_m_ta    = enna.enna.id[String]("")
  def def_enna_enna_id_m_in    = enna.enna id ""
  def def_enna_enna_id_m_in_ta = enna.enna id[String] ""

  def eta = meth _

  trait testPostfixOps {
    import scala.language.postfixOps

    def qual(): ExplicitNonNullaryApply

    enna.meth();
//    enna meth() // won't compile

    qual().meth();
    qual().meth();
//    qual meth()   // won't compile
//    qual() meth() // won't compile
    qual().meth();
    qual().meth();
    qual().meth()
    qual().meth()
  }
}
