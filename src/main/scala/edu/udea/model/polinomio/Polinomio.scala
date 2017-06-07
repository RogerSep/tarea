package edu.udea.model.polinomio

/**
  * Created by roger on 29/03/17.
  */
object Polinomio {

  val cero: List[ Monomio ] = List[ Monomio ]( Monomio( 0, 0 ) )

  def apply( monomios: List[ Monomio ] ) = {
    val m = monomios
      .groupBy( _.exponente )
      .foldLeft( List[ Monomio ]() )( ( acc, m ) => {
        val coeficiente = m._2.foldLeft( BigDecimal(0.0) )( _ + _.coeficiente )

        Monomio( coeficiente, m._1 ) :: acc
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

class Polinomio( val monomios: List[ Monomio ] ) {

  def eval( x: BigDecimal ): BigDecimal = monomios
    .foldLeft( BigDecimal(0.0) )( ( acc, m ) => acc + m.eval( x ) )

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

  override def toString() = monomios
    .sortBy(_.exponente)(Ordering.BigDecimal.reverse)
    .mkString(" +")
    .replaceAll("\\+-", "-")

}
