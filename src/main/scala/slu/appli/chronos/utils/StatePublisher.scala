package slu.appli.chronos.utils

trait StatePublisher {
  protected var pushStateChanged: () => Unit = () => Unit

  def onStateChanged(listener: () => Unit): Unit = {
    val oldPush = pushStateChanged
    pushStateChanged = () => {
      oldPush()
      listener()
    }
  }
}
