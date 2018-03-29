package slu.appli.chronos.ui

import javax.swing.event.{MenuEvent, MenuListener}

import slu.appli.chronos.utils.DigitBlock

import scala.swing._

class NumberSelector(maxValue: Int)(ft: (Int) => Unit) extends PopupMenu() {
  import NumberSelector._
  buildComponents(DigitBlock(maxValue))(ft).foreach {compo => contents += compo}
}

object NumberSelector {

  def buildComponents(digitBlock: DigitBlock)(ft: (Int) => Unit): Seq[Component] = digitBlock.values.map {
    case Left(leaf) => new NumberSelectorMenuItem(leaf)(ft)
    case Right(value) => new NumberSelectorMenu(digitBlock.maxValue, value)(ft)
  }

  class NumberSelectorMenuItem(value: Int)(ft: (Int) => Unit) extends MenuItem(Action(value.toString) { ft(value) })

  class NumberSelectorMenu(maxValue: Int, value: Int)(ft: (Int) => Unit) extends Menu(value.toString) {
    peer.addMenuListener( new MenuListener {

      override def menuSelected(e: MenuEvent): Unit = {
        DigitBlock.nextBlock(Right.apply(value), maxValue).foreach { digitBlock =>
          buildComponents(digitBlock)(ft).foreach {compo => contents += compo}
        }
      }

      override def menuCanceled(e: MenuEvent): Unit = contents.clear()

      override def menuDeselected(e: MenuEvent): Unit = contents.clear()
    })
  }
}
