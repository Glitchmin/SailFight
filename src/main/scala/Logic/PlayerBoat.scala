package Logic

import Gui.{Drawable, ImageLoader, KeyPolling}
import scalafx.scene.Node
import scalafx.scene.image.{Image, ImageView}
import scalafx.scene.input.KeyCode

import scala.math.{cos, max, min, sin, toRadians}

object PlayerBoat {
  val steerDeflectionSpeed = 90.0; //degrees per second
  val maxDeflection = 60.0; //degrees
}


class PlayerBoat(private val keyPolling: KeyPolling,
                 private val keyMap: PlayerControlsKeymap,
                 private var speed: Double, private var heading: Double, var position: Vector2d,
                 private var steerDeflection: Double = 0.0, private var lastTimeEval: Int = 0) extends Drawable {
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

  def calcPosition(nanoTimeElapsed: Long): Unit = {
    val rad = toRadians(heading)
    position += Vector2d((nanoTimeElapsed * sin(rad)) / 1E7, -(nanoTimeElapsed * cos(rad)) / 1E7)
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

  var hullImage: ImageView = new ImageView(ImageLoader.getImage("src/main/resources/hull.png"))
  hullImage.scaleX = 0.2
  hullImage.scaleY = 0.2


  override def getNodes(): List[Node] = {
    List[Node](hullImage)
  }

  override def refresh(): Unit = {
    hullImage.x = position.x
    hullImage.y = position.y
  }
  refresh()
}
