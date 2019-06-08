package impl

import scalafix.v1._

import scala.collection.mutable
import scala.meta._

final class GlobalImports {
  private val known = mutable.Set.empty[String]
  private val added = mutable.ListBuffer.empty[Patch]

  def patch: Patch = added.toList.asPatch

  def add(importer: Importer): Unit =
    if (known.add(importer.syntax))
      added += Patch.addGlobalImport(importer)
}
