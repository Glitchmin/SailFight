package Logic

case class Vector2d(x: Double, y: Double) {
  def +(other: Vector2d): Vector2d = Vector2d(x + other.x, y + other.y)
  def unary_- : Vector2d = Vector2d(-x, -y)
}
