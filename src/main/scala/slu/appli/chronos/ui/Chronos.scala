package slu.appli.chronos.ui

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
    iconImage_=(MyImagesIcons.apps.getImage)

    val calendarPanel = new CalendarPanel
    calendarPanel.name = "calendar"
    val chronoPanel: ChronoPanel = new ChronoPanel(chronometer)
    chronoPanel.name = "chrono"
    val timerPanel: TimerTextPanel = new TimerTextPanel(timer)
    timerPanel.name = "timer"

    swingTimer.start(
      IntervalTimerItem(100, () => { chronoPanel.whatElapseIsIt() }),
      IntervalTimerItem(100, () => { timerPanel.whatElapseIsIt() }),
      IntervalTimerItem(TimerItem.intervalForMinute, () => { title = whatTimeIsIt() }, TimerItem.firstInstanceForMinute, waitFirstInstance = false),
      IntervalTimerItem(TimerItem.intervalForMinute, () => {calendarPanel.whatTimeIsIt() }, TimerItem.firstInstanceForMinute, waitFirstInstance = false)
    )

    val switchPanel = new SwitchPanel(chronoPanel, timerPanel)
    switchPanel.name = "switch"

    val northPanel = new BorderLayoutPanel()
    .addWest(calendarPanel)
    .addEast(new SelectorPanel(switchPanel))

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
