package Logic

object MainLoop {
  var player1Boat: PlayerBoat = _
  var player2Boat: PlayerBoat = _

  def start(): Unit = {
    while (true) {
      player1Boat.calcPosition()
    }
  }
}
