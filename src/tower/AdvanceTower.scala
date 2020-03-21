package tower

import processing.core.PApplet
import gamemaps._
import attackers._
import scala.collection.mutable.Buffer

class AdvanceTower(c: Cell, sketch: PApplet) extends Tower(c, sketch) {
  val name = "advance"
  val price: Int = 10
  val healthPoints: Int = 100
  val range: Int = 100
  val attackSpeed: Int = 10
  val attackDamage: Int = 10

  def display() {
    sketch.fill(0, 153, 0)
    sketch.rect(cell.x, cell.y, 50, 50)
  }
  def fire(fire: Fire) = ???

  def attack(attacker: Attackers) = {
    if (cell.distance(attacker.cell) < range) {
      attacker.healthPoints -= attackDamage
    }
  }

}