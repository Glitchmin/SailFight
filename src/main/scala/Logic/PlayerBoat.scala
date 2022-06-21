
package Logic

import scala.math.{cos, max, min, sin, toRadians}


object PlayerBoat {
  val steerDeflectionSpeed = 10.0; //degrees per second
  val maxDeflection = 60.0; //degrees
}

class PlayerBoat(private var speed: Double, private var heading: Double, var position: Vector2d,
                 private var steerDeflection: Double = 0.0, private var lastTimeEval: Int = 0) {

  private def nanoSecondIntoSecond(nanoTime: Long): Double = {
    nanoTime / 1e9
  }

  def calcPosition(nanoTimeElapsed: Long): Unit = {
    val rad = toRadians(heading)
    position += Vector2d((nanoTimeElapsed * sin(rad)) / 1E8, -(nanoTimeElapsed * cos(rad)) / 1E8)
    heading += steerDeflection * nanoSecondIntoSecond(nanoTimeElapsed)
  }


  def deflectSteerRight(nanoTimeElapsed: Long): Unit = {
    steerDeflection += PlayerBoat.steerDeflectionSpeed * nanoSecondIntoSecond(nanoTimeElapsed)
    steerDeflection = min(PlayerBoat.maxDeflection, steerDeflection)
  }

  def deflectSteerLeft(nanoTimeElapsed: Long): Unit = {
    steerDeflection -= PlayerBoat.steerDeflectionSpeed * nanoSecondIntoSecond(nanoTimeElapsed)
    steerDeflection = max(-PlayerBoat.maxDeflection, steerDeflection)
  }

  def SteerDeflection: Double = {
    steerDeflection
  }

}
