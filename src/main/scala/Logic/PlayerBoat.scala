package Logic

import Gui.{Drawable, ImageLoader, KeyPolling, ScalaFXHelloWorld}
import Logic.PlayerBoat.projectileSpeed
import Logic.Projectile.{AddProjectileSubject, Projectile}
import scalafx.scene.Node
import scalafx.scene.control.Label
import scalafx.scene.effect.ColorAdjust
import scalafx.scene.image.ImageView
import scalafx.scene.paint.Color
import scalafx.scene.text.{Font, FontWeight}
import scalafx.stage.Screen

import scala.math.{asin, cos, max, min, pow, random, scalb, sin, sqrt, toDegrees, toRadians}

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


class PlayerBoat(private val keyPolling: KeyPolling, private val keyMap: PlayerControlsKeymap, hudAnchor: Vector2d, var speed: Double,
                 var heading: Double, var position: Vector2d, private var steerDeflection: Double = 0.0,
                 private var HP: Int = PlayerBoat.startingHP, private var leftCannonTimer: Long = 0,
                 private var rightCannonTimer: Long = 0, private var hue: Double  = 0.0)
  extends MovableObject with Drawable with AddProjectileSubject {
  var score: Int = 0

  def handleInput(timeElapsed: Long): Unit = {
    if (keyPolling.isDown(keyMap.backward)) {
      color = Color.hsb((color.hue + 0.0001) % 360.0, 1.0, 1.0)
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
    val angle = toDegrees(asin(pow(PlayerBoat.projectileSpeed, 2) / (pow(PlayerBoat.projectileSpeed, 2) + pow(speed, 2))))
    if (keyPolling.isDown(keyMap.shootRight) && rightCannonTimer == 0L) {
      notifyAddProjectile(new Projectile(PlayerBoat.projectileSpeed,
        heading + angle, position, PlayerBoat.projectileDamage, this))
      rightCannonTimer = (1e9 * PlayerBoat.reloadTime).toLong
    }
    if (keyPolling.isDown(keyMap.shootLeft) && leftCannonTimer == 0L) {
      notifyAddProjectile(new Projectile(PlayerBoat.projectileSpeed,
        heading - angle + 360.0, position, PlayerBoat.projectileDamage, this))
      leftCannonTimer = (1e9 * PlayerBoat.reloadTime).toLong
    }
    leftCannonTimer = max(leftCannonTimer - timeElapsed, 0)
    rightCannonTimer = max(rightCannonTimer - timeElapsed, 0)
  }

  def randomize_position(): Unit = {
    position = Vector2d(random() * ScalaFXHelloWorld.stage.getWidth, random() * ScalaFXHelloWorld.stage.getHeight)
  }

  def receiveDamage(projectile: Projectile): Unit = {
    HP -= projectile.dmg
    if (HP < 0) {
      HP = PlayerBoat.startingHP
      randomize_position()
      heading = random() * 360.0
      steerDeflection = 0
      projectile.parent.score += 1
    }
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

    position = Vector2d((position.x + ScalaFXHelloWorld.stage.getWidth) % ScalaFXHelloWorld.stage.getWidth,
      (position.y + ScalaFXHelloWorld.stage.getHeight) % ScalaFXHelloWorld.stage.getHeight)
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
  private val scoreLabel: Label = new Label
  scoreLabel.layoutX = ScalaFXHelloWorld.stage.getWidth * hudAnchor.x
  scoreLabel.layoutY = ScalaFXHelloWorld.stage.getHeight * hudAnchor.y
  scoreLabel.setFont(Font("arial", FontWeight.Bold, 40.2137))
  private var color = Color.hsb(hue, 1.0, 1.0)
  private val hueAdjust = new ColorAdjust()
  steeringRudder.setEffect(hueAdjust)

  override def getNodes: List[Node] = {
    List[Node](hullImage, steeringRudder, scoreLabel)
  }

  override def refresh(): Unit = {
    hullImage.x = position.x - hullImage.getImage.getWidth / 2
    hullImage.y = position.y - hullImage.getImage.getHeight / 2
    hullImage.setRotate(heading)
    steeringRudder.x = position.x - steeringRudder.getImage.getWidth / 2 + hullImage.getScaleY * hullImage.getImage.getHeight / 2 * sin(toRadians(-heading))
    steeringRudder.y = position.y - steeringRudder.getImage.getHeight / 2 + hullImage.getScaleY * hullImage.getImage.getHeight / 2 * cos(toRadians(-heading))
    steeringRudder.setRotate(heading - steerDeflection + 180)
    scoreLabel.text = score.toString
    hueAdjust.setHue( 2 * ((color.hue+180)%360) / 360.0 - 1)
    scoreLabel.setTextFill(color)

  }

  refresh()
}
