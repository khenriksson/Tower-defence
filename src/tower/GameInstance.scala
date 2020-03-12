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

  def addTower(to: Cell, tower: Tower) {
    println("To X  " + to.x + "   To Y  " + to.y)
    // Check if enough money
    var towerCell = cellToMap.getCell(to).isInstanceOf[TowerCell]
    println(towerCell.toString)

    if (towerCell && player.money - tower.price >= 0) {
      // Remove money
      player.removeMoney(tower)
      towers += tower

    }
  }

  def removeTower(from: Cell) {
    // How can I get which tower is there?

    // TODO: Add possibility to get money
    towers = towers.filter(p => p.location != from.location)
  }
}