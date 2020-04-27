package attackers

import gamemaps.Cell

class AdvancedAttacker(c: Cell) extends Attackers(c) {
  val name = "advanced"
  val winning = 20
  val attackDamage = 20

  var reward = 20
  var healthPoints = 150
  var course = 0

}