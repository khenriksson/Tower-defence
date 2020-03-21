package tower
import gamemaps._
import attackers._

class Fire(val from: Tower, var to: Attackers) {

  var x: Int = from.cell.x
  var y: Int = from.cell.y

  def findDirection(from: Tower, to: Attackers): Unit = {
    val tX = from.cell.x
    val tY = from.cell.y
    val aX = to.cell.x
    val aY = to.cell.y
    if (from.cell.distance(to.cell) < from.range) {
      if (tX > aX && tY > aY) {
        x -= from.attackSpeed
        y -= from.attackSpeed
      } else if (tX < aX && tY > aY) {
        x += from.attackSpeed
        y -= from.attackSpeed
      } else if (tX > aX && tY < aY) {
        x -= from.attackSpeed
        y += from.attackSpeed
      } else if (tX < aX && tY < aY) {
        x += from.attackSpeed
        y += from.attackSpeed
      }
    }
  }
}