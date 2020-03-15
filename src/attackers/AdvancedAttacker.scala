package attackers

import tower._
import processing.core.PApplet
import gamemaps._
import scala.collection.mutable.Buffer

class AdnvancedAttacker(c: Cell) extends Attackers(c) {
  val speed = 5
  val winning = 20
  val attackDamage = 10

  var reward = 20
  var healthPoints = 100
  var course = 0
  var moving: Boolean = true
  var foundAttacker = None

  var x: Int = cell.x // Starting point
  var y: Int = cell.y

  val icon = "resources/attackers/zombie.png"

  def neighborCells(cell: Int) = {
    ???
  }

  def move(): Boolean = {
    //    x = x + speed;
    //    //    println("This is x in basic: " + x)
    //    if (x > mWidth) {
    //      x = 0;
    //
    //    }
    //    display()
    //    if (moving) {
    //    while (x < wWidth) {
    //      x += speed
    //    }
    //      if (x == 750) moving = false
    //    }
    true
  }

  //  def isHit = {
  //
  //  }

}

// CreditsStephen Challener (Redshrike), hosted by OpenGameArt.org
//Link my profile (if reasonable), credit Redshrike as graphic artist, and credit me as contributor (I did pay to have it made after all...)
//File(s):

// Irina Mir (irmirx) Zombies