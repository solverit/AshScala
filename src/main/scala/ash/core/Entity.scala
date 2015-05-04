package ash.core

import scala.collection.mutable

/**
 * An entity is composed from components. As such, it is essentially a collection object for components.
 * Sometimes, the entities in a game will mirror the actual characters and objects in the game, but this
 * is not necessary.
 *
 * <p>Components are simple value objects that contain data relevant to the entity. Entities
 * with similar functionality will have instances of the same components. So we might have
 * a position component</p>
 *
 * <p><code>public class PositionComponent
 * {
 * var x : Double;
 * var y : Double;
 * }</code></p>
 *
 * <p>All entities that have a position in the game world, will have an instance of the
 * position component. Systems operate on entities based on the components they have.</p>
 */

trait Entity {
  var name: String
  val components: mutable.HashMap[Class[_ <: Component], Component] = mutable.HashMap.empty[Class[_ <: Component], Component]

  /**
   *
   * @param component
   * @return
   */
  def add(component: Component): Entity = {
    add(component.getClass, component)
  }

  /**
   * Faster way to add a component to the Entity
   *
   * @param klass
   * @param component
   * @return
   */
  def add(klass: Class[_ <: Component], component: Component): Entity = {
    if(has(klass)) {
      remove(klass)
    }
    component.entity = this
    components.put(klass, component)
    this
  }

  def remove(klass: Class[_ <: Component]): Option[Component] = {
    if(has(klass)) {
      val component: Option[Component] = components.remove(klass)
      component.get.entity = null
      component
    }
    else {
      None
    }
  }

  def has(klass: Class[_ <: Component]): Boolean = {
    components.contains(klass)
  }
}
