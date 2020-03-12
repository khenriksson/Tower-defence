package gamemaps

import processing.core._
import tower._
import math.{ pow, sqrt }

class Cell(val x: Int, val y: Int) {

  def distance(to: Cell): Int = {
    sqrt(pow(this.y - to.y, 2) + pow(this.x - to.x, 2)).toInt
  }

  val location = (x, y)

  def directionCheck(direction: Int): Cell = {
    direction match {
      case 0 => new Cell(y + 1, x)
      case 1 => new Cell(y, x + 1)
      case 2 => new Cell(y - 1, x)
      case 3 => new Cell(y, x - 1)
    }
  }
}

