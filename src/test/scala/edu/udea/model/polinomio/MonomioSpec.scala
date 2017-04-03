package edu.udea.model.polinomio

import org.scalatest.{FunSpec, Matchers}

/**
  * Created by roger on 4/2/17.
  */
class MonomioSpec extends FunSpec with Matchers {

  describe( "Derivada de un monomio" ) {

    it( "Debería ser cero si el exponente es cero (o bien el monomio es una constante)" ) {
      Monomio( 3, 0 ).derivada( 1 ) should equal ( Monomio( 0, 0 ) )
    }

    it( "Debería calcular la n-sima derivada" ) {
      Monomio( 5, 4 ).derivada( 4 ) should equal( Monomio( 5 * 4 * 3 * 2 * 1, 0 ) )
    }

    it( "Debería ser 0, siempre que llegue a una constante" ) {
      Monomio( 5, 4 ).derivada( 5 ) should equal( Monomio( 0, 0 ) )
    }

  }

  describe( "Multiplicación de monomios" ) {

    it( "Debería multiplicar el coeficiente y sumar los exponentes" ) {
      Monomio( 3, 4 ).multiplicar( Monomio( 10, 5 ) ) should equal( Monomio( 30, 9 ) )
    }

  }

}
