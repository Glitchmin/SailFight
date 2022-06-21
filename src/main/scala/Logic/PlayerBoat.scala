package Logic

import Gui.KeyPolling
import scalafx.scene.input.KeyCode

import scala.math.{cos, max, min, sin, toRadians}

object PlayerBoat {
  val steerDeflectionSpeed = 90.0; //degrees per second
  val maxDeflection = 60.0; //degrees
}


class PlayerBoat (private val keyPolling: KeyPolling, private val keyMap: PlayerControlsKeymap,var speed: Double,
                  var heading: Double, var position: Vector2d, private var steerDeflection: Double = 0.0)
  extends MovableObject {
  def handleInput(timeElapsed: Long): Unit = {
    if (keyPolling.isDown(keyMap.backward)) {
      position += Vector2d(0, timeElapsed / 1E6)
    }
    if (keyPolling.isDown(keyMap.forward)) {
      position += Vector2d(0, -timeElapsed / 1E6)
    }
    if (keyPolling.isDown(keyMap.steerLeft)) {
      deflectSteerLeft(timeElapsed)
    }
    if (keyPolling.isDown(keyMap.steerRight)) {
      deflectSteerRight(timeElapsed)
    }

  }

  private def nanoSecondIntoSecond(nanoTime: Long): Double = {
    nanoTime / 1e9
  }

  override def calcPosition(nanoTimeElapsed: Long): Unit = {
    super.calcPosition(nanoTimeElapsed)
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
