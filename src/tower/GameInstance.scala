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
  var towers: Buffer[Tower] = Buffer[Tower]()
  var waveNr = 1
  //  var spawn = cellToMap.generateCell

  def getCell(attacker: Attackers) = {
    cellToMap.getCell(attacker.cell)
  }

  def neighborCell(attacker: Attackers) = {
    println("first " + cellToMap.cells(attacker.x / 50 + 1)(attacker.y / 50))
    //    println("second " + cellToMap.cells(attacker.x / 50)(attacker.y / 50 + 1) == Route)
    //    println("third " + cellToMap.cells(attacker.x / 50)(attacker.y / 50 - 1) == Route)
    if (cellToMap.cells(attacker.x / 50 + 1)(attacker.y / 50) == Route) {
      attacker.cell = new Cell(attacker.x / 50 + 1, attacker.y / 50)
    } else if (cellToMap.cells(attacker.x / 50)(attacker.y / 50 + 1) == Route) {
      attacker.cell = new Cell(attacker.x / 50, attacker.y / 50 + 1)
    } else if (cellToMap.cells(attacker.x / 50)(attacker.y / 50 - 1) == Route) {
      attacker.cell = new Cell(attacker.x / 50, attacker.y / 50 - 1)
    }
    //    attacker.cell
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
    // How can I get which tower is there?
    val whichTower = towers.filter(p => p.location == from.location)
    player.money += (whichTower.last.price * 0.8).toInt
    towers = towers.filter(p => p.location != from.location)
  }

  //  def incomingWave() = {
  //    var step = 0
  //    while (step <= 0) {
  //      attackers += new BasicAttacker(spawn, this)
  //    }
  //    waveNr += 1
  //  }

  def addAttacker(to: Cell, tower: Attackers) {
    val spawn = cellToMap.generateCell
    println(spawn.y + "       spawn y")
    for (i <- 0 until waveNr) {
      attackers += new BasicAttacker(spawn)
    }
  }
}