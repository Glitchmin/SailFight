package Gui

import Gui.ScalaFXHelloWorld.pane
import Logic.{MainLoop, Observer}

import java.util.concurrent.TimeUnit
//import Logic.{MainLoop, Observer}
import scalafx.scene.layout.Pane
import scalafx.scene.shape.Circle

class MapGui(val pane: Pane) extends Observer[MainLoop] {
  val refresh_time: Double = 1.0 / 30 * TimeUnit.NANOSECONDS.convert(1, TimeUnit.SECONDS)
  val playerCircle = new Circle()
  playerCircle.radius = 10
  playerCircle.centerX = 20
  playerCircle.centerY = 20
  pane.children.add(playerCircle)

  var last_update_time: Long = System.nanoTime()

  override def receiveUpdate(subject: MainLoop): Unit = {
    val now = System.nanoTime
    val timeElapsed = now - last_update_time
    if (timeElapsed < refresh_time) {
      return
    }
    last_update_time = now

    playerCircle.centerX = subject.player1Boat.position.x
    playerCircle.centerY = subject.player1Boat.position.y
    //    playerCircle.radius =  playerCircle.radius() + 0.01
    println("updated")
  }
}
