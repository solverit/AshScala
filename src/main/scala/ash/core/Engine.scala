package ash.core

import scala.collection.mutable


class Engine
{
  private val entityList: mutable.ListBuffer[ Entity ] = mutable.ListBuffer.empty[Entity]
  private val systemList: mutable.ListBuffer[ System ] = mutable.ListBuffer.empty[System]
  private val families: mutable.HashMap[ Class[ _ ], Family ] = mutable.HashMap.empty[ Class[ _ ], Family ]

  def add(entity: Entity) {
    entityList.+:(entity)
    families.foreach(family => family._2.newEntity(entity))
  }

  def remove(entity: Entity) {
    families.foreach(family => family._2.removeEntity(entity))
    entityList.-(entity)
  }

  def removeAllEntities() {
    while (!entityList.isEmpty) {
      remove(entityList.head)
    }
  }
}
