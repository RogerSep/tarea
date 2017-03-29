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

  }

}
