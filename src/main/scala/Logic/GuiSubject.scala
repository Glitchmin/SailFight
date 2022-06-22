package Logic

import Gui.Drawable

trait AddDrawableSubject {
  private var observers: List[AddDrawableObserver] = Nil

  def addAddDrawableObserver(observer: AddDrawableObserver): Unit = observers = observer :: observers

  def notifyAddDrawable(drawable: Drawable): Unit = {
    observers.foreach(_.addDrawableUpdate(drawable))
  }
}

trait RemoveDrawableSubject {
  private var observers: List[RemoveDrawableObserver] = Nil

  def addRemoveDrawableObserver(observer: RemoveDrawableObserver): Unit = observers = observer :: observers

  def notifyRemoveDrawable(drawable: Drawable): Unit = observers.foreach(_.removeDrawableUpdate(drawable))
}


trait RefreshSubject {
  private var observers: List[RefreshObserver] = Nil

  def addRefreshObserver(observer: RefreshObserver): Unit = observers = observer :: observers

  def notifyRefresh(): Unit = observers.foreach(_.refreshUpdate())
}

trait GuiSubject extends AddDrawableSubject with RemoveDrawableSubject with RefreshSubject {
  def addObserver(observer: GuiObserver): Unit = {
    addRefreshObserver(observer.asInstanceOf[RefreshObserver])
    addAddDrawableObserver(observer.asInstanceOf[AddDrawableObserver])
    addRemoveDrawableObserver(observer.asInstanceOf[RemoveDrawableObserver])
  }
}
