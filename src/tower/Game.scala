package tower

import processing.core.{ PApplet }
import attackers._
import gamemaps._

import scala.collection.mutable.Buffer

class Game extends Helper {

  val gameIns = new GameInstance
  val player = gameIns.player

  //    image(icon, attacker.cell.x, attacker.cell.y)

  var selectedCell: Boolean = false
  var selected = new Cell(0, 0)
  var wave: Boolean = false
  var what: Option[Tower] = None

  def mapWidth: Int = gameIns.map.length
  def mapHeight: Int = gameIns.map(0).length

  override def setup() = {
    frameRate(10)
    textSize(20)
    menuBox(topX, topY, boxWidth, topHeight, "Money")
    menuBox(boxWidth, topY, boxWidth, topHeight, "Health")
    menuBox(boxWidth * 2, topY, boxWidth, topHeight, "Wave")
    menuBox(mWidth, 0, menuWidth, menuHeight, "Towers")
    menuBox(mWidth, menuHeight, menuWidth, menuHeight, "Messages")
    //    menuBox(boxWidth * 3, topY, 225, topHeight, "Quit")
    text(player.money.toString, topX + 2, topY + 30 + 60)
    text(player.healthPoints.toString, boxWidth + 2, topY + 30 + 60)
    text(gameIns.waveNr.toString, boxWidth * 2 + 2, topY + 30 + 60)

  }

  override def settings() = {
    size(wWidth, wHeight)

  }

  override def draw() = {
    drawBoard()
    drawMessages()

    try {
      for (tower <- 0 until gameIns.towers.length) {
        val t = gameIns.towers(tower)
        t.display()
        if (!gameIns.attackers.isEmpty) {
          t.findClose(gameIns.attackers)
          if (t.target.isDefined) {
            if (!t.target.get.isDead()) t.attack(t.target.get)
            //            t.fire()
            //            println(t.target)
            //          if (t.target.isDefined) {
            //            val fire = new Fire(t, t.target.get)
            //            if (fire.x != t.target.get.cell.x && fire.y != t.target.get.cell.y) {
            //              fire.add()
            //              drawProj(fire)
            //            }
          }
        }
      }
      if (wave) {
        if (!gameIns.attackers.isEmpty) {
          gameIns.attackers.foreach(f => {
            if (f.move(gameIns.cellToMap)) drawing(f) else gameIns.removeAttacker(f)

          })
        }
      } else {
        gameOver()
      }
    } catch {
      case _: Exception => println("Something wrong")
    }

  }

