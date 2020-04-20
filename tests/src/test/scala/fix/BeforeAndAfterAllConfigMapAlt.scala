package fix

import org.scalatest._

object BeforeAndAfterAllConfigMapAlt {
  private def anExceptionThatShouldCauseAnAbort(throwable: Throwable): Boolean = throwable match {
    case _: java.lang.annotation.AnnotationFormatError |
         _: java.nio.charset.CoderMalfunctionError |
         _: javax.xml.parsers.FactoryConfigurationError |
         _: LinkageError |
         _: ThreadDeath |
         _: javax.xml.transform.TransformerFactoryConfigurationError |
         _: VirtualMachineError                         => true
    case e if e.getClass.getName == "java.awt.AWTError" => true
    case _                                              => false
  }
}; import BeforeAndAfterAllConfigMapAlt._

trait BeforeAndAfterAllConfigMapAlt extends SuiteMixin { _: Suite =>
  protected def beforeAllAlt(configMap: ConfigMap) = ()
  protected def afterAllAlt(configMap: ConfigMap) = ()

  abstract override def run(testName: Option[String], args: Args): Status = {
    val shouldRun = !args.runTestInNewInstance && expectedTestCount(args.filter) > 0
    var exception: Exception = null
    var status: Status = FailedStatus

    try {
      if (shouldRun) beforeAllAlt(args.configMap)
      status = super.run(testName, args)
    } catch {case e: Exception => exception = e}

    try {
      val statusToReturn = if (shouldRun) {
        status.withAfterEffect {
          try afterAllAlt(args.configMap) catch {
            case e: Exception if !anExceptionThatShouldCauseAnAbort(e) && exception != null =>
          }
        }
      } else status
      if (exception != null) throw exception
      statusToReturn
    } catch {
      case e: Exception =>
        if (exception != null) throw exception
        throw e
    }
  }
}

