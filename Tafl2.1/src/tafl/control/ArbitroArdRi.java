package tafl.control;

import tafl.excepcion.CoordenadasIncorrectasException;
import tafl.modelo.Celda;
import tafl.modelo.Jugada;
import tafl.modelo.Pieza;
import tafl.modelo.Tablero;
import tafl.util.Color;
import tafl.util.Coordenada;
import tafl.util.TipoCelda;
import tafl.util.TipoPieza;

/**
 * ArbitroArdri Gestionar el funcionamiento de ARDRI Se toma en cuenta la
 * inicializacion especifica de las piezas en el tablero, cambian algunos
 * movimientos legales y se establecen las condiciones para que sea victoria del
 * rey.
 * 
 * @author Alberto Rafael Muñoz Moreno
 * @version 2.2 FECHA 22/01/2024
 */
public class ArbitroArdRi extends ArbitroAbstracto {
	/**
	 * ArbitroArdRi Constructor de la clase Ardri Crea una instancia de Ardri
	 * asociada al tablero
	 * 
	 * @param tablero El tablero al que estara asociado
	 */
	public ArbitroArdRi(Tablero tablero) {
		super(tablero); // LLamada al constructor de la clase padre
	}

	/**
	 * colocarPiezasConfiguracionInicial Coloca las piezas correspondientes a la
	 * configuración de inicio del juego con sus piezas retorna las piezas colocadas
	 * correctamente sobre el tablero e inicializa la partida dando el turno a las
	 * piezas negras. INICIALIZACION ACORDE PARA ARDRI
	 */
	public void colocarPiezasConfiguracionInicial() {
		try {
			// colocamos el rey
			tablero.colocar(new Pieza(TipoPieza.REY), new Coordenada(3, 3));

			// colocamos los defensores
			tablero.colocar(new Pieza(TipoPieza.DEFENSOR), new Coordenada(2, 3));
			tablero.colocar(new Pieza(TipoPieza.DEFENSOR), new Coordenada(3, 2));
			tablero.colocar(new Pieza(TipoPieza.DEFENSOR), new Coordenada(4, 3));
			tablero.colocar(new Pieza(TipoPieza.DEFENSOR), new Coordenada(3, 4));
			tablero.colocar(new Pieza(TipoPieza.DEFENSOR), new Coordenada(2, 2));
			tablero.colocar(new Pieza(TipoPieza.DEFENSOR), new Coordenada(2, 4));
			tablero.colocar(new Pieza(TipoPieza.DEFENSOR), new Coordenada(4, 2));
			tablero.colocar(new Pieza(TipoPieza.DEFENSOR), new Coordenada(4, 4));

			// Colocamos los atacantes
			tablero.colocar(new Pieza(TipoPieza.ATACANTE), new Coordenada(3, 0));
			tablero.colocar(new Pieza(TipoPieza.ATACANTE), new Coordenada(3, 1));
			tablero.colocar(new Pieza(TipoPieza.ATACANTE), new Coordenada(1, 3));
			tablero.colocar(new Pieza(TipoPieza.ATACANTE), new Coordenada(0, 3));
			tablero.colocar(new Pieza(TipoPieza.ATACANTE), new Coordenada(3, 5));
			tablero.colocar(new Pieza(TipoPieza.ATACANTE), new Coordenada(3, 6));
			tablero.colocar(new Pieza(TipoPieza.ATACANTE), new Coordenada(5, 3));
			tablero.colocar(new Pieza(TipoPieza.ATACANTE), new Coordenada(6, 3));
			tablero.colocar(new Pieza(TipoPieza.ATACANTE), new Coordenada(0, 2));
			tablero.colocar(new Pieza(TipoPieza.ATACANTE), new Coordenada(0, 4));
			tablero.colocar(new Pieza(TipoPieza.ATACANTE), new Coordenada(2, 0));
			tablero.colocar(new Pieza(TipoPieza.ATACANTE), new Coordenada(4, 0));
			tablero.colocar(new Pieza(TipoPieza.ATACANTE), new Coordenada(2, 6));
			tablero.colocar(new Pieza(TipoPieza.ATACANTE), new Coordenada(4, 6));
			tablero.colocar(new Pieza(TipoPieza.ATACANTE), new Coordenada(6, 2));
			tablero.colocar(new Pieza(TipoPieza.ATACANTE), new Coordenada(6, 4));

		} catch (CoordenadasIncorrectasException e) {
			e.printStackTrace();
		}
		// Establecemos el turno
		this.turnoActual = Color.NEGRO;
	}

