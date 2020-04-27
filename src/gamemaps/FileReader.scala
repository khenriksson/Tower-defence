package gamemaps

import java.io.File
import java.io.FileNotFoundException
import java.io.IOException

import scala.collection.mutable.Buffer
import scala.io.Source

import game.GenerateCell
import game.MapCell
import game.Route
import game.Target
import game.TowerCell

object FileReader {

  /**
   * Used to find all the maps in the current resources
   * @return array containing all the maps and how their printed
   */
  def getMaps() = {
    val files = FileReader.getListOfFiles("resources/gamemaps")
    val results = Buffer[Array[Array[Char]]]()
    files.foreach({
      f =>
        try {
          val save = Buffer[Array[Char]]()
          val bufferedSource = Source.fromFile(f)
          for (line <- bufferedSource.getLines()) {
            save += line.toCharArray()
          }
          results += save.toArray
          bufferedSource.close()
        } catch {
          case e: FileNotFoundException => println("File not found " + f.getAbsolutePath)
          case e: IOException           => println("IO exception")
        }
    })
    results
  }

  /**
   * Parses from text file to array to array
   * @param directory the directory where we want to parse
   * @return array of the map
   */
  def parse(directory: String): Array[Array[Char]] = {
    val results = Buffer[Array[Char]]()
    try {
      val bufferedSource = Source.fromFile(directory)
      for (line <- bufferedSource.getLines()) {
        results += line.toCharArray()
      }
      bufferedSource.close()
    } catch {
      case e: FileNotFoundException => println("File not found " + directory)
      case e: IOException           => println("IO exception")
    }
    results.toArray
  }

  /**
   * Gets list of file in the directory, used when parsing the maps
   * @param dir directory where .txt files are
   * @return List[File] list of files in the directory
   */
  def getListOfFiles(dir: String): List[File] = {
    val d = new File(dir)
    if (d.exists && d.isDirectory) {
      d.listFiles.filter(_.isFile).toList
    } else {
      List[File]()
    }
  }

}

class FileToMap(map: Array[Array[Char]]) {
  val width = map.length
  val height = map(0).length

  val cells = map.map(f => f.map {
    case '-' => new TowerCell // Add new to make class
    case '0' => GenerateCell
    case '1' => Route
    case '2' => Target
  })

  /**
   * Yields the spawncell for the attackers
   * @return spawncell
   */
  def generateCell = {
    val a = for {
      x <- 0 until width
      y <- 0 until height
      if (cells(x)(y) == GenerateCell)
    } yield {
      new Cell(x, y)
    }
    a(0)
  }

  // This one gets the cellType from the cells directly
  def cellType(cell: Cell): MapCell = {
    cells(cell.x)(cell.y) // 14, 13
  }
  // This one converts Processing coordinates to index in array
  def getCell(cell: Cell): MapCell = {
    cells(cell.x / 50)(cell.y / 50)
  }

}