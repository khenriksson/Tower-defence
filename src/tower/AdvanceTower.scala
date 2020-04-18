package tower

import processing.core.{ PApplet, PImage }
import gamemaps._
import attackers._
import scala.collection.mutable.Buffer
import java.io.IOException

class AdvanceTower(c: Cell, sketch: PApplet) extends Tower(c, sketch) {
  val name = "advance"
  val price: Int = 20
  val healthPoints: Int = 100
  val attackSpeed: Int = 10
  val levelsMapped = Map(0 -> (price * 1.3).toInt, 1 -> (price * 1.5).toInt, 2 -> (price * 1.7).toInt)
  val dir = "resources/towers/"
  var icon: Map[Int, PImage] = Map()

  // Modified variables
  var attackDamage: Int = 20
  var range: Int = 400
  var level: Int = 0

  def display() {
    sketch.image(icon(level), cell.x, cell.y)
  }
  def fire(fire: Fire) = sketch.image(icon(3), fire.x, fire.y)

  def init(): Unit = {
    try {
      icon += (0 -> sketch.loadImage(dir + "advance0.png"),
        1 -> sketch.loadImage(dir + "advance1.png"),
        2 -> sketch.loadImage(dir + "advance2.png"),
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

  def attack(attacker: Attackers) = {
    val fire = new Fire(this, this.target.get)
    if (target.get.cell.distance(this.cell) < range && target.isDefined) {
      this.fire(fire)
      attacker.takingDamage(this)
    }
  }

}