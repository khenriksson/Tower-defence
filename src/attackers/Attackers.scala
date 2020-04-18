package attackers

import tower._
import gamemaps._
import processing.core.PApplet

// Abstract class for the attackers to keep track of everything
abstract class Attackers(var cell: Cell) extends Helper {
  val name: String
  val speed: Int
  val winning: Int
  var healthPoints: Int
  val attackDamage: Int
  var course: Int
  var dead: Boolean = false

  def takingDamage(fromTower: Tower): Unit = {
    healthPoints -= fromTower.attackDamage
    if (isDead) Player.money += winning
  }

  def isDead(): Boolean = {
    healthPoints <= 0
  }

  def attack(): Unit = {
    if (!isDead) {
      Player.healthPoints -= attackDamage
    }
  }

  def checkDirection(map: FileToMap, course: Int) = {
    map.cellType(cell.directionCheck(course))
  }

  // DO NOT TOUCH
  def move(map: FileToMap): Boolean = {
    if (isDead) {
      dead = true
      return false
    }
    if (map.cellType(cell.directionCheck(course % 4)) == Route ||
      map.cellType(cell.directionCheck(course % 4)) == GenerateCell) {
      cell = cell.directionCheck(course)
    } else if (map.cellType(cell.directionCheck((course + 1) % 4)) == Route ||
      map.cellType(cell.directionCheck((course + 1) % 4)) == GenerateCell) {
      course = (course + 1) % 4
      cell = cell.directionCheck(course)
    } else if (map.cellType(cell.directionCheck((4 + course - 1) % 4)) == Route ||
      map.cellType(cell.directionCheck(4 + (course - 1) % 4)) == GenerateCell) {
      course = (4 + course - 1) % 4
      cell = cell.directionCheck(course)
    } else if (map.cellType(cell.directionCheck(course)) == Target ||
      map.cellType(cell.directionCheck((course + 1) % 4)) == Target ||
      map.cellType(cell.directionCheck((4 + course - 1))) == Target) {
      attack()
      return false
    }
    true
  }

}