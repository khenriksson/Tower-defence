package tower

import processing.core.PApplet
import gamemaps._
import attackers._
import scala.collection.mutable.Buffer

class AdvanceTower(c: Cell, sketch: PApplet) extends Tower(c, sketch) {
  val name = "advance"
  val price: Int = 20
  val healthPoints: Int = 100
  val attackSpeed: Int = 10
  val levelsMapped = Map(0 -> (price * 1.3).toInt, 1 -> (price * 1.5).toInt, 2 -> (price * 1.7).toInt)

  // Modified variables
  var attackDamage: Int = 20
  var range: Int = 100
  var level: Int = 0

  def display() {
    sketch.fill(0, 153, 0)
    sketch.rect(cell.x, cell.y, 50, 50)
  }
  def fire(fire: Fire) = ???

  def attack(attacker: Attackers) = {
    attacker.takingDamage(this)
  }

}