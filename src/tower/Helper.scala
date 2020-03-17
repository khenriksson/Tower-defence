package tower

import processing.core.PApplet
import scala.collection.mutable.Buffer
import java.util.Calendar

// Rename Helper to make it more clear what it does
// Describe in more detail
// Loop functionality for wave, all enemies are dead or health is 0
// Start button to begin the round, each loop enemies are progressing by one and towers are shooting, probably should happen in GameState

// Store the cells somewhere, eg Map class
// Booleans for cells buildability
// How to divide main game loop and drawing loop
// Implement all the unit tests mentioned in the technical plan
// Instruction button to main file for user, small popup on how to use

// Implement conditions for the maps (how large and width)

abstract class Helper extends PApplet {
  val wWidth: Int = 900
  val wHeight: Int = 750
  val cellSize: Int = 50

  // Sidemenu
  val menuWidth = 200

  // Top menu
  val topY = 650
  val topX = 0
  val topWidth = wWidth
  val topHeight = 100

  // Dynamic to each other

  val menuHeight = 325

  // Towermenu, containing all the towers visible
  val menuY = wHeight + topHeight // Sidemenu

  // A width for the boxes in the top
  val boxWidth = 225

  // MapWidth and Height
  val mWidth = 700
  val mHeight = 650

  /**
   * Used to create a box
   * @param x position on the grid
   * @param y position on the grid
   * @param w the width of the box
   * @param h the height of the box
   * @param t the text in the box
   */
  def menuBox(x: Int, y: Int, w: Int, h: Int, t: String) = {
    fill(255, 255, 255)
    rect(x, y, w, h)
    fill(235, 52, 52)
    text(t, x + 2, y + 30)
  }

  def getTime = {
    val now = Calendar.getInstance();
    now.get(Calendar.MINUTE) * 60 + now.get(Calendar.SECOND)
  }
}

abstract class MapCell

class TowerCell extends MapCell {
  //  var tower: Option[Tower] = None
}

object Route extends MapCell
object GenerateCell extends MapCell
object Target extends MapCell
object Menu extends MapCell