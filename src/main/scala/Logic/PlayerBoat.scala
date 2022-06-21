package Logic

import Gui.KeyPolling
import scalafx.scene.input.KeyCode

import scala.math.{cos, max, min, sin, toRadians}

object PlayerBoat {
  val steerDeflectionSpeed = 10.0; //degrees per second
  val maxDeflection = 60.0; //degrees
}


class PlayerBoat (private val keyPolling: KeyPolling,
                  private val keyMap: PlayerControlsKeymap,
                  private var speed: Double, private var heading: Double, var position: Vector2d,
                  private var steerDeflection: Double = 0.0, private var lastTimeEval: Int = 0){
  def handleInput(timeElapsed: Long): Unit = {
    if (keyPolling.isDown(keyMap.backward)) {
      position += Vector2d(0, timeElapsed / 1E7)
    }
    if (keyPolling.isDown(keyMap.forward)) {
      position += Vector2d(0, -timeElapsed / 1E7)
    }
    if (keyPolling.isDown(keyMap.steerLeft)) {
      position += Vector2d(-timeElapsed / 1E7, 0)
    }
    if (keyPolling.isDown(keyMap.steerRight)) {
      position += Vector2d(timeElapsed / 1E7, 0)
    }

  }

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
