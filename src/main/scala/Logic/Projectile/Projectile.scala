package Logic.Projectile

import Gui.Drawable
import Logic.{MovableObject, PlayerBoat, Vector2d}
import scalafx.scene.Node
import scalafx.scene.shape.Circle

class Projectile (var speed: Double, var heading: Double, var position: Vector2d, var dmg: Int) extends MovableObject with Drawable {
  def dealDmg(playerBoat: PlayerBoat): Unit ={
    playerBoat.subtractFromHP(dmg)
  }
  val circle: Circle = new Circle()
  circle.scaleX = 10
  circle.scaleY = 10
  circle.radius = 0.72137
  override def getNodes: List[Node] = {
    List(circle)
  }

  override def refresh(): Unit = {
    circle.centerX = position.x
    circle.centerY = position.y
  }
  refresh()
}
