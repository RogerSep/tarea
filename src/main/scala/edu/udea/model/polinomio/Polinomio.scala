package edu.udea.model.polinomio

import scala.scalajs.js
import scala.scalajs.js.Array
import scala.scalajs.js.annotation.JSExportAll

/**
  * Created by roger on 29/03/17.
  */
object Polinomio {

  val cero: Array[ Monomio ] = js.Array[ Monomio ]()
  cero.push( Monomio( 0, 0 ) )

  def apply( monomios: js.Array[ Monomio ] ) = {
    val m = monomios
      .groupBy( _.exponente )
      .foldLeft( js.Array[ Monomio ]() )( ( acc, m ) => {
        val coeficiente = m._2.foldLeft( 0.0 )( _ + _.coeficiente )

        acc.push( Monomio( coeficiente, m._1 ) )
        acc
      } )
      .filter( _.coeficiente != 0 )
      .sortBy( _.exponente )

    new Polinomio(
      m.length match {
        case 0 => cero
        case _ => m
      }
    )
  }



}

@JSExportAll
class Polinomio( val monomios: js.Array[ Monomio ] ) {

  def eval( x: Double ): Double = monomios
    .foldLeft( 0.0 )( ( acc, m ) => acc + m.eval( x ) )

  def sumar( p: Polinomio ): Polinomio = Polinomio(
    monomios.concat( p.monomios )
  )

  def multiplicar( p: Polinomio ): Polinomio = {
    Polinomio(
      monomios
        .flatMap( m => p.monomios.map( pm => m.multiplicar( pm ) ) )
    )
  }

  def derivada( n: Int ): Polinomio =
    Polinomio( monomios.map( _.derivada( n ) ) )

  def integral(): Polinomio =
    Polinomio( monomios.map( _.integral() ) )

  override def equals( o: scala.Any ): Boolean = o match {
    case p: Polinomio => p.monomios.equals( this.monomios )
    case _ => super.equals(o)
  }

  override def toString() = monomios.mkString(" ")

}
