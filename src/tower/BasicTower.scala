package tower

import processing.core.PApplet
import gamemaps._

class BasicTower(cell: Cell, sketch: PApplet) extends Tower(cell, sketch) {
  val price: Int = 10
  val healthPoints: Int = 100
  val range: Int = 100
  //  val icon = (0 + t.x, topHeight + t.y, 50, 50)
  val attackDamage: Int = 10

  def display() {
    sketch.fill(13, 255, 0)
    sketch.rect(x, y, 50, 50)
  }

  //  def drawTower(tower: Tower, x: Int, y: Int): Unit = {
  //    tower.icon
  //  }
}