package tafl.util;

/**
 * NOMBRE: TipoCelda
 * 
 * @author Alberto Rafael Muñoz Moreno FECHA 26/12/2023 
 * FUNCIÓN; definir el tipo de celdas que existen en el juego. 
 * TRONO para el lugar del rey, se ubica en el centro del tablero 
 * PROVINCIA se ubica a las esquinas del tablero, es donde debe 
 * 			llegar el rey para ganar una partida 
 * NORMAL son el resto de celdas que pueden ser ocupadas por defensores,
 * 		 rey y atacantes.
 */

public enum TipoCelda {
	/** Inicializamos la Celda tipo Trono */
	TRONO,
	/** Inicializamos las celdas tipo Provincia */
	PROVINCIA,
	/** Inicializamos las celdas normales */
	NORMAL;
}
