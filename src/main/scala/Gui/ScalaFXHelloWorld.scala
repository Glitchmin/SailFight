package Gui

import Logic.{MainLoop, PlayerBoat, Vector2d}
import scalafx.application.JFXApp3
import scalafx.beans.property.ReadOnlyObjectProperty
import scalafx.geometry.Insets
import scalafx.scene.Scene
import scalafx.scene.effect.DropShadow
import scalafx.scene.layout.{HBox, Pane}
import scalafx.scene.paint.Color._
import scalafx.scene.paint._
import scalafx.scene.shape.Circle
import scalafx.scene.text.Text
import scalafx.stage.{Screen, Stage}
import scalafx.Includes._


object ScalaFXHelloWorld extends JFXApp3 {
  val pane: Pane = new Pane()
  val mapGui: MapGui = new MapGui(pane)
  var scene: Scene = _

  override def start(): Unit = {
    //    scene =
    stage = new JFXApp3.PrimaryStage {
      //    initStyle(StageStyle.Unified)
      title = "ScalaFX Hello World"
      width = Screen.primary.getBounds.getWidth * 0.9
      height = Screen.primary.getBounds.getHeight * 0.9

      scene = new Scene {
        fill = Color.rgb(80, 150, 250)
        content = new HBox {
          children = Seq(pane)
        }

      }
    }
    val executionThread: Thread = new Thread(() => execute())
    executionThread.start()
  }

  def execute(): Unit = {
    val mainLoop: MainLoop = new MainLoop()
    mainLoop.addObserver(mapGui)
    mainLoop.init(stage.getScene)
    mainLoop.start()
  }
}