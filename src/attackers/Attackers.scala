package attackers

import tower._
import processing.core.PApplet

// Abstract class for the attackers to keep track of everything
abstract class Attackers(var cell: Cell, sketch: PApplet) {
  val speed: Int
  val winning: Int
  var healthPoints: Int
  val icon: String
  //  val screenWidth: Int

  def towerDefense(damage: Int) = {
    healthPoints -= damage
  }

  def move()

}