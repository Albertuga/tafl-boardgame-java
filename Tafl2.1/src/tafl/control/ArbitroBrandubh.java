package tafl.control;

import tafl.excepcion.CoordenadasIncorrectasException;
import tafl.modelo.Celda;
import tafl.modelo.Pieza;
import tafl.modelo.Tablero;
import tafl.util.Color;
import tafl.util.Coordenada;
import tafl.util.TipoCelda;
import tafl.util.TipoPieza;

/**
 * ArbitroBrandubh Gestionar el funcionamiento de BRANDUBH Se toma en cuenta la
 * inicializacion especifica de las piezas en el tablero, se establecen las
 * condiciones para que sea victoria del rey, especificas del juego.
 * 
 * @author Alberto Rafael Muñoz Moreno
 * @version 2.2 fecha 22/01/2024
 */
public class ArbitroBrandubh extends ArbitroAbstracto {
	/**
	 * ArbitroBrandubh Constructor de la clase Brandubh Crea una instancia de
	 * Brandubh asociada al tablero
	 * 
	 * @param tablero El tablero al que estara asociado
	 */
	public ArbitroBrandubh(Tablero tablero) {
		super(tablero); // LLamada al constructor de la clase padre
	}

	/**
	 * colocarPiezasConfiguracionInicial Coloca las piezas correspondientes a la
	 * configuración de inicio del juego con sus piezas retorna las piezas colocadas
	 * correctamente sobre el tablero e inicializa la partida dando el turno a las
	 * piezas negras. INICIALIZACION ACORDE PARA BRANDUBH
	 */
	@Override
	public void colocarPiezasConfiguracionInicial() {

		// Establecemos el turno
		this.turnoActual = Color.NEGRO;

		try {
			// colocamos el rey
			tablero.colocar(new Pieza(TipoPieza.REY), new Coordenada(3, 3));

			// colocamos los defensores
			tablero.colocar(new Pieza(TipoPieza.DEFENSOR), new Coordenada(2, 3));
			tablero.colocar(new Pieza(TipoPieza.DEFENSOR), new Coordenada(3, 2));
			tablero.colocar(new Pieza(TipoPieza.DEFENSOR), new Coordenada(4, 3));
			tablero.colocar(new Pieza(TipoPieza.DEFENSOR), new Coordenada(3, 4));

			// Colocamos los atacantes
			tablero.colocar(new Pieza(TipoPieza.ATACANTE), new Coordenada(3, 0));
			tablero.colocar(new Pieza(TipoPieza.ATACANTE), new Coordenada(3, 1));
			tablero.colocar(new Pieza(TipoPieza.ATACANTE), new Coordenada(1, 3));
			tablero.colocar(new Pieza(TipoPieza.ATACANTE), new Coordenada(0, 3));
			tablero.colocar(new Pieza(TipoPieza.ATACANTE), new Coordenada(3, 5));
			tablero.colocar(new Pieza(TipoPieza.ATACANTE), new Coordenada(3, 6));
			tablero.colocar(new Pieza(TipoPieza.ATACANTE), new Coordenada(5, 3));
			tablero.colocar(new Pieza(TipoPieza.ATACANTE), new Coordenada(6, 3));

		} catch (CoordenadasIncorrectasException e) {
			e.printStackTrace();
		}
	}

	/**
	 * haGanadoRey Verifica la victoria del rey al llegar una de las 4 provincias
	 * del tablero (BRANDUBH)
	 * 
	 * @return true si el rey ha conseguido llegar a la provicia, false caso
	 *         contrario (no ha llegado aun)
	 * @throws CoordenadasIncorrectasException Si las coordenadas del rey son
	 *                                         incorrectas.
	 */
	@Override
	public boolean haGanadoRey() throws CoordenadasIncorrectasException {

		Coordenada ubicacionRey = coordenadaRey();

		if (ubicacionRey == null && !consultarTurno().equals(Color.BLANCO)) {
			return false;
		}

		Celda celdaRey = tablero.consultarCelda(ubicacionRey);

		if (!celdaRey.estaVacia() && celdaRey.consultarPieza().consultarTipoPieza() == TipoPieza.REY
				&& celdaRey.consultarTipoCelda() == TipoCelda.PROVINCIA) {
			return true;
		}

		for (int fila = 0; fila < tablero.consultarNumeroFilas(); fila++) {
			for (int columna = 0; columna < tablero.consultarNumeroColumnas(); columna++) {
				Celda celda = tablero.consultarCelda(new Coordenada(fila, columna));
				Pieza pieza = celda.consultarPieza();

				if (pieza != null && pieza.consultarTipoPieza() == TipoPieza.REY
						&& celda.consultarTipoCelda() == TipoCelda.PROVINCIA
						&& pieza.consultarColor() == Color.BLANCO) {
					return true;
				}
			}
		}
		return false;
	}

}
