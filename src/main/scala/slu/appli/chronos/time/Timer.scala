package slu.appli.chronos.time

trait Timer[T] extends Chronometer[T] {
  def initialValue(value: T): Unit
  def isEnded: Boolean
}

object Timer {

  def apply(): Timer[Long] = apply(0L)

  def apply(value: Long): Timer[Long] = {
    val timer = new SystemTimer
    timer.initialValue(value)
    timer
  }

  def apply(hours: Long, minutes: Long, seconds: Long): Timer[Long] = apply((hours * 3600L + minutes * 60L + seconds) * 1000L)
}

class SystemTimer() extends Timer[Long] {

  private var startedTime: Long = 0L
  private var elapse: Long = 0L
  private var pauseStartedTime: Long = 0L
  private var pauseElapse: Long = 0L

  private var stopValue: Long = 0L
  private var endValue: Long = 0L

  override def initialValue(value: Long): Unit = {
    endValue = value
    stopValue = value
    reset
  }

  override def start: Unit = if (isStopped  || isEnded) {
    raz
    startedTime = System.currentTimeMillis()
    pushStateChanged()
  }

  override def pause: Unit = if (isStarted  && !isEnded) {
    pauseStartedTime = System.currentTimeMillis
    setElapse(elapse + pauseStartedTime - startedTime)
    pushStateChanged()
  }

  override def stop: Unit =  if (isStarted) {
    stopValue = if (isPaused) elapse else computeElapse
    raz
    setElapse(stopValue)
    pushStateChanged()
  }

  override def restart: Unit = if (isPaused) {
    startedTime = System.currentTimeMillis()
    pauseElapse = pauseElapse + startedTime - pauseStartedTime
    pauseStartedTime = 0L
    pushStateChanged()
  } else if (isStopped  || isEnded) {
    start
  }

  override def reset: Unit = {
    raz
    pushStateChanged()
  }

  private def raz: Unit = {
    startedTime = 0L
    pauseStartedTime = 0L
    setElapse(0L)
    pauseElapse = 0L
  }

  override def elapseTime: Long = {
    val value: Long = if (isStopped || isPaused  || isEnded) elapse else computeElapse
    if (value < endValue) endValue - value else endValue
  }

  override def duration: Long = if (isStopped  || isEnded) {
    elapse + pauseElapse
  } else if (isPaused) {
    elapse + pauseElapse + System.currentTimeMillis - pauseStartedTime
  } else if (isStarted) {
    pauseElapse + computeElapse
  } else 0L

  override def isStarted: Boolean = startedTime != 0L

  override def isPaused: Boolean = pauseStartedTime != 0L

  override def isStopped: Boolean = startedTime == 0L

  override def isNull: Boolean = startedTime == 0L && (elapse == 0L || stopValue == endValue)

  override def isEnded: Boolean = {
    val ended = elapse >= endValue
    if (ended && !isStopped) {
      stop
    }
    ended
  }

  private def setElapse(value: Long): Long = {
    elapse = if (value >= endValue) endValue else value
    elapse
  }

  private def computeElapse(): Long = {
    val now = System.currentTimeMillis
    val value = elapse + now - startedTime
    startedTime = now
    elapse = if (value >= endValue) endValue else value
    elapse
  }
}
