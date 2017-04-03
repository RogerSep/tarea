import React from "react"
import styles from "./Main.scss"
import Rx from "rx"
import { XYPlot, XAxis, YAxis, HorizontalGridLines, LineSeries } from 'react-vis';

const parse = window.exports.parse

function isNumeric(n) {
  return !isNaN(parseFloat(n)) && isFinite(n);
}

export default class Main extends React.Component {

  constructor( props ) {
    super( props )

    this.state = {
      expresion: "",
      polinomio: [],
      error: "",
      x: 0,
      resultado: null
    }
  }

  componentWillMount() {
    this.subject = new Rx.Subject()

    this.subject
      .debounce( 500 )
      .map( e => {
        const parsed = parse( e )

        return parsed
      } )
      .subscribe( e => {
        const obj = ( ( typeof e === "string" ) ?
          { error: e, polinomio: [] } :
          { error: "",
            polinomio: e.monomios
              .map( m => ( { exponente: m.exponente, coeficiente: m.coeficiente } ) ),
            resultado: e.eval( this.state.x )
          }
        )

        if ( typeof e !== "string" ) {
          this.polinomio = e
        }

        this.setState( old => Object.assign( {}, old, obj ) )
      } )

  }

  actualizarPolinomio = ( e ) => {

    this.subject.onNext( e )
    this.setState( old => ( {
      ...old,
      expresion: e
    } ) )

  }

  evaluar = ( e ) => {

    if ( isNumeric( e ) ) {
      const n = parseFloat( e )
      const resultado = this.polinomio.eval( n )

      this.setState( old => ( {
        ...old,
        x: n,
        resultado: resultado
      } ) )
    }

  }

  renderMonomio( m, i ) {
    let coeficiente
    if ( m.exponente === 0 ) {
      coeficiente = m.coeficiente
    } else if ( m.coeficiente === 1 ) {
      coeficiente = ""
    } else if ( m.coeficiente === -1 ) {
      coeficiente = "-"
    } else {
      coeficiente = m.coeficiente
    }

    if ( m.coeficiente > 0 && i > 0) {
      coeficiente = `+${coeficiente}`
    }

    return (
      <div className={ styles.monomio } key={ i } >
        <span>{ coeficiente }</span>
        <span>{ m.coeficiente !== 0 && m.exponente !== 0 ? 'x' : null }</span>
        <sup>{ m.exponente !== 0 && m.exponente !== 1 ? m.exponente : null }</sup>
      </div>
    );
  }

  render() {
    return (
      <div className={ styles.main }>
        <input onChange={ e => this.actualizarPolinomio( e.target.value ) }
          value={ this.state.expresion }
          placeholder="Escribe aquí tu polinomio"
          className={ styles.cajaPolinomio }
          type="text"/>

        { this.state.polinomio.length > 0 &&
          <div className={ styles.resultado }>
            <h2>Resultado</h2>
            <div className={ styles.monomios }>
              <div>f( x ) = </div>
              { this.state.polinomio.map( this.renderMonomio ) }
            </div>
            <div className={ styles.evalBox }>
              <span>f(</span>
              <input value={ this.state.x } onChange={ e => this.evaluar( e.target.value ) } type="text"/>
              <span>) = { this.state.resultado }</span>
              <div>
                { this.state.resultado === 0 && `( x - ( ${this.state.x} ) ) es factor del polinomio` }
              </div>
              <div>
                <XYPlot
                  width={ 300 }
                  height={ 300 }>
                  <HorizontalGridLines />
                  <LineSeries
                    data={[
                      {x: -10},
                      {x: -9},
                      {x: -8},
                      {x: -7},
                      {x: -6},
                      {x: -5},
                      {x: -4},
                      {x: -3},
                      {x: -2},
                      {x: -1},
                      {x: 0},
                      {x: 1},
                      {x: 2},
                      {x: 3},
                      {x: 4},
                      {x: 5},
                      {x: 6},
                      {x: 7},
                      {x: 8},
                      {x: 9},
                      {x: 10},
                    ].map( i => ( { ...i, y: this.polinomio.eval( i.x ) } ) ) }/>
                  <XAxis />
                  <YAxis />
                </XYPlot>
              </div>
            </div>
          </div>
        }
        { this.state.error &&
          <pre>
            { this.state.error.split(";").map( e => <div>{ e }</div> ) }
          </pre> }
      </div>
    )
  }

}
