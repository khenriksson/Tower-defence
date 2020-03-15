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
  var wave: Boolean = false

  def mapWidth: Int = gameIns.map.length
  def mapHeight: Int = gameIns.map(0).length

  override def setup() = {
    frameRate(15)
    textSize(20)
    menuBox(topX, topY, boxWidth, topHeight, "Money")
    menuBox(boxWidth, topY, boxWidth, topHeight, "Health")
    menuBox(boxWidth * 2, topY, boxWidth, topHeight, "Wave")
    menuBox(boxWidth * 3, topY, boxWidth, (0.4 * wHeight).toInt, "Towers")
    menuBox(boxWidth * 3, (0.4 * wHeight).toInt, boxWidth, (0.5 * wHeight).toInt, "Messages")
    menuBox(boxWidth * 3, (0.9 * wHeight).toInt, boxWidth, (0.1 * wHeight).toInt, "Quit")
    text(player.money.toString, topX + 2, topY + 30 + 60)
    text(player.healthPoints.toString, boxWidth + 2, topY + 30 + 60)
    text(gameIns.waveNr.toString, boxWidth * 2 + 2, topY + 30 + 60)

  }

  override def settings() = {
    size(wWidth, wHeight)

  }

  def addWave() = {
    gameIns.addAttacker()
  }

  override def draw() = {
    drawBoard()
    drawMessages()
    try {
      if (wave) {
        if (!gameIns.attackers.isEmpty) {
          gameIns.attackers.foreach(f => {
            drawing(f)
            if (f.x < mWidth) f.x += f.speed else gameIns.removeAttacker(f)
          })
        }
      } else if (gameIns.gameOver) {
        println("Game Over")
      }
      for (tower <- 0 until gameIns.towers.length) {
        val t = gameIns.towers(tower)
        t.display()
        if (!gameIns.attackers.isEmpty) {
          val close = t.findClose(gameIns.attackers)
          val proj = new Fire(t, close)
          val projectiles = t.fires
          projectiles += proj
          for (fire <- projectiles) {
            if (fire.x != fire.to.cell.x && fire.y != fire.to.cell.y) {
              val dir = fire.findDirection(t, close)
              fire.x += dir._1
              fire.y += dir._2
            }
          }
        }
        gameOver()
      }
    } catch {
      case _: Exception => println("Something wrong")
    }

  }

  private def gameOver() = {
    if (!player.isAlive) {
      gameIns.attackers.clear
      gameIns.towers.clear
      fill(250, 250, 250)
      rect(0, 0, 900, 750)
      gameIns.gameOver = true
    }
  }

  override def mouseClicked() {
    val mX = mouseX
    val mY = mouseY
    val outlineX = mouseX / 50
    val outlineY = mouseY / 50

    selected = new Cell(chooseRight(mouseX), chooseRight(mouseY))
    //    outlineCell(new Cell(mouseX, mouseY))
    var what: Option[Tower] = Some(new BasicTower(selected, this))
    if (mouseX > 0 && mouseX < 200 && mouseY < 100 && selectedCell == false) {
      selectedCell = true
      if (selectedCell) {
        fill(100, 100, 100)
        rect(0, 0, boxWidth, 100);
        addWave()
        gameIns.messages += "Wave incoming"
        wave = true
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
        if (selectedCell) {
          gameIns.addTower(selected, what.get)
          gameIns.messages += "Tower Added"
          selectedCell = false
        }
      } else {
        selectedCell = true
        // Selecting a tower
        if (selectedCell) {
          gameIns.removeTower(selected)
          gameIns.messages += "Tower Removed"
          selectedCell = false
        }
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
        fill(100, 255, 100) // Color: Green
        rect(cell.x * cellSize, topHeight + cell.y * cellSize, cellSize, cellSize)
        fill(255, 0, 0)
        text("G", cell.x * cellSize, 25 + topHeight + cell.y * cellSize)
      case Route =>
        fill(0, 0, 0) // Color: Black
        rect(cell.x * cellSize, topHeight + cell.y * cellSize, cellSize, cellSize)
        fill(255, 0, 0)
        text("R", cell.x * cellSize, 25 + topHeight + cell.y * cellSize)
      case Target =>
        fill(255, 0, 0) // Color: Red
        rect(cell.x * cellSize, topHeight + cell.y * cellSize, cellSize, cellSize)
        fill(255, 255, 255)
        text("T", cell.x * cellSize, 25 + topHeight + cell.y * cellSize)
      case _ =>
        fill(255, 255, 255) // Color: White
        rect(cell.x * cellSize, topHeight + cell.y * cellSize, cellSize, cellSize)
        fill(255, 0, 0)
        text("-", cell.x * cellSize, 25 + topHeight + cell.y * cellSize)
    }
  }

  private def drawMenu(): Unit = {
    menuBox(topX, topY, boxWidth, topHeight, "Money")
    menuBox(boxWidth, topY, boxWidth, topHeight, "Health")
    menuBox(boxWidth * 2, topY, boxWidth, topHeight, "Wave")
    menuBox(boxWidth * 3, (0.4 * wHeight).toInt, boxWidth, (0.5 * wHeight).toInt, "Messages")
    fill(13, 255, 0)
    rect(boxWidth * 3 + 5, topY + 100, cellSize, cellSize)
    fill(100, 100, 0)
    rect(boxWidth * 3 + 5 + 100, topY + 100, cellSize, cellSize)
    fill(235, 52, 52)

    text(player.money.toString, topX + 2, topY + 30 + 60)
    text(player.healthPoints.toString, boxWidth + 2, topY + 30 + 60)
    text(gameIns.waveNr.toString, boxWidth * 2 + 2, topY + 30 + 60)

  }

  private def drawBoard() = {
    drawMenu()
    for (x <- 0 until mapWidth) {
      for (y <- 0 until mapHeight) {
        drawCell(new Cell(x, y))
      }
    }
  }

  def drawing(attacker: Attackers): Unit = {
    fill(13, 255, 0)
    ellipse(attacker.x, attacker.y + 25, 40, 40) // +25 for getting it to the middle
  }

  //  def drawingProj(proj: Fire): Unit = {
  //    fill(13, 255, 0)
  //    var x = proj.x
  //    var y = proj.y
  //    ellipse(x, y + 25, 30, 30) // +25 for getting it to the middle
  //  }

  private def drawMessages() = {
    for (m <- 0 until gameIns.messages.length) {
      message(gameIns.messages(m), m * 20)
    }
    gameIns.deleteMessage()
  }

  private def message(message: String, y: Int) = {
    textSize(20)
    fill(0, 0, 0)
    text(message, boxWidth * 3 + 3, (0.4 * wHeight).toInt + 50 + y)

  }

  private def outlineCell(cell: Cell) = {
    noFill()
    stroke(204, 102, 0)
    rect(cell.x * cellSize, cell.y, cellSize, cellSize)
  }

}

object Game extends App {

  PApplet.main("tower.Game")

}

