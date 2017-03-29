package edu.udea.model.polinomio

/**
  * Created by roger on 29/03/17.
  */
case class Monomio( coeficiente: BigDecimal, exponente: BigDecimal ) {

  def eval( x: BigDecimal ) = coeficiente * scala.math.pow( x.doubleValue(), exponente.doubleValue() )

  override def toString() = s"${ coeficiente } x ^ ${ exponente }"

}
