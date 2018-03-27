package slu.appli.chronos.ui

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
    DigitBlock.nextBlock(Right.apply(value), maxValue).foreach { digitBlock =>
      buildComponents(digitBlock)(ft).foreach {compo => contents += compo}
    }
  }
}