	/**
	 * esMovimientoLegal Verifica la legalidad de la jugad asegun las normas del
	 * juego El metodo aplica las reglas descritas. AGREGAMOS QUE LAS PIEZAS NO
	 * PUEDAN DESPLAZARSE A MAYOR DISTANCIA QUE SUS CELDAS ADYACENTES
	 * 
	 * @param jugada a evaluar
	 * @return True si la jugada es legal, si no false
	 * @throws CoordenadasIncorrectasException Si las coordenadas de la jugada son
	 *                                         incorrectas.
	 * @throws IllegalArgumentException        Si la jugada es nula.
	 */
	public boolean esMovimientoLegal(Jugada jugada) throws CoordenadasIncorrectasException {
		if (jugada == null) {
			throw new IllegalArgumentException();
		}
		// obtenemos las celdas de origen y destino de la jugada
		Celda celdaOrigen = jugada.origen();
		Celda celdaDestino = jugada.destino();

		// Verificamos que la celda de destino sea adyacente a la de origen (ARDRI)
		if (!esCeldaAdyacente(celdaOrigen.consultarCoordenada(), celdaDestino.consultarCoordenada())) {
			return false;
		}

		// Verificamos que la celda de origen no este vacia
		if (celdaOrigen.estaVacia()) {
			return false;
		}

		// Verificamos que la celda de destino si este vacia.
		if (!celdaDestino.estaVacia()) {
			return false;
		}

		// Si la jugada no es un movimiento horizontal o vertical entonces no se puede
		// hacer
		if (!jugada.esMovimientoHorizontalOVertical()) {
			return false;
		}

		// Verificamos que la pieza a mover le corresponda el turnoActual
		if (!celdaOrigen.consultarColorDePieza().equals(turnoActual)) {
			return false;
		}

		// La celda o jugada de destino no puede ser igual a la de origen.
		if (celdaDestino.equals(celdaOrigen)) {
			return false;
		}

		// Piezas distintas al rey no pueden estar en el trono o en provincias
		if (celdaOrigen.consultarPieza().consultarTipoPieza() != TipoPieza.REY
				&& (celdaDestino.consultarTipoCelda() == TipoCelda.PROVINCIA
						|| celdaDestino.consultarTipoCelda() == TipoCelda.TRONO)) {
			return false;
		}

		return noSaltos(jugada); // No es necesario aqui
	}

	/**
	 * haGanadoRey Verifica la victoria del rey al llegar a un extremo del tablero
	 * (ARDRI)
	 * 
	 * @return true si el rey ha conseguido llegar al extremo, false caso contrario
	 *         (no ha llegado aun)
	 * @throws CoordenadasIncorrectasException Si las coordenadas del rey son
	 *                                         incorrectas.
	 */
	@Override
	public boolean haGanadoRey() throws CoordenadasIncorrectasException {
		// obtenemos las coordenadas actuales del rey
		Coordenada ubicacionRey = coordenadaRey();
		int filaExtremo = ubicacionRey.fila();
		int columnaExtremo = ubicacionRey.columna();

		// verificacion si el rey se encuentra en alguno de los extremos del tablero
		if (filaExtremo == 0 || columnaExtremo == 0 || filaExtremo == tablero.consultarNumeroFilas() - 1
				|| columnaExtremo == tablero.consultarNumeroColumnas() - 1) {
			return true;// rey gana
		}
		return false; // rey no ha llegado
	}

}
