package tower

import processing.core.PApplet
import gamemaps._
import attackers._
import scala.collection.mutable.Buffer

class BasicTower(c: Cell, sketch: PApplet) extends Tower(c, sketch) {
  val name = "basic"
  val price: Int = 10
  val healthPoints: Int = 100
  val range: Int = 300
  val attackSpeed: Int = 1
  val attackDamage: Int = 10
  val icon = "resources/attackers/zombie.png"
  var addX = cell.x
  var addY = cell.y
  def display() {
    sketch.fill(102, 255, 102)
    sketch.rect(cell.x, cell.y, 50, 50)
  }

  def fire(fire: Fire) {
    sketch.fill(0, 0, 0)
    sketch.ellipse(fire.x, fire.y, 30, 30)

  }

  def attack(attacker: Attackers) = {
    //    println(cell.distance(attacker.cell) + " distance")
    //    val proj = new Fire(this, target.get)
    //    proj.findDirection(proj.from, proj.to)
    //    fire(proj)
    attacker.takingDamage(this)
    println(attacker.healthPoints)
    println("inside")

  }

}