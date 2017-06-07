package edu.udea.puertos.io

import java.io.File

import edu.udea.model.grafo.Grafo
import org.scalatest.{Matchers, WordSpec}

import scala.util.Success

class GrafoReaderSpec extends WordSpec with Matchers {

  "Leer los edges de un grafo" in {
    GrafoReader.crearGrafo(5, List(
      (1, 2)
    )) should equal (
      Grafo(List(
        (1, List(2)),
        (2, List(1)),
        (3, Nil),
        (4, Nil),
        (5, Nil)
      ))
    )
  }

  "Parsear correctamente un archivo" in {

    val path = getClass.getResource("/edu/udea/puertos/io/GrafoReaderSpec/test1.col").getPath
    val testFile = new File(path)

    GrafoReader.leerArchivo(testFile) should equal (
      Success(
        Grafo(List(
          (1, List(5)),
          (5, List(1)),
          (2, Nil),
          (3, Nil),
          (4, Nil),
          (6, Nil),
          (7, Nil),
          (8, Nil),
          (9, Nil),
          (10, Nil)
        ))
      )
    )

  }

}
