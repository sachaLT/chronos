package slu.appli.chronos.ui

import slu.appli.chronos.time.Elapse.values
import slu.appli.chronos.time.Timer

import scala.swing._
import scala.swing.event.ValueChanged

class TimerTextPanel (timer: Timer[Long]) extends DecoratedGridBagPanel {
  private var previousValues = (0L,0L,0L,0L,0L)
  private val cbHours = newCompo(23)
  private val cbMinutes = newCompo(59)
  private val cbSeconds = newCompo(59)

  private val labTTT = new Label(".000")

  private val commandPanel = new CommandPanel(timer)

  private def newCompo(count: Int): TextField = {
    val length: Int = count.toString.length
    val format: String = s"%0${length}d"
    val tf = new TextField(format.format(0), length)
    tf.editable_=(false)
    tf.horizontalAlignment_=(Alignment.Center)
    tf.peer.setComponentPopupMenu(new NumberSelector(count)( (value) =>
      if (timer.isNull) tf.text_=(format.format(value))
    ).peer)
    tf
  }

  private def newPoint: Label = {
    val lab = new Label(":")
    setFont(lab.font.deriveFont(lab.font.getSize2D * 6F), lab)
    lab
  }

  setFont(labTTT.font.deriveFont(labTTT.font.getSize2D * 6F), cbHours, cbMinutes, cbSeconds)
  setFont(labTTT.font.deriveFont(labTTT.font.getSize2D * 2F), labTTT)

  add(cbHours, constraints(0,0, weightx = 0.5, anchor = GridBagPanel.Anchor.LineEnd))
  add(newPoint, constraints(1,0))
  add(cbMinutes, constraints(2,0))
  add(newPoint, constraints(3,0))
  add(cbSeconds, constraints(4,0))
  add(labTTT, constraints(5,0, weightx = 0.5, anchor = GridBagPanel.Anchor.LineStart))

  add(commandPanel, constraints(0,2, gridwidth = 6))

  listenTo(cbSeconds, cbMinutes, cbHours)
  reactions += {
    case ValueChanged(`cbSeconds`) if timer.isNull && !cbSeconds.text.isEmpty =>
      initTimer()
    case ValueChanged(`cbMinutes`) if timer.isNull && !cbMinutes.text.isEmpty =>
       initTimer()
    case ValueChanged(`cbHours`) if timer.isNull && !cbHours.text.isEmpty =>
       initTimer()
  }

  private def initTimer(): Unit = {
    val ss = cbSeconds.text.toLong * 1000L
    val mm = cbMinutes.text.toLong * 60000L
    val hh = cbHours.text.toLong * 3600000L
    timer.initialValue(ss + mm + hh)
  }

  whatElapseIsIt()

  def whatElapseIsIt() = {
    val newValues = values(timer.elapseTime)
    if (newValues != previousValues) {
      previousValues = newValues
      val (_, hours, minutes, seconds, millis) = newValues
      setText(cbHours, hours)
      setText(cbMinutes, minutes)
      setText(cbSeconds, seconds)
      labTTT.text_=(".%03d".format(millis))
    }
  }

  private def setText(tf: TextField, value: Long) = {
    val length: Int = tf.columns
    val format: String = s"%0${length}d"
    tf.text_=(format.format(value))
  }

}