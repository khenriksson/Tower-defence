package tower
import gamemaps._
import attackers._

class Fire(val from: Tower, var to: Attackers, val time: Int) {

  var x: Int = from.x
  var y: Int = from.y

  //  def findDirection(from: Tower, to: Attackers): (Int, Int) = {
  //    val tX = from.cell.x
  //    val tY = from.cell.y
  //    val aX = to.cell.x
  //    val aY = to.cell.y
  //    if (from.cell.distance(to.cell) < from.range) {
  //      if (tX > aX && tY > aY) {
  //        //        return (-from.attackSpeed, -from.attackSpeed)
  //        x -= from.attackSpeed
  //        y -= from.attackSpeed
  //      } else if (tX < aX && tY > aY) {
  //        return (from.attackSpeed, -from.attackSpeed)
  //      } else if (tX > aX && tY < aY) {
  //        return (-from.attackSpeed, from.attackSpeed)
  //      } else if (tX < aX && tY < aY) {
  //        return (from.attackSpeed, from.attackSpeed)
  //      }
  //    }
  //    (0, 0)
  //  }

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