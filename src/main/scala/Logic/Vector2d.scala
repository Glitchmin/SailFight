package Logic

class Vector2d(val x: Double, val y: Double) {
  def +(other: Vector2d) = new Vector2d(x + other.x, y + other.y)
  def unary_- = new Vector2d(-x, -y)
}
