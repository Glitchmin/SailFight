package Gui

import scalafx.scene.image.Image

import java.io.FileInputStream
import java.io.FileNotFoundException
import scala.collection.mutable

object ImageLoader {
  val map = new mutable.HashMap[String, Image]()

  def getImage(path: String): Image = {
    if (!map.contains(path)) try map.put(path, new Image(new FileInputStream(path)))
    catch {
      case e: FileNotFoundException =>
        println(e.getMessage)
    }
    map(path)
  }
}