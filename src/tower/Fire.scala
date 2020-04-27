package tower
import gamemaps._
import attackers._

class Fire(val from: Tower, var to: Attackers) {

  def x: Int = from.cell.x
  def y: Int = from.cell.y

}