package Logic

trait Observer[S] {
  def receiveUpdate(subject: S): Unit
}

trait Subject[S] {
  private var observers: List[Observer[S]] = Nil

  def addObserver(observer: Observer[S]): Unit = observers = observer :: observers

  def notifyObservers(subject: S): Unit = observers.foreach(_.receiveUpdate(subject))
}