package Gui

import Gui.ScalaFXHelloWorld.pane
import Logic.{MainLoop, Observer}

import java.util.concurrent.TimeUnit
import scalafx.scene.layout.Pane
import scalafx.scene.shape.Circle

class MapGui(val pane: Pane) extends Observer[MainLoop] {
  val refreshTime: Double = 1.0 / 30 * TimeUnit.NANOSECONDS.convert(1, TimeUnit.SECONDS)
  val playerCircle = new Circle()
  playerCircle.radius = 10
  playerCircle.centerX = 20
  playerCircle.centerY = 20
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
    playerCircle.centerX = subject.players(0).position.x
    playerCircle.centerY = subject.players(0).position.y
    //    playerCircle.radius =  playerCircle.radius() + 0.01
    println("updated")
  }
}
