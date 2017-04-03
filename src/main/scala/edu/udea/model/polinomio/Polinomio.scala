package edu.udea.model.polinomio

/**
  * Created by roger on 29/03/17.
  */
object Polinomio {

  def apply( monomios: List[ Monomio ] ) = new Polinomio(
    monomios
      .groupBy( _.exponente )
      .foldLeft( List[ Monomio ]() )( ( acc, m ) => {
        val coeficiente = m._2.foldLeft( BigDecimal( 0 ) )( _ + _.coeficiente )

        Monomio( coeficiente, m._1 ) :: acc
      } )
      .filter( _.coeficiente != 0 )
      .sortBy( _.exponente )
  )

}

class Polinomio( val monomios: List[ Monomio ] ) {

  def eval( x: BigDecimal ): BigDecimal = monomios
    .foldLeft( BigDecimal( 0 ) )( ( acc, m ) => acc + m.eval( x ) )

  def sumar( p: Polinomio ): Polinomio = Polinomio(
    monomios ::: p.monomios
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
