package edu.udea.model.grafo

import edu.udea.model.polinomio.{Monomio, Polinomio}
import org.scalatest.{FunSpec, Matchers}

/**
  * Created by roger on 5/21/17.
  */
class GrafoSpec extends FunSpec with Matchers {

  describe("Grafos") {
    it("es completo cuando todos sus vértices están conectados") {
      Grafo(List(
        (1, List(2, 3, 4)),
        (2, List(1, 3, 4)),
        (3, List(1, 2, 4)),
        (4, List(1, 2, 3))
      ))
    }

    it("el polinomio de un grafo completo es x(x − 1)(x − 2)...(x − (n − 1))") {
      Grafo(List(
        (1, List(2, 3, 4)),
        (2, List(1, 3, 4)),
        (3, List(1, 2, 4)),
        (4, List(1, 2, 3))
      )) polinomio() should equal (Polinomio(List(
        Monomio(1, 4),
        Monomio(-6, 3),
        Monomio(11, 2),
        Monomio(-6, 1)
      )))

      Grafo(List(
        (1, List(2, 3, 4, 5)),
        (2, List(1, 3, 4, 5)),
        (3, List(1, 2, 4, 5)),
        (4, List(1, 2, 3, 5)),
        (5, List(1, 2, 3, 4))
      )) polinomio() should equal (Polinomio(List(
        Monomio(1, 5),
        Monomio(-10, 4),
        Monomio(35, 3),
        Monomio(-50, 2),
        Monomio(24, 1)
      )))
    }

    it("el polinomio del grafo desconexo es x^n") {
      Grafo(List(
        (1, Nil),
        (2, Nil),
        (3, Nil),
        (4, Nil),
        (5, Nil)
      )) polinomio() should equal(Polinomio(List(
        Monomio(1, 5)
      )))
    }

    it("calcula una arista faltante") {
      Grafo(List(
        (1, List(2, 3, 4, 5)),
        (2, List(1, 3, 4, 5)),
        (3, List(1, 2, 4, 5)),
        (4, List(1, 2, 3)),
        (5, List(1, 2, 3))
      )) aristaFaltante() should equal (Some((4, 5)))
    }

    it("elimina una arista haciendo merge de las conexiones") {
      val g = Grafo(List(
        (1, List(2, 3, 4, 5)),
        (2, List(1, 3, 4, 5)),
        (3, List(1, 2, 4, 5)),
        (4, List(1, 2, 3, 5)),
        (5, List(1, 2, 3, 4))
      )) combinarVertices (1, 2)

      println(g)
    }

    it("polinomio del grafo petersen") {
      val start = System.currentTimeMillis()

      val p = Grafo(List(
        (1, List(2, 5, 6)),
        (2, List(1, 3, 7)),
        (3, List(2, 4, 8)),
        (4, List(3, 5, 9)),
        (5, List(1, 4, 10)),
        (6, List(1, 8, 9)),
        (7, List(2, 9, 10)),
        (8, List(3, 6, 10)),
        (9, List(4, 6, 7)),
        (10, List(5, 7, 8))
      )).polinomio()


      p should equal(Polinomio(List(
        Monomio(1, 10),
        Monomio(-15, 9),
        Monomio(105, 8),
        Monomio(-455, 7),
        Monomio(1353, 6),
        Monomio(-2861, 5),
        Monomio(4275, 4),
        Monomio(-4305, 3),
        Monomio(2606, 2),
        Monomio(-704, 1)
      )))
    }

  }

}
