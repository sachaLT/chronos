package slu.appli.chronos.ui

import slu.appli.chronos.time.Elapse.values
import slu.appli.chronos.time.{Chronometer}

import scala.swing._

class ChronoPanel(chronometer: Chronometer[Long]) extends DecoratedGridBagPanel {
  private var previousValues = (0L,0L,0L,0L,0L)
  private val labHHMMSS = new Label("00:00:00")
  private val labTTT = new Label(".000")
  private val labDay = new Label("0")
  private val commandPanel = new CommandPanel(chronometer)

  labHHMMSS.background_=(java.awt.Color.yellow)

  setFont(labHHMMSS.font.deriveFont(labHHMMSS.font.getSize2D * 2F), labTTT, labDay)
  setFont(labHHMMSS.font.deriveFont(labHHMMSS.font.getSize2D * 6F), labHHMMSS)

  add(labDay, constraints(0,0))
  add(labHHMMSS, constraints(0,1, gridheight = 3, weightx = 0.5, anchor = GridBagPanel.Anchor.LineEnd))
  add(labTTT, constraints(1,3, weightx = 0.5, anchor = GridBagPanel.Anchor.LineStart))
  add(commandPanel, constraints(0,4, gridwidth = 2))

  def whatElapseIsIt() = {
    val newValues = values(chronometer.elapseTime)
    if (newValues != previousValues) {
      previousValues = newValues
      val (days, hours, minutes, seconds, millis) = newValues
      labHHMMSS.text_=("%02d:%02d:%02d".format(hours, minutes, seconds))
      labTTT.text_=(".%03d".format(millis))
      labDay.text_=("%d".format(days))

    }
  }

}

