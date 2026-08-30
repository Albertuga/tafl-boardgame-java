package tafl.util;

/**
 * NOMBRE: TipoPieza
 * 
 * @author Alberto Rafael Muñoz Moreno FECHA 26/10/2023 
 * FUNCIÓN; definir el tipo de piezas que existen en el juego asignandole
 *  un caracter y color que los diferencie. 
 *  D para el defensor blanco 
 *  A para el atacante (las Piezas de color negro) 
 *  R para definir al rey de color blanco.
 */

public enum TipoPieza {
	/** Tipo de pieza DEFENSOR, D, color BLANCO */
	DEFENSOR('D', Color.BLANCO),
	/** Tipo de Pieza ATACANTE, A, color NEGRO */
	ATACANTE('A', Color.NEGRO),
	/** Tipo de Pieza REU, R, color BLANCO */
	REY('R', Color.BLANCO);

	/**
	 * caracter: variable privada de tipo char que contendrá el caracter que defina
	 * el tipo de pieza
	 */
	private char caracter;
	/** color: variable privada que contiene el valor del color de la pieza. */
	private Color color;

	/**
	 * TipoPieza
	 * 
	 * Funcion privada que contiene las variables color y caracter
	 * 
	 * @param caracter contiene el caracter que distingue la pieza
	 * @param color    contiene el valor de color para dicha pieza
	 */
	private TipoPieza(char caracter, Color color) {
		this.caracter = caracter;
		this.color = color;
	}

	/**
	 * consultarColor Funcion de tipo color que nos retorna el color de la pieza
	 * actual
	 * 
	 * @return color variable que contendra el color de la pieza actual
	 */
	public Color consultarColor() {
		return this.color;
	}

	/**
	 * ToChar
	 * 
	 * Funcion publica que nos dice el caracter de la pieza actual.
	 * 
	 * @return caracter. variable que contendra la letra que caracteriza esa pieza.
	 */
	public char toChar() {
		return this.caracter;
	}
}
