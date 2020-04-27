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
  val levelsMapped = Map(0 -> (price * 1.3).toInt, 1 -> (price * 1.5).toInt, 2 -> (price * 1.7).toInt)

  var icon: Map[Int, PImage] = Map()
  var attackDamage: Int = 10
  var level: Int = 0

  // Initializing and loading the images for the tower
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

  // Displaying the tower
  def display() {
    sketch.image(icon(level), cell.x, cell.y)
  }

  // Fire shown when shooting
  def fire(fire: Fire) {
    sketch.image(icon(3), fire.x, fire.y)
  }
  // Function for attacking enemies
  def attack() = {
    val fire = new Fire(this, this.target.get)
    this.fire(fire)
    target.get.takingDamage(this)
  }

}