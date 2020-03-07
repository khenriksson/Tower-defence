package tower

import processing.core.PApplet

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