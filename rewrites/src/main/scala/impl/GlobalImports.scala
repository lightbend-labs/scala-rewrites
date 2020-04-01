package impl

import scalafix.v1._

import scala.collection.mutable
import scala.meta._

final class GlobalImports {
  private val known = mutable.Set.empty[String]

  def add(importer: Importer): Patch =
    if (known.add(importer.syntax))
      Patch.addGlobalImport(importer)
    else
      Patch.empty
}
