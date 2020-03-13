package attackers

import tower._
import processing.core.PApplet
import gamemaps._

class BasicAttacker(c: Cell) extends Attackers(c) {
  //  this.sketch = sketch
  val speed = 10

  var reward = 20
  val winning = 20
  var healthPoints = 100
  val attackDamage = 10
  var course = 0

  var x: Int = cell.x // Starting point
  var y: Int = cell.y

  val icon = "resources/attackers/zombie.png"

  def neighborCells(cell: Int) = {
    ???
  }

  def move(map: FileToMap) = {
    //    x = x + speed;
    //    //    println("This is x in basic: " + x)
    //    if (x > mWidth) {
    //      x = 0;
    //
    //    }
    //    display()
    ???
  }

}

// CreditsStephen Challener (Redshrike), hosted by OpenGameArt.org
//Link my profile (if reasonable), credit Redshrike as graphic artist, and credit me as contributor (I did pay to have it made after all...)
//File(s):

// Irina Mir (irmirx) Zombies