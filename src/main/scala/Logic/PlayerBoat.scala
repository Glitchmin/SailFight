package Logic

class PlayerBoat (private var speed: Double, private var heading: Double, var position: Vector2d,
                  private var steerDeflection: Double = 0.0, private var lastTimeEval: Int = 0){
  var lastUpdateTime: Long = System.nanoTime()
  def calcPosition(): Unit ={
    val now = System.nanoTime
    val timeElapsed = now -  lastUpdateTime
    lastUpdateTime = now
    position += Vector2d(timeElapsed/1E7, timeElapsed/1E8)
    println(position)
  }
}
