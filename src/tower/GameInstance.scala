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
  var gßmeOver: Boolean = false
  var fires = Buffer[Fire]()

  def deleteMessage() = {
    if (messages.length > 13) {
      //      println(messages.length)
      messages.remove(0)
    }
  }

  def addTower(tower: Tower) {
    //    println("To X  " + to.x + "   To Y  " + to.y)
    // Check if enough money
    var towerCell = cellToMap.getCell(tower.cell).isInstanceOf[TowerCell]
    if (towerCell && player.money - tower.price >= 0) {
      // Remove money
      player.removeMoney(tower)
      towers += tower
      //      println(tower.name)
    } else {
      messages += "Not enough money"
    }
  }

  def removeTower(from: Cell) {
    val whichTower = towers.filter(p => p.location == from.location)
    player.money += (whichTower.last.price * 0.8).toInt
    towers = towers.filter(p => p.location != from.location)
  }

  def addAttacker() = {
    for (i <- 0 until (waveNr * 1.5).toInt) {
      attackers += new BasicAttacker(spawn)
    }
    waveNr += 1
  }

  def removeAttacker(attacker: Attackers) = {
    attackers -= attacker
    //    player.removeHealth(attacker)
  }

  //  def removeFire(fire: Fire) = {
  //    if (fire.time > fire.time + 4) {
  //      fires -= fire
  //    }
  //  }

}