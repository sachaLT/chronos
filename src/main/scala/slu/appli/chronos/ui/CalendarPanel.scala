package slu.appli.chronos.ui

import javax.swing.BorderFactory

import slu.appli.chronos.time.TimePartLabels

import scala.swing._

class CalendarPanel extends DecoratedGridBagPanel {
  val txtDayLabel = newTextField(3)

  val txtDayNumber = newTextField(2)

  val txtMonth = newTextField(4)

  val txtYear = newTextField(4)

  val txtHour = newTextField(5)

  setFont(txtDayLabel.font.deriveFont(txtDayLabel.font.getSize2D * 2), txtHour, txtDayNumber)

  private def newTextField(cols: Int): TextField = {
    val tf = new TextField { columns = cols }
    tf.border_=(BorderFactory.createEmptyBorder())
    tf.editable_=(false)
    tf.horizontalAlignment_=(Alignment.Center)
    tf
  }

  add(txtDayLabel, constraints(0,0, gridheight = 2))
  add(txtDayNumber, constraints(1,0, gridheight = 2))
  add(txtMonth, constraints(2,0))
  add(txtYear, constraints(2,1))
  add(txtHour, constraints(4,0, gridheight = 2))

  def whatTimeIsIt() = {
    val timesParts = TimePartLabels()
    timesParts.toOption.fold(txtHour.text_=("??:??")) { parts =>
      txtDayLabel.text_=(parts.dayLabel.take(3))
      txtDayNumber.text_=(parts.dayNumber)
      txtMonth.text_=(parts.monthLabel.take(4))
      txtYear.text_=(parts.year)
      txtHour.text_=(s"${parts.hours}:${parts.minutes}")
    }
  }

}
