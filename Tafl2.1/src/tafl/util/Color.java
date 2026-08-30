package tafl.util;

/**
 * NOMBRE: Color (enumerado)
 * 
 * @author Alberto Rafael Muñoz Moreno FECHA 26/10/2023 
 * Funcion; definir los colores de las piezas y asignar caracteres para identificar las 
 * piezas. N para las piezas atacantes (negro) B para las piezas
 * blancas. R es el rey que pertenecera al equipo de las piezas negras.
 */

public enum Color {
	/** BLANCO */
	BLANCO('B'),
	/** NEGRO */
	NEGRO('N');

	/**
	 * letra: variable privada que contiene la letra que indica el color de la pieza
	 */
	private char letra;

	/**
	 * Color Funcion privada que se encarga de enumerar el color.
	 * 
	 * @param letra Variable que contendra el caracter del color determinado.
	 */
	private Color(char letra) {
		this.letra = letra;
	}

	/**
	 * consultarContrario Funcion: muestra el color contrario del color de fichas
	 * actual.
	 * 
	 * @return NEGRO: en caso de que se controlen las piezas blancas. BLANCO: si se
	 *         controlan las piezas negras.
	 */
	public Color consultarContrario() { // Consultar el color contrario al actual
		if (this == BLANCO) {
			return NEGRO;
		} else {
			return BLANCO;
		}
	}

	/**
	 * Funcion publica que nos regresa la letra del color actual.
	 * 
	 * @return N para el negro B para el blanco.
	 */
	public char toChar() {
		return letra;
	}
}
