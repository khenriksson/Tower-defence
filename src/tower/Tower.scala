package tower

import processing.core.PApplet
import attackers._
import gamemaps._
import scala.collection.mutable.Buffer
import game.Helper

abstract class Tower(var cell: Cell, sketch: PApplet) extends Helper {
  val name: String
  val price: Int
  val levelsMapped: Map[Int, Int]

  // Modified variables
  var attackDamage: Int
  var level: Int
  var location = (cell.x, cell.y)
  var target: Option[Attackers] = None

  def attack(): Unit
  def display(): Unit
  def fire(fire: Fire): Unit
  def init(): Unit

  // Levels the tower up
  def levelUp() = level += 1

  /**
   * Finds the closest attacker
   * Problem: It's not updating therefore range cannot be calculated
   * @param arr array of attackers from gameInstance
   */
  def findClose(arr: Buffer[Attackers]): Unit = {
    if (target.isDefined) {
      attack()
    } else if (!arr.isEmpty) {
      var test = arr.map(f => (f, f.cell.distance(this.cell))).minBy(_._2)
      var attacker = test._1
      target = Some(attacker)
    } else target = None
  }

}

