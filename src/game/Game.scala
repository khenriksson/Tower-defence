package game

import processing.core.{ PApplet, PImage }
import attackers._
import gamemaps._
import java.io.IOException
import tower._

class Game extends Helper {

  val gameIns = GameInstance
  val player = gameIns.player

  var map = gameIns.cellToMap
  var spriteMap: Map[String, PImage] = Map[String, PImage]()
  var selectedCell: Boolean = false
  var selected = new Cell(0, 0)
  var hoverCell = new Cell(0, 0)
  var wave: Boolean = false
  var gameBegun: Boolean = false

  def mapWidth: Int = gameIns.map.length
  def mapHeight: Int = gameIns.map(0).length

  override def setup() = {
    frameRate(5)
    menuBox(topX, topY, boxWidth, topHeight, "Money", regular, rYOffset)
    menuBox(boxWidth, topY, boxWidth, topHeight, "Health", regular, rYOffset)
    menuBox(boxWidth * 2, topY, boxWidth, topHeight, "Wave", regular, rYOffset)
    menuBox(mWidth, 0, menuWidth, menuHeight, "Towers", regular, rYOffset)
    menuBox(mWidth, menuHeight, menuWidth, menuHeight, "Messages", regular, rYOffset)
    menuBox(boxWidth * 3, topY, boxWidth / 2, topHeight / 2, "Quit", small, sYOffset)
    menuBox(boxWidth * 3, topY + (topHeight / 2), boxWidth / 2, topHeight / 2, "Remove tower", small, sYOffset)
    text(player.money.toString, topX + 2, topY + 30 + 60)
    text(player.healthPoints.toString, boxWidth + 2, topY + 30 + 60)
    text(gameIns.waveNr.toString, boxWidth * 2 + 2, topY + 30 + 60)
    try {

      spriteMap += ("grass" -> loadImage("resources/grass.png"),
        "castle" -> loadImage("resources/castle.png"),
        "road" -> loadImage("resources/road.png"),
        "basic" -> loadImage("resources/attackers/basic.png"),
        "advanced" -> loadImage("resources/attackers/advanced.png"),
        "basicTower" -> loadImage("resources/towers/basic0.png"),
        "advancedTower" -> loadImage("resources/towers/advance0.png"))

      for (i <- spriteMap) {
        i._2.resize(50, 50)
      }

    } catch {
      case e: IOException =>
        text("File not read", 500, 500)
        fill(0, 0, 0)
        rect(0, 0, wWidth, wHeight)
    }
  }

  override def settings() = {
    size(wWidth, wHeight)
  }

  override def draw() = {

    if (!gameBegun) {
      // Draw the choose map
      drawChooseMap()
    } else {
      drawBoard()
      drawMessages()
      try {
        if (!gameIns.towers.isEmpty) {
          for (tower <- gameIns.towers) {
            tower.display()
            tower.findClose(gameIns.attackers)
          }
        }
        if (wave) {
          if (!gameIns.attackers.isEmpty) {
            gameIns.attackers.foreach(f => {
              if (f.move(map)) drawing(f) else gameIns.removeAttacker(f)
            })
          } else {
            wave = false
          }
        }
        gameOver()
      } catch {
        case e: Exception => e.getCause()
      }
    }
  }

  // Click boxes
  /**
   * @param mouseXL Left x coordinate
   * @param mouseXR Right x coordinate
   * @param mouseYT Top y coordinate
   * @param mouseYB Bottom y coordinate
   * @param callback Function to be called
   * @return Unit
   */
  def mouseBox(mouseXL: Int, mouseXR: Int, mouseYT: Int, mouseYB: Int, callback: Unit) = {
    if (mouseX > mouseXL && mouseX < mouseXR && mouseY > mouseYT && mouseY < mouseYB) {
      selectedCell = true
      if (selectedCell) {
        callback
      }
    }
  }

