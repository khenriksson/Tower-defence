package test

import org.scalatest.funsuite._
import tower._
import attackers._
import gamemaps._
import processing.core.PApplet

import java.io.File
import java.io.FileNotFoundException
import java.io.IOException
import scala.io.Source
import scala.collection.mutable.Buffer

class UnitTests extends AnyFunSuite {
  val game = new Game
  val cell = new Cell(500, 500)
  val tower = new BasicTower(cell, game)
  val route = Route
  val attacker = new BasicAttacker(cell)

  test("A tower should be added to the game instance array") {
    game.gameIns.addTower(tower)
    assert(!game.gameIns.towers.isEmpty)
  }

  test("An attacker should be added to the game instance attacker array") {
    game.gameIns.addAttacker()
    assert(!game.gameIns.attackers.isEmpty)
  }

  test("Adding and removing a tower should assert the towers empty") {
    game.gameIns.addTower(tower)
    game.gameIns.selectTower(cell)
    game.gameIns.removeTower()
    assert(game.gameIns.towers.isEmpty)
  }

  test("Attackers take damage") {
    val attackerHealth = attacker.healthPoints
    attacker.takingDamage(tower)
    assert(attacker.healthPoints == (attackerHealth - tower.attackDamage))
  }

  test("Tower is upgraded and level changed") {
    val before = (tower.attackDamage, tower.range)
    val levelBefore = tower.level
    game.gameIns.addTower(tower)
    game.gameIns.selectTower(cell)
    game.gameIns.upgrade()
    assert(tower.attackDamage > before._1 && tower.range > before._2 && tower.level > levelBefore)
  }

  test("Player takes damage when attackers attack") {
    val before = Player.healthPoints
    attacker.attack()
    assert(before > Player.healthPoints)
  }

  test("Tower is not upgraded when player money isn't enough") {
    val levelBefore = tower.level
    game.gameIns.player.money = 11
    game.gameIns.addTower(tower)
    game.gameIns.selectTower(cell)
    game.gameIns.upgrade()
    assert(tower.level == levelBefore)
  }

  test("Test all maps") {
    val files = FileReader.getListOfFiles("resources/gamemaps")
    val results = Buffer[Buffer[Array[Char]]]()
    files.foreach({
      f =>
        try {
          val save = Buffer[Array[Char]]()
          val bufferedSource = Source.fromFile(f)
          for (line <- bufferedSource.getLines()) {
            save += line.toCharArray()
          }
          results += save
          bufferedSource.close()
        } catch {
          case e: FileNotFoundException => println("File not found " + f.getAbsolutePath)
          case e: IOException           => println("IO exception")
        }
    })
    results.foreach(f => assert(f.length == game.gameIns.map.length && game.gameIns.map(0).length == f(0).length))
  }

}