
package fix.scala213.explicitNonNullaryApply

import scala.language.postfixOps

class Postfix {
  self.meth();
//self meth();   // won't typecheck - parsed as self.meth(())


  qual().meth();
  qual().meth();

//qual meth();   // won't typecheck - parsed as qual.meth(())
//qual() meth(); // won't typecheck - parsed as qual().meth(())
}