  override def mouseClicked() {
    selected = new Cell(chooseRight(mouseX), chooseRight(mouseY))

    if (mouseX > (boxWidth * 3 + boxWidth / 2) && mouseX < (wWidth) && mouseY > (topY) && mouseY < topY + (topHeight / 2) && selectedCell == false) {
      selectedCell = true
      if (selectedCell && !wave) {
        fill(100, 100, 100)
        rect((boxWidth * 3 + boxWidth / 2), topY, boxWidth, topHeight / 2)
        addWave()
        gameIns.messages += "Wave incoming"
        wave = true
      } else {
        gameIns.messages += "Wave already incoming"
      }
      // If game hasn't begun, show the maps that can be chosen
    } else if (!gameBegun) {
      for (i <- 0 until gameIns.maps.length) {
        val l = 100 * gameIns.maps.length
        val xMin = ((wWidth / 2) + (100 * i) - l / 2).toInt
        val xMax = ((wWidth / 2) + (100 * i) - l / 2 + 75).toInt
        val yMin = (wHeight / 2).toInt - 25
        val yMax = (wHeight / 2).toInt + 25
        if (mouseX > xMin && mouseX < xMax && mouseY > yMin && mouseY < yMax) {
          gameIns.updateMap(gameIns.maps(i))
          map = gameIns.cellToMap
          gameBegun = true
        }
      }
    } else if (mouseX > 705 && mouseX < 755 && mouseY > 50 && mouseY < 100) {
      // Selecting a tower
      selectedCell = true
      if (selectedCell) {
        rect(705, 50, 50, 50)
        gameIns.towerType = Some(new BasicTower(selected, this))
        selectedCell = false
      }
    } else if (mouseX > 760 && mouseX < 810 && mouseY > 50 && mouseY < 100) {
      // Selecting a tower
      selectedCell = true
      if (selectedCell) {
        rect(760, 50, 50, 50)
        gameIns.towerType = Some(new AdvanceTower(selected, this))
        selectedCell = false
      }
    } else if (mouseX > 0 && mouseX < 700 && mouseY > 0 && mouseY < mHeight) {
      // This function checks whether there's a tower in the location already
      // If there is, then it deletes that tower
      selectedCell = true

      if (!gameIns.towers.exists(f => f.location == selected.location)) {
        if (selectedCell && gameIns.towerType.isDefined) {
          fill(100, 100, 100)
          gameIns.towerType.get.cell = selected
          rect(selected.x, selected.y, cellSize, cellSize)
          gameIns.towerType.get.location = selected.location
          gameIns.addTower(gameIns.towerType.get)
          // Load in pictures
          gameIns.towers.filter(p => p.location == selected.location)(0).init()
          selectedCell = false
          gameIns.towerType = None
        }
      } else {
        // Selecting a tower
        if (selectedCell) {
          rect(selected.x, selected.y, cellSize, cellSize)
          gameIns.selectTower(selected)
          selectedCell = false
        }
      }
    } else if (mouseX > boxWidth * 3 && mouseX < (boxWidth * 3 + boxWidth / 2) && mouseY > (topY + (topHeight / 2)) && mouseY < wHeight) {
      gameIns.removeTower()
    } else if (mouseX > (boxWidth * 3 + boxWidth / 2) && mouseX < (wWidth) && mouseY > (topY + (topHeight / 2)) && mouseY < wHeight) {
      gameIns.upgrade()
    } else if (mouseX > boxWidth * 3 && mouseX < boxWidth * 3 + (boxWidth / 2) && mouseY > topY && mouseY < (topY + (topHeight / 2))) {
      exit()
    } else {
      selectedCell = false
    }
  }

  /**
   * Used for finding the right cell in the map
   */
  def chooseRight(which: Int) = {
    var stepper = 0
    var chosen = 0
    while (stepper * 50 <= which) {
      chosen = stepper * 50
      stepper += 1
    }
    chosen
  }

  /**
   * Function for drawing the hovering over cells
   */
  override def mouseMoved() {
    hoverCell = new Cell(chooseRight(mouseX), chooseRight(mouseY))
    if (mouseX < mWidth && mouseY < mHeight &&
      (map.getCell(hoverCell) == Route
        || map.getCell(hoverCell) == Target
        || map.getCell(hoverCell) == GenerateCell)
        && !wave && gameBegun) {
      noStroke()
    } else if (mouseX < mWidth && mouseY < mHeight && !wave && gameBegun) {
      stroke(255, 255, 255)
      noFill()
      rect(chooseRight(mouseX), chooseRight(mouseY), cellSize, cellSize)
    } else if (mouseX > 705 && mouseX < 755 && mouseY > 50 && mouseY < 100) {
      stroke(0, 0, 0)
      noFill()
      rect(705, 50, cellSize, cellSize)
    } else if (mouseX > 760 && mouseX < 810 && mouseY > 50 && mouseY < 100) {
      stroke(0, 0, 0)
      noFill()
      rect(760, 50, cellSize, cellSize)
    }
  }