  override def mouseClicked() {
    selected = new Cell(chooseRight(mouseX), chooseRight(mouseY))

    if (mouseX > 0 && mouseX < boxWidth && mouseY > topY && selectedCell == false) {
      selectedCell = true
      if (selectedCell) {
        fill(100, 100, 100)
        rect(0, mHeight, boxWidth, 100);
        addWave()
        gameIns.messages += "Wave incoming"
        wave = true
      }
    } else if (mouseX > 705 && mouseX < 755 && mouseY > 50 && mouseY < 100) {
      // Selecting a tower
      selectedCell = true
      if (selectedCell) {
        rect(705, 50, 50, 50)
        what = Some(new BasicTower(selected, this))
        selectedCell = false
      }
    } else if (mouseX > 755 && mouseX < 805 && mouseY > 50 && mouseY < 100) {
      // Selecting a tower
      selectedCell = true
      if (selectedCell) {
        rect(755, 50, 50, 50)
        what = Some(new AdvanceTower(selected, this))
        selectedCell = false
      }
    } else if (mouseX > 0 && mouseX < 700 && mouseY > 0 && mouseY < mHeight) {
      // This function checks whether there's a tower in the location already
      // If there is, then it deletes that tower
      selectedCell = true

      if (!gameIns.towers.exists(f => f.location == selected.location)) {
        if (selectedCell && what.isDefined) {
          println(what.get.location + " before")
          what.get.cell = selected
          what.get.location = selected.location
          println(what.get.location + " after")
          gameIns.addTower(what.get)
          gameIns.messages += "Tower Added"
          selectedCell = false
          what = None
        }
      } else {
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
    //    val icon = loadImage("resources/grass.png")
    //    icon.resize(50, 50)
    gameIns.cellToMap.cellType(cell) match {
      case GenerateCell =>
        fill(100, 255, 100) // Color: Green
        rect(cell.x * cellSize, cell.y * cellSize, cellSize, cellSize)
      case Route =>
        fill(0, 0, 0) // Color: Black
        rect(cell.x * cellSize, cell.y * cellSize, cellSize, cellSize)
      case Target =>
        fill(255, 0, 0) // Color: Red
        rect(cell.x * cellSize, cell.y * cellSize, cellSize, cellSize)

      case _ =>
        fill(255, 255, 255) // Color: White
        rect(cell.x * cellSize, cell.y * cellSize, cellSize, cellSize)
      //        image(icon, cell.x, cell.y)
    }
  }

  private def drawMenu(): Unit = {
    menuBox(topX, topY, boxWidth, topHeight, "Money")
    menuBox(boxWidth, topY, boxWidth, topHeight, "Health")
    menuBox(boxWidth * 2, topY, boxWidth, topHeight, "Wave")
    menuBox(mWidth, 0, menuWidth, menuHeight, "Towers")
    menuBox(mWidth, menuHeight, menuWidth, menuHeight, "Messages")
    //    menuBox(boxWidth * 3, topY, 200, topHeight, "Quit")

    fill(13, 255, 0)
    rect(boxWidth * 3 + 5, topY + 100, cellSize, cellSize)
    fill(100, 100, 0)
    rect(boxWidth * 3 + 5 + 100, topY + 100, cellSize, cellSize)

    fill(102, 255, 102)
    rect(705, 50, cellSize, cellSize)
    fill(0, 153, 0)
    rect(755, 50, cellSize, cellSize)
    fill(235, 52, 52)

    text(player.money.toString, topX + 2, topY + 30 + 60)
    text(player.healthPoints.toString, boxWidth + 2, topY + 30 + 60)
    text(gameIns.waveNr.toString, boxWidth * 2 + 2, topY + 30 + 60)
  }

  // Working stuff down here!

  private def drawMessages() = {
    for (m <- 0 until gameIns.messages.length) {
      message(gameIns.messages(m), m * 20)
    }
    gameIns.deleteMessage()
  }

  def drawProj(fire: Fire): Unit = {

    fill(13, 255, 0)
    ellipse(fire.x + 25, fire.y + 25, 40, 40) // +25 for getting it to the middle

  }

  /**
   * Draws the attacker on the map
   * @param attacker The attacker that's being drawn
   */
  def drawing(attacker: Attackers): Unit = {
    fill(13, 255, 0)
    ellipse(attacker.cell.x * 50 + 25, attacker.cell.y * 50 + 25, 40, 40) // +25 for getting it to the middle
    //    val icon = loadImage(attacker.icon)
    //    image(icon, attacker.cell.x, attacker.cell.y)
  }

  //  def drawSome[T <% Helper](thing: T) {
  //    fill(13, 255, 0)
  //    ellipse(thing.cell.x * 50 + 1, attacker.cell.y * 50 + 1, 40, 40)
  //  }

  /** Drawing the board  */
  private def drawBoard() = {
    drawMenu()
    for (x <- 0 until mapWidth) {
      for (y <- 0 until mapHeight) {
        drawCell(new Cell(x, y))
      }
    }
  }

  /** Adds a wave to the game instance */
  def addWave() = {
    gameIns.addAttacker()
  }

  /**
   * Draws the message in the message box
   * @param message The message string to be written
   * @param y Used for positioning
   */
  private def message(message: String, y: Int) = {
    textSize(20)
    fill(0, 0, 0)
    text(message, mWidth + 3, menuHeight + 50 + y)
  }

  /** Draws the game over board if the player loses */
  private def gameOver() = {
    if (!player.isAlive) {
      gameIns.attackers.clear
      gameIns.towers.clear
      fill(250, 250, 250)
      textSize(50)
      rect(0, 0, 900, 750)
      fill(0, 0, 0)
      text("Game over", 400, 375)
    }
  }

}

object Game extends App {

  PApplet.main("tower.Game")

}

