package Gui

import Gui.ScalaFXHelloWorld.pane
import Logic.{MainLoop, Observer}
//import Logic.{MainLoop, Observer}
import scalafx.scene.layout.Pane
import scalafx.scene.shape.Circle

class MapGui(val pane: Pane) extends Observer[MainLoop]{
  val playerCircle = new Circle()
  playerCircle.radius = 10
  playerCircle.centerX = 20
  playerCircle.centerY = 20
  pane.children.add(playerCircle)

  override def receiveUpdate(subject: MainLoop): Unit = {
    playerCircle.centerX = subject.player1Boat.position.x
    playerCircle.centerY = subject.player1Boat.position.y
    playerCircle.radius =  playerCircle.radius() + 0.001
    println("updated")
  }
}