  /**
   * Function for drawing the cells on the map
   * @param cell which cell to draw
   */
  private def drawCell(cell: Cell): Unit = {
    val castle = spriteMap("castle")
    val grass = spriteMap("grass")
    val road = spriteMap("road")
    map.cellType(cell) match {
      case GenerateCell =>
        fill(0, 0, 0) // Color: Black
        rect(cell.x * cellSize, cell.y * cellSize, cellSize, cellSize)
      case Route =>
        image(road, cell.x * cellSize, cell.y * cellSize)
      case Target =>
        image(castle, cell.x * cellSize, cell.y * cellSize)
      case _ =>
        image(grass, cell.x * cellSize, cell.y * cellSize)
    }
  }

  private def drawMenu(): Unit = {
    stroke(0, 0, 0)
    menuBox(topX, topY, boxWidth, topHeight, "Money", regular, rYOffset)
    menuBox(boxWidth, topY, boxWidth, topHeight, "Health", regular, rYOffset)
    menuBox(boxWidth * 2, topY, boxWidth, topHeight, "Wave", regular, rYOffset)
    menuBox(mWidth, 0, menuWidth, menuHeight, "Towers", regular, rYOffset)
    menuBox(mWidth, menuHeight, menuWidth, menuHeight, "Messages", regular, rYOffset)
    menuBox(boxWidth * 3, topY, boxWidth / 2, topHeight / 2, "Quit", small, sYOffset)
    menuBox(boxWidth * 3, topY + (topHeight / 2), boxWidth / 2, topHeight / 2, "Remove tower", small, sYOffset)
    menuBox(boxWidth * 3 + (boxWidth / 2).toInt, topY + (topHeight / 2), boxWidth / 2, topHeight / 2, ("Upgrade\n cost " + gameIns.currentUpgradeCost), small, sYOffset)
    menuBox(boxWidth * 3 + (boxWidth / 2).toInt, topY, boxWidth / 2, topHeight / 2, "Next wave", small, sYOffset)

    fill(13, 255, 0)
    rect(boxWidth * 3 + 5, topY + 100, cellSize, cellSize)
    fill(100, 100, 0)
    rect(boxWidth * 3 + 5 + 100, topY + 100, cellSize, cellSize)

    fill(0, 0, 0)
    textSize(25)
    text(player.money.toString, topX + 2, topY + 30 + 60)
    text(player.healthPoints.toString, boxWidth + 2, topY + 30 + 60)
    text(gameIns.waveNr.toString, boxWidth * 2 + 2, topY + 30 + 60)

    noStroke()
    image(spriteMap("basicTower"), 705, 50)
    image(spriteMap("advancedTower"), 760, 50)
  }

  private def drawChooseMap(): Unit = {
    fill(49, 99, 0) // Color: Grass Green
    rect(0, 0, wWidth, wHeight)
    for (i <- 0 until gameIns.maps.length) {
      // Räkna ut summan av längden
      // =n 100*i
      val l = 100 * gameIns.maps.length

      fill(255, 255, 255)
      rect((wWidth / 2 + 100 * i).toInt - l / 2, (wHeight / 2).toInt - 25, 75, 50, 7)
      fill(0, 0, 0)
      textSize(25)
      text("Map " + (i + 1).toString, (wWidth / 2 + (100 * i) - (l / 2)).toInt, (wHeight / 2).toInt + 15)
      textAlign(0)
    }
  }

  // Working stuff down here!

  private def drawMessages() = {
    for (m <- 0 until gameIns.messages.length) {
      message(gameIns.messages(m), m * 20)
    }
    gameIns.deleteMessage()
  }

  /**
   * Draws the attacker on the map
   * @param attacker The attacker that's being drawn
   */
  def drawing(attacker: Attackers): Unit = {
    val tank = spriteMap(attacker.name)
    image(tank, attacker.cell.x * 50, attacker.cell.y * 50)
  }

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

  PApplet.main("game.Game")

}

