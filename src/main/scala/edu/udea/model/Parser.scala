package edu.udea.model

import edu.udea.model.polinomio.{Monomio, Polinomio}
import fastparse.utils.Utils

import scala.scalajs.js
import scala.scalajs.js.annotation.{JSExportTopLevel}

/**
  * Created by roger on 4/2/17.
  */
object PolinomioParser {

  import fastparse.all._

  val entero: Parser[ Double ] = P( CharIn( '0' to '9' ).rep( 1 ).!.map( v => v.toDouble ) )
  val decimal: Parser[ Double ] = P( entero ~ P( "." ) ~/ entero ).!.map( v => v.toDouble )
  val numero: Parser[ Double ] = P( signo.? ~ ( decimal | entero ) ).map { x =>
    val ( s, n ) = x
    s.getOrElse( 1.0 ) * n
  }

  val signo: Parser[ Double ] = P( "+" | "-" ).!.map( v =>
    if ( v == "+" ) 1.0
    else -1.0
  )
  val x = P( "x" )

  val monomio: Parser[ Monomio ] = P( signo ~/ ( entero | decimal ).? ~/ ( x ~/ entero.? ).? ).map { x =>
    val ( signo, coeficiente, e ) = x
    val exponente = e.map( e => e.getOrElse( 1.0 ) ).getOrElse( 0.0 )

    Monomio( signo * coeficiente.getOrElse( 1.0 ), exponente )
  }

  val poliVacio: Parser[ Polinomio ] = P( Start ~ End ).map { _ =>
    Polinomio( js.Array(  ) )
  }

  val poli: Parser[ Polinomio ] = P( monomio.rep( 1 ) ).map { x =>
      // val ( m, ms ) = x

    val m = x.foldLeft( js.Array[ Monomio ]() )( ( acc, m ) => {
      acc.push( m )
      acc
    } )
    Polinomio( m )
  }

  val derivada: Parser[ Polinomio ] = P( "d" ~/ entero.? ~/ "x" ~/ parentesis ).map { x =>
    val ( n, polinomio ) = x
    polinomio.derivada( n.map( _.intValue() ).getOrElse( 1 ) )
  }

  val integral: Parser[ Polinomio ] =
    P( ( "∫" | "int" ) ~/ ( "[" ~/ numero ~/ "," ~/ numero ~/ "]" ).? ~/ parentesis ).map { x =>
      val ( lims, pol ) = x

      lims match {
        case None => pol.integral()
        case Some( ( linf, lsup ) ) => {
          val integral = pol.integral()

          Polinomio( js.Array( Monomio( integral.eval( lsup ) - integral.eval( linf ), 0 ) ) )
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

  val expresion = P( ( poliVacio | integral | derivada | poli | sumaomult | parentesis ) ~/ End )

  @JSExportTopLevel( "parse" )
  def parse( expr: String ) = {
    val x = expr
      .replaceAll( "\\s", "" )
      .replaceAll( "\\((\\d)", "(+$1" )
      .replaceAll( "\\(x", "(+x" )
      .replaceAll( "^x", "+x" )
      .replaceAll( "^(\\d)", "+$1" )

    expresion.parse(
      x
    ).fold[Any](
      ( lastParser, index, extra ) =>  {
        val pretty = Utils.literalize( extra.input.slice( index, index + 15)).toString

        s"Sintáxis inválida parseando $x; cerca del índice $index; se encontró $pretty; se esperaba ${lastParser.toString()}"
      },
      ( x, i ) => x
    )
  }

}
