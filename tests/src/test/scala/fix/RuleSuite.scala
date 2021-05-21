package fix

import scala.reflect.ensureAccessible
import org.scalatest.{ ConfigMap, FunSpecLike }
import scalafix.testkit.AbstractSemanticRuleSuite

class RuleSuite extends AbstractSemanticRuleSuite with FunSpecLike with BeforeAndAfterAllConfigMapAlt {
  val isSaveExpectField = ensureAccessible(classOf[AbstractSemanticRuleSuite].getDeclaredField("isSaveExpect"))

  // If you invoke the test as "tests/testOnly -- -Doverwrite=true" it will save the expected
  override protected def beforeAllAlt(configMap: ConfigMap) = {
    val overwrite = configMap.getWithDefault("overwrite", "false").equalsIgnoreCase("true")
    isSaveExpectField.setBoolean(this, overwrite)
  }

  runAllTests()
}
