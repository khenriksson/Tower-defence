package gamemaps

import tower._
import math.{ pow, sqrt }

class Cell(val x: Int, val y: Int) {

  val location = (x, y)

  // Supposed to be used for calculating tower range
  def distance(to: Cell): Int = {
    sqrt(pow((this.y - to.y), 2) + pow((this.x - to.x), 2)).toInt
  }

  /**
   * Used in the attackers move function to check where they are heading
   * @param direction Int, used in the attackers choosing their direction
   * @return new Cell location for the attacker
   */
  def directionCheck(direction: Int): Cell = {
    direction match {
      case 0 => new Cell(x + 1, y)
      case 1 => new Cell(x, y - 1)
      case 2 => new Cell(x - 1, y)
      case 3 => new Cell(x, y + 1)
    }
  }
}