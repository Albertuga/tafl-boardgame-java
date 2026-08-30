package tafl.modelo;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import tafl.excepcion.CoordenadasIncorrectasException;
import tafl.util.Coordenada;
import tafl.util.TipoCelda;
import tafl.util.TipoPieza;

/**
 * Tablero Inicializa el tablero para el juego, contiene la matriz de celdas
 * donde se colocaran las piezas de cada jugada. Tambien incluye metodos para la
 * consulta de celdas y otras posiciones del tablero
 * 
 * @author Alberto Rafael Muñoz fecha 2/01/2024
 * @version 2.2
 */
public class Tablero {
	/**
	 * Lista de listas de celdas que contiene todas las celdas que conforman el
	 * tablero
	 */
	List<List<Celda>> tablero;

	/**
	 * Tablero. Funcion: Inicializar la matriz de celdas y conforma el tablero
	 *
	 */

	public Tablero() {
		tablero = new ArrayList<>();
		for (int fila = 0; fila < 7; fila++) {
			List<Celda> filas = new ArrayList<>();
			for (int columna = 0; columna < 7; columna++) {
				// Declaramos provincias, trono y tipo normales
				if ((fila == 0 && columna == 0) || (fila == 6 && columna == 6) || (fila == 0 && columna == 6)
						|| (fila == 6 && columna == 0)) {
					filas.add(new Celda(new Coordenada(fila, columna), TipoCelda.PROVINCIA));
				} else if (fila == 3 && columna == 3) {
					filas.add(new Celda(new Coordenada(fila, columna), TipoCelda.TRONO));
				} else {
					filas.add(new Celda(new Coordenada(fila, columna), TipoCelda.NORMAL));
				}
			}
			tablero.add(filas);
		}
	}

	/**
	 * aTexto Funcion: devuelve el estado del tablero con las piezas actualmente
	 * colocadas en formato cadena de caracteres, para mostrar en pantalla.
	 * 
	 * @return Cadena de texto con el estado del tablero actual.
	 */
	public String aTexto() {
		StringBuilder textoTablero = new StringBuilder();

		// Bucle para recorrer las filas del tablero
		for (int i = 0; i < 7; i++) {
			textoTablero.append((7 - i)).append(" "); // Número de fila en orden descendente

			// Bucle para recorrer las columnas del tablero
			for (int j = 0; j < 7; j++) {
				textoTablero.append(" "); // Espacio entre columnas

				Pieza pieza = tablero.get(i).get(j).consultarPieza();
				char tipo = (pieza == null) ? '-' : pieza.consultarTipoPieza().toChar(); // si la pieza es nula colocar
																							// -
																							// si no, consultar el tipo
																							// de pieza
				textoTablero.append(tipo);
			}
			textoTablero.append("\n");
		}
		// agregamos las letras de las columnas
		textoTablero.append("   a b c d e f g\n");

		return textoTablero.toString();
	}

	/**
	 * clonar Funcion: devuelve un clon en profundidad del tablero actual.
	 * 
	 * @return tableroClon devuelve un clon en profundidad del tablero actual.
	 * 
	 */
	public Tablero clonar() {

		Tablero tableroClon = new Tablero();
		for (int fila = 0; fila < tablero.size(); fila++) {
			for (int columna = 0; columna < tablero.get(0).size(); columna++) {
				if (!this.tablero.get(fila).get(columna).estaVacia()) {
					tableroClon.tablero.get(fila).get(columna)
							.colocar(this.tablero.get(fila).get(columna).consultarPieza());
				}
			}
		}
		return tableroClon;
	}

