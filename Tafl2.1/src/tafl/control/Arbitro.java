package tafl.control;

import tafl.excepcion.CoordenadasIncorrectasException;
import tafl.modelo.Jugada;
import tafl.modelo.Tablero;
import tafl.util.Color;
import tafl.util.TipoPieza;

/**
 * INTERFASE Arbitro Define las reglas para la gestion de las partidas. Controla
 * el turno, gestiona el tablero y verifica la legalidad de los movimientos. de
 * cada jugada, determinando un ganador y permitiendo el retroceso de las
 * jugadas.
 * 
 * Un arbitro es el responsable de coordinar las jugadas.
 * 
 * @author Alberto Rafael Muñoz Moreno
 * @version 2.2 Fecha 22/01/2024
 * 
 */

public interface Arbitro {

	/**
	 * cambiarTurno cambiar el turno al otro contricante
	 */
	public void cambiarTurno();

	/**
	 * colocarPiezas coloca las piezas iniciales en el tablero
	 * 
	 * @param tipo        Especifica el tipo de pieza seleccionado
	 * @param coordenadas Arreglo que contiene las coordenadas iniciales
	 * @param turnoActual Color del jugador que teine el turno de la jugada.
	 * @throws CoordenadasIncorrectasException En caso de que las coordenadas dadas
	 *                                         sean incorrectas
	 */
	public void colocarPiezas(TipoPieza[] tipo, int[][] coordenadas, Color turnoActual)
			throws CoordenadasIncorrectasException;

	/**
	 * colocarPiezasConfiguracionInicial coloca las piezas dependiendo de la
	 * configuracion inicial
	 */
	public void colocarPiezasConfiguracionInicial();

	/**
	 * consultarNumeroJugada consulta el numero de la jugada actual
	 * 
	 * @return numero de la jugada actual
	 */
	public int consultarNumeroJugada();

	/**
	 * consultarTablero consulta el estado actual del tablero
	 * 
	 * @return el tablero actual.
	 */
	public Tablero consultarTablero();

	/**
	 * consultarTurno consulta el color del jugador que tiene el turno actual
	 * 
	 * @return el color del jugador actual.
	 */
	public Color consultarTurno();

	/**
	 * esMovimientoLegal Verifica si la jugada cumple con las normas del juego para
	 * ser un movimiento legal y validarlo
	 * 
	 * @param jugada Argumento que contiene la informacion de la jugada a evaluar
	 * @return false en caso de que el movimiento no sea legal, true en caso
	 *         contrario
	 * @throws CoordenadasIncorrectasException En caso de que las coordenadas sean
	 *                                         incorrectas
	 */
	public boolean esMovimientoLegal(Jugada jugada) throws CoordenadasIncorrectasException;

	/**
	 * haGanadoAtacante Verifica si la victoria es del atacante
	 * 
	 * @throws CoordenadasIncorrectasException En caso de que las coordenadas sean
	 *                                         incorrectas
	 * @return true en caso de que hayan ganado, false en caso que aun no hayan
	 *         ganado
	 */
	public boolean haGanadoAtacante() throws CoordenadasIncorrectasException;

	/**
	 * haGanadoRey Verifica si la victoria es del rey
	 * 
	 * @throws CoordenadasIncorrectasException En caso de que las coordenadas sean
	 *                                         incorrectas
	 * @return true en caso de los atacantes hayan ganado la partida, false en caso
	 *         de que aun no hayan ganado
	 */
	public boolean haGanadoRey() throws CoordenadasIncorrectasException;

	/**
	 * mover Permite el movimiento de las piezas sobre el tablero de la jugada
	 * especificada
	 * 
	 * @param jugada contiene la Informacion de la jugada a realizar
	 * @throws CoordenadasIncorrectasException En caso de que las coordenadas sean
	 *                                         incorrectas
	 */
	public void mover(Jugada jugada) throws CoordenadasIncorrectasException;

	/**
	 * realizarCapturaTrasMover Realiza la/las captura/capturas de la/las
	 * pieza/piezas a partir de la ultima jugada
	 * 
	 * @throws CoordenadasIncorrectasException En caso de que las coordenadas sean
	 *                                         incorrectas
	 */
	public void realizarCapturasTrasMover() throws CoordenadasIncorrectasException;

	/**
	 * retroceder Retrocede una jugada de la partida, deshaciendo la ultima accion
	 * hecha
	 * 
	 */
	public void retroceder();

}
