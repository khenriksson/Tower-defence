package tower

import processing.core.{ PApplet, PImage }
import gamemaps._
import attackers._
import scala.collection.mutable.Buffer
import java.io.IOException

class BasicTower(c: Cell, sketch: PApplet) extends Tower(c, sketch) {
  val name = "basic"
  val dir = "resources/towers/"
  val price: Int = 10
  val healthPoints: Int = 100
  var icon: Map[Int, PImage] = Map()

  val attackSpeed: Int = 1
  val levelsMapped = Map(0 -> (price * 1.3).toInt, 1 -> (price * 1.5).toInt, 2 -> (price * 1.7).toInt)

  var attackDamage: Int = 10
  var range: Int = 300
  var level: Int = 0
  var addX = cell.x
  var addY = cell.y

  def init() = {
    try {
      icon += (0 -> sketch.loadImage(dir + "basic0.png"),
        1 -> sketch.loadImage(dir + "basic1.png"),
        2 -> sketch.loadImage(dir + "basic2.png"),
        3 -> sketch.loadImage(dir + "fire.png"))
      for (i <- icon) {
        i._2.resize(50, 50)
      }
    } catch {
      case e: IOException => {
        sketch.fill(0, 0, 0)
        sketch.rect(0, 0, wWidth, wHeight)
      }
    }
  }

  def display() {
    sketch.image(icon(level), cell.x, cell.y)
  }

  def fire(fire: Fire) {
    sketch.image(icon(3), fire.x, fire.y)
  }

  def attack(attacker: Attackers) = {
    val fire = new Fire(this, this.target.get)
    //    if (target.get.cell.distance(this.cell) < range) {
    this.fire(fire)
    attacker.takingDamage(this)
    //    }
  }

}