package slu.appli.chronos.ui

import scala.swing.{BorderPanel, Component}

class BorderLayoutPanel extends BorderPanel {

  def addCenter(comp: Component): BorderLayoutPanel = {
    add(comp, BorderPanel.Position.Center)
    this
  }
  def addEast(comp: Component): BorderLayoutPanel = {
    add(comp, BorderPanel.Position.East)
    this
  }
  def addWest(comp: Component): BorderLayoutPanel = {
    add(comp, BorderPanel.Position.West)
    this
  }
  def addNorth(comp: Component): BorderLayoutPanel = {
    add(comp, BorderPanel.Position.North)
    this
  }
  def addSouth(comp: Component): BorderLayoutPanel = {
    add(comp, BorderPanel.Position.South)
    this
  }
}
