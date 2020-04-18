package attackers

import tower._
import gamemaps._
import processing.core.PApplet
import gamemaps._
import scala.collection.mutable.Buffer
import math.abs

class BasicAttacker(c: Cell) extends Attackers(c) {
  val name = "basic"
  val speed = 5
  val winning = 12
  val attackDamage = 10
  var course: Int = 0
  var reward = 20
  var healthPoints = 100
  var moving: Boolean = true

}

// CreditsStephen Challener (Redshrike), hosted by OpenGameArt.org
//Link my profile (if reasonable), credit Redshrike as graphic artist, and credit me as contributor (I did pay to have it made after all...)
//File(s):

// Irina Mir (irmirx) Zombies