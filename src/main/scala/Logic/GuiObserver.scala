package Logic

import Gui.Drawable

trait AddDrawableObserver {
  def addDrawableUpdate(drawable: Drawable): Unit
}

trait RemoveDrawableObserver {
  def removeDrawableUpdate(drawable: Drawable): Unit
}

trait RefreshObserver{
  def refreshUpdate(): Unit
}

trait GuiObserver extends AddDrawableObserver with RemoveDrawableObserver with RefreshObserver {

}
