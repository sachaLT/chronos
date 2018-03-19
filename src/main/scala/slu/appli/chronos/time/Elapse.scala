package slu.appli.chronos.time

object Elapse {

  private val hoursPerDay = 24
  private val minutesPerHour = 60
  private val secondsPerMinute = 60
  private val millisPerSecond = 1000
  private val millisPerMinute = secondsPerMinute * millisPerSecond
  private val millisPerHour = minutesPerHour * millisPerMinute
  private val millisPerDay = hoursPerDay * millisPerHour

  def prettyPrint(value: Long, format: ElapseFormatter = ElapseFormatter.DDHHMMSSTTT): String = {
    val (days, hours, minutes, seconds, millis) = values(value)
    format.format(days, hours, minutes, seconds, millis)
  }

  def values(value: Long): (Long, Long, Long, Long, Long) = {
    val days: Long = value / Elapse.millisPerDay
    val hhmmssttt: Long = value % Elapse.millisPerDay
    val hours: Long = hhmmssttt / Elapse.millisPerHour
    val mmssttt: Long = hhmmssttt % Elapse.millisPerHour
    val minutes: Long = mmssttt / Elapse.millisPerMinute
    val ssttt: Long = mmssttt % Elapse.millisPerMinute
    val seconds: Long = ssttt / Elapse.millisPerSecond
    val millis: Long = ssttt % Elapse.millisPerSecond
   (days, hours, minutes, seconds, millis)
  }

}
