package attackers

import gamemaps.Cell

class BasicAttacker(c: Cell) extends Attackers(c) {
  val name = "basic"
  val winning = 12
  val attackDamage = 10

  var course: Int = 0
  var reward = 20
  var healthPoints = 120

}

