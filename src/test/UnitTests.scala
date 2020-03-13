package test

import org.scalatest.funsuite._
import tower._
import attackers._
import gamemaps._
import processing.core.PApplet

class UnitTests extends AnyFunSuite {
  val game = new Game
  val cell = new Cell(500, 500)
  val tower = new BasicTower(cell, game)
  val attacker = new BasicAttacker(cell)

  test("A tower should be added to the game instance array") {
    game.gameIns.addTower(cell, tower)
    assert(!game.gameIns.towers.isEmpty)
  }

  test("An attacker should be added to the game instance attacker array") {
    game.gameIns.addAttacker(cell, attacker)
    assert(!game.gameIns.attackers.isEmpty)
  }

  test("Adding and removing a tower should assert the towers empty") {
    game.gameIns.addTower(cell, tower)
    game.gameIns.removeTower(cell)
    assert(game.gameIns.towers.isEmpty)
  }

}