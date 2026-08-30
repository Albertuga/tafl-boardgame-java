package tafl.control.historial;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Timeout.ThreadMode.SEPARATE_THREAD;
import static tafl.control.TestUtil.fabricarJugada;

import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import tafl.control.Historial;
import tafl.control.Registro;
import tafl.excepcion.CoordenadasIncorrectasException;
import tafl.modelo.Jugada;
import tafl.modelo.Pieza;
import tafl.modelo.Tablero;
import tafl.util.Coordenada;
import tafl.util.TipoPieza;


/**
 * Comprobación de historial de registros.
 * 
 * Depende de una correcta implementación de Tablero y de sus métodos de comparación (equasl, hashCode, etc.), junto
 * con las clases de {@link tafl.util} y {@link tafl.modelo}.
 * 
 * @author <a href="rmartico@ubu.es">Raúl Marticorena</a>
 * @since 1.0
 * @version 1.0
 * @see tafl.util
 * @see tafl.modelo
 */
@DisplayName("Tests del Historial de registros.")
@Timeout(value = 2, unit = TimeUnit.SECONDS, threadMode = SEPARATE_THREAD) // Time out global para todos los tests salvo los de ciclo de vida
public class HistorialTest {

	/** Historial de testing. */
	private Historial historial;

	/** Generación del historial para testing. */
	@BeforeEach
	void inicializar() {
		historial = new Historial();		
	}
	
	/**
	 * Comprueba que el historial inicialmente no contiene registros.
	 */
	@Test
	@DisplayName("Comprobar estado inicial básico sin tablero.")
	void comprobarEstadoInicialSinTablero() {
		assertThat("Número de tableros incorrecto.", historial.consultarNumeroRegistros(), is(0));
	}
	
	/**
	 * Comprueba que el historial inicialmente no retorna ningún registro.
	 */
	@Test
	@DisplayName("Comprobar estado inicial básico no retornando registro.")
	void comprobarEstadoInicialNoRetornaTablero() {
		assertNull(historial.extraerUltimoRegistro(), "Inicialmente debería retornar un nulo.");
	}
	
	/**
	 * Comprueba que el historial almacena un registro.
	 */
	@Test
	@DisplayName("Comprobar almacenamiento de un registro.")
	void comprobarAlmacenamientoDeUnTablero() {
		// given
		Tablero tablero = new Tablero();
		Jugada jugada = fabricarJugada(tablero, 3, 6, 2, 6);
		Registro ultimoRegistro = new Registro(tablero, jugada);
		// when
		historial.añadirUltimoRegistro(ultimoRegistro);
		// then
		assertAll(
				()-> assertThat("Número de tableros incorrecto.", historial.consultarNumeroRegistros(), is(1)),
				()-> assertThat("Registro extraído no coincide con el añadido.", historial.extraerUltimoRegistro(), is(ultimoRegistro)),
				()-> assertThat("Número de tableros incorrecto tras extraer el único tablero añadido.", historial.consultarNumeroRegistros(), is(0))
		);
	}
	
