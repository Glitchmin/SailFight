package Logic

import Gui.{Drawable, ImageLoader, KeyPolling}
import Logic.PlayerBoat.speedChangePerSec
import scalafx.scene.Node
import scalafx.scene.image.ImageView

import scala.math.{max, min, sqrt}

object PlayerBoat {
  val steerDeflectionSpeed = 30.0 //degrees per second
  val maxDeflection = 60.0 //degrees
  val startingHP = 100
  val startingSpeed = 0.0
  val speedChangePerSec = 0.5
  val maxForwardSpeed = 5.0
  val maxBackwardSpeed: Double = -1.0
}


class PlayerBoat (private val keyPolling: KeyPolling, private val keyMap: PlayerControlsKeymap,var speed: Double = PlayerBoat.startingSpeed,
                  var heading: Double, var position: Vector2d, private var steerDeflection: Double = 0.0,
                  private var HP: Int = PlayerBoat.startingHP)
  extends MovableObject with Drawable {
    def handleInput(timeElapsed: Long): Unit = {
      if (keyPolling.isDown(keyMap.backward)) {
        speed -= nanoSecondIntoSecond(timeElapsed) * speedChangePerSec
        speed = max(speed, PlayerBoat.maxBackwardSpeed)
      }
      if (keyPolling.isDown(keyMap.forward)) {
        speed += nanoSecondIntoSecond(timeElapsed) * speedChangePerSec
        speed = min(speed, PlayerBoat.maxForwardSpeed)
      }
      if (keyPolling.isDown(keyMap.steerLeft)) {
        deflectSteerLeft(timeElapsed)
      }
      if (keyPolling.isDown(keyMap.steerRight)) {
        deflectSteerRight(timeElapsed)
      }
//      println(steerDeflection)
      if (!keyPolling.isDown(keyMap.steerRight) && !keyPolling.isDown(keyMap.steerLeft)) {
        deflectSteerNoInput(timeElapsed)
      }
      if (keyPolling.isDown(keyMap.shootRight)) {
        println("shooting right")
      }
      if (keyPolling.isDown(keyMap.shootLeft)) {
        println("shooting left")
      }
    }

    def subtractFromHP(dmg: Int): Unit = {
      HP -= dmg
    }

    private def nanoSecondIntoSecond(nanoTime: Long): Double = {
      nanoTime / 1e9
    }

    override def calcPosition(nanoTimeElapsed: Long): Unit = {
      super.calcPosition(nanoTimeElapsed)
      if (speed > 0) {
        heading += steerDeflection * nanoSecondIntoSecond(nanoTimeElapsed) * sqrt(speed)
      } else if (speed < 0) {
        heading -= steerDeflection * nanoSecondIntoSecond(nanoTimeElapsed) * sqrt(-speed)
      }
//      println(heading)
    }


    def deflectSteerRight(nanoTimeElapsed: Long): Unit = {
      steerDeflection += PlayerBoat.steerDeflectionSpeed * nanoSecondIntoSecond(nanoTimeElapsed)
      steerDeflection = min(PlayerBoat.maxDeflection, steerDeflection)
    }

    def deflectSteerLeft(nanoTimeElapsed: Long): Unit = {
      steerDeflection -= PlayerBoat.steerDeflectionSpeed * nanoSecondIntoSecond(nanoTimeElapsed)
      steerDeflection = max(-PlayerBoat.maxDeflection, steerDeflection)
    }

    def deflectSteerNoInput(nanoTimeElapsed: Long): Unit = {
      if (steerDeflection > 0) {
        deflectSteerLeft(nanoTimeElapsed)
        steerDeflection = max(0, steerDeflection)
      } else {
        deflectSteerRight(nanoTimeElapsed)
        steerDeflection = min(0, steerDeflection)
      }

    }

    def SteerDeflection: Double = {
      steerDeflection
    }

    var hullImage: ImageView = new ImageView(ImageLoader.getImage("src/main/resources/hull.png"))
    hullImage.scaleX = 0.2
    hullImage.scaleY = 0.2


    override def getNodes: List[Node] = {
      List[Node](hullImage)
    }

    override def refresh(): Unit = {
      hullImage.x = position.x
      hullImage.y = position.y
    }

    refresh()
  }
