package slu.appli.chronos.ui

import scala.swing.{Component, Font, GridBagPanel}

class DecoratedGridBagPanel extends GridBagPanel {

  protected def constraints(x: Int, y: Int,
    gridwidth: Int = 1, gridheight: Int = 1,
    weightx: Double = 0.0, weighty: Double = 0.0,
    fill: GridBagPanel.Fill.Value = GridBagPanel.Fill.None)
  : Constraints = {
    val c = new Constraints
    c.gridx = x
    c.gridy = y
    c.gridwidth = gridwidth
    c.gridheight = gridheight
    c.weightx = weightx
    c.weighty = weighty
    c.fill = fill
    c
  }

  protected def setFont(font: Font, components: Component*) = {
    components.foreach(_.font_=(font))
  }

}
