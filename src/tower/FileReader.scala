package tower

import scala.io.Source
import scala.collection.mutable.Buffer
import java.io.File
import java.io.FileNotFoundException
import java.io.IOException

object FileReader {
  def parse(directory: String): Array[Array[Char]] = {
    val results = Buffer[Array[Char]]()
    try {
      val bufferedSource = Source.fromFile(directory)
      for (line <- bufferedSource.getLines()) {
        results += line.toCharArray()
      }
      bufferedSource.close()
    } catch {
      case e: FileNotFoundException => println("file not found " + directory)
      case e: IOException           => println("IO exception")
    }
    results.toArray
  }

}