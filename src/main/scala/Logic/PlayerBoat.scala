package Logic

import Gui.KeyPolling
import scalafx.scene.input.KeyCode

class PlayerBoat (private val keyPolling: KeyPolling, private var speed: Double, private var heading: Double, var position: Vector2d,
                  private var steerDeflection: Double = 0.0, private var lastTimeEval: Int = 0){
  def handleInput(timeElapsed: Long): Unit = {
    if (keyPolling.isDown(KeyCode.Down)) {
      position += Vector2d(-timeElapsed / 1E8, timeElapsed / 1E7)
    }
  }

  def calcPosition(nanoTimeElapsed: Long): Unit ={

    position += Vector2d(nanoTimeElapsed/1E7, nanoTimeElapsed/1E8)
    println(position)
  }
}
