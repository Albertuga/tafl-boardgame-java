package tafl.excepcion;

import java.lang.Exception;

/** TipoArbitroException
 * gestiona las excepciones especificas de errores relacionados con el tipo de arbitro del juego
 * durante la seleccion/ejecucion del mismo
 *  
 *  @author Alberto Rafael Muñoz Moreno
 *  @version 2.2 
 *  FECHA 22/01/2024
 * */

public class TipoArbitroException extends Exception {
	/** Autogenerado por eclipse */
	private static final long serialVersionUID = 1L;

	/**
	 * inicializacion de la excepcion, sin mensajes de detalle para lanzar
	 * excepciones
	 */
	public TipoArbitroException() {
	}

	/**
	 * TipoArbitroException constructor que incluye el mensaje de la excepcion
	 * 
	 * @param message con el error descriptivo
	 * 
	 */
	public TipoArbitroException(String message) {
		super(message); // LLamada al constructor de la superclase
	}

	/**
	 * TipoArbitroException Constructor que incluye el trowable de la excepcion
	 * (contiene la causa que provoca el error)
	 * 
	 * @param cause Causa que genera el error
	 */
	public TipoArbitroException(Throwable cause) {
		super(cause);// LLamada al constructor de la superclase
	}

	/**
	 * TipoArbitroException constructor que incluye el mensaje de la excepcion
	 * Constructor que incluye el trowable de la excepcion (contiene la causa que
	 * provoca el error)
	 * 
	 * @param message con el error descriptivo
	 * @param cause   Causa que genera el error
	 */
	public TipoArbitroException(String message, Throwable cause) {
		super(message, cause); // LLamada al constructor de la superclase
	}
}
