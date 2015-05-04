package ash.core

import ash.core.Component

/**
 * The interface for classes that are used to manage NodeLists (set as the familyClass property
 * in the Engine object). Most developers don't need to use this since the default implementation
 * is used by default and suits most needs.
 */

trait Family {
  def getNodeList(): List

  def newEntity(entity: Entity)

  def removeEntity(entity: Entity)

  def componentAddedToEntity(entity: Entity, klass: _ <: Node)

  def componentRemovedFromEntity(entity: Entity, klass: _ <: Node)

  def cleanUp()
}
