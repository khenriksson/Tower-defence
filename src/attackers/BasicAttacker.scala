package attackers

import tower._
import processing.core.PApplet

class BasicAttacker(cell: Cell, sketch: PApplet) extends Attackers(cell, sketch) {
  //  this.sketch = sketch
  val speed = 1

  var reward = 20
  val winning = 20
  var healthPoints = 100

  var x: Int = cell.x // Starting point
  var y: Int = cell.y

  val icon = "resources/attackers/zombie.png"

  def move() = {
    x = x + speed;
    println("This is x in basic: " + x)
    if (x > 700) {
      x = 0;
    }
    display()
  }

  def display() {
    sketch.fill(150, 100, 150)
    //    sketch.rect(x, y, 50, 50);
    drawAttacker(this, x, y)
  }

  private def drawAttacker(attacker: Attackers, x: Int, y: Int): Unit = {
    val dir: String = attacker.icon
    sketch.image(sketch.loadImage(dir), x, y)
  }

}

// CreditsStephen Challener (Redshrike), hosted by OpenGameArt.org
//Link my profile (if reasonable), credit Redshrike as graphic artist, and credit me as contributor (I did pay to have it made after all...)
//File(s):

// Irina Mir (irmirx) Zombies