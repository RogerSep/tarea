package edu.udea.model.polinomio

import org.scalatest.{FunSpec, Matchers}

/**
  * Created by roger on 29/03/17.
  */
class PolinomioSpec extends FunSpec with Matchers {

  describe( "evaluación de polinomios" ) {

    it( "evaluar p( x )" ) {

      val pol = Polinomio( List( Monomio( 10, 3 ), Monomio( -8, 2 ) ) )

      pol.eval( 5 ) should equal ( 10 * scala.math.pow( 5, 3 ) - 8 * scala.math.pow( 5, 2 )  )

    }

    it( "sumar p1 y p2 correctamente" ) {

      val p1 = Polinomio( List(
        Monomio( 5, 5 ),
        Monomio( 5, 4 ),
        Monomio( 5, 3 ),
        Monomio( 5, 2 ),
        Monomio( 5, 1 ),
        Monomio( 5, 0 )
      ) )

      val p2 = Polinomio( List(
        Monomio( 3, 5 ),

        Monomio( -6, 3 ),
        Monomio( 5, 2 ),
        Monomio( -1, 1 )

      ) )

      p1.sumar( p2 ) should equal( Polinomio( List(
        Monomio( 8, 5 ),
        Monomio( 5, 4 ),
        Monomio( -1, 3 ),
        Monomio( 10, 2 ),
        Monomio( 4, 1 ),
        Monomio( 5, 0 )
      ) ) )

    }

    it( "evaluar la derivada polinomios correctamente" ) {
      val polinomio = Polinomio( List(
        Monomio(  5, 5 ),
        Monomio(  6, 4 ),
        Monomio(  7, 3 ),
        Monomio(  8, 2 ),
        Monomio(  9, 1 ),
        Monomio( 10, 0 )
      ) )
      val d1 = Polinomio( List(
        Monomio( 25, 4 ),
        Monomio( 24, 3 ),
        Monomio( 21, 2 ),
        Monomio( 16, 1 ),
        Monomio(  9, 0 )
      ) )
      val d2 = Polinomio( List(
        Monomio( 100, 3 ),
        Monomio(  72, 2 ),
        Monomio(  42, 1 ),
        Monomio(  16, 0 )
      ) )

      polinomio.derivada( 1 ) should equal ( d1 )
      polinomio.derivada( 2 ) should equal ( d2 )
      polinomio.derivada( 5 ) should equal ( Polinomio( List( Monomio( 600, 0 ) ) ) )
      polinomio.derivada( 6 ) should equal ( Polinomio( List( Monomio( 0, 0 ) ) ) )

    }

    it( "multiplicar los polinomios correctamente" ) {
      val p1 = Polinomio( List( Monomio( 2, 2 ), Monomio( -1, 0 ) ) )
      val p2 = Polinomio( List( Monomio( 2, 1 ), Monomio( 1, 2 ) ) )
      val p3 = Polinomio( List( Monomio( 2, 4 ), Monomio( 4, 3 ), Monomio( -1, 2 ), Monomio( -2, 1 ) ) )

      p1.multiplicar( p2 ) should equal ( p3 )
    }

  }

}
