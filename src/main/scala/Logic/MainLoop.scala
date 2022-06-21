package Logic

import Gui.KeyPolling
import scalafx.scene.Scene
import scalafx.scene.input.KeyCode
import scalafx.Includes._


class MainLoop extends Subject[MainLoop] {
  val keys: KeyPolling = KeyPolling.getInstance
  var lastUpdateTime: Long = System.nanoTime()

  private val playersKeymaps = Array(PlayerControlsKeymap(KeyCode.Right, KeyCode.Left, KeyCode.Up, KeyCode.Down,
    KeyCode.M, KeyCode.N))
  var players: Array[PlayerBoat] = Array[PlayerBoat](new PlayerBoat(keys, playersKeymaps(0),
    2.0, 90.0, Vector2d(100,100), 0.0, 0))

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
