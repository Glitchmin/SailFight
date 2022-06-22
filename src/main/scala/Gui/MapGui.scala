package Gui

import Gui.ScalaFXHelloWorld.pane
import Logic.{GuiObserver, MainLoop, Observer}
import scalafx.application.Platform
import scalafx.scene.Node
import scalafx.scene.image.ImageView

import java.util.concurrent.TimeUnit
import scalafx.scene.layout.Pane
import scalafx.scene.shape.Circle

import scala.collection.mutable

class MapGui(val pane: Pane) extends GuiObserver {
  val refreshTime: Double = 1.0 / 30 * TimeUnit.NANOSECONDS.convert(1, TimeUnit.SECONDS)
  private val drawables = new mutable.HashSet[Drawable]

  var lastUpdateTime: Long = System.nanoTime()

  override def addDrawableUpdate(drawable: Drawable): Unit = {
    drawables.add(drawable)
    val runnable = new Runnable {
      override def run(): Unit = {
        drawable.getNodes.foreach((node: Node) => {
          pane.children.add(node)
        })
      }
    }
    Platform.runLater(runnable)
  }

  override def removeDrawableUpdate(drawable: Drawable): Unit = {
    drawables.remove(drawable)
    val runnable = new Runnable {
      override def run(): Unit = {
        drawable.getNodes.foreach((node: Node) => {
          pane.children.remove(node)
        })
      }
    }
    Platform.runLater(runnable)
  }

  override def refreshUpdate(): Unit = {
    val now = System.nanoTime
    val timeElapsed = now - lastUpdateTime
    if (timeElapsed < refreshTime) {
      return
    }
    lastUpdateTime = now
    val runnable = new Runnable {
      override def run(): Unit = {
        drawables.foreach(_.refresh())
      }
    }
    Platform.runLater(runnable)
  }
}
