package Logic

import Projectile.{Projectile, ProjectileObserver}
import Gui.KeyPolling
import scalafx.scene.Scene
import scalafx.scene.input.KeyCode
import scalafx.Includes._
import scalafx.scene.shape.{Circle, Rectangle, Shape}
import scalafx.stage.Stage

import scala.collection.mutable

class MainLoop extends GuiSubject with ProjectileObserver {
  val keys: KeyPolling = KeyPolling.getInstance
  var lastUpdateTime: Long = System.nanoTime()

  private val playersKeymaps = Array(PlayerControlsKeymap(KeyCode.Right, KeyCode.Left, KeyCode.Up, KeyCode.Down,
                                        KeyCode.M, KeyCode.N),
                                    PlayerControlsKeymap(KeyCode.D, KeyCode.A, KeyCode.W, KeyCode.S,
                                      KeyCode.V, KeyCode.C))
  private var players: Array[PlayerBoat] = Array[PlayerBoat](new PlayerBoat(keys, playersKeymaps(0), Vector2d(0.05, 0.02),
    2.0, 90.0, Vector2d(100,100), 0.0),
    new PlayerBoat(keys, playersKeymaps(1), Vector2d(0.95-0.03, 0.02),
      2.0, 180.0+90.0, Vector2d(700,300), 0.0))
  private var projectiles = mutable.HashSet[Projectile]()
  players.foreach(_.addAddProjectileObserver(this))

  def init(scene: Scene): Unit = {
    keys.pollScene(scene)
    players.foreach(player => notifyAddDrawable(player))
  }

  private def checkProjectiles(): Unit = {
    for (projectile <- projectiles; boat <- players){
      if(boat.hullImage.getBoundsInParent.intersects(projectile.circle.getBoundsInParent) && boat != projectile.parent){
        println("intersection", boat.position, projectile.position)
        projectile.dealDmg(boat)
        removeProjectileUpdate(projectile)
      }
    }
  }

  def start(): Unit = {
    while (true) {
      val now = System.nanoTime
      val timeElapsed = now - lastUpdateTime
      lastUpdateTime = now
      players.foreach(_.handleInput(timeElapsed))
      players.foreach(_.calcPosition(timeElapsed))
      projectiles.foreach(_.calcPosition(timeElapsed))
      notifyRefresh()
      checkProjectiles()
    }
  }

  override def addProjectileUpdate(projectile: Projectile): Unit = {
    projectiles.add(projectile)
    notifyAddDrawable(projectile)
  }

  override def removeProjectileUpdate(projectile: Projectile): Unit = {
    projectiles.remove(projectile)
    notifyRemoveDrawable(projectile)
  }
}
