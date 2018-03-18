package slu.appli.chronos.timer

import scala.concurrent.{ExecutionContext, Future}
import scala.swing.{Action, Swing}

trait SwingTimer {
  def start(timerItems: TimerItem*): Unit
  def stop(): Unit
}

case class CompoundSwingTimer(interval: Int) extends SwingTimer {
  private var isStarted = false
  val peer = new javax.swing.Timer(interval,  Action.NoAction.peer)
  peer.setRepeats(true)

  def start(timerItems: TimerItem*): Unit = {
    if (!isStarted) {
      peer.addActionListener(Action("timer") {
        timerItems.foreach(_.next())
      }.peer)
      peer.start
      isStarted = true
    }
  }

  def stop = {
    if (isStarted) {
      peer.stop
      isStarted = false
    }
  }
}

object SwingTimer {

  def apply(interval: Int): SwingTimer = CompoundSwingTimer(interval)

  def asynchronusTask(task: () => Unit, onEdt: () => Unit)(implicit ec: ExecutionContext): () => Unit = () => {
    Future { task } map { _ => Swing.onEDT { onEdt() } }
    ()
  }
}