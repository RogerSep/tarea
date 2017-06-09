package edu.udea.puertos.io

import java.io.File

import edu.udea.model.grafo.Grafo

import scala.util.{Failure, Success, Try}

object GrafoReader {

  val problemRegex = """^p\s+edge\s+(\d+)\s+(\d+)$""".r
  val edgeRegex = """^e\s+(\d+)\s+(\d+)$""".r

  def leerArchivo(file: File): Try[Grafo] =
    Try {
      val lineas = io.Source.fromFile(file).getLines().toList

      val aristas = lineas.collectFirst {
        case problemRegex(aristas, _) => aristas.toInt
      }

      val vertices = lineas.collect {
        case edgeRegex(a, b) => (a.toInt, b.toInt)
      }

      (aristas, vertices)
    } flatMap {
      case (Some(aristas), vertices) if !vertices.isEmpty =>
        Success(crearGrafo(aristas, vertices))
      case (None, _) =>
        Failure(new Exception("No se encontró la línea que contiene el problema a solucionar"))
    }

  def crearGrafo(nodos: Int, vertices: List[(Int, Int)]): Grafo = {
    vertices
      .foldLeft(Grafo.nuevo((1 to nodos).map(x => (x, Set.empty[Int])).toList)) { (g, a) =>
        g.agregarArista(a._1, a._2)
      }
  }

}
