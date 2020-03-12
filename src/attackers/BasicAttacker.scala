package attackers

import tower._
import processing.core.PApplet
import gamemaps._

class BasicAttacker(c: Cell, sketch: PApplet) extends Attackers(c, sketch) {
  //  this.sketch = sketch
  val speed = 10

  var reward = 20
  val winning = 20
  var healthPoints = 100
  val attackDamage = 10
  var course = 0

  var x: Int = cell.x // Starting point
  var y: Int = cell.y

  val icon = "resources/attackers/zombie.png"

  def move(map: FileToMap) = {
    //    x = x + speed;
    //    //    println("This is x in basic: " + x)
    //    if (x > mWidth) {
    //      x = 0;
    //
    //    }
    //    display()

    if (map.getCell(cell.directionCheck(course)) == Route ||
      map.getCell(cell.directionCheck(course)) == GenerateCell) {
      cell = cell.directionCheck(course)
    } else if (map.getCell(cell.directionCheck((course + 1) % 4)) == Route ||
      map.getCell(cell.directionCheck((course + 1) % 4)) == GenerateCell) {
      course = (course + 1) % 4
      cell = cell.directionCheck(course)
    } else if (map.getCell(cell.directionCheck((4 + course - 1) % 4)) == Route ||
      map.getCell(cell.directionCheck((4 + course - 1) % 4)) == GenerateCell) {
      course = (4 + course - 1) % 4
      cell = cell.directionCheck(course)
    }
    true
  }

  def display() {
    sketch.fill(150, 100, 150)
    sketch.rect(x, y, 50, 50);
    //    drawAttacker(this, x, y)
  }

  //  def drawAttacker(attacker: Attackers, x: Int, y: Int): Unit = {
  //    sketch.fill(13, 255, 0)
  //    sketch.rect(x, y, 50, 50)
  //  }

}

// CreditsStephen Challener (Redshrike), hosted by OpenGameArt.org
//Link my profile (if reasonable), credit Redshrike as graphic artist, and credit me as contributor (I did pay to have it made after all...)
//File(s):

// Irina Mir (irmirx) Zombies