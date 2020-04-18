package attackers

import tower._
import processing.core.PApplet
import gamemaps._
import scala.collection.mutable.Buffer

class AdvancedAttacker(c: Cell) extends Attackers(c) {
  val name = "advanced"
  val speed = 5
  val winning = 20
  val attackDamage = 20

  var reward = 20
  var healthPoints = 120
  var course = 0
  var moving: Boolean = true

}

// CreditsStephen Challener (Redshrike), hosted by OpenGameArt.org
//Link my profile (if reasonable), credit Redshrike as graphic artist, and credit me as contributor (I did pay to have it made after all...)
//File(s):

// Irina Mir (irmirx) Zombies