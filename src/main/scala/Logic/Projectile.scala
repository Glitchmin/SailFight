package Logic

class Projectile (var speed: Double, var heading: Double, var position: Vector2d, var dmg: Int) extends MovableObject {
  def dealDmg(playerBoat: PlayerBoat): Unit ={
    playerBoat.subtractFromHP(dmg)
  }
}
