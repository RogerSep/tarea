package edu.udea.model.grafo

import edu.udea.model.polinomio.{Monomio, Polinomio}

case class Grafo(aristas: List[(Int, List[Int])]) {

  def esCompleto(): Boolean = {
    aristas.forall { a =>
      aristas.size == ( a._2.size + 1 )
    }
  }

  def esDesconexo(): Boolean = {
    aristas.forall(_._2.isEmpty)
  }

  def agregarArista(a: Int, b: Int): Grafo = {
    val na = (a, aristas.find(_._1 == a).map(x => (b :: x._2).distinct).getOrElse(List(b)))
    val nb = (b, aristas.find(_._1 == b).map(x => (a :: x._2).distinct).getOrElse(List(a)))

    Grafo(List(na, nb) ++ aristas.filter(x => x._1 != a && x._1 != b))
  }

  def polinomio(): Polinomio = {
    if (esCompleto()) {
      (1 until aristas.size).foldLeft(Polinomio(List(Monomio(1, 1)))) { (p, i) =>
        p.multiplicar(Polinomio(List(Monomio(1, 1), Monomio(-i, 0))))
      }
    } else if (esDesconexo()) {
      Polinomio(List(Monomio(1, aristas.size)))
    } else {
      aristaFaltante()
        .map { x =>
          agregarArista(x._1, x._2).polinomio().sumar(
            combinarVertices(x._1, x._2).polinomio()
          )
        }
        .get
    }
  }

  def combinarVertices(a: Int, b: Int): Grafo = {
    val nuevasAristas = aristas.foldLeft(List.empty[(Int, List[Int])]) { (acc, arista) =>
      if (arista._1 == a) {
        val conexionesB = aristas.find(_._1 == b).map(_._2).getOrElse(Nil)

        (b, (arista._2 ++ conexionesB).distinct.filter(x => x != a && x != b )) :: acc
      } else if (arista._1 == b) {
        acc
      } else {
        (arista._1, arista._2.map(x => if (x == a) b else x).distinct) :: acc
      }
    }

    Grafo(nuevasAristas)
  }

  def aristaFaltante(): Option[(Int, Int)] = {
    val x = aristas.collectFirst {
      case a if a._2.size < (aristas.size - 1) =>
        aristas
          .collectFirst {
            case (x, l) if (x != a._1 && !l.exists(_ == a._1)) => (a._1, x)
          }
    }.flatten

    x
  }

}