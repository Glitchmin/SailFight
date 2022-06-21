package Logic

import Gui.KeyPolling
import scalafx.scene.Scene
import scalafx.scene.input.KeyCode
import scalafx.Includes._


class MainLoop extends Subject[MainLoop] {
  var player1Boat: PlayerBoat = new PlayerBoat(0.0, 0.0, Vector2d(0, 0), 0.0, 0)
  var player2Boat: PlayerBoat = _
  val keys: KeyPolling = KeyPolling.getInstance
  var lastUpdateTime: Long = System.nanoTime()

  def init(scene: Scene): Unit = {
    keys.pollScene(scene)
  }

  def handleInput(timeElapsed: Long): Unit = {
    if (keys.isDown(KeyCode.Down)) {
      player1Boat.position += Vector2d(-timeElapsed / 1E8, timeElapsed / 1E7)
    }
    else {
      println(KeyPolling.keysCurrentlyDown)

    }
  }

  def start(): Unit = {
    while (true) {
      val now = System.nanoTime
      val timeElapsed = now - lastUpdateTime
      lastUpdateTime = now
      handleInput(timeElapsed)
      player1Boat.calcPosition(timeElapsed)
      notifyObservers()
    }
  }
}
