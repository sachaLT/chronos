package slu.appli.chronos.ui

import java.awt.Dimension

import scala.swing.{BoxPanel, Component, Orientation}

class SwitchPanel(mainComponent: Component, others: Component*) extends BoxPanel(Orientation.Vertical) {
  contents += mainComponent
  others.foreach(comp => contents += comp)

  preferredSize_=( others.foldLeft(mainComponent.preferredSize) { (dim, comp) => biggerDimension(dim, comp.preferredSize)} )

  mainComponent.visible_=(true)
  others.foreach(_.visible_=(false))

  private def biggerDimension(dim1: Dimension, dim2: Dimension): Dimension = {
    val dim = new Dimension()
    dim.height =  if (dim1.height > dim2.height) dim1.height else dim2.height
    dim.width =  if (dim1.width > dim2.width) dim1.width else dim2.width
    dim
  }

  def switchNext(): (Option[Component], Component) = {
    val (mayBePrevious, next) = others.foldLeft[(Option[Component], Component)]((others.lastOption, mainComponent)) {
      case (result @ (Some(previous), _), _) if previous.visible =>
        result
      case ((_, previous), current) =>
        (Some(previous), current)
    }
    mayBePrevious.foreach(_.visible_=(false))
    next.visible_=(true)
    (mayBePrevious, next)
  }

  def switchPrevious(): (Option[Component], Component) = {
    val (next, mayBePrevious) = others.foldRight[(Component, Option[Component])]((mainComponent, others.headOption)) {
      case (_, result @ (_, Some(previous))) if previous.visible =>
        result
      case (current, (previous, _)) =>
        (current, Some(previous))
    }
    mayBePrevious.foreach(_.visible_=(false))
    next.visible_=(true)
    (mayBePrevious, next)
  }


  def select(component: Component): Unit = {
    (mainComponent +: others)
      .collect {
        case comp if comp == component => comp.visible_=(true)
        case comp if comp.visible => comp.visible_=(false)
      }
  }

}
