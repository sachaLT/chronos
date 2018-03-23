package slu.appli.chronos.ui

import slu.appli.chronos.time.Elapse.values
import slu.appli.chronos.time.Timer

import scala.swing.event.SelectionChanged
import scala.swing.{ComboBox, GridBagPanel, Label}

class TimerPanel(timer: Timer[Long]) extends DecoratedGridBagPanel {
  private var previousValues = (0L,0L,0L,0L,0L)
  private val cbHours = newCombo(24)
  private val cbMinutes = newCombo(60)
  private val cbSeconds = newCombo(60)

  private val labTTT = new Label(".000")

  private val commandPanel = new CommandPanel(timer)

  private def newCombo(count: Int): ComboBox[String] = new ComboBox(Range(0,count).map(v => "%02d".format(v)))

  setFont(labTTT.font.deriveFont(labTTT.font.getSize2D * 6F), cbHours, cbMinutes, cbSeconds)
  setFont(labTTT.font.deriveFont(labTTT.font.getSize2D * 2F), labTTT)

  add(labTTT, constraints(3,0, weightx = 0.5, anchor = GridBagPanel.Anchor.LineStart))
  add(cbHours, constraints(0,1, weightx = 0.5, anchor = GridBagPanel.Anchor.LineEnd))
  add(cbMinutes, constraints(1,1))
  add(cbSeconds, constraints(2,1))

  add(commandPanel, constraints(0,2, gridwidth = 4))

  listenTo(cbSeconds.selection, cbMinutes.selection, cbHours.selection)
  reactions += {
    case SelectionChanged(cb) if cb.enabled =>
      val ss = cbSeconds.selection.item.toLong * 1000L
      val mm = cbMinutes.selection.item.toLong * 60000L
      val hh = cbHours.selection.item.toLong * 3600000L
      timer.initialValue(ss + mm + hh)
  }

  whatElapseIsIt()

  timer.onStateChanged { () =>
    val enabled = timer.isNull
    cbSeconds.enabled_=(enabled)
    cbMinutes.enabled_=(enabled)
    cbHours.enabled_=(enabled)
  }

  def whatElapseIsIt() = {
    val newValues = values(timer.elapseTime)
    if (newValues != previousValues) {
      previousValues = newValues
      val (_, hours, minutes, seconds, millis) = newValues
      cbHours.selection.index_=(hours.toInt)
      cbMinutes.selection.index_=(minutes.toInt)
      cbSeconds.selection.index_=(seconds.toInt)
      labTTT.text_=(".%03d".format(millis))
    }
  }

}
