package edu.udea.model.grafo

import edu.udea.model.polinomio.{Monomio, Polinomio}

object Grafo {

  def nuevo(aristas: List[(Int, Set[Int])]): Grafo = {
    new Grafo(
      aristas.sortBy(x => (x._2.size, x._1))(Ordering.Tuple2(Ordering.Int.reverse, Ordering.Int))
    )
  }

}

case class Grafo(aristas: List[(Int, Set[Int])]) {

  def esCompleto(): Boolean = {
    aristas.forall { a =>
      aristas.size == ( a._2.size + 1 )
    }
  }

  def esDesconexo(): Boolean = {
    aristas.forall(_._2.isEmpty)
  }

  def agregarArista(a: Int, b: Int): Grafo = {
    val la = aristas.collectFirst {
      case (x, l) if (x == a) => l + b
    } getOrElse(Set(b))

    val lb = aristas.collectFirst {
      case (x, l) if (x == b) => l + a
    } getOrElse(Set(a))

    Grafo.nuevo(
      List((a, la)).filter(_ => !aristas.exists(_._1 == a)) ++
      List((b, lb)).filter(_ => !aristas.exists(_._1 == b)) ++
      aristas.collect {
        case (i, l) if (i == a) => (i, la)
        case (i, l) if (i == b) => (i, lb)
        case (i, l) => (i, l)
      }
    )
  }

  def eliminarArista(a: Int, b: Int): Grafo = {
    Grafo.nuevo(
      aristas.collect {
        case (i, l) if (i == a || i == b) => (i, l.filter(x => x != a && x != b))
        case (i, l) => (i, l)
      }
    )
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

  def polinomioVorder(): Polinomio = {
    if (esCompleto()) {
      (1 until aristas.size).foldLeft(Polinomio(List(Monomio(1, 1)))) { (p, i) =>
        p.multiplicar(Polinomio(List(Monomio(1, 1), Monomio(-i, 0))))
      }
    } else if (esDesconexo()) {
      Polinomio(List(Monomio(1, aristas.size)))
    } else {
      aristaAReducir()
        .fold[Polinomio]({
          println("Falla para")
          println(aristas)
          Polinomio(List())
        }) { x =>
          eliminarArista(x._1, x._2).polinomioVorder().sumar(
            combinarVertices(x._1, x._2).polinomioVorder()
              .map(m => m.copy(coeficiente = -1 * m.coeficiente))
          )
        }
    }
  }

  def combinarVertices(a: Int, b: Int): Grafo = {
    val nuevasAristas =
      aristas.collect {
        case (i, l) if (i != a && i != b) => (i, l.map(x => if (x == b) a else x))
        case (i, l) if (i == a) => (i, aristas.collectFirst{
          case (x, ll) if (x == b) => l.union(ll).diff(Set(a, b))
        }.getOrElse(l))
      }

    Grafo.nuevo(nuevasAristas)
  }

  def aristaAReducir(): Option[(Int, Int)] = {
    aristas.headOption.flatMap { h =>
      h._2.collectFirst {
        case i if (aristas.exists(x => x._1 == i && !x._2.isEmpty)) => (h._1, i)
      }
    }
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