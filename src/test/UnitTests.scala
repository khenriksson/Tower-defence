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
    game.gameIns.removeTower(cell)
    assert(game.gameIns.towers.isEmpty)
  }

  test("Attackers take damage") {
    val attackerHealth = attacker.healthPoints
    attacker.takingDamage(tower)
    assert(attacker.healthPoints == (attackerHealth - tower.attackDamage))
  }

  //  test("Map should be of correct size") {
  //    cancel()
  //    val src = Source.fromFile("resources/gamemaps/first.txt")
  //    var check: Int = 0
  //    var firstLine: Int = 0
  //    try {
  //      check = src.getLines.size
  //    } finally {
  //      src.close()
  //    }
  //    assert(check == game.gameIns.map.length && game.gameIns.map(0).length == 15)
  //  }

  test("Test all maps") {
    val files = FileReader.getListOfFiles("resources/gamemaps")
    val results = Buffer[Buffer[Array[Char]]]()
    files.foreach({
      f =>
        try {
          println(f.length + " f.length")
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