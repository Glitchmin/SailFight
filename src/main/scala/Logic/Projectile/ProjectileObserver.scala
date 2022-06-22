package Logic.Projectile


trait AddProjectileObserver {
  def addProjectileUpdate(projectile: Projectile): Unit
}

trait RemoveProjectileObserver {
  def removeProjectileUpdate(projectile: Projectile): Unit
}

trait ProjectileObserver extends AddProjectileObserver with RemoveProjectileObserver {

}
