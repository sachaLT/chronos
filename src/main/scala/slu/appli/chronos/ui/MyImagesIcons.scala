package slu.appli.chronos.ui

import javax.swing.ImageIcon

object MyImagesIcons {
  val apps = new ImageIcon(getClass.getClassLoader.getResource("images/clock.png"))
  val chronoStart = new ImageIcon(getClass.getClassLoader.getResource("images/chrono_start.png"))
  val chronoStop = new ImageIcon(getClass.getClassLoader.getResource("images/chrono_stop.png"))
  val chronoPause = new ImageIcon(getClass.getClassLoader.getResource("images/chrono_pause.png"))
  val chronoReset = new ImageIcon(getClass.getClassLoader.getResource("images/chrono_reset.png"))
  val chronos = new ImageIcon(getClass.getClassLoader.getResource("images/chronos_48x48.png"))
  val clock = new ImageIcon(getClass.getClassLoader.getResource("images/horloge_48x48.png"))
  val timer = new ImageIcon(getClass.getClassLoader.getResource("images/sablier_48x48.png"))
}
