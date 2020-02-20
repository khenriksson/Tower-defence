package tower

import gamemaps._
import attackers._
import tower._
import scala.collection.mutable.Buffer

class GameInstance {
  val player = Player
  val attackers: Buffer[Attackers] = Buffer[Attackers]()
  var towers: Buffer[Tower] = Buffer[Tower]()

  def addTower(to: Cell, tower: Tower) {
    // Remove money
    player.removeMoney(tower)
    towers += tower
  }

  def removeTower(from: Cell) {
    // How can I get which tower is there?
    towers = towers.filter(p => p.location != from.location)
  }
}