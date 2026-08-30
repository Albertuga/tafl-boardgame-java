package tafl.excepcion;

import java.lang.Exception;

/**
 * CoordenadasIncorrectasException Excepcion especifica para errores
 * relacionados con las coordenadas de las jugadas durante la ejecucion del
 * juego.
 * 
 * @author Alberto Rafael Muñoz Moreno
 * @version 2.2 FECHA 22/01/2024
 */
public class CoordenadasIncorrectasException extends Exception {
	/** Autogenerado por eclipse */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor que inicializa la excepcion
	 */
	public CoordenadasIncorrectasException() {
	}

	/**
	 * CoordenadasIncorrectasException constructor que contiene el mensaje de la
	 * excepcion
	 * 
	 * @param message Mensaje que describe el error.
	 */
	public CoordenadasIncorrectasException(String message) {
		super(message);
	}

	/**
	 * CoordenadasIncorrectasException constructor que la causa que genera la
	 * excepcion
	 * 
	 * @param cause Causa que origino la excepcion
	 */
	public CoordenadasIncorrectasException(Throwable cause) {
		super(cause);
	}

	/**
	 * CoordenadasIncorrectasException constructor que contiene el mensaje de la
	 * excepcion y la causa que genero la excepcion
	 * 
	 * @param message Mensaje que describe el error.
	 * @param cause   Causa que origino la excepcion
	 */
	public CoordenadasIncorrectasException(String message, Throwable cause) {
		super(message, cause);
	}
}
