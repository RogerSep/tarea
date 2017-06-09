package edu.udea.model.grafo

import edu.udea.model.polinomio.{Monomio, Polinomio}
import org.scalatest.{FunSpec, Matchers}

/**
  * Created by roger on 5/21/17.
  */
class GrafoSpec extends FunSpec with Matchers {

  describe("Grafos") {
    it("es completo cuando todos sus vértices están conectados") {
      Grafo.nuevo(List(
        (1, Set(2, 3, 4)),
        (2, Set(1, 3, 4)),
        (3, Set(1, 2, 4)),
        (4, Set(1, 2, 3))
      ))
    }

    it("el polinomio de un grafo completo es x(x − 1)(x − 2)...(x − (n − 1))") {
      Grafo.nuevo(List(
        (1, Set(2, 3, 4)),
        (2, Set(1, 3, 4)),
        (3, Set(1, 2, 4)),
        (4, Set(1, 2, 3))
      )) polinomio() should equal (Polinomio(List(
        Monomio(1, 4),
        Monomio(-6, 3),
        Monomio(11, 2),
        Monomio(-6, 1)
      )))

      Grafo.nuevo(List(
        (1, Set(2, 3, 4, 5)),
        (2, Set(1, 3, 4, 5)),
        (3, Set(1, 2, 4, 5)),
        (4, Set(1, 2, 3, 5)),
        (5, Set(1, 2, 3, 4))
      )) polinomio() should equal (Polinomio(List(
        Monomio(1, 5),
        Monomio(-10, 4),
        Monomio(35, 3),
        Monomio(-50, 2),
        Monomio(24, 1)
      )))
    }

    it("el polinomio del grafo desconexo es x^n") {
      Grafo.nuevo(List(
        (1, Set.empty),
        (2, Set.empty),
        (3, Set.empty),
        (4, Set.empty),
        (5, Set.empty)
      )) polinomio() should equal(Polinomio(List(
        Monomio(1, 5)
      )))
    }

    it("calcula una arista faltante") {
      Grafo.nuevo(List(
        (1, Set(2, 3, 4, 5)),
        (2, Set(1, 3, 4, 5)),
        (3, Set(1, 2, 4, 5)),
        (4, Set(1, 2, 3)),
        (5, Set(1, 2, 3))
      )) aristaFaltante() should equal (Some((4, 5)))
    }

    it("calcula la arista a eliminar") {
      Grafo.nuevo(List(
        (0, Set.empty),
        (1, Set.empty)
      )) aristaAReducir() should equal (None)

      Grafo.nuevo(List(
        (0, Set(1)),
        (1, Set(0))
      )) aristaAReducir() should equal (Some(0, 1))

      Grafo.nuevo(List(
        (2, Set(1)),
        (0, Set(1, 2, 3, 4)),
        (3, Set(1)),
        (1, Set(2, 3)),
        (4, Set(1))
      )) aristaAReducir() should equal (Some(0, 1))
    }

    it("combina una arista") {
      val g = Grafo.nuevo(List(
        (1, Set(2, 3, 4, 5)),
        (2, Set(1, 3, 4, 5)),
        (3, Set(1, 2, 4, 5)),
        (4, Set(1, 2, 3, 5)),
        (5, Set(1, 2, 3, 4))
      )) combinarVertices (1, 2)

      g should equal(Grafo.nuevo(List(
        (1, Set(3, 4, 5)),
        (3, Set(1, 4, 5)),
        (4, Set(1, 3, 5)),
        (5, Set(1, 3, 4))
      )))
    }

    it("polinomio del grafo petersen") {

      val petersen = Grafo.nuevo(List(
        (1, Set(2, 5, 6)),
        (2, Set(1, 3, 7)),
        (3, Set(2, 4, 8)),
        (4, Set(3, 5, 9)),
        (5, Set(1, 4, 10)),
        (6, Set(1, 8, 9)),
        (7, Set(2, 9, 10)),
        (8, Set(3, 6, 10)),
        (9, Set(4, 6, 7)),
        (10, Set(5, 7, 8))
      ))

      val start = System.currentTimeMillis()

      val p = petersen.polinomioVorder()
      val pv = petersen.polinomio()

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

      pv should equal (p)
    }

  }

}
