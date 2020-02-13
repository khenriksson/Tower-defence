package tower

import processing.core._
import Game._
import math.{ pow, sqrt }

class Cell(val x: Int, val y: Int) {

  //  def draw() = {
  //    noFill();
  //    stroke(0, 0, 0)
  //    rect(x * 20, y * 20, 20, 20)
  //  }

  def distance(to: Cell): Int = {
    sqrt(pow(this.y - to.y, 2) + pow(this.x - to.x, 2)).toInt
  }

}