package attackers

import tower._
import gamemaps._
import processing.core.PApplet
import gamemaps.Cell

// Abstract class for the attackers to keep track of everything
abstract class Attackers(var cell: Cell) extends Helper {
  val speed: Int
  val winning: Int
  var healthPoints: Int
  val icon: String
  val attackDamage: Int
  var course: Int
  var x: Int
  var y: Int

  def towerDefense(damage: Int) = {

  }

  def move(map: FileToMap): Boolean

  def takingDamage(fromTower: Tower): Unit = {
    healthPoints -= fromTower.attackDamage
  }

  def isDead(): Boolean = {
    healthPoints <= 0
  }

  def attack(): Unit = {
    Player.healthPoints -= attackDamage
  }

}