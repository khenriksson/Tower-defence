package tower

import processing.core.{ PApplet, PConstants }
import attackers._

class Game extends PApplet {
  val wWidth: Int = 900
  val wHeight: Int = 750
  val map = FileReader.parse("resources/gamemaps/first.txt")

  var selectedCell: Boolean = false

  // Sidemenu
  val menuWidth = 200

  // Top menu
  val topY = 0
  val topX = 0
  val topWidth = wWidth
  val topHeight = 100

  // Dynamic to each other

  val menuHeight = wHeight - topHeight // topMenu

  // Towermenu, containing all the towers visible
  val menuY = wHeight + topHeight // Sidemenu

  // A width for the boxes in the top
  val boxWidth = (wWidth - menuWidth) / 3

  def mapHeight: Int = map.length
  def mapWidth: Int = map(0).length

  var startPoint = new Cell(0, 500)
  val base = new BasicAttacker(startPoint, this)

  override def setup() = {
    background(255, 255, 50)

  }

  override def settings() = {
    size(wWidth, wHeight)

  }

  override def draw() = {

    textSize(30)

    menuBox(topX, topY, boxWidth, topHeight, "Money")
    menuBox(boxWidth, topY, boxWidth, topHeight, "Kill")
    menuBox(boxWidth * 2, topY, boxWidth, topHeight, "Wave")
    menuBox(boxWidth * 3, topY, boxWidth, (0.4 * wHeight).toInt, "Towers")
    menuBox(boxWidth * 3, (0.4 * wHeight).toInt, boxWidth, (0.5 * wHeight).toInt, "Upgrades")
    menuBox(boxWidth * 3, (0.9 * wHeight).toInt, boxWidth, (0.1 * wHeight).toInt, "Quit")

    for (x <- 0 until 14) {
      for (y <- 0 until 13) {
        if (map(x)(y) == '-') fill(255, 255, 255)
        else if (map(x)(y) == '0') fill(200, 250, 100)
        else if (map(x)(y) == '1') fill(0, 0, 0)
        else if (map(x)(y) == '2') fill(100, 100, 100)
        else fill(255, 0, 0)

        new Cell(x, y)
        rect(0 + x * 50, 100 + y * 50, 50, 50) // 1 cell size
      }
    }

    //    drawAttacker(base, 710, 330)
    base.move()
    //    base.display();

  }

  //  private def moneyMenu() = {
  //    fill(255, 255, 255)
  //    rect(topX, topY, boxWidth, topHeight)
  //    fill(200, 200, 200)
  //    text("Money", topX + 2, topY + 30)
  //  }

  private def menuBox(x: Int, y: Int, w: Int, h: Int, t: String) = {
    fill(255, 255, 255)
    rect(x, y, w, h)
    fill(235, 52, 52)
    text(t, x + 2, y + 30)
  }

  override def mouseClicked() {
    if (mouseX > 0 && mouseX < 200 && mouseY < 100 && selectedCell == false) {
      selectedCell = true
      if (selectedCell) {
        fill(100, 100, 100)
        rect(0, 0, boxWidth, 100);
      }
    } else if (mouseX > 700 && mouseX < 900 && mouseY > 650 && selectedCell == false) {
      selectedCell = true
      if (selectedCell) {
        fill(100, 100, 100)
        rect(boxWidth * 3, (0.9 * wHeight).toInt, boxWidth, (0.1 * wHeight).toInt);
        exit()
      }
    } else {
      selectedCell = false
    }
  }

  //  override def mouseClicked(xLeft: Int, xRight: Int, yHigh: Int) {
  //    if (mouseX > xLeft && mouseX < xRight && mouseY < yHigh && selectedCell == false) {
  //      selectedCell = true
  //      if (selectedCell) {
  //        fill(100, 100, 100)
  //        rect(0, 0, boxWidth, 100);
  //      }
  //      //    } else if (mouseX > 700 && mouseX < 900 && mouseY > 800 && selectedCell == false) {
  //      //      if (selectedCell) {
  //      //        fill(100, 100, 100)
  //      //        rect(0, 0, 900, 120);
  //      //        exit()
  //      //      }
  //
  //    } else {
  //      selectedCell = false
  //    }
  //  }

}

object Game extends App {

  PApplet.main("tower.Game")

}

