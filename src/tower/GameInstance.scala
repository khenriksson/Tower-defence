package tower

import gamemaps._
import attackers._
import tower._
import scala.collection.mutable.Buffer

// Add cells to Map here

class GameInstance {
  val map = FileReader.parse("resources/gamemaps/first.txt")
  val cellToMap = new FileToMap(map)
  val player = Player
  val attackers: Buffer[Attackers] = Buffer[Attackers]()
  val messages: Buffer[String] = Buffer[String]()
  var towers: Buffer[Tower] = Buffer[Tower]()
  var waveNr = 1
  var spawn = cellToMap.generateCell
  var gameOver: Boolean = false
  var fires = Buffer[Fire]()
  var what: Option[Tower] = None

  def currentUpgradeCost: String = if (what.isDefined) (what.get.levelsMapped(what.get.level)).toString else ""

  def deleteMessage() = {
    if (messages.length > 12) {
      messages.remove(0)
    }
  }

  def addTower(tower: Tower) {
    // Check if enough money
    var towerCell = cellToMap.getCell(tower.cell).isInstanceOf[TowerCell]
    if (towerCell && player.money - tower.price >= 0) {
      // Remove money
      player.removeMoney(tower)
      towers += tower
    } else {
      messages += "Not enough money"
    }
  }

  def removeTower() {
    if (what.isDefined) {
      player.money += (what.get.price * 0.8).toInt
      towers = towers.filter(p => p.location != what.get.location)
    }
  }

  def selectTower(from: Cell): Unit = {
    val whichTower = towers.filter(p => p.location == from.location)
    if (whichTower.isDefinedAt(0)) {
      what = Some(whichTower.last)
      messages += what.get.location + " selected"
    } else None
  }

  def addAttacker() = {
    for (i <- 0 until (waveNr * 1.5).toInt) {
      attackers += new BasicAttacker(spawn)
    }
    waveNr += 1
  }

  def removeAttacker(attacker: Attackers) = {
    attackers -= attacker
  }

  def check = what.get.levelsMapped(what.get.level)

  def upgrade() { //
    if (what.isDefined) {
      val price = what.get.levelsMapped(what.get.level)
      val difference = player.money - price
      if (what.get.level < 3 && difference >= 0) {
        what.get.attackDamage = (what.get.attackDamage * 2).toInt
        what.get.range = (what.get.range * 1.5).toInt
        player.money -= what.get.levelsMapped(what.get.level)
        what.get.levelUp()

        messages += "Damage upgrade " + what.get.attackDamage
      } else {
        messages += "Not enough money"
      }
    } else {
      messages += "No tower selected"
    }
    what = None
  }

  //  def removeFire(fire: Fire) = {
  //    if (fire.time > fire.time + 4) {
  //      fires -= fire
  //    }
  //  }

}