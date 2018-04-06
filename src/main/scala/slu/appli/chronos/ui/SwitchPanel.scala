package slu.appli.chronos.ui

import java.awt.Dimension

import scala.swing.event.Event
import scala.swing.{BoxPanel, Component, Orientation}

class SwitchPanel(mainComponent: Component, others: Component*) extends BoxPanel(Orientation.Vertical) {
  contents += mainComponent
  others.foreach(comp => contents += comp)

  preferredSize_=( others.foldLeft(mainComponent.preferredSize) { (dim, comp) => biggerDimension(dim, comp.preferredSize)} )

  mainComponent.visible_=(true)
  others.foreach(_.visible_=(false))
  select(mainComponent.name)

  private def biggerDimension(dim1: Dimension, dim2: Dimension): Dimension = {
    val dim = new Dimension()
    dim.height =  if (dim1.height > dim2.height) dim1.height else dim2.height
    dim.width =  if (dim1.width > dim2.width) dim1.width else dim2.width
    dim
  }

  def switchNext(): Unit = {
    val (mayBePrevious, selected) = others.foldLeft[(Option[Component], Component)]((others.lastOption, mainComponent)) {
      case (result @ (Some(previous), _), _) if previous.visible =>
        result
      case ((_, previous), current) =>
        (Some(previous), current)
    }

    selected.visible_=(true)
    publish(SwitchPanel.SelectionChanged(
      mayBePrevious.map { comp =>
        comp.visible_=(false)
        comp.name
      },
      Some(selected.name)
    ))
  }

  def switchPrevious(): Unit = {
    val (selected, mayBePrevious) = others.foldRight[(Component, Option[Component])]((mainComponent, others.headOption)) {
      case (_, result @ (_, Some(previous))) if previous.visible =>
        result
      case (current, (previous, _)) =>
        (current, Some(previous))
    }

    selected.visible_=(true)
    publish(SwitchPanel.SelectionChanged(
      mayBePrevious.map { comp =>
        comp.visible_=(false)
        comp.name
      },
      Some(selected.name)
    ))
  }


  def select(name: String): Unit = {
    var previous: Option[String] = None
    var selected: Option[String] = None
    (mainComponent +: others)
      .collect {
        case comp if comp.name == name =>
          comp.visible_=(true)
          selected = Some(comp.name)
        case comp if comp.visible =>
          comp.visible_=(false)
          previous = Some(comp.name)
      }
    publish(SwitchPanel.SelectionChanged(previous, selected))
  }

  def componentNames: Seq[String] = contents.map(_.name)

}

object SwitchPanel {
  case class SelectionChanged(previous: Option[String]=None, selected: Option[String]) extends Event
}
