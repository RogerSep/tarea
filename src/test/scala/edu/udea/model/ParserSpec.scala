package edu.udea.model

import edu.udea.model.polinomio.{Monomio, Polinomio}
import fastparse.core.Parsed.Success
import org.scalatest.{FunSuite, Matchers}

/**
  * Created by roger on 4/2/17.
  */
class ParserSpec extends FunSuite with Matchers {

  import PolinomioParser._

  test( "parseando los strings de la aplicación" ) {

    entero.parse( "123456789" ) should equal ( Success( 123456789, 9 ) )
    decimal.parse( "10.5" ) should equal ( Success( BigDecimal( 10.5 ), 4 ) )
    signo.parse( "+" ) should equal ( Success( 1, 1 ) )
    signo.parse( "-" ) should equal ( Success( -1, 1 ) )

    monomio.parse( "-12x2" ) should equal ( Success( Monomio( -12, 2 ), 5 ) )
    monomio.parse( "+x" ) should equal ( Success( Monomio( 1, 1 ), 2 ) )
    monomio.parse( "-x" ) should equal ( Success( Monomio( -1, 1 ), 2 ) )
    monomio.parse( "+3" ) should equal ( Success( Monomio( 3, 0 ), 2 ) )
    monomio.parse( "-3" ) should equal ( Success( Monomio( -3, 0 ), 2 ) )
    monomio.parse( "-3" ) should equal ( Success( Monomio( -3, 0 ), 2 ) )

    poli.parse( "+3x4+3x3-2x2+x-3" ) should equal ( Success(
      Polinomio( List(
        Monomio(-3, 0),
        Monomio(1, 1),
        Monomio(-2, 2),
        Monomio(3, 3),
        Monomio(3, 4)
      ) ), 16
    ) )
    poli.parse( "-3x2" ) should equal ( Success( Polinomio( List( Monomio( -3, 2 ) ) ), 4 ) )

    parentesis.parse("(-3x2+1)") should equal ( Success( Polinomio( List( Monomio( -3, 2 ), Monomio( 1, 0 ) ) ), 8 ) )
    parentesis.parse("(+1)") should equal ( Success( Polinomio( List( Monomio( 1, 0 ) ) ), 4 ) )
    parentesis.parse("(+x)") should equal ( Success( Polinomio( List( Monomio( 1, 1 ) ) ), 4 ) )

    sumaomult.parse("(-3x2+1)*(+2x)") should equal ( Success( Polinomio( List(
      Monomio( -6, 3 ),
      Monomio( 2, 1 )
    ) ), 14 ) )

    derivada.parse( "dx(+1)" ) should equal ( Success( Polinomio( List(
      Monomio( 0, 0 )
    ) ), 6 ) )

    derivada.parse( "d2x(-3x2+1)" ) should equal ( Success( Polinomio ( List (
      Monomio( -6, 0 )
    ) ), 11 ) )

    integral.parse( "∫[-1,1](+3x2)" ) should equal ( Success( Polinomio ( List( Monomio( 2, 0 ) ) ), 13 ) )
    integral.parse( "∫(+3)" ) should equal ( Success( Polinomio ( List( Monomio( 3, 1 ) ) ), 5 ) )
    integral.parse( "∫(+x)" ) should equal ( Success( Polinomio ( List( Monomio( 0.5, 2 ) ) ), 5 ) )

  }

  test( "Some test" ) {
    val str = "(∫(-1x))*(x)"
    val parsed = parse( str )

    println( parsed )

  }

}
