package Gui

import Logic.{MainLoop, PlayerBoat, Vector2d}
import scalafx.application.JFXApp3
import scalafx.geometry.Insets
import scalafx.scene.Scene
import scalafx.scene.effect.DropShadow
import scalafx.scene.layout.{HBox, Pane}
import scalafx.scene.paint.Color._
import scalafx.scene.paint._
import scalafx.scene.shape.Circle
import scalafx.scene.text.Text
import scalafx.stage.Screen

object ScalaFXHelloWorld extends JFXApp3 {
  val pane: Pane = new Pane()
  val playerCircle = new Circle()
  override def start(): Unit = {
    stage = new JFXApp3.PrimaryStage {
      //    initStyle(StageStyle.Unified)
      title = "ScalaFX Hello World"
      width = Screen.primary.getBounds.getWidth*0.9
      height = Screen.primary.getBounds.getHeight*0.9
      val playerBoat1 = new PlayerBoat(0.0,0.0,new Vector2d(0,0),0.0,0)

      playerCircle.radius = 10
      playerCircle.centerX = 20
      playerCircle.centerY = 20
      pane.children.add(playerCircle)
      scene = new Scene {
        fill = Color.rgb(80, 150, 250)
        content = new HBox {
          children = Seq(pane)
        }
      }
      //MainLoop.player1Boat = playerBoat1
      //MainLoop.start()
    }
  }
}