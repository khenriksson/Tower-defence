package attackers

import tower._

// Abstract class for the attackers to keep track of everything
abstract class Attackers(var cell: Cell) {
  val speed: Int
  val winning: Int
  var healthPoints: Int
  val icon: String

  def towerDefense(damage: Int) = {
    healthPoints -= damage
  }

  def move()
}