package tower

import processing.core.{ PApplet, PConstants }
import attackers._
import gamemaps._

import scala.collection.mutable.Buffer

class Game extends Helper {

  val gameIns = new GameInstance
  val player = gameIns.player
  val sketch = this

  var selectedCell: Boolean = false
  var selected = new Cell(0, 0)

  def mapWidth: Int = gameIns.map.length
  def mapHeight: Int = gameIns.map(0).length

  var startPoint = new Cell(0, 500)
  var towerPoint = new Cell(50, 200)
  val testTower = new BasicTower(towerPoint, this)
  val base = new BasicAttacker(startPoint, this)
  gameIns.attackers += base

  override def setup() = {
    textSize(30)
    menuBox(topX, topY, boxWidth, topHeight, "Money")
    menuBox(boxWidth, topY, boxWidth, topHeight, "Health")
    menuBox(boxWidth * 2, topY, boxWidth, topHeight, "Wave")
    menuBox(boxWidth * 3, topY, boxWidth, (0.4 * wHeight).toInt, "Towers")
    menuBox(boxWidth * 3, (0.4 * wHeight).toInt, boxWidth, (0.5 * wHeight).toInt, "Upgrades")
    menuBox(boxWidth * 3, (0.9 * wHeight).toInt, boxWidth, (0.1 * wHeight).toInt, "Quit")
    text(player.money.toString, topX + 2, topY + 30 + 60)
    text(player.healthPoints.toString, boxWidth + 2, topY + 30 + 60)
  }

  override def settings() = {
    size(wWidth, wHeight)

  }

  override def draw() = {

    //    for (x <- 0 until 14) {
    //      for (y <- 0 until 13) {
    //        if (map(x)(y) == '-') fill(255, 255, 255)
    //        else if (map(x)(y) == '0') fill(200, 250, 100)
    //        else if (map(x)(y) == '1') fill(0, 0, 0)
    //        else fill(255, 0, 0)
    //
    //        new Cell(x, y)
    //        rect(0 + x * 50, 100 + y * 50, 50, 50) // 1 cell size
    //      }
    //    }

    //    if (gameIns.towers.isEmpty) {
    //      drawBoard()
    //    }
    //    gameIns.attackers.foreach { x =>
    //
    //      if (x.move(gameIns.cellToMap)) {
    //        x.display()
    //      }
    //    }

    drawBoard()

    for (tower <- 0 until gameIns.towers.length) {
      val t = gameIns.towers(tower)
      t.display()
    }

  }

  private def menuBox(x: Int, y: Int, w: Int, h: Int, t: String) = {
    fill(255, 255, 255)
    rect(x, y, w, h)
    fill(235, 52, 52)
    text(t, x + 2, y + 30)
  }

  private def gameOver = {
    if (!player.isAlive) {
      fill(250, 250, 250)
      rect(0, 0, 900, 750)
    }
  }

  override def mouseClicked() {
    val mX = mouseX
    val mY = mouseY

    selected = new Cell(chooseRight(mouseX), chooseRight(mouseY))
    //    println("chooseRight X   " + chooseRight(mouseX) + "   chooseRight Y   " + chooseRight(mouseY))
    val what = new BasicTower(selected, this)
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
    } else if (mouseX > 700 && mouseX < 900 && mouseY > 100 && mouseY < (0.4 * wHeight).toInt) {
      // Selecting a tower
      selectedCell = true
      if (selectedCell) {
        fill(100, 100, 100)
        rect(boxWidth * 3 + 5, topY + 100, 50, 50)
        selectedCell = false
      }
    } else if (mouseX > 1 && mouseX < 700 && mouseY > 100 && mouseY < 750) {
      // This function checks whether there's a tower in the location already
      // If there is, then it deletes that tower
      selectedCell = true
      if (!gameIns.towers.exists(f => f.location == selected.location)) {
        //        val what = new BasicTower(selected, this)
        if (selectedCell) {
          gameIns.addTower(selected, what)
          selectedCell = false
        }
        //        drawMenu()
        //        drawBoard()

      } else {
        println("In the other one")
        selectedCell = true
        // Selecting a tower
        if (selectedCell) {
          println("Before " + gameIns.towers)
          gameIns.removeTower(selected)
          println("After " + gameIns.towers)
          selected = new Cell(0, 0)
          selectedCell = false
        }
        //        drawMenu()
        //        drawBoard()
      }
    } else {
      selectedCell = false
    }
  }

  def chooseRight(which: Int) = {
    var stepper = 0
    var chosen = 0
    while (stepper * 50 <= which) {
      chosen = stepper * 50
      stepper += 1
    }
    chosen
  }

  private def drawCell(cell: Cell): Unit = {
    gameIns.cellToMap.cellType(cell) match {
      case GenerateCell =>
        fill(200, 255, 100)
        rect(0 + cell.x * 50, 100 + cell.y * 50, 50, 50)
      case Route =>
        fill(0, 0, 0)
        rect(0 + cell.x * 50, 100 + cell.y * 50, 50, 50)
      case Target =>
        fill(255, 0, 0)
        rect(0 + cell.x * 50, 100 + cell.y * 50, 50, 50)
      case _ =>
        fill(255, 255, 255)
        rect(0 + cell.x * 50, 100 + cell.y * 50, 50, 50)
    }
  }

  private def drawMenu(): Unit = {
    menuBox(topX, topY, boxWidth, topHeight, "Money")
    menuBox(boxWidth, topY, boxWidth, topHeight, "Health")
    menuBox(boxWidth * 2, topY, boxWidth, topHeight, "Wave")
    fill(13, 255, 0)
    rect(boxWidth * 3 + 5, topY + 100, 50, 50)
    fill(235, 52, 52)
    text(player.money.toString, topX + 2, topY + 30 + 60)
    text(player.healthPoints.toString, boxWidth + 2, topY + 30 + 60)
  }

  private def drawBoard() = {
    drawMenu()
    for (x <- 0 until mapWidth) {
      for (y <- 0 until mapHeight) {
        drawCell(new Cell(x, y))
      }
    }
  }

}

object Game extends App {

  PApplet.main("tower.Game")

}

