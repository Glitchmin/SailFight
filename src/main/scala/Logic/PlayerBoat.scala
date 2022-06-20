package Logic

class PlayerBoat (private var speed: Double, private var heading: Double, private var position: Vector2d,
                  private var steerDeflection: Double = 0.0, private var lastTimeEval: Int = 0){
  def calcPosition(): Unit ={
    val now = System.nanoTime
    val timeElapsed = System.nanoTime - now
    position+=new Vector2d(timeElapsed, 0)
  }
}
