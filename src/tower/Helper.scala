package tower

import processing.core.PApplet

abstract class Helper extends PApplet {
  val wWidth: Int = 900
  val wHeight: Int = 750

  // Sidemenu
  val menuWidth = 200

  // Top menu
  val topY = 0
  val topX = 0
  val topWidth = wWidth
  val topHeight = 100

  // Dynamic to each other

  val menuHeight = wHeight - topHeight // topMenu

  // Towermenu, containing all the towers visible
  val menuY = wHeight + topHeight // Sidemenu

  // A width for the boxes in the top
  val boxWidth = (wWidth - menuWidth) / 3

  // MapWidth and Height
  val mWidth = boxWidth * 3 - 50
  val mHeight = wHeight
}

abstract class MapCell

class TowerCell extends MapCell
object Route extends MapCell
object GenerateCell