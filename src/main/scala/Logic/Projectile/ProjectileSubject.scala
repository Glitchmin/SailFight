package Logic.Projectile


trait AddProjectileSubject {
  private var observers: List[AddProjectileObserver] = Nil

  def addAddProjectileObserver(observer: AddProjectileObserver): Unit = observers = observer :: observers

  def notifyAddProjectile(projectile: Projectile): Unit = {
    observers.foreach(_.addProjectileUpdate(projectile))
  }
}

trait RemoveProjectileSubject {
  private var observers: List[RemoveProjectileObserver] = Nil

  def addRemoveProjectileObserver(observer: RemoveProjectileObserver): Unit = observers = observer :: observers

  def notifyRemoveProjectile(projectile: Projectile): Unit = observers.foreach(_.removeProjectileUpdate(projectile))
}

trait ProjectileSubject extends AddProjectileSubject with RemoveProjectileSubject{
  def addObserver(observer: ProjectileObserver): Unit = {
    addAddProjectileObserver(observer.asInstanceOf[AddProjectileObserver])
    addRemoveProjectileObserver(observer.asInstanceOf[RemoveProjectileObserver])
  }
}
