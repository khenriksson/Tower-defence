package attackers

import tower._
import processing.core.PApplet

class BasicAttacker(cell: Cell) extends Attackers(cell) {
  val speed = 15
  var reward = 20
  val winning = 20

  var healthPoints = 100

  var x = cell.x
  var y = cell.y

  val icon = "resources/attackers/zombie.png"

  def move() = {
    x += speed;
    println("This is x in basic: " + x)
    if (x > Game.width) {
      x = 0;
    }

  }

  def display() {
    Game.fill(150, 100, 150)
    Game.rect(x, y, 50, 50);
  }

}

// CreditsStephen Challener (Redshrike), hosted by OpenGameArt.org
//Link my profile (if reasonable), credit Redshrike as graphic artist, and credit me as contributor (I did pay to have it made after all...)
//File(s):

// Irina Mir (irmirx) Zombies