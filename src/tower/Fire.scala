package tower
import gamemaps._
import attackers._

class Fire(val from: Tower, var to: Attackers) {

  var x: Int = from.cell.x
  var y: Int = from.cell.y

}