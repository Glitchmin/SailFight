package Gui

import Gui.ScalaFXHelloWorld.pane
import Logic.{MainLoop, Observer}
import scalafx.scene.image.ImageView

import java.util.concurrent.TimeUnit
import scalafx.scene.layout.Pane
import scalafx.scene.shape.Circle

class MapGui(val pane: Pane) extends Observer[MainLoop] {
  val refreshTime: Double = 1.0 / 30 * TimeUnit.NANOSECONDS.convert(1, TimeUnit.SECONDS)
  val playerCircle = new ImageView(ImageLoader.getImage("src/main/resources/hull.png"))
  playerCircle.scaleX = 0.1
  playerCircle.scaleY = 0.1
//  playerCircle.radius = 10
//  playerCircle.centerX = 20
//  playerCircle.centerY = 20
  pane.children.add(playerCircle)

  var lastUpdateTime: Long = System.nanoTime()

  override def receiveUpdate(subject: MainLoop): Unit = {
    val now = System.nanoTime
    val timeElapsed = now - lastUpdateTime
    if (timeElapsed < refreshTime) {
      return
    }
    lastUpdateTime = now

    //TODO: make scallable
    playerCircle.x = subject.players(0).position.x
    playerCircle.y = subject.players(0).position.y
    //    playerCircle.radius =  playerCircle.radius() + 0.01
  }
}
