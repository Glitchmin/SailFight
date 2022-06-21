package Logic

import scala.math.{cos, sin, toRadians}

trait MovableObject {
  protected var speed: Double; protected var heading: Double; var position: Vector2d
  def calcPosition(nanoTimeElapsed: Long): Unit = {
    val rad = toRadians(heading)
    position += Vector2d((nanoTimeElapsed * sin(rad)) / 1E7, -(nanoTimeElapsed * cos(rad)) / 1E7)
  }
}
