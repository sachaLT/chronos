package slu.appli.chronos.time

trait Chronometer[T] {
  def start: Unit
  def pause: Unit
  def stop: Unit
  def restart: Unit
  def reset: Unit
  def elapseTime: T
  def duration: T
  def isStarted: Boolean
  def isPaused: Boolean
  def isStopped: Boolean
  def isNull: Boolean
}

object Chronometer {
  def apply(): Chronometer[Long] = new SystemChronometer
}


class SystemChronometer() extends Chronometer[Long] {

  private var startedTime: Long = 0L
  private var elapse: Long = 0L
  private var pauseStartedTime: Long = 0L
  private var pauseElapse: Long = 0L

  override def start: Unit = if (isStopped) {
    startedTime = System.currentTimeMillis()
    elapse = 0L
    pauseStartedTime = 0L
    pauseElapse = 0L
  }

  override def pause: Unit = if (isStarted) {
    pauseStartedTime = System.currentTimeMillis
    elapse = elapse + pauseStartedTime - startedTime
  }

  override def stop: Unit =  if (isStarted) {
    val stopValue = System.currentTimeMillis
    elapse = if (isPaused) elapse else elapse + stopValue - startedTime
    startedTime = 0L
    pauseStartedTime = 0L
  }

  override def restart: Unit = if (isPaused) {
    startedTime = System.currentTimeMillis()
    pauseElapse = pauseElapse + startedTime - pauseStartedTime
    pauseStartedTime = 0L
  } else if (isStopped) {
    start
  }

  override def reset: Unit = {
    startedTime = 0L
    pauseStartedTime = 0L
    elapse = 0L
    pauseElapse = 0L
  }

  override def elapseTime: Long =
    if (isStopped || isPaused) elapse else {
    elapse + System.currentTimeMillis - startedTime
  }

  override def duration: Long = if (isStopped) {
    elapse + pauseElapse
  } else if (isPaused) {
    elapse + pauseElapse + System.currentTimeMillis - pauseStartedTime
  } else if (isStarted) {
    elapse + pauseElapse + System.currentTimeMillis - startedTime
  } else 0L

  override def isStarted: Boolean = startedTime != 0L

  override def isPaused: Boolean = pauseStartedTime != 0L

  override def isStopped: Boolean = startedTime == 0L

  override def isNull: Boolean = startedTime == 0L && elapse == 0L
}
