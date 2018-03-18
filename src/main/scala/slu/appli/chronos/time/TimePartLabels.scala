package slu.appli.chronos.time

import java.time.format.DateTimeFormatter
import java.time.{LocalDateTime, ZoneId}
import java.util.Date

import scala.util.{Failure, Success, Try}

case class TimePartLabels (
  dayLabel: String,
  dayNumber: String,
  monthLabel: String,
  monthNumber: String,
  year: String,
  hours: String,
  minutes: String,
  seconds: String
)

object TimePartLabels {
  private val generiqueDateTimeFormatter = DateTimeFormatter.ofPattern("EEEE dd MMMM MM yyyy HH mm ss")

  def apply(): Try[TimePartLabels] = apply(LocalDateTime.now(ZoneId.systemDefault))

  def apply(localDateTime: LocalDateTime): Try[TimePartLabels] = Try {
   localDateTime.format(generiqueDateTimeFormatter).split(' ')
  } flatMap { parts =>
    if (parts.length != 8) Failure(new RuntimeException(s"parsing error"))
    else Success(
      TimePartLabels (
        dayLabel = parts(0),
        dayNumber = parts(1),
        monthLabel = parts(2),
        monthNumber = parts(3),
        year = parts(4),
        hours = parts(5),
        minutes = parts(6),
        seconds = parts(7)
      )
    )
  }

  def apply(value: Long): Try[TimePartLabels] = apply(LocalDateTime.ofInstant(new Date(value).toInstant, ZoneId.systemDefault()))

}
