package slu.appli.chronos.timer

trait TimerItem {
  val task: () => Unit
  def firtsInstance: () => Long
  def next(): Boolean
}

case class IntervalTimerItem (
  interval: Int,
  task: () => Unit,
  firtsInstance: () => Long = () => System.currentTimeMillis,
  waitFirstInstance: Boolean = true
) extends TimerItem {
  private var previous: Long = 0L
  private var processAt: Long = 0L

  def lastValue: Long = previous
  def nextValue: Long = processAt

  def next(): Boolean = {
    val now = System.currentTimeMillis
    processAt = if (processAt == 0L) firtsInstance() else processAt
    val timeToProcess = processAt <= now
    if (timeToProcess) {
      previous = processAt
      processAt = processAt + interval
      task()
    } else if (previous == 0 && !waitFirstInstance) {
      previous = now
      task()
    }
    timeToProcess
  }

}

case class SingleTimerItem (
  task: () => Unit,
  firtsInstance: () => Long = () => System.currentTimeMillis
) extends TimerItem {
  private var previous: Long = 0L
  private var processAt: Long = 0L

  def lastValue: Long = previous
  def nextValue: Long = processAt

  def next(): Boolean = {
    val now = System.currentTimeMillis
    processAt = if (processAt == 0L) firtsInstance() else processAt
    val timeToProcess = processAt > 0L && processAt <= now
    if (timeToProcess) {
      previous = processAt
      processAt = -1L
      task()
    }
    timeToProcess
  }

}

object TimerItem {

  val intervalForSecond: Int = 1000
  val intervalForMinute: Int = 60000
  val intervalForHour: Int = 3600000

  private def firstInstanceForTime(timeInMillis: Long) = {
    val millis: Long = System.currentTimeMillis
    if (millis % timeInMillis < timeInMillis / 2L) (System.currentTimeMillis / timeInMillis) * timeInMillis
    else ((System.currentTimeMillis / timeInMillis) + 1L) * timeInMillis
  }

  def firstInstanceForMillis() = System.currentTimeMillis

  def firstInstanceForSecond() = firstInstanceForTime(intervalForSecond)

  def firstInstanceForMinute() = firstInstanceForTime(intervalForMinute)

  def firstInstanceForHour() = firstInstanceForTime(intervalForHour)

}
