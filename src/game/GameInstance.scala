package game

import gamemaps._
import attackers._
import tower._
import scala.collection.mutable.Buffer

// Add cells to Map here

object GameInstance {
  val maps = FileReader.getMaps()
  val player = Player
  val attackers: Buffer[Attackers] = Buffer[Attackers]()
  val messages: Buffer[String] = Buffer[String]()

  var map = maps(0)
  var cellToMap = new FileToMap(map)
  var towers: Buffer[Tower] = Buffer[Tower]()
  var waveNr = 1
  var spawn = cellToMap.generateCell
  var gameOver: Boolean = false
  var towerType: Option[Tower] = None // Used for selecting tower on board

  /**
   * @return amount of enemies per wave
   */
  def enemyAmount = (waveNr * 1.5).toInt

  /**
   * Calculating the cost of upgrading tower
   * @return string for drawing upgrade cost
   */
  def currentUpgradeCost: String = if (towerType.isDefined) (towerType.get.levelsMapped(towerType.get.level)).toString else ""

  /**
   * Deletes messages from the sidebar in the game
   * @return removes messages from array
   */
  def deleteMessage() = {
    if (messages.length > 12) {
      messages.remove(0)
    }
  }

  /**
   * Used when choosing map in the beginning of the game,
   * updates the variable afterwards
   * @param map Array of Arrays read in FileReader earlier
   */
  def updateMap(map: Array[Array[Char]]): Unit = {
    cellToMap = new FileToMap(map)
  }

  /**
   * Adding a tower to the array, that is then drawn in Game
   * @param tower the type of tower to be added
   */
  def addTower(tower: Tower) {
    // Check if enough money
    var towerCell = cellToMap.getCell(tower.cell).isInstanceOf[TowerCell]
    if (towerCell) {
      if (player.money - tower.price >= 0) {
        // Remove money
        player.removeMoney(tower)
        towers += tower
        messages += "Tower added"
      } else {
        messages += "Not enough money"
      }
    } else {
      messages += "Not possible"
    }
  }

  /**
   * Removing a tower if it's sold
   */
  def removeTower() {
    if (towerType.isDefined) {
      player.money += (towerType.get.price * 0.8).toInt
      towers = towers.filter(p => p.location != towerType.get.location)
    }
  }

  /**
   * When selecting a tower on the map, this function finds the tower from the array of towers
   * @param from Cell that is selected on the map
   */
  def selectTower(from: Cell): Unit = {
    val whichTower = towers.filter(p => p.location == from.location)
    if (whichTower.isDefinedAt(0)) {
      towerType = Some(whichTower.last)
      messages += towerType.get.location + " selected"
    } else None
  }

  /**
   * Used for adding the attackers when the wave is spawned
   */
  def addAttacker() = {
    var enemyNr = 0
    for (i <- 0 until (waveNr * 1.5).toInt) {
      attackers += new BasicAttacker(spawn)
      enemyNr += 1
    }
    // Adds a stronger attacker every third and fifth wave
    if (waveNr % 3 == 0 || waveNr % 5 == 0) attackers += new AdvancedAttacker(spawn)
    waveNr += 1
  }

  /**
   * Removes attacker from array
   * @param attacker to be removed
   * @return unit
   */
  def removeAttacker(attacker: Attackers) = {
    attackers -= attacker
  }

  /**
   * Upgrades a tower level and damage
   */
  def upgrade() {
    if (towerType.isDefined) {
      // Getting price of upgrade
      val price = towerType.get.levelsMapped(towerType.get.level)
      // Calculating money left
      val difference = player.money - price
      // If not fully upgraded and has enough money, improve damage and remove money
      if (towerType.get.level < 2 && difference >= 0) {
        towerType.get.attackDamage = (towerType.get.attackDamage * 2).toInt
        player.money -= towerType.get.levelsMapped(towerType.get.level)
        towerType.get.levelUp()
        messages += "Damage upgrade " + towerType.get.attackDamage
        if (towerType.get.level == 2) messages += "Fully upgraded"
      } else if (towerType.get.level == 2) {
        messages += "Fully upgraded"
      } else {
        messages += "Not enough money"
      }
    } else {
      messages += "No tower selected"
    }
    towerType = None
  }

}