	/**
	 * colocar Funcion: Coloca la pieza en la coordenada indicada
	 * 
	 * @param pieza      Pieza a colocar.
	 * @param coordenada Coordenada de la celda donde se colocara la celda
	 * @throws CoordenadasIncorrectasException la coordenada esta fuera del tablero.
	 * @throws IllegalArgumentException        la coordenada es nula.
	 */
	public void colocar(Pieza pieza, Coordenada coordenada) throws CoordenadasIncorrectasException {
		if (pieza == null || coordenada == null) {
			throw new IllegalArgumentException();
		}
		if (!estaEnTablero(coordenada)) {
			throw new CoordenadasIncorrectasException();
		}
		if (coordenada.fila() >= 0 && coordenada.fila() < tablero.size() && coordenada.columna() >= 0
				&& coordenada.columna() < tablero.get(0).size()) {

			int fila = coordenada.fila();
			int columna = coordenada.columna();

			this.tablero.get(fila).get(columna).colocar(pieza);
		}
	}

	/**
	 * consultarCelda FUNCION: devuelve un clon en profundidad de la celda con las
	 * coordenadas indicadas
	 * 
	 * @param coordenada Coordenadas de la celda a consultar
	 * @return clon en profundidad de la celda.
	 * @throws CoordenadasIncorrectasException la coordenada esta fuera del tablero.
	 * @throws IllegalArgumentException        la coordenada es nula.
	 */
	public Celda consultarCelda(Coordenada coordenada) throws CoordenadasIncorrectasException {
		if (coordenada == null) {
			throw new IllegalArgumentException();
		}
		int fila = coordenada.fila();
		int columna = coordenada.columna();
		if (fila >= 0 && fila < tablero.size() && columna >= 0 && columna < tablero.size()) {
			return this.tablero.get(coordenada.fila()).get(coordenada.columna()).clonar();
		} else {
			throw new CoordenadasIncorrectasException();
		}
	}

	/**
	 * consultarCeldas Funcion: devuelve una lista de celdas con clones en
	 * profundidad de todas las celdas del tablero, recorriendo las celdas de de
	 * izquierda a derecha y de arriba hacia abajo.
	 *
	 * @return Lista de celdas clonadas en profundidad.
	 */
	public List<Celda> consultarCeldas() {
		List<Celda> listaCeldas = new ArrayList<>();
		for (int fila = 0; fila < tablero.size(); fila++) {
			for (int columna = 0; columna < tablero.get(0).size(); columna++) {
				Celda celdaReal = tablero.get(fila).get(columna);
				Celda celdaClon = celdaReal.clonar();
				listaCeldas.add(celdaClon);
			}
		}
		return listaCeldas;
	}

	/**
	 * consultarCeldasContiguas funcion; devuelve una lista de celdas con clones en
	 * profundidad de todas las celdas contiguas a la coordenada dada.
	 *
	 * @param coordenada Coordenada a partir de la cual se calculan las celdas
	 *                   adyacentes
	 * @return celdasContiguas lista que contiene las coordenadas de las celdas
	 *         adyacentes/contiguas
	 * @throws CoordenadasIncorrectasException la coordenada esta fuera del tablero.
	 * @throws IllegalArgumentException        la coordenada es nula.
	 * 
	 */
	public List<Celda> consultarCeldasContiguas(Coordenada coordenada) throws CoordenadasIncorrectasException {
		if (coordenada == null) {
			throw new IllegalArgumentException();
		}
		if (!estaEnTablero(coordenada)) {
			throw new CoordenadasIncorrectasException();
		}

		List<Celda> celdasContiguas = new ArrayList<>(); // y las almacenamos en una nueva lista

		int fila = coordenada.fila();
		int columna = coordenada.columna();
		// Verificamos en cada direccion arriba, abajo izquierda, derecha
		// Verificamos la celda de arriba
		if (fila - 1 >= 0) { // Verificamos si la celda contigua se encuentra dentro del tablero
			celdasContiguas.add(tablero.get(fila - 1).get(columna).clonar());
		} // En caso de que se encuentre, se retorna la ubicacion de esta celda clonada.

		// Verificamos la celda de abajo
		if (fila + 1 < tablero.size()) {// Verificamos si la celda contigua se encuentra dentro del tablero
			celdasContiguas.add(tablero.get(fila + 1).get(columna).clonar());
		}

		// Verificamos celda izquierda
		if (columna - 1 >= 0) {// Verificamos si la celda contigua se encuentra dentro del tablero
			celdasContiguas.add(tablero.get(fila).get(columna - 1).clonar());
		}

		// Verificamos celda derecha
		if (columna + 1 < tablero.get(0).size()) {// Verificamos si la celda contigua se encuentra dentro del tablero
			celdasContiguas.add(tablero.get(fila).get(columna + 1).clonar());
		}
		return celdasContiguas;
	}

	/**
	 * consultarCeldasContiguasEnVertical funcion; devuelve una lista de celdas con
	 * clones en profundidad de todas las celdas contiguas a la coordenada dada solo
	 * en vertical
	 *
	 * @param coordenada Coordenada a partir de la cual se calculan las celdas
	 *                   adyacentes en vertical
	 * @return celdasContiguas lista que contiene las coordenadas de las celdas
	 *         adyacentes/contiguas en vertical
	 * @throws CoordenadasIncorrectasException la coordenada esta fuera del tablero.
	 * @throws IllegalArgumentException        la coordenada es nula.
	 * 
	 */

	public List<Celda> consultarCeldasContiguasEnVertical(Coordenada coordenada)
			throws CoordenadasIncorrectasException {
		if (coordenada == null) {
			throw new IllegalArgumentException();
		}
		if (!estaEnTablero(coordenada)) {
			throw new CoordenadasIncorrectasException();
		}

		List<Celda> celdasContiguas = new ArrayList<>();
		int[] desplazamientos = { -1, 1 }; // Desplazamientos hacia arriba y hacia abajo

		for (int desplazamiento : desplazamientos) {
			int filaVecina = coordenada.fila() + desplazamiento;

			// Verifica si la celda vecina está dentro de los límites del tablero
			if (filaVecina >= 0 && filaVecina < tablero.size()) {
				celdasContiguas.add(this.tablero.get(filaVecina).get(coordenada.columna()).clonar());
			}
		}

		return celdasContiguas;
	}

	/**
	 * consultarCeldasContiguasEnHorizontal funcion; devuelve una lista de celdas
	 * con clones en profundidad de todas las celdas contiguas a la coordenada dada
	 * solo en horizontal.
	 *
	 * @param coordenada Coordenada a partir de la cual se calculan las celdas
	 *                   adyacentes en horizontal
	 * @return celdasContiguas lista que contiene las coordenadas de las celdas
	 *         adyacentes/contiguas en horizontal
	 * @throws CoordenadasIncorrectasException la coordenada esta fuera del tablero.
	 * @throws IllegalArgumentException        la coordenada es nula.
	 * 
	 */

	public List<Celda> consultarCeldasContiguasEnHorizontal(Coordenada coordenada)
			throws CoordenadasIncorrectasException {
		if (coordenada == null) {
			throw new IllegalArgumentException();
		}
		if (!estaEnTablero(coordenada)) {
			throw new CoordenadasIncorrectasException();
		}

		List<Celda> celdasContiguas = new ArrayList<>();
		int[] desplazamientos = { -1, 1 }; // Desplazamientos hacia la izquierda y la derecha

		for (int desplazamiento : desplazamientos) {
			int columnaVecina = coordenada.columna() + desplazamiento;

			// Verifica si la columna vecina está dentro de los límites del tablero
			if (columnaVecina >= 0 && columnaVecina < tablero.size()) {
				celdasContiguas.add(this.tablero.get(coordenada.fila()).get(columnaVecina).clonar());
			}
		}

		return celdasContiguas;
	}

	/**
	 * consultarNumeroColumnas Funcion; retornar el numero de columnas total del
	 * tablero
	 * 
	 * @return tamaño de las columnas del tablero
	 */
	public int consultarNumeroColumnas() {
		return tablero.get(0).size();
	}

	/**
	 * consultarNumeroFilas Funcion; retornar el numero de filas total del tablero
	 * 
	 * @return tamaño de las filas del tablero
	 */
	public int consultarNumeroFilas() {
		return tablero.size();
	}

	/**
	 * consultarNumeroPiezas Funcion: Retorna el numero total de piezas de un tipo
	 * especifico
	 * 
	 * @param tipoPieza, el tipo especifico de la pieza a tomar en cuenta
	 * @return numPieza: retorna el numero de piezas encontradas en el tablero
	 * @throws IllegalArgumentException Si el tipo de pieza es nulo
	 */
	public int consultarNumeroPiezas(TipoPieza tipoPieza) {
		if (tipoPieza == null) {
			throw new IllegalArgumentException();
		}
		int numPieza = 0; // Contador
		// recorremos todas las celdas del tablero
		for (int fila = 0; fila < tablero.size(); fila++) {
			for (int columna = 0; columna < tablero.get(0).size(); columna++) {
				// Cogemos la ubicacion de la celda en el tablero
				Celda celda = tablero.get(fila).get(columna);
				Pieza pieza = celda.consultarPieza(); // en caso de que tenga pieza, la obtendremos

				if (pieza != null && pieza.consultarTipoPieza() == tipoPieza) {
					numPieza++; // en caso de que exista la pieza, aumentar el contador
				}
			}
		}
		return numPieza;
	}

	/**
	 * eliminarPieza FUNCION: elimina la pieza de la celda con coordenada indicada
	 * 
	 * @param coordenada de la celda del tablero que contiene la pieza a eliminar
	 * 
	 * @throws CoordenadasIncorrectasException la coordenada esta fuera del tablero.
	 * @throws IllegalArgumentException        la coordenada es nula.
	 */
	public void eliminarPieza(Coordenada coordenada) throws CoordenadasIncorrectasException {
		if (coordenada == null) {
			throw new IllegalArgumentException();
		}
		int fila = coordenada.fila();
		int columna = coordenada.columna();

		if (fila < 0 || fila >= tablero.size() || columna < 0 || columna >= tablero.get(0).size()) {
			throw new CoordenadasIncorrectasException();
		}

		Pieza pieza = tablero.get(fila).get(columna).consultarPieza();
		if (pieza != null) {
			tablero.get(fila).get(columna).eliminarPieza();
		}
	}

	/**
	 * obtenerCelda Funcion: retorna la referencia de la celda de una coordenada
	 * especifica
	 * 
	 * @param coordenada de la celda
	 * @return retorna la celda especifica a las coordenadas proporcionadas
	 * @throws IllegalArgumentException        Si la coordenada es nula.
	 * @throws CoordenadasIncorrectasException Si la coordenada se encuentra fuera
	 *                                         del tablero.
	 *
	 */
	public Celda obtenerCelda(Coordenada coordenada) throws CoordenadasIncorrectasException {
		if (coordenada == null) {
			throw new IllegalArgumentException();
		}
		int fila = coordenada.fila();
		int columna = coordenada.columna();

		if (fila < 0 || fila >= tablero.size() || columna < 0 || columna >= tablero.get(0).size()) {
			throw new CoordenadasIncorrectasException();
		}
		return tablero.get(fila).get(columna);
	}

	/**
	 * estaEnTablero Funcion: comprueba si la coordenada está dentro de los límites
	 * del tablero.
	 * 
	 * @param coordenada de la celda introducida
	 * @return true: si la coordenada esta dentro del tablero false: en caso
	 *         contrario
	 * @throws IllegalArgumentException Si la coordenada es nula.
	 * 
	 */
	public boolean estaEnTablero(Coordenada coordenada) {
		if (coordenada == null) {
			throw new IllegalArgumentException();
		}
		int fila = coordenada.fila();
		int columna = coordenada.columna();
		if (fila < 0 || fila >= tablero.size() || columna < 0 || columna >= tablero.size()) {
			return false;
		} else {
			return true;
		}
	}

	// hashcode
	@Override
	public int hashCode() {
		return Objects.hash(tablero);
	}

	// equals
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Tablero other = (Tablero) obj;
		return Objects.equals(tablero, other.tablero);
	}

	// toString
	@Override
	public String toString() {
		return "Tablero [tablero=" + tablero + "]";
	}

}
