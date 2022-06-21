package Logic

import Gui.KeyPolling
import scalafx.scene.input.KeyCode

class PlayerBoat (private val keyPolling: KeyPolling,
                  private val keyMap: PlayerControlsKeymap,
                  private var speed: Double, private var heading: Double, var position: Vector2d,
                  private var steerDeflection: Double = 0.0, private var lastTimeEval: Int = 0){
  def handleInput(timeElapsed: Long): Unit = {
    if (keyPolling.isDown(keyMap.backward)) {
      position += Vector2d(0, timeElapsed / 1E7)
    }
    if (keyPolling.isDown(keyMap.forward)) {
      position += Vector2d(0, -timeElapsed / 1E7)
    }
    if (keyPolling.isDown(keyMap.steerLeft)) {
      position += Vector2d(-timeElapsed / 1E7, 0)
    }
    if (keyPolling.isDown(keyMap.steerRight)) {
      position += Vector2d(timeElapsed / 1E7, 0)
    }

  }

  def calcPosition(nanoTimeElapsed: Long): Unit ={

    position += Vector2d(nanoTimeElapsed/1E7, nanoTimeElapsed/1E8)
    println(position)
  }
}
