package slu.appli.chronos.ui

import javax.swing.ImageIcon

import slu.appli.chronos.time.{Chronometer, Elapse, TimePartLabels}
import slu.appli.chronos.timer.{IntervalTimerItem, SwingTimer, TimerItem}

import scala.swing._

object Chronos extends SimpleSwingApplication {
  val timer = SwingTimer(100)
  private val chronometer = Chronometer()

  def top: MainFrame = new MainFrame {
    title = whatTimeIsIt()
    iconImage_=(new ImageIcon(getClass.getClassLoader.getResource("images/clock.png")).getImage)
    val timeLabel = new Label(showTime)
    timeLabel.font_=(timeLabel.font.deriveFont(timeLabel.font.getSize2D * 5F))
    val calandarPanel = new CalendarPanel

    timer.start(
      IntervalTimerItem(100, () => { timeLabel.text_=(showTime) }),
      IntervalTimerItem(TimerItem.intervalForMinute, () => { title = whatTimeIsIt() }, TimerItem.firstInstanceForMinute, waitFirstInstance = false),
      IntervalTimerItem(TimerItem.intervalForMinute, () => { calandarPanel.whatTimeIsIt() }, TimerItem.firstInstanceForMinute, waitFirstInstance = false)
    )
    contents = new BorderLayoutPanel()
      .addCenter( timeLabel )
      .addSouth( new CommandPanel(chronometer) )
      .addNorth( calandarPanel )
      .addEast(new Label("East"))
      .addWest(new Label("West"))

    override def closeOperation() { quit }
  }

  override def shutdown(): Unit = {
    timer.stop
  }

  private def showTime: String = Elapse.prettyPrint(chronometer.elapseTime)

  private def whatTimeIsIt() = {
    val timesParts = TimePartLabels()
    timesParts.toOption.fold("Hour not found !!") { parts =>
      s"${parts.dayLabel} ${parts.dayNumber} ${parts.monthLabel} ${parts.year} - ${parts.hours}:${parts.minutes}"
    }
  }
}
