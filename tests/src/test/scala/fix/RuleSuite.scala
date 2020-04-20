package fix

import scala.reflect.ensureAccessible
import org.scalatest.ConfigMap
import scalafix.testkit.SemanticRuleSuite

class RuleSuite extends SemanticRuleSuite with BeforeAndAfterAllConfigMapAlt {
  val isSaveExpectField = ensureAccessible(classOf[SemanticRuleSuite].getDeclaredField("isSaveExpect"))

  override protected def beforeAllAlt(configMap: ConfigMap) = {
    val overwrite = configMap.getWithDefault("overwrite", "false").equalsIgnoreCase("true")
    isSaveExpectField.setBoolean(this, overwrite)
  }

  runAllTests()
}
