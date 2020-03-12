package test

import org.scalatest.funsuite._
import tower._
import gamemaps._
import processing.core.PApplet

class UnitTests extends AnyFunSuite {
  val game = new Game
  val cell = new Cell(500, 500)
  val tower = new BasicTower(cell, game)

  test("A tower should be added to the game instance array") {
    game.gameIns.addTower(cell, tower)
    assert(!game.gameIns.towers.isEmpty)
  }

  test("A cell should be a Tower cell") {
    cancel()
  }

}