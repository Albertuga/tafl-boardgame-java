package tafl.control.historial;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Timeout.ThreadMode.SEPARATE_THREAD;

import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import tafl.control.Arbitro;
import tafl.modelo.Tablero;
import tafl.util.Color;

/**
 * Comprobación del uso del historial aplicado a un arbitro para retroceder
 * jugadas.
 * 
 * Depende de una correcta implementación de {@link tafl.control.Historial}.
 * 
 * @author <a href="rmartico@ubu.es">Raúl Marticorena</a>
 * @since 1.0
 * @version 1.0
 * @see tafl.control.Historial
 */
@DisplayName("Tests del Arbitro usando el Historial para retroceder.")
@Timeout(value = 2, unit = TimeUnit.SECONDS, threadMode = SEPARATE_THREAD) // Time out global para todos los tests
																			// salvoF los de ciclo de vida
public abstract class ArbitroAbstractoConHistorialTest {

	/** Arbitro para testing. */
	protected Arbitro arbitro;

	/**
	 * Comprueba situación inicial tras retroceder.
	 */
	@Test
	@DisplayName("Comprobar estado inicial básico sin una jugada y tras retroceder.")
	void comprobarEstadoInicialUsandoHistorial() {
		// given
		Tablero tablero = arbitro.consultarTablero(); // tablero sin movimiento alguno
		// when
		arbitro.retroceder(); // NO debería tener ningún efecto porque no hay ninguna jugada realizada
		// Then
		assertAll(
				() -> assertThat("Número de jugadas incorrecto tras retroceder sin haber realizado jugada alguna.",
						arbitro.consultarNumeroJugada(), is(0)),
				() -> assertThat("El turno debería seguir siendo el mismo al inicial, porque no se ha podido retroceder.",
						arbitro.consultarTurno(), is(Color.NEGRO)),
				() -> assertThat(
						"El tablero actual debería coincidir con el inicial, dado que no se han realizado movimientos.",
						arbitro.consultarTablero(), is(tablero)));
	}
}