package ash.core

import ash.core.{Component, Node}

import scala.collection.mutable


class Engine {
  private val entityList: mutable.ListBuffer[Entity] = mutable.ListBuffer.empty[Entity]
  private val systemList: mutable.ListBuffer[System] = mutable.ListBuffer.empty[System]
  private val families: mutable.HashMap[_ <: Node, Family] = mutable.HashMap.empty[_ <: Node, Family]

  def add(entity: Entity) {
    entityList.+:(entity)
    families.foreach(family => family._2.newEntity(entity))
  }

  def remove(entity: Entity) {
    families.foreach(family => family._2.removeEntity(entity))
    entityList.-(entity)
  }

  def removeAllEntities() {
    entityList.clear()
  }

  def getEntities(): List[Entity] = {
    entityList.result()
  }

  def componentAdded(entity: Entity, klass: _ <: Component) {
    families.foreach((f: (_ <: Node, Family)) => f._2.componentAddedToEntity(entity, f._1) )
  }

  def componentRemoved(entity: Entity, klass: _ <: Component) {
    families.foreach((f: (_ <: Node, Family)) => f._2.componentRemovedFromEntity(entity, f._1) )
  }

  def releaseNodeList(klass: _ <: Node) {
    val family = families.remove(klass)
    if (family.get != null) {
      family.get.cleanUp()
    }
  }

    def add(system:System) {
      systemList.+:(system)
    }

    def remove(system:System) {
      systemList.-(system)
    }

    def removeAllSystems() {
      systemList.clear()
    }

    def clear() {
      removeAllEntities()
      removeAllSystems()
    }
}
