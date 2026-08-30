package tafl.modelo;

import java.util.Objects;

import tafl.util.Color;
import tafl.util.Coordenada;
import tafl.util.TipoCelda;

/**
 * Clase Celda Representa las celdas del juego, tomando en cuenta sus
 * coordenadas tipos de celda y emplea metodos para consultar el tipo de pieza
 * en caso de que alguna ficha este posada sobre ellas o incluso para saber si
 * estaVacia.
 * 
 * Tambien se encarga de la eliminacion de las piezas del tablero que son
 * capturadas
 * 
 * @author Alberto Rafael Muñoz Moreno
 * @version 2.2 FECHA 22/01/2024
 */

public class Celda {
	/** coordenada de la celda */
	private Coordenada coordenada;
	/** tipo de la celda */
	private TipoCelda tipoCelda;
	/** Informacion de la pieza que esta en la celda */
	private Pieza pieza;

	/**
	 * Celda Funcion: crea una celda de tipo normal a partir de la coordenada dada
	 * 
	 * @param coordenada Pasamos por parametro Coordenadas.
	 * 
	 */
	public Celda(Coordenada coordenada) {
		this(coordenada, TipoCelda.NORMAL);
	}

	/**
	 * Celda Funcion da formato a la celda otorgando coordenada y tipo de celda a
	 * demas asigna el valor nulo para el tipo de pieza el cual se asignara en otros
	 * metodos de ser necesario.
	 * 
	 * @param coordenada pasamos por parametro las coordendas
	 * @param tipoCelda  pasamos por parametro el tipo de celda
	 */
	public Celda(Coordenada coordenada, TipoCelda tipoCelda) {
		this.coordenada = coordenada;
		this.tipoCelda = tipoCelda;
	}

	/**
	 * clonar Funcion: metodo de tipo celda que permite crear un DeepClone de la
	 * celda si hay una pieza en la celda, tambien se clona
	 * 
	 * @return clonProfundidad: clon de la celda con sus propiedades
	 * 
	 */
	public Celda clonar() {
		Celda clonProfundidad = new Celda(coordenada, tipoCelda);
		// Creamos la celda clonada
		// en caso de que haya una pieza en la celda tambien debe estar clonada
		if (pieza != null) {
			clonProfundidad.pieza = pieza.clonar();
		}
		return clonProfundidad;
	}

	/**
	 * colocar Funcion: coloca la pieza en la celda
	 * 
	 * @param pieza: es el tipo de pieza seleccionado para colocar en la celda
	 * 
	 */
	public void colocar(Pieza pieza) {
		this.pieza = pieza;
	}

	/**
	 * consultarColorDePieza Funcion: devolver el color de la pieza que se ubica en
	 * la celda
	 * 
	 * @return pieza.consultarColor(); regresa el color de la pieza, null si la
	 *         celda estaba vacia
	 * 
	 */
	public Color consultarColorDePieza() {
		if (pieza != null) {
			return pieza.consultarColor();
		} else {
			return null;
		}
	}

	/**
	 * consultarCoordenada Funcion: consulta la coordenada con la misma fila y
	 * columna de esa celda
	 * 
	 * @return new Coordenada de la celda solicitada
	 */
	public Coordenada consultarCoordenada() {
		return new Coordenada(this.coordenada.fila(), this.coordenada.columna());
	}

	/**
	 * consultarPieza Funcion: saner cual es la pieza que esta sobre la celda
	 * 
	 * @return pieza: devuelve la pieza actual sobre la celda
	 */
	public Pieza consultarPieza() {
		return this.pieza;
	}

	/**
	 * consultarTipoCelda Funcion: consultar el tipo de celda seleccionada
	 * 
	 * @return tipoCelda puede ser provincia, normal o trono
	 */
	public TipoCelda consultarTipoCelda() {
		return this.tipoCelda;
	}

	/**
	 * eliminarPieza Funcion eliminar la piezaseleccionada
	 * 
	 */
	public void eliminarPieza() {
		this.pieza = null;
	}

	/**
	 * estaVacia Funcion comprobar si una celda esta vacia o contiene una pieza
	 * 
	 * @return true si esta vacio, false en caso contrario
	 */
	public boolean estaVacia() {
		if (pieza == null) {
			return true;
		}
		return false;
	}

	// Equals, Hashcode, toString
	@Override
	public int hashCode() {
		return Objects.hash(coordenada, pieza, tipoCelda);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Celda other = (Celda) obj;
		return Objects.equals(coordenada, other.coordenada) && Objects.equals(pieza, other.pieza)
				&& tipoCelda == other.tipoCelda;
	}

	@Override
	public String toString() {
		return "Celda [coordenada=" + coordenada + ", tipoCelda=" + tipoCelda + ", pieza=" + pieza + "]";
	}

}
