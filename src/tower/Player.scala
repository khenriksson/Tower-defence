package tower
import attackers._

object Player extends Helper {
  var healthPoints = 100
  var money = 40
  var score = 0

  def isAlive = {
    (Player.healthPoints > 0)
  }

  def addMoney(attacker: Attackers) = {
    money += attacker.winning
  }

  def removeMoney(tower: Tower) = {
    money -= tower.price
  }

  def removeHealth(attacker: Attackers) = {
    println(healthPoints + "   healthpoints")
    healthPoints -= attacker.attackDamage
  }

}