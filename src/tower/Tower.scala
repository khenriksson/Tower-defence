package tower

import processing.core.PApplet
import attackers._
import gamemaps._

abstract class Tower(val cell: Cell, sketch: PApplet) extends Helper {
  val price: Int
  val healthPoints: Int
  val range: Int
  //  def icon: String
  val attackDamage: Int

  var x: Int = cell.x // Starting point
  var y: Int = cell.y
  val location = (x, y)

  def attack(attacker: Attackers): Unit = {
    attacker.takingDamage(this)
  }

  def display(): Unit

  override def toString() = {
    "Cell " + cell
  }
}

