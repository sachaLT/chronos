package slu.appli.chronos.ui

import java.awt.Dimension
import javax.swing.ImageIcon

import slu.appli.chronos.time.{Chronometer, Elapse, TimePartLabels, Timer}
import slu.appli.chronos.timer.{IntervalTimerItem, SwingTimer, TimerItem}

import scala.swing._

object Chronos extends SimpleSwingApplication {
  val swingTimer = SwingTimer(100)
  private val chronometer = Chronometer()
  private val timer = Timer(0,0,20)

  def top: MainFrame = new MainFrame {
    val me = this
    title = whatTimeIsIt()
    iconImage_=(new ImageIcon(getClass.getClassLoader.getResource("images/clock.png")).getImage)

    val chronoPanel: ChronoPanel = new ChronoPanel(chronometer)
    val calendarPanel = new CalendarPanel
    val timerPanel: TimerPanel = new TimerPanel(timer)

    swingTimer.start(
      IntervalTimerItem(100, () => { chronoPanel.whatElapseIsIt() }),
      IntervalTimerItem(100, () => { timerPanel.whatElapseIsIt() }),
      IntervalTimerItem(TimerItem.intervalForMinute, () => { title = whatTimeIsIt() }, TimerItem.firstInstanceForMinute, waitFirstInstance = false),
      IntervalTimerItem(TimerItem.intervalForMinute, () => {calendarPanel.whatTimeIsIt() }, TimerItem.firstInstanceForMinute, waitFirstInstance = false)
    )

    val switchPanel = new SwitchPanel(calendarPanel, chronoPanel, timerPanel)

    val northPanel = new FlowPanel {
      contents += Button("next") {
        switchPanel.switchNext()
      }
      contents += Button("previous") {
        switchPanel.switchPrevious()
      }
    }

    contents = new BorderLayoutPanel()
      .addCenter(switchPanel )
      .addSouth( new Label("South") )
      .addNorth( northPanel  )
      .addEast( new Label("East") )
      .addWest(new Label("West") )

    override def closeOperation() { quit }
  }

  override def shutdown(): Unit = {
    swingTimer.stop
  }

  private def showTime: String = Elapse.prettyPrint(chronometer.elapseTime)

  private def whatTimeIsIt() = {
    val timesParts = TimePartLabels()
    timesParts.toOption.fold("Hour not found !!") { parts =>
      s"${parts.dayLabel} ${parts.dayNumber} ${parts.monthLabel} ${parts.year} - ${parts.hours}:${parts.minutes}"
    }
  }
}
