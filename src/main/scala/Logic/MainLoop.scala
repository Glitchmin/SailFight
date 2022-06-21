package Logic

object MainLoop {
  var player1Boat: PlayerBoat = new PlayerBoat(0.0, 0.0, new Vector2d(0, 0), 0.0, 0)
  var player2Boat: PlayerBoat = _

  def start(): Unit = {
    while (true) {
      player1Boat.calcPosition()
    }
  }
}
