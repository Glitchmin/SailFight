package Logic

import Gui.{Drawable, ImageLoader, KeyPolling}

import Logic.Projectile.{AddProjectileSubject, Projectile}
import scalafx.scene.Node
import scalafx.scene.image.ImageView

import scala.math.{asin, cos, max, min, pow, sin, sqrt, toDegrees, toRadians}

object PlayerBoat {
  val steerDeflectionSpeed = 30.0 //degrees per second
  val maxDeflection = 60.0 //degrees
  val startingHP = 100
  val startingSpeed = 0.0
  val speedChangePerSec = 0.5
  val maxForwardSpeed = 5.0
  val maxBackwardSpeed: Double = -1.0
  val projectileSpeed: Double = 8.2137
  val projectileDamage = 34
  val reloadTime = 1.0
}


class PlayerBoat(private val keyPolling: KeyPolling, private val keyMap: PlayerControlsKeymap,var speed: Double,
                 var heading: Double, var position: Vector2d, private var steerDeflection: Double = 0.0,
                 private var HP: Int = PlayerBoat.startingHP, private var leftCannonTimer : Long = 0,
                 private var rightCannonTimer : Long = 0)
  extends MovableObject with Drawable with AddProjectileSubject {

  def handleInput(timeElapsed: Long): Unit = {
    if (keyPolling.isDown(keyMap.backward)) {
      speed -= nanoSecondIntoSecond(timeElapsed) * PlayerBoat.speedChangePerSec
      speed = max(speed, PlayerBoat.maxBackwardSpeed)
    }
    if (keyPolling.isDown(keyMap.forward)) {
      speed += nanoSecondIntoSecond(timeElapsed) * PlayerBoat.speedChangePerSec
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
    val angle = toDegrees(asin(pow(PlayerBoat.projectileSpeed,2)/(pow(PlayerBoat.projectileSpeed,2) + pow(speed,2))))
    if (keyPolling.isDown(keyMap.shootRight) && rightCannonTimer == 0L) {
      notifyAddProjectile(new Projectile(PlayerBoat.projectileSpeed,
        heading + angle, position, PlayerBoat.projectileDamage,this))
      rightCannonTimer = (1e9 * PlayerBoat.reloadTime).toLong
    }
    if (keyPolling.isDown(keyMap.shootLeft) && leftCannonTimer == 0L) {
      notifyAddProjectile(new Projectile(PlayerBoat.projectileSpeed,
        heading - angle + 360.0, position, PlayerBoat.projectileDamage,this))
      leftCannonTimer = (1e9 * PlayerBoat.reloadTime).toLong
    }
    leftCannonTimer = max(leftCannonTimer-timeElapsed,0)
    rightCannonTimer = max(rightCannonTimer-timeElapsed,0)
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

  val hullImage: ImageView = new ImageView(ImageLoader.getImage("src/main/resources/hull.png"))
  hullImage.scaleX = 0.2
  hullImage.scaleY = 0.2
  private val steeringRudder = new ImageView(ImageLoader.getImage("src/main/resources/lightsaber.png"))
  steeringRudder.scaleX = 0.2
  steeringRudder.scaleY = 0.2


  override def getNodes: List[Node] = {
    List[Node](hullImage, steeringRudder)
  }

  override def refresh(): Unit = {
    hullImage.x = position.x - hullImage.getImage.getWidth  / 2
    hullImage.y = position.y - hullImage.getImage.getHeight / 2
    hullImage.setRotate(heading)
    steeringRudder.x = position.x - steeringRudder.getImage.getWidth  / 2 + hullImage.getScaleY * hullImage.getImage.getHeight / 2 * sin(toRadians(-heading))
    steeringRudder.y = position.y - steeringRudder.getImage.getHeight / 2 + hullImage.getScaleY * hullImage.getImage.getHeight / 2 * cos(toRadians(-heading))
    steeringRudder.setRotate(heading-steerDeflection+180)
  }

  refresh()
}
