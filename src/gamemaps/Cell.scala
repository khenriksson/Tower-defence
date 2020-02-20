package gamemaps

import processing.core._
import tower._
import math.{ pow, sqrt }

class Cell(val x: Int, val y: Int) {

  def distance(to: Cell): Int = {
    sqrt(pow(this.y - to.y, 2) + pow(this.x - to.x, 2)).toInt
  }

  val location = (x, y)
}

