package slu.appli.chronos.ui

import slu.appli.chronos.time.Chronometer

import scala.swing.{Button, FlowPanel}

class CommandPanel[T](chronometer: Chronometer[T]) extends FlowPanel {

  val btnStart = Button("start") {
    chronometer.restart
  }
  btnStart.icon_=(MyImagesIcons.chronoStart)

  val btnStop = Button("stop") {
    chronometer.stop
  }
  btnStop.icon_=(MyImagesIcons.chronoStop)

  val btnPause = Button("pause") {
    chronometer.pause
  }
  btnPause.icon_=(MyImagesIcons.chronoPause)

  val btnReset = Button("reset") {
    chronometer.reset
  }
  btnReset.icon_=(MyImagesIcons.chronoReset)

  contents += btnStart
  contents += btnPause
  contents += btnStop
  contents += btnReset

  chronometer.onStateChanged { () =>
    state
  }

  state

  private def state: Unit = {
    btnStart.enabled_=(chronometer.isStopped || chronometer.isPaused || chronometer.isNull)
    btnStop.enabled_=(chronometer.isStarted)
    btnPause.enabled_=(chronometer.isStarted && !chronometer.isPaused)
    btnReset.enabled_=(chronometer.isStopped && !chronometer.isNull)
  }

}
