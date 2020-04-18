package tower

import gamemaps._
import attackers._
import tower._
import scala.collection.mutable.Buffer
import java.util.Calendar

// Add cells to Map here

object GameInstance {
  val maps = FileReader.getMaps()
  var map = maps(0)

  var cellToMap = new FileToMap(map)
  val player = Player
  val attackers: Buffer[Attackers] = Buffer[Attackers]()
  val messages: Buffer[String] = Buffer[String]()

  var towers: Buffer[Tower] = Buffer[Tower]()
  var waveNr = 1
  var spawn = cellToMap.generateCell
  var gameOver: Boolean = false
  var fires = Buffer[Fire]()
  var what: Option[Tower] = None
  val timeBetweenSpawn: Int = 5000
  var lastTime: Int = 0

  def enemyAmount = (waveNr * 1.5).toInt

  def currentUpgradeCost: String = if (what.isDefined) (what.get.levelsMapped(what.get.level)).toString else ""

  def deleteMessage() = {
    if (messages.length > 12) {
      messages.remove(0)
    }
  }

  def updateMap(map: Array[Array[Char]]): Unit = {
    cellToMap = new FileToMap(map)
  }

  def addTower(tower: Tower) {
    // Check if enough money
    var towerCell = cellToMap.getCell(tower.cell).isInstanceOf[TowerCell]
    if (towerCell && player.money - tower.price >= 0) {
      // Remove money
      player.removeMoney(tower)
      tower.init()
      towers += tower
      messages += "Tower added"
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
    //    for (i <- 0 until (waveNr * 1.5).toInt) {
    //      if (getTime() - lastTime > timeBetweenSpawn) {
    //        println(getTime() + " GEET " + lastTime + " lastttimee " + (getTime() - lastTime) + " difference ")
    //        println()
    waveAttack()

    //        lastTime = getTime()
    //      }
    //    }
  }

  def waveAttack() = {
    var enemyNr = 0

    for (i <- 0 until (waveNr * 1.5).toInt) {
      attackers += new BasicAttacker(spawn)
      Thread.sleep(timeBetweenSpawn)
      enemyNr += 1
    }
    if (waveNr % 3 == 0 || waveNr % 5 == 0) attackers += new AdvancedAttacker(spawn)
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
      if (what.get.level < 2 && difference >= 0) {
        what.get.attackDamage = (what.get.attackDamage * 2).toInt
        what.get.range = (what.get.range * 1.5).toInt
        player.money -= what.get.levelsMapped(what.get.level)
        what.get.levelUp()

        messages += "Damage upgrade " + what.get.attackDamage
        if (what.get.level == 2) messages += "Fully upgraded"
      } else if (what.get.level == 2) {
        messages += "Fully upgraded"
      } else {
        messages += "Not enough money"
      }
    } else {
      messages += "No tower selected"
    }
    what = None
  }

}