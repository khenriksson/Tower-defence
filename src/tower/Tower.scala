package tower

import processing.core.PApplet
import attackers._
import gamemaps._
import scala.collection.mutable.Buffer

abstract class Tower(var cell: Cell, sketch: PApplet) extends Helper {
  val name: String
  val price: Int
  val healthPoints: Int
  val range: Int
  //  def icon: String
  val attackDamage: Int
  val attackSpeed: Int
  var fires = Buffer[Fire]()

  //  var x: Int = cell.x // Starting point
  //  var y: Int = cell.y
  var location = (cell.x, cell.y)
  var target: Option[Attackers] = None

  def attack(attacker: Attackers): Unit

  def display(): Unit
  def fire(fire: Fire): Unit

  override def toString() = {
    "Cell " + cell
  }

  def findClose(arr: Buffer[Attackers]): Unit = {
    if (!target.isDefined && !arr.isEmpty) {
      target = Some(arr.map(f => (f, f.cell.distance(this.cell))).minBy(_._2)._1)
    } else None
  }

}

