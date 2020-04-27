package attackers

import tower._
import gamemaps._
import game.Target
import game.Route
import game.GenerateCell
import game.Player

// Abstract class for the attackers to keep track of everything
abstract class Attackers(var cell: Cell) {
  val name: String
  val winning: Int
  val attackDamage: Int

  var healthPoints: Int
  var course: Int
  var dead: Boolean = false

  /**
   * If the attacker is taking damage, remove it
   * @param fromTower tower that is attacking, needs because towers have different damage
   */
  def takingDamage(fromTower: Tower): Unit = {
    healthPoints -= fromTower.attackDamage
    if (isDead) {
      fromTower.target = None
    }
  }

  /**
   * Checks if the attacker is dead
   */
  def isDead(): Boolean = {
    healthPoints <= 0

  }

  /**
   * Attacks the castle and removes player health
   */
  def attack(): Unit = {
    if (!isDead) {
      Player.healthPoints -= attackDamage
    }
  }

  // DO NOT TOUCH
  /**
   * Used for drawing the attackers movements in Game
   * There are 4 different courses, 0 to 3
   * 0 -> heading is in the +x direction (right)
   * 1 -> heading is in the -y direction (up)
   * 2 -> heading is in the -x direction (left)
   * 3 -> heading is in the +y direction (down)
   * @param map current map played
   * @return Boolean
   */
  def move(map: FileToMap): Boolean = {
    // If attacker is dead, then it's not moving at all
    if (isDead) {
      if (map.cellType(cell.directionCheck(course)) != Target ||
        map.cellType(cell.directionCheck((course + 1) % 4)) != Target ||
        map.cellType(cell.directionCheck((4 + course - 1))) != Target) Player.money += winning
      dead = true
      return false
    }
    // If the cell in front is route or spawn continue in the same direction
    if (map.cellType(cell.directionCheck(course % 4)) == Route ||
      map.cellType(cell.directionCheck(course % 4)) == GenerateCell) {
      cell = cell.directionCheck(course)
    } else if (map.cellType(cell.directionCheck((course + 1) % 4)) == Route ||
      map.cellType(cell.directionCheck((course + 1) % 4)) == GenerateCell) {
      // Else check if course + 1 is route or spawn, and change the course
      course = (course + 1) % 4
      cell = cell.directionCheck(course)
    } else if (map.cellType(cell.directionCheck((4 + course - 1) % 4)) == Route ||
      map.cellType(cell.directionCheck(4 + (course - 1) % 4)) == GenerateCell) {
      // Else check if the opposite direction to the previous one is route or spawn and continue in that direction
      course = (4 + course - 1) % 4
      cell = cell.directionCheck(course)
    } else if (map.cellType(cell.directionCheck(course)) == Target ||
      map.cellType(cell.directionCheck((course + 1) % 4)) == Target ||
      map.cellType(cell.directionCheck((4 + course - 1))) == Target) {
      // Attack if we're heading to the castle
      attack()
      return false
    }
    true
  }

}