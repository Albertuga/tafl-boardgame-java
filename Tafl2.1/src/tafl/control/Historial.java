package tafl.control;

import java.util.ArrayList;
import java.util.List;

/**
 * Historial Clase que representa el historial de registros de las jugadas
 * 
 * @author Alberto Rafael Muñoz Moreno
 * @version 2.2 Fecha 22/01/2024
 */
public class Historial {
	/** Lista que contiene el registro de las jugadas que se realizan */
	private List<Registro> registros;

	/**
	 * constructor que inicializa el historial vacio
	 */
	public Historial() {
		this.registros = new ArrayList<>();
	}

	/**
	 * añadirUltimoRegistro Añade el ultimo registro a la lista de registros donde
	 * se van almacenando las jugadas
	 * 
	 * @param registro, añade el registro al historial pero este no puede ser nulo
	 *                  porque lanza excepciones
	 * @throws IllegalArgumentException Si es nulo el registro, Tablero nulo, Jugada
	 *                                  nula
	 */
	public void añadirUltimoRegistro(Registro registro) {
		if (registro == null || registro.tablero() == null || registro.jugada() == null) {
			throw new IllegalArgumentException("El valor del registro no puede ser nulo. ");
		}
		registros.add(registro);
	}

	/**
	 * consultarNumeroRegistros Consulta el numero total de jugadas registradas
	 * 
	 * @return numero total de registros
	 */
	public int consultarNumeroRegistros() {
		return registros.size();
	}

	/**
	 * extraerUltimoRegistro Extrae el ultimo registro del historial
	 * 
	 * @return ultimo registro del historial null si el historial esta vacio.
	 */
	public Registro extraerUltimoRegistro() {
		if (registros.size() > 0) {
			Registro ultimoRegistro = registros.get(registros.size() - 1);

			registros.remove(registros.size() - 1);
			return ultimoRegistro;
		} else {
	
			return null;
		}
	}
}
