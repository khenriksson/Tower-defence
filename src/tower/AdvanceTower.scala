package tower

import processing.core.PApplet
import gamemaps._
import attackers._
import scala.collection.mutable.Buffer

class AdvanceTower(cell: Cell, sketch: PApplet) extends Tower(cell, sketch) {
  val price: Int = 10
  val healthPoints: Int = 100
  val range: Int = 100
  val attackSpeed: Int = 10
  val attackDamage: Int = 10

  def display() {
    sketch.fill(100, 255, 0)
    sketch.rect(x, y, 50, 50)
  }

  def attack(attacker: Attackers) = {
    if (cell.distance(attacker.cell) < range) {
      attacker.healthPoints -= attackDamage
    }
  }

}