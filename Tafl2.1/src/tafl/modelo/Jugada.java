package tafl.modelo;

import tafl.util.Coordenada;
import tafl.util.Sentido;

/**
 * REGISTRO Jugada Representa las jugadas del juego, tomando en cuenta los
 * movimientos de una celda origen a una destino
 * 
 * Almacena informacion de la jugada, contiene metodos para la consulta del
 * sentido del movimiento y verificaciones si es horizontal o vertical.
 * 
 * 
 * @author Alberto Rafael Muñoz Moreno
 * @version 2.2 FECHA 22/01/2024
 * @param origen  Contiene la informacion de la celda origen donde se comienza
 *                la jugada
 * @param destino Contiene la informacion de la celda destino de la jugada
 */
public record Jugada(Celda origen, Celda destino) {

	/**
	 * consultarSentido consulta el sentido del movimiento entre la celda de origen
	 * y la celda destino
	 * 
	 * @return el sentido del movimiento (VERTICAL_S, VERTICAL_N, HORIZONTAL_O,
	 *         HORIZONTAL_E)
	 */
	public Sentido consultarSentido() {
		Coordenada coordOrigen = origen.consultarCoordenada();
		Coordenada coordDestino = destino.consultarCoordenada();

		if (coordOrigen.columna() == coordDestino.columna()) {
			if (coordOrigen.fila() < coordDestino.fila()) {
				return Sentido.VERTICAL_S;
			} else {
				return Sentido.VERTICAL_N;
			}
		} else {
			if (coordOrigen.columna() > coordDestino.columna()) {
				return Sentido.HORIZONTAL_O;
			} else {
				return Sentido.HORIZONTAL_E;
			}
		}

	}

	/**
	 * esMovimientoHorizontalOVertical
	 * 
	 * Verifica si el movimiento es horizontal o vertical.
	 * 
	 * @return true en caso de que el movimiento sea vertical o horizontal, false en
	 *         caso contrario.
	 */
	public boolean esMovimientoHorizontalOVertical() {
		if ((origen.consultarCoordenada().fila() == destino.consultarCoordenada().fila()
				&& origen.consultarCoordenada().columna() != destino.consultarCoordenada().columna())
				|| origen.consultarCoordenada().fila() != destino.consultarCoordenada().fila()
						&& origen.consultarCoordenada().columna() == destino.consultarCoordenada().columna()) {
			return true;
		}
		return false;
	}
}
