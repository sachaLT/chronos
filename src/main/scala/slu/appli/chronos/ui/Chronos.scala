package slu.appli.chronos.ui

import slu.appli.chronos.time.{Chronometer, Elapse, TimePartLabels}
import slu.appli.chronos.timer.{IntervalTimerItem, SwingTimer, TimerItem}

import scala.swing._

object Chronos extends SimpleSwingApplication {
  val timer = SwingTimer(100)

  def top: MainFrame = new MainFrame {
    title = whatTimeIsIt()
    val timeLabel = new Label(showTime)
    val calandarPanel = new CalandarPanel

    timer.start(
      IntervalTimerItem(100, () => { timeLabel.text_=(showTime) }),
      IntervalTimerItem(TimerItem.intervalForMinute, () => { title = whatTimeIsIt() }, TimerItem.firstInstanceForMinute, waitFirstInstance = false),
      IntervalTimerItem(TimerItem.intervalForMinute, () => { calandarPanel.whatTimeIsIt() }, TimerItem.firstInstanceForMinute, waitFirstInstance = false)
    )
    contents = new BorderLayoutPanel()
      .addNorth( timeLabel )
      .addSouth( Button("Stop") { timer.stop } )
      .addCenter( calandarPanel )
      .addEast(new Label("East"))
      .addWest(new Label("West"))

    override def closeOperation() { quit }
  }

  override def shutdown(): Unit = {
    timer.stop
  }

  private val chronometer = Chronometer()
  chronometer.start

  private def showTime: String = Elapse.prettyPrint(chronometer.elapseTime)

  private def whatTimeIsIt() = {
    val timesParts = TimePartLabels()
    timesParts.toOption.fold("Hour not found !!") { parts =>
      s"${parts.dayLabel} ${parts.dayNumber} ${parts.monthLabel} ${parts.year} - ${parts.hours}:${parts.minutes}"
    }
  }
}
