package gamemaps

import scala.io.Source
import scala.collection.mutable.Buffer
import java.io.FileNotFoundException
import java.io.IOException
import tower._
import java.io.File

import java.io.File
import java.io.FileNotFoundException
import java.io.IOException
import scala.io.Source

object FileReader {

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

  def generateCell = {
    val a = for {
      x <- 0 until width
      y <- 0 until height
      if (cells(x)(y) == GenerateCell)
    } yield {
      new Cell(x, y)
    }
    a.last
  }

  // This one gets the cellType from the cells directly
  def cellType(cell: Cell): MapCell = {
    cells(cell.x)(cell.y) // 14, 13
  }

  def getCell(cell: Cell): MapCell = {
    cells(cell.x / 50)(cell.y / 50)
  }

}