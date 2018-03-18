package slu.appli.chronos

import java.time.LocalDateTime

import scala.util.Success

import org.scalatest.{FlatSpec, Matchers}


class TimePartLabelsSpec  extends FlatSpec with Matchers {
  val date: LocalDateTime = LocalDateTime.parse("2017-12-25T10:15:30")

  date.toString should "be parsed" in {
    TimePartLabels(date).map { timePartLabels =>
      Seq(
        timePartLabels.dayLabel,
        timePartLabels.dayNumber,
        timePartLabels.monthLabel,
        timePartLabels.monthNumber,
        timePartLabels.year,
        timePartLabels.hours,
        timePartLabels.minutes,
        timePartLabels.seconds
      ).mkString(" ")
    } should be (Success("lundi 25 décembre 12 2017 10 15 30"))
  }
}
