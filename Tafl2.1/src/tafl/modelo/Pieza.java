package tafl.modelo;

import java.util.Objects;

import tafl.util.Color;
import tafl.util.TipoPieza;

/**
 * clase Pieza Especifica el color, tipo y especifica el turno del jugador a
 * partir de la pieza. Representa una pieza del juego.
 * 
 * @author Alberto Rafael Muñoz FECHA 2/01/2024
 * @version 2.2
 */

public class Pieza {
	/** Informacion sobre el tipo de pieza seleccionado */
	private TipoPieza tipoPieza;

	/**
	 * Pieza construye una pieza del tipo especificado
	 * 
	 * @param tipoPieza El tipo de la pieza (ATACANTE, REY o DEFENSOR).
	 */
	public Pieza(TipoPieza tipoPieza) {
		this.tipoPieza = tipoPieza;
	}

	/**
	 * clonar construye y retorna un clon de la pieza seleccionada
	 * 
	 * @return instancia de la pieza clonada
	 */
	public Pieza clonar() {
		return new Pieza(this.tipoPieza);
	}

	/**
	 * cosultarColor consulta el color asociado a la pieza
	 * 
	 * @return el color de la pieza (NEGRO o BLANCO)
	 * 
	 */
	public Color consultarColor() {
		if (TipoPieza.ATACANTE == tipoPieza) {
			return Color.NEGRO;
		} else {
			return Color.BLANCO;
		}
	}

	/**
	 * consultarTipoPieza consulta el tipo de pieza
	 * 
	 * @return el tipo de pieza (REY, ATACANTE, DEFENSOR)
	 * 
	 */
	public TipoPieza consultarTipoPieza() {
		return this.tipoPieza;
	}

	@Override
	public int hashCode() {
		return Objects.hash(tipoPieza);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pieza other = (Pieza) obj;
		return tipoPieza == other.tipoPieza;
	}

	@Override
	public String toString() {
		return "Pieza [tipoPieza=" + tipoPieza + "]";
	}

}
