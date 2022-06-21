package Gui

import scalafx.scene.Scene
import scalafx.scene.input.{KeyCode, KeyEvent}
import scalafx.Includes._

import scala.collection.mutable
import scala.collection.mutable.HashSet
//import java.util


/**
 * Based on java code:
 * author: Ed Eden-Rump
 * project: EdenCoding Space Shooter
 * */
object KeyPolling {
  private var scene: Scene = _
//  private val keysCurrentlyDown = new util.HashSet[KeyCode]
  val keysCurrentlyDown = new mutable.HashSet[KeyCode]

  def getInstance = new KeyPolling
}

class KeyPolling private() {
  def pollScene(scene: Scene): Unit = {
    clearKeys()
    removeCurrentKeyHandlers()
    setScene(scene)
  }

  private def clearKeys(): Unit = {
    KeyPolling.keysCurrentlyDown.clear()
  }

  private def removeCurrentKeyHandlers(): Unit = {
    if (KeyPolling.scene != null) {
      KeyPolling.scene.setOnKeyPressed(null)
      KeyPolling.scene.setOnKeyReleased(null)
    }
  }

  private def setScene(scene: Scene): Unit = {
    KeyPolling.scene = scene
    KeyPolling.scene.setOnKeyPressed((keyEvent: scalafx.scene.input.KeyEvent) => {
      def foo(keyEvent: KeyEvent) = KeyPolling.keysCurrentlyDown.add(keyEvent.getCode)

      foo(keyEvent)
    })
    KeyPolling.scene.setOnKeyReleased((keyEvent: KeyEvent) => {
      def foo(keyEvent: KeyEvent) = KeyPolling.keysCurrentlyDown.remove(keyEvent.getCode)

      foo(keyEvent)
    })
  }

  def isDown(keyCode: KeyCode): Boolean = KeyPolling.keysCurrentlyDown.contains(keyCode)

//  override def toString: String = {
//    val keysDown = new StringBuilder("KeyPolling on scene (").append(KeyPolling.scene).append(")")
//    import scala.collection.JavaConversions._
//    for (code <- KeyPolling.keysCurrentlyDown) {
//      keysDown.append(code.getName).append(" ")
//    }
//    keysDown.toString
//  }
}