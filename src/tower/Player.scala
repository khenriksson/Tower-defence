package tower

object Player {
  var healthPoints = 100
  var money = 40
  var score = 0

  def isAlive = {
    (Player.healthPoints > 0)
  }

}