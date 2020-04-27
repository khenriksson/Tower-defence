package game
import attackers._
import tower._

object Player {
  var healthPoints = 100
  var money = 40

  def isAlive = {
    (Player.healthPoints > 0)
  }

  def addMoney(attacker: Attackers) = {
    money += attacker.winning
  }

  def removeMoney(tower: Tower) = {
    money -= tower.price
  }

}