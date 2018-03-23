package slu.appli.chronos.ui

import java.awt.Color

import scala.swing.event.MouseReleased
import scala.swing.{Alignment, FlowPanel, Label, Swing}

class SelectorPanel(switch: SwitchPanel) extends FlowPanel {
  val labChrono = new Label("", MyImagesIcons.chronos, Alignment.Center) {
    listenTo(mouse.clicks)
    reactions += {
      case MouseReleased(_, _, _, _, _) => switch.select("chrono")
    }
  }
  val labTimer = new Label("", MyImagesIcons.timer, Alignment.Center){
    listenTo(mouse.clicks)
    reactions += {
      case MouseReleased(_, _, _, _, _) => switch.select("timer")
    }
  }
  val map: Map[String, Label] = Map (
    ("chrono", labChrono),
    ("timer", labTimer)
  )

  map.foreach { case (_, lab) =>
    unselect(lab)
    contents += lab
  }

  listenTo(switch)
  reactions += {
    case SwitchPanel.SelectionChanged(mayBePrevious, mayBeSelected) =>
      mayBePrevious.flatMap(map.get).foreach(unselect)
      mayBeSelected.flatMap(map.get).foreach(select)
  }

  switch.select("chrono")

  private def select(lab: Label) = lab.border_=(Swing.LineBorder(Color.BLUE, 2))

  private def unselect(lab: Label) = lab.border_=(Swing.LineBorder(Color.WHITE, 2))
}
