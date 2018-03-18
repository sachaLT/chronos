package slu.appli.chronos.time

trait ElapseFormatter {
  def format(days: Long, hours: Long, minutes: Long, seconds: Long, millis: Long): String
}

object ElapseFormatter {

  case object DDHHMMSSTTT extends ElapseFormatter {
    override def format(days: Long, hours: Long, minutes: Long, seconds: Long, millis: Long): String =
      if (days == 0L) "%02d:%02d:%02d.%03d".format (hours, minutes, seconds, millis)
      else "%d %02d:%02d:%02d.%03d".format (days, hours, minutes, seconds, millis)
  }

  case object DDHHMMSS extends ElapseFormatter {
    override def format(days: Long, hours: Long, minutes: Long, seconds: Long, millis: Long): String =
      if (days == 0L) HHMMSS.format(days, hours, minutes, seconds, millis)
      else "%d %02d:%02d:%02d".format (days, hours, minutes, seconds)
  }

  case object HHMMSS extends ElapseFormatter {
    override def format(days: Long, hours: Long, minutes: Long, seconds: Long, millis: Long): String =
      "%02d:%02d:%02d".format (hours, minutes, seconds)
  }

}
