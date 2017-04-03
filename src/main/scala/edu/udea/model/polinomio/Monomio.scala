package edu.udea.model.polinomio

import scala.scalajs.js.annotation.JSExportAll

/**
  * Created by roger on 29/03/17.
  */
@JSExportAll
case class Monomio( coeficiente: Double, exponente: Double ) {

  def eval( x: Double ): Double = coeficiente *
    scala.math.pow( x.doubleValue(), exponente.doubleValue() )

  def derivada( n: Int ): Monomio = {
    if ( n == 0 ) this
    else if ( exponente == 0 ) Monomio( 0, 0 )
    else Monomio( coeficiente * exponente, exponente - 1 ).derivada( n - 1 )
  }

  def multiplicar( m: Monomio ): Monomio =
    Monomio( coeficiente * m.coeficiente, exponente + m.exponente )

  def integral(): Monomio = Monomio( coeficiente / ( exponente + 1 ), exponente + 1 )

}
