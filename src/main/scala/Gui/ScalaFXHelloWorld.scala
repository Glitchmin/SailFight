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
  val mapGui: MapGui = new MapGui(pane)

  override def start(): Unit = {
    stage = new JFXApp3.PrimaryStage {
      //    initStyle(StageStyle.Unified)
      title = "ScalaFX Hello World"
      width = Screen.primary.getBounds.getWidth * 0.9
      height = Screen.primary.getBounds.getHeight * 0.9
//      val playerBoat1 = new PlayerBoat(0.0, 0.0, new Vector2d(0, 0), 0.0, 0)

      scene = new Scene {
        fill = Color.rgb(80, 150, 250)
        content = new HBox {
          children = Seq(pane)
        }
      }
      //MainLoop.gi player1Boat = playerBoat1
      //MainLoop.start()
    }
//    execute()
    val executionThread: Thread = new Thread(() => execute())
    executionThread.start()
  }

  def execute(): Unit = {
    MainLoop.start()
  }
}