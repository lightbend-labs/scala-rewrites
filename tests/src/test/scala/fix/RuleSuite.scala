package fix

import scala.reflect.ensureAccessible
import org.scalatest.ConfigMap
import scalafix.testkit.SemanticRuleSuite

class RuleSuite extends SemanticRuleSuite with BeforeAndAfterAllConfigMapAlt {
  val isSaveExpectField = ensureAccessible(classOf[SemanticRuleSuite].getDeclaredField("isSaveExpect"))

  // If you invoke the test as "tests/testOnly -- -Doverwrite=true" it will save the expected
  override protected def beforeAllAlt(configMap: ConfigMap) = {
    val overwrite = configMap.getWithDefault("overwrite", "false").equalsIgnoreCase("true")
    isSaveExpectField.setBoolean(this, overwrite)
  }

  runAllTests()
}