	/**
	 * Comprueba que el historial almacena dos tableros.
	 * 
	 * @throws CoordenadasIncorrectasException si hay una coordenada incorrecta
	 * @throws IllegalArgumentException si los argumentos son incorrectos o nulos
	 */
	@Test
	@DisplayName("Comprobar almacenamiento de dos registros.")
	void comprobarAlmacenamientoDeDosTablerosConJugada() throws IllegalArgumentException, CoordenadasIncorrectasException {
		// given dos registros sin relación con una partida real
		Tablero tablero1 = new Tablero();
		tablero1.colocar(new Pieza(TipoPieza.ATACANTE), new Coordenada(3, 6));
		Jugada jugada1 = fabricarJugada(tablero1, 3, 6, 2, 6);
		Registro registro1 = new Registro(tablero1, jugada1);
		
		Tablero tablero2 = new Tablero();
		tablero2.colocar(new Pieza(TipoPieza.DEFENSOR), new Coordenada(3,4));
		Jugada jugada2 = fabricarJugada(tablero2, 3, 4, 3, 5);
		Registro registro2 = new Registro(tablero2, jugada2);

		// when
		historial.añadirUltimoRegistro(registro1);
		historial.añadirUltimoRegistro(registro2);
		// then
		assertAll(
				()-> assertThat("Número de registros incorrecto.", historial.consultarNumeroRegistros(), is(2)),
				()-> assertThat("Primer registro extraído no coincide con el último añadido.", historial.extraerUltimoRegistro(), is(registro2)),
				()-> assertThat("Solo debería quedar un registro.",  historial.consultarNumeroRegistros(), is(1)),
				()-> assertThat("Segundo registro extraído no coincide con el penúltimo añadido.", historial.extraerUltimoRegistro(), is(registro1)),
				()-> assertThat("No deberían quedar registros una vez extráidos todos.",  historial.consultarNumeroRegistros(), is(0))
		);
	}
	
	
	/**
	 * Comprueba que el historial almacena tres registros.
	 * 
	 * @throws CoordenadasIncorrectasException si hay una coordenada incorrecta
	 * @throws IllegalArgumentException si los argumentos son incorrectos o nulos
	 */
	@Test
	@DisplayName("Comprobar almacenamiento de tres registros.")
	void comprobarAlmacenamientoDeTresTableros() throws IllegalArgumentException, CoordenadasIncorrectasException {
		// given tres tableros con una pieza colocada
		Tablero tablero1 = new Tablero();
		tablero1.colocar(new Pieza(TipoPieza.ATACANTE), new Coordenada(3,6));
		Jugada jugada1 = fabricarJugada(tablero1, 3, 6, 2, 6);
		Registro registro1 = new Registro(tablero1, jugada1);
		
		Tablero tablero2 = new Tablero();
		tablero1.colocar(new Pieza(TipoPieza.DEFENSOR), new Coordenada(3,4));
		Jugada jugada2 = fabricarJugada(tablero2, 3, 4, 3, 5);
		Registro registro2 = new Registro(tablero2, jugada2);
		
		Tablero tablero3 = new Tablero();
		tablero3.colocar(new Pieza(TipoPieza.REY), new Coordenada(3,3));
		Jugada jugada3 = fabricarJugada(tablero3, 3, 3, 4, 3);
		Registro registro3 = new Registro(tablero3, jugada3);
		
		// when
		historial.añadirUltimoRegistro(registro1);
		historial.añadirUltimoRegistro(registro2);
		historial.añadirUltimoRegistro(registro3);
		// then
		assertAll(
				()-> assertThat("Número de registros añadidos inicialmente incorrecto.", historial.consultarNumeroRegistros(), is(3)),
				()-> assertThat("Primer registro extraído no coincide con el último añadido.", historial.extraerUltimoRegistro(), is(registro3)),
				()-> assertThat("Solo deberían quedar dos registros.",  historial.consultarNumeroRegistros(), is(2)),
				()-> assertThat("Segundo registro extraído no coincide con el penúltimo añadido.", historial.extraerUltimoRegistro(), is(registro2)),
				()-> assertThat("Solo debería quedar un registro.",  historial.consultarNumeroRegistros(), is(1)),
				()-> assertThat("Tercer registro extraído no coincide con el primero añadido.", historial.extraerUltimoRegistro(), is(registro1)),
				()-> assertThat("No deberían quedar registros una vez extraidos todos.",  historial.consultarNumeroRegistros(), is(0)),
				()-> assertNull(historial.extraerUltimoRegistro(), "Debería retornar un nulo, si no quedan registros en el historial.")
		);
	}
	
	
	/**
	 * Comprueba que añadir un registro nulo lanza excepción.
	 * 
	 * @see tafl.control.Historial#añadirUltimoRegistro(Registro)
	 */
	@DisplayName("Comprueba que añadir un registro nulo lanza la excepción adecuada.")
	@Test
	void comprobarLanzamientoExcepcionConRegistroNulo() {
		// given
		Registro registro = null;
		// when
		// then
		assertThrows(IllegalArgumentException.class, () -> historial.añadirUltimoRegistro(registro), "Con registro nulo.");
	}
	
	/**
	 * Comprueba que añadir un registro con jugada nula lanza excepción.
	 * 
	 * @see tafl.control.Historial#añadirUltimoRegistro(Registro)
	 */
	@DisplayName("Comprueba que añadir un registro con jugada nula lanza la excepción adecuada.")
	@Test
	void comprobarLanzamientoExcepcionConJugadaNulaEnRegistro() {
		// given
		Tablero tablero = new Tablero();
		Registro registro = new Registro(tablero, null);
		// when
		// then
		assertThrows(IllegalArgumentException.class, () -> historial.añadirUltimoRegistro(registro), "Con jugada nula.");
	}
	
	/**
	 * Comprueba que añadir un registro con tablero nulo lanza excepción.
	 * 
	 * @see tafl.control.Historial#añadirUltimoRegistro(Registro)
	 */
	@DisplayName("Comprueba que añadir un registro con tablero nulo lanza la excepción adecuada.")
	@Test
	void comprobarLanzamientoExcepcionConTableroNuloEnRegistro() {
		// given
		Tablero tablero = new Tablero();
		Jugada jugada = fabricarJugada(tablero, 0, 0, 1, 1);
		Registro registro = new Registro(null, jugada);
		// when
		// then
		assertThrows(IllegalArgumentException.class, () -> historial.añadirUltimoRegistro(registro), "Con tablero nulo.");
	}

}