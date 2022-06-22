package Gui

import scalafx.scene

trait Drawable {
  def getNodes: List[scene.Node]

  def refresh(): Unit
}
