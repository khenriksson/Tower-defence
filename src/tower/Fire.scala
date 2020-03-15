package tower
import gamemaps._
import attackers._

class Fire(from: Tower, val to: Attackers) {

  var x: Int = from.x
  var y: Int = from.y

  def findDirection(from: Tower, to: Attackers): (Int, Int) = {
    val tX = from.cell.x
    val tY = from.cell.y
    val aX = to.cell.x
    val aY = to.cell.y
    if (tX > aX && tY > aY) {
      return (-from.attackSpeed, -from.attackSpeed)
    } else if (tX < aX && tY > aY) {
      return (from.attackSpeed, -from.attackSpeed)
    } else if (tX > aX && tY < aY) {
      return (-from.attackSpeed, from.attackSpeed)
    } else if (tX < aX && tY < aY) {
      return (from.attackSpeed, from.attackSpeed)
    }
    (0, 0)
  }
}