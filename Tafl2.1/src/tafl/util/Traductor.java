package tafl.util;

/**
 * 
 * Traductor Clase que se encarga de la traduccion de las jugadas que entran por
 * teclado en mandatos para realizar las jugadas sobre el tablero
 * @author Alberto Rafael Muñoz Moreno FECHA 26/10/2023 
 */
public class Traductor {

	/**
	 * Traductor constructor predeterminado del traductor no realiza funcion.
	 */
	public Traductor() {
	}

	/**
	 * consultarCoordenadaParaNotacionAlgebraica Convierte una cadena en notación
	 * algebraica.
	 *
	 * @param texto La cadena de texto en notación algebraica que se desea
	 *              convertir. Debe tener un formato correcto
	 * @return Una nueva instancia de Coordenada correspondiente a la posición
	 *         indicada en la notación algebraica, null en caso de que las
	 *         coordenadas no sean correctas
	 */
	public static Coordenada consultarCoordenadaParaNotacionAlgebraica(String texto) {
		if (esTextoCorrectoParaCoordenada(texto)) {
			char caracterFila = texto.charAt(1);
			char caracterColumna = texto.charAt(0);
			// Formato cfcf
			// verificamos que las coordenadas esten en el rango adecuado
			if (caracterFila >= '1' && caracterFila <= '7' && caracterColumna >= 'a' && caracterColumna <= 'g') {
				int fila = '7' - caracterFila;
				int columna = caracterColumna - 'a';
				return new Coordenada(fila, columna);
			}
		}
		return null;
	}

	/**
	 * consultarTextoEnNotacionAlgebraica Convierte una coordenada en su
	 * representación de texto en notación algebraica.
	 *
	 * @param coordenada ubicacion exacta de la coordenada que debe convertir a
	 *                   texto
	 * @return Una cadena en notación algebraica que representa la coordenada, o
	 *         null si la coordenada proporcionada está fuera del rango válido.
	 * 
	 */
	public static String consultarTextoEnNotacionAlgebraica(Coordenada coordenada) {
		int fila = coordenada.fila();
		int columna = coordenada.columna();

		if (fila >= 0 && fila <= 6 && columna >= 0 && columna <= 6) {
			return String.valueOf((char) ('a' + columna)) + (7 - fila);
		} else {
			return null;
		}
	}

	/**
	 * esTextoCorrectoParaCoordenada Tiene como objetivo validar si la cadena de
	 * texto de entrada es correctamente una coordenada valida del tablero
	 * 
	 * @param texto cadena tipo string que contiene los caracteres de la coordenada
	 *              de la pieza en el tablero.
	 * @return true en caso de que el texto sea acorde para ser coordenada y no sea
	 *         nulo False en caso contrario
	 */
	public static boolean esTextoCorrectoParaCoordenada(String texto) {
		if (texto != null && texto.length() == 2) {
			char fila = texto.charAt(1);
			char columna = texto.charAt(0);
			if (fila >= '1' && fila <= '7' && columna >= 'a' && columna <= 'g') {
				return true;
			}
		}
		return false;
	}
}
