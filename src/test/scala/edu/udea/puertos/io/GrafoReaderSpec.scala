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
      Grafo.nuevo(List(
        (1, Set(2)),
        (2, Set(1)),
        (3, Set.empty),
        (4, Set.empty),
        (5, Set.empty)
      ))
    )
  }

  "Parsear correctamente un archivo" in {

    val path = getClass.getResource("/edu/udea/puertos/io/GrafoReaderSpec/test1.col").getPath
    val testFile = new File(path)

    GrafoReader.leerArchivo(testFile) should equal (
      Success(
        Grafo.nuevo(List(
          (1, Set(5)),
          (5, Set(1)),
          (2, Set.empty),
          (3, Set.empty),
          (4, Set.empty),
          (6, Set.empty),
          (7, Set.empty),
          (8, Set.empty),
          (9, Set.empty),
          (10, Set.empty)
        ))
      )
    )

    println(GrafoReader.leerArchivo(new File("/Users/roger/Downloads/chromaticPolynomial_instances 2/graph0008_n0010_m00004_f4.col")))

  }

  "blah" in {

    val g = GrafoReader
      .leerArchivo(new File("/Users/roger/Downloads/chromaticPolynomial_instances 2/graph0020_n0010_m00022_f4.col"))
      .get

    val start = System.currentTimeMillis()
    val poly = g.polinomioVorder()
    val took = System.currentTimeMillis() - start
    println(took)
    println(poly)

  }

}
