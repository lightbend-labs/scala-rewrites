
package fix.scala213.explicitNonNullaryApply

class Infix {
//this meth ()    // no point, parses as "this.meth(())" which doesn't typecheck

  qual() id qual()
  qual() id qual()
  qual() id qual()
  qual() id qual()

  qual() id (qual)
  qual() id ((qual))
  qual() id (((qual)))

  qual() id[Any] qual()
}
