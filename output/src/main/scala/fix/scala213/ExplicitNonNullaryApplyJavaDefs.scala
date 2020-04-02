package fix.scala213

object ExplicitNonNullaryApplyJavaDefs {
       class CM    extends Runnable with ExplicitNonNullaryApplyI { override def toString() = ""; def run() = (); def foo() = "" }
       class CP    extends Runnable with ExplicitNonNullaryApplyI { override def toString   = ""; def run   = (); def foo   = "" }

  case class CCM() extends Runnable with ExplicitNonNullaryApplyI { override def toString() = ""; def run() = (); def foo() = "" }
  case class CCP() extends Runnable with ExplicitNonNullaryApplyI { override def toString   = ""; def run   = (); def foo   = "" }

  class VCM(val x: String) extends AnyVal { override def toString() = "" }
  class VCP(val x: String) extends AnyVal { override def toString   = "" }

  case class VCCM(x: String) extends AnyVal { override def toString() = "" }
  case class VCCP(x: String) extends AnyVal { override def toString   = "" }

  val any: Any    = ""
  val ref: AnyRef = ""
  val obj: Object = ""
  val str: String = ""

  val cm: CM     = new CM()
  val cp: CP     = new CP()
  val vcm: VCM   = new VCM("")
  val vcp: VCP   = new VCP("")
  val ccm: CCM   = CCM()
  val ccp: CCP   = CCP()
  val vccm: VCCM = VCCM("")
  val vccp: VCCP = VCCP("")
}
