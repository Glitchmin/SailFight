package Logic

import Gui.KeyPolling
import scalafx.scene.Scene
import scalafx.scene.input.KeyCode
import scalafx.Includes._


class MainLoop extends Subject[MainLoop] {
  val keys: KeyPolling = KeyPolling.getInstance
  var players: Array[PlayerBoat] = Array[PlayerBoat](new PlayerBoat(keys,0.0, 0.0, Vector2d(0, 0), 0.0, 0))
  var lastUpdateTime: Long = System.nanoTime()

  def init(scene: Scene): Unit = {
    keys.pollScene(scene)
  }



  def start(): Unit = {
    while (true) {
      val now = System.nanoTime
      val timeElapsed = now - lastUpdateTime
      lastUpdateTime = now
      players.foreach(_.handleInput(timeElapsed))
      players.foreach(_.calcPosition(timeElapsed))
      notifyObservers()
    }
  }
}
