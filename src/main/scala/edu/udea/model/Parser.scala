package edu.udea.model

import edu.udea.model.polinomio.{Monomio, Polinomio}

import scala.scalajs.js.annotation.{JSExport, JSExportTopLevel}

/**
  * Created by roger on 4/2/17.
  */
object PolinomioParser {

  import fastparse.all._

  val entero: Parser[ BigDecimal ] = P( CharIn( '0' to '9' ).rep( 1 ).!.map( v => BigDecimal( v ) ) )
  val decimal: Parser[ BigDecimal ] = P( entero ~ P( "." ) ~/ entero ).!.map( v => BigDecimal( v ) )
  val numero: Parser[ BigDecimal ] = P( signo.? ~ ( decimal | entero ) ).map { x =>
    val ( s, n ) = x
    s.getOrElse( BigDecimal( 1 ) ) * n
  }

  val signo: Parser[ BigDecimal ] = P( "+" | "-" ).!.map( v =>
    if ( v == "+" ) BigDecimal( 1 )
    else BigDecimal( -1 )
  )
  val x = P( "x" )

  val monomio: Parser[ Monomio ] = P( signo ~/ ( entero | decimal ).? ~/ ( x ~/ entero.? ).? ).map { x =>
    val ( signo, coeficiente, e ) = x
    val exponente = e.map( e => e.getOrElse( BigDecimal( 1 ) ) ).getOrElse( BigDecimal( 0 ) )

    Monomio( signo * coeficiente.getOrElse( 1 ), exponente )
  }

  val poli: Parser[ Polinomio ] = P( monomio.rep( 1 ) ).map { x =>
      // val ( m, ms ) = x

    Polinomio( x.toList )
  }

  val derivada: Parser[ Polinomio ] = P( "d" ~/ entero.? ~/ "x" ~/ parentesis ).map { x =>
    val ( n, polinomio ) = x
    polinomio.derivada( n.map( _.intValue() ).getOrElse( 1 ) )
  }

  val integral: Parser[ Polinomio ] =
    P( "∫" ~/ ( "[" ~/ numero ~/ "," ~/ numero ~/ "]" ).? ~/ parentesis ).map { x =>
      val ( lims, pol ) = x

      lims match {
        case None => pol.integral()
        case Some( ( linf, lsup ) ) => {
          val integral = pol.integral()

          Polinomio( List( Monomio( integral.eval( lsup ) - integral.eval( linf ), 0 ) ) )
        }
      }
    }

  val parentesis: Parser[ Polinomio ] = P( "(" ~/ ( poli | integral | derivada | sumaomult ) ~/ ")" )

  val sumaomult: Parser[ Polinomio ] = P( parentesis ~/ CharIn( "+*" ).! ~/ parentesis ).map { x =>
    val ( p1, op, p2 ) = x

    op match {
      case "+" => p1.sumar( p2 )
      case "*" => p1.multiplicar( p2 )
    }
  }

  val expresion = P( ( integral | derivada | poli | sumaomult | parentesis ) ~/ End ).log()

  @JSExportTopLevel( "parse" )
  def parse( expr: String ) = {
    expresion.parse(
      expr
        .replaceAll( "\\s", "" )
        .replaceAll( "\\((\\d)", "(+$1" )
        .replaceAll( "\\(x", "(+x" )
    )
  }

}
