package Logic

class PlayerBoat (private var speed: Double, private var heading: Double, var position: Vector2d,
                  private var steerDeflection: Double = 0.0, private var lastTimeEval: Int = 0){

  def calcPosition(nanoTimeElapsed: Long): Unit ={

    position += Vector2d(nanoTimeElapsed/1E7, nanoTimeElapsed/1E8)
    println(position)
  }
}
