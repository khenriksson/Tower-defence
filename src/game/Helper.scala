package game

import processing.core.PApplet

abstract class Helper extends PApplet {
  // Window and cell sizes
  val wWidth: Int = 900
  val wHeight: Int = 750
  val cellSize: Int = 50

  // Sidemenu
  val menuWidth = 200

  // Top menu
  val topY = 650
  val topX = 0
  val topHeight = 100

  // Dynamic to each other
  val menuHeight = 325

  // Towermenu, containing all the towers visible
  val menuY = wHeight + topHeight // Sidemenu

  // A width for the boxes in the bottom
  val boxWidth = 225

  // MapWidth and Height
  val mWidth = 700
  val mHeight = 650

  // Textsizes
  val regular = 30
  val small = 15
  val rYOffset = 30
  val sYOffset = 15

  /**
   * Used to create a box
   * @param x position on the grid
   * @param y position on the grid
   * @param w the width of the box
   * @param h the height of the box
   * @param t the text in the box
   */
  def menuBox(x: Int, y: Int, w: Int, h: Int, t: String, size: Int, yOffset: Int) = {
    fill(255, 255, 255)
    rect(x, y, w, h, 10)
    fill(0, 0, 0)
    textSize(size)
    text(t, x + 2, y + yOffset)
  }

}

abstract class MapCell

class TowerCell extends MapCell

object Route extends MapCell
object GenerateCell extends MapCell
object Target extends MapCell