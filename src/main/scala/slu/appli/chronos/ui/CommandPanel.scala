package slu.appli.chronos.ui

import javax.swing.ImageIcon

import slu.appli.chronos.time.Chronometer

import scala.swing.{Button, FlowPanel}

class CommandPanel[T](chronometer: Chronometer[T]) extends FlowPanel {

  val btnStart = Button("start") {
    chronometer.restart
    state
  }
  btnStart.icon_=(new ImageIcon(getClass.getClassLoader.getResource("images/chrono_start.png")))

  val btnStop = Button("stop") {
    chronometer.stop
    state
  }
  btnStop.icon_=(new ImageIcon(getClass.getClassLoader.getResource("images/chrono_stop.png")))

  val btnPause = Button("pause") {
    chronometer.pause
    state
  }
  btnPause.icon_=(new ImageIcon(getClass.getClassLoader.getResource("images/chrono_pause.png")))

  val btnReset = Button("reset") {
    chronometer.reset
    state
  }
  btnReset.icon_=(new ImageIcon(getClass.getClassLoader.getResource("images/chrono_reset.png")))

  contents += btnStart
  contents += btnPause
  contents += btnStop
  contents += btnReset

  state

  private def state: Unit = {
    btnStart.enabled_=(chronometer.isStopped || chronometer.isPaused || chronometer.isNull)
    btnStop.enabled_=(chronometer.isStarted)
    btnPause.enabled_=(chronometer.isStarted && !chronometer.isPaused)
    btnReset.enabled_=(chronometer.isStopped && !chronometer.isNull)
  }

}
