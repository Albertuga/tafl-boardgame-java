package tafl.util;

/**
 * NOMBRE: Sentido (enumerado)
 * 
 * @author Alberto Rafael Muñoz Moreno Fecha 26/12/2023 FUNCIÓN: indica la
 *         direccion y cantidad de movimiento que realizaran las piezas
 */

public enum Sentido {
	/** Sentido NORTE */
	VERTICAL_N(-1, 0),
	/** Sentido SUR */
	VERTICAL_S(+1, 0),
	/** Sentido ESTE */
	HORIZONTAL_E(0, +1),
	/** Sentido OESTE */
	HORIZONTAL_O(0, -1);

	/**
	 * desplazamientoEnFilas contiene el numero de desplazamiento de filas a
	 * realizar
	 */
	private int desplazamientoEnFilas;
	/**
	 * desplazamientoEnColumnas contiene el numero de desplazamiento de columnas a
	 * realizar
	 */
	private int desplazamientoEnColumnas;

	/**
	 * Sentido Funcion publica que asigna el desplazamiento a las variables
	 * iniciadas
	 * 
	 * @param desplazamientoEnFilas    contiene los desplazamientos a realizar en
	 *                                 las filas
	 * @param desplazamientoEnColumnas contiene los desplazamientos a realizar en
	 *                                 las columnas
	 */
	private Sentido(int desplazamientoEnFilas, int desplazamientoEnColumnas) {
		this.desplazamientoEnFilas = desplazamientoEnFilas;
		this.desplazamientoEnColumnas = desplazamientoEnColumnas;
	}

	/**
	 * consultarDesplazamientoEnFilas
	 * 
	 * Funcion publica de tipo entero que nos regresa en formato de numeros enteros
	 * la cantidad de desplazamiento en filas
	 * 
	 * @return desplazamientoEnFilas variable que contendra la cantidad entera de
	 *         desplazamientos
	 */

	public int consultarDesplazamientoEnFilas() {
		return desplazamientoEnFilas;
	}

	/**
	 * consultarDesplazamientoEnColumnas
	 * 
	 * Funcion publica de tipo entero que nos regresa en formato de numeros enteros
	 * la cantidad de desplazamiento de las columnas
	 * 
	 * @return desplazamientoEnColumnas variable que contendra la cantidad entera de
	 *         desplazamientos
	 */

	public int consultarDesplazamientoEnColumnas() {
		return desplazamientoEnColumnas;
	}
}
