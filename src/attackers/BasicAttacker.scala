package attackers

import tower._
import gamemaps._
import processing.core.PApplet
import gamemaps._
import scala.collection.mutable.Buffer

class BasicAttacker(c: Cell) extends Attackers(c) {
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

  def move(map: FileToMap): Boolean = {
    if (healthPoints < 0) return false
    if (map.cellType(cell.directionCheck(course)) == Route) {
      cell = cell.directionCheck(course)
    } else if (map.cellType(cell.directionCheck((course + 1) % 4)) == Route) {
      course = (course + 1) % 4
      cell = cell.directionCheck(course)
    } else if (map.cellType(cell.directionCheck(4 + (course + 1) % 4)) == Route) {
      course = 4 + (course + 1) % 4
      cell = cell.directionCheck(course)
    }
    true

  }
}

// CreditsStephen Challener (Redshrike), hosted by OpenGameArt.org
//Link my profile (if reasonable), credit Redshrike as graphic artist, and credit me as contributor (I did pay to have it made after all...)
//File(s):

// Irina Mir (irmirx) Zombies