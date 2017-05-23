package edu.udea.model.polinomio

case class Monomio( coeficiente: BigDecimal, exponente: BigDecimal ) {

  def eval( x: BigDecimal ): BigDecimal = coeficiente * x.pow(exponente.intValue())

  def derivada( n: Int ): Monomio = {
    if ( n == 0 ) this
    else if ( exponente == 0 ) Monomio( 0, 0 )
    else Monomio( coeficiente * exponente, exponente - 1 ).derivada( n - 1 )
  }

  def multiplicar( m: Monomio ): Monomio =
    Monomio( coeficiente * m.coeficiente, exponente + m.exponente )

  def integral(): Monomio = Monomio( coeficiente / ( exponente + 1 ), exponente + 1 )

}
