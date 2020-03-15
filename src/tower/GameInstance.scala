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
  var spawn = new Cell(cellToMap.generateCell._1, cellToMap.generateCell._2)
  var gameOver: Boolean = false

  def getCell(attacker: Attackers) = {
    cellToMap.getCell(attacker.cell)
  }

  def deleteMessage() = {
    if (messages.length > 15) {
      //      println(messages.toArray.deep.mkString("\n"))
      println(messages.length)
      messages.remove(0)
    }
  }

  def addTower(to: Cell, tower: Tower) {
    println("To X  " + to.x + "   To Y  " + to.y)
    // Check if enough money
    var towerCell = cellToMap.getCell(to).isInstanceOf[TowerCell]
    if (towerCell && player.money - tower.price >= 0) {
      // Remove money
      player.removeMoney(tower)
      towers += tower
    } else {
      println("Not available")
    }
  }

  def removeTower(from: Cell) {
    val whichTower = towers.filter(p => p.location == from.location)
    player.money += (whichTower.last.price * 0.8).toInt
    towers = towers.filter(p => p.location != from.location)
  }

  def addAttacker() = {
    for (i <- 0 until waveNr) {
      attackers += new BasicAttacker(spawn)
    }
    waveNr += 1
  }

  def removeAttacker(attacker: Attackers) = {
    attackers -= attacker
    player.removeHeath(attacker)
  }

}