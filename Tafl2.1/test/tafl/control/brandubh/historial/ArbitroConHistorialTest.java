package tafl.control.brandubh.historial;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Timeout.ThreadMode.SEPARATE_THREAD;
import static tafl.control.TestUtil.fabricarJugada;

import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import tafl.control.ArbitroBrandubh;
import tafl.control.historial.ArbitroAbstractoConHistorialTest;
import tafl.excepcion.CoordenadasIncorrectasException;
import tafl.modelo.Tablero;
import tafl.util.Color;
import tafl.util.TipoPieza;

/**
 * Comprobación del uso del historial aplicado a un arbitro Brandubh para retroceder
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
																			// salvo los de ciclo de vida
public class ArbitroConHistorialTest extends ArbitroAbstractoConHistorialTest {

	/**
	 * Inicializa con todas las piezas colocadas.
	 */
	// @formatter:off
	/* Rellenaremos el tablero tal y como se muestra:	
	 * 7 - - - A - - -  
	 * 6 - - - A - - - 
	 * 5 - - - D - - - 
	 * 4 A A D R D A A 
	 * 3 - - - D - - -
	 * 2 - - - A - - - 
	 * 1 - - - A - - -
	 *   a b c d e f g   
	 */
	 // @formatter:on
	@BeforeEach
	void inicializar() {
		arbitro = new ArbitroBrandubh(new Tablero());
		arbitro.colocarPiezasConfiguracionInicial();
	}


	/**
	 * Comprueba el estado inicial básico al retroceder una jugada realizada.
	 * 
	 * @throws RuntimeException si hay un error grave en el código
	 * @throws CoordenadasIncorrectasException si hay una coordenada incorrecta
	 * @throws IllegalArgumentException si los argumentos son incorrectos o nulos
	 */
	@Test
	@DisplayName("Comprobar estado inicial básico al retroceder una jugada realizada.")
	void comprobarRealizarUnaJugadaYRetroceder() throws IllegalArgumentException, CoordenadasIncorrectasException, RuntimeException {
		// given
		Tablero tablero = arbitro.consultarTablero(); // tablero sin movimiento alguno
		// when
		arbitro.mover(fabricarJugada(tablero, 3, 6, 1, 6)); // realiza un primer movimiento
		arbitro.realizarCapturasTrasMover();
		arbitro.cambiarTurno();
		arbitro.retroceder();
		// Then
		assertAll(
				() -> assertThat("Número de jugadas incorrecto tras retroceder la única jugada realizada.",
						arbitro.consultarNumeroJugada(), is(0)),
				() -> assertThat("El turno debería volver al inicial, al retroceder la única jugada realizada.",
						arbitro.consultarTurno(), is(Color.NEGRO)),
				() -> assertThat("El tablero actual debería coincidir con el inicial sin haber realizado movimientos.",
						arbitro.consultarTablero(), is(tablero)));
	}

	/**
	 * Comprueba el estado al retroceder dos jugadas.
	 * 
	 * @throws RuntimeException si hay un error grave en el código
	 * @throws CoordenadasIncorrectasException si hay una coordenada incorrecta
	 * @throws IllegalArgumentException si los argumentos son incorrectos o nulos
	 */
	@Test
	@DisplayName("Comprobar estado al retroceder dos jugadas.")
	void comprobarRealizarDosJugadasYRetroceder() throws IllegalArgumentException, CoordenadasIncorrectasException, RuntimeException {
		// given
		Tablero tablero0 = arbitro.consultarTablero(); // tablero sin movimiento alguno
		// when
		arbitro.mover(fabricarJugada(tablero0, 3, 6, 1, 6)); // primera jugada
		arbitro.cambiarTurno();
		Tablero tablero1 = arbitro.consultarTablero();
		arbitro.mover(fabricarJugada(tablero1, 2, 3, 2, 0)); // segunda jugada
		arbitro.cambiarTurno();
		// El tablero debería tener este estado con turno de negras...
		// @formatter:off
		/* Rellenaremos el tablero tal y como se muestra:	
		 * 7 - - - A - - -  
		 * 6 - - - A - - A 
		 * 5 D - - - - - - 
		 * 4 A A D R D A - 
		 * 3 - - - D - - -
		 * 2 - - - A - - - 
		 * 1 - - - A - - -
		 *   a b c d e f g   
		 */
		 // @formatter:on
		// Then
		assertAll(
				() -> assertThat(
						"Número de jugadas incorrecto ante de retroceder.", arbitro.consultarNumeroJugada(), is(2)),
				() -> assertThat("El turno es incorrecto.",
						arbitro.consultarTurno(), is(Color.NEGRO)),
				() -> arbitro.retroceder(),
				() -> assertThat("Número de jugadas incorrecto tras retroceder la segunda jugada.",
						arbitro.consultarNumeroJugada(), is(1)),
				() -> assertThat("El turno es incorrecto tras retroceder la segunda jugada.",
						arbitro.consultarTurno(), is(Color.BLANCO)),
				() -> assertThat("Tablero incorrecto tras retroceder la segunda jugada.", arbitro.consultarTablero(),
						is(tablero1)),
				() -> arbitro.retroceder(),
				() -> assertThat("El turno es incorrecto tras retroceder la primera jugada.",
						arbitro.consultarTurno(), is(Color.NEGRO)),
				() -> assertThat("Número de jugadas incorrecto tras retroceder la primera jugada.",
						arbitro.consultarNumeroJugada(), is(0)),
				() -> assertThat("Tablero incorrecto tras retroceder la primera jugada.", arbitro.consultarTablero(),
						is(tablero0)));
	}

	/**
	 * Comprueba el estado al retroceder con varias jugadas con una captura entre medias contra provincia.
	 * 
	 * @throws RuntimeException si hay un error grave en el código
	 * @throws CoordenadasIncorrectasException si hay una coordenada incorrecta
	 * @throws IllegalArgumentException si los argumentos son incorrectos o nulos
	 */
	@Test
	@DisplayName("Comprobar estado al retroceder con varias jugadas con una captura entre medias con provincia.")
	void comprobarRealizarVariasJugadasUnaConCapturaEntreMediasContraProvinciaYRetroceder() throws IllegalArgumentException, CoordenadasIncorrectasException, RuntimeException {
		// given
		Tablero tablero0 = arbitro.consultarTablero(); // tablero sin movimiento alguno
		// when
		arbitro.mover(fabricarJugada(tablero0, 3, 6, 1, 6)); // jugada 1
		arbitro.realizarCapturasTrasMover();
		arbitro.cambiarTurno();
		Tablero tablero1 = arbitro.consultarTablero();
		// El tablero1 debería tener este estado...
		// @formatter:off
		/* Rellenaremos el tablero tal y como se muestra:	
		 * 7 - - - A - - -  
		 * 6 - - - A - - A 
		 * 5 - - - - - -  
		 * 4 A A D R D A - 
		 * 3 - - - D - - -
		 * 2 - - - A - - - 
		 * 1 - - - A - - -
		 *   a b c d e f g   
		 */
		 // @formatter:on
		arbitro.mover(fabricarJugada(tablero1, 2, 3, 2, 6)); // jugada 2 captura atacante contra provincia
		arbitro.realizarCapturasTrasMover();
		arbitro.cambiarTurno();
		Tablero tablero2 = arbitro.consultarTablero();
		// El tablero2 debería tener este estado...
		// @formatter:off
		/* Rellenaremos el tablero tal y como se muestra:	
		 * 7 - - - A - - -  
		 * 6 - - - A - - - 
		 * 5 - - - - - - D 
		 * 4 A A D R D A - 
		 * 3 - - - D - - -
		 * 2 - - - A - - - 
		 * 1 - - - A - - -
		 *   a b c d e f g   
		 */
		 // @formatter:on		
		arbitro.mover(fabricarJugada(tablero2, 3, 5, 3, 6)); // jugada 3
		arbitro.realizarCapturasTrasMover(); 
		arbitro.cambiarTurno();
		// El tablero debería tener este estado tras la captura...
		// @formatter:off
		/* Rellenaremos el tablero tal y como se muestra:	
		 * 7 - - - A - - -  
		 * 6 - - - A - - - 
		 * 5 - - - - - - D 
		 * 4 A A D R D - A 
		 * 3 - - - D - - -
		 * 2 - - - A - - - 
		 * 1 - - - A - - -
		 *   a b c d e f g   
		 */
		// Then
		assertAll(
		() -> assertThat("Número de jugadas incorrecto antes de retroceder ninguna jugada.", arbitro.consultarNumeroJugada(), is(3)),
		() -> assertThat("Número de piezas atacantes incorrecto antes de retroceder ninguna jugada.", arbitro.consultarTablero().consultarNumeroPiezas(TipoPieza.ATACANTE),is(7)),
		() -> assertThat("Número de piezas defensoras incorrecto antes de retroceder ninguna jugada.", arbitro.consultarTablero().consultarNumeroPiezas(TipoPieza.DEFENSOR),is(4)),
		() -> arbitro.retroceder(),
		() -> assertThat("Número de piezas atacantes incorrecto tras retroceder la tercera jugada.", arbitro.consultarTablero().consultarNumeroPiezas(TipoPieza.ATACANTE),is(7)),
		() -> assertThat("Número de piezas defensoras incorrecto tras retroceder la tercera jugada.", arbitro.consultarTablero().consultarNumeroPiezas(TipoPieza.DEFENSOR),is(4)),
		() -> assertThat("Número de jugadas incorrecto tras retroceder la tercera jugada.", arbitro.consultarNumeroJugada(), is(2)),
		() -> assertThat("Tablero incorrecto tras retroceder la tercer jugada.", arbitro.consultarTablero(), is(tablero2)),
		() -> arbitro.retroceder(),
		() -> assertThat("Número de piezas atacantes incorrecto tras retroceder la segunda jugada.", arbitro.consultarTablero().consultarNumeroPiezas(TipoPieza.ATACANTE),is(8)),
		() -> assertThat("Número de piezas defensoras incorrecto tras retroceder la segunda jugada.", arbitro.consultarTablero().consultarNumeroPiezas(TipoPieza.DEFENSOR),is(4)),
		() -> assertThat("Número de jugadas incorrecto tras retroceder la segunda jugada.", arbitro.consultarNumeroJugada(), is(1)),
		() -> assertThat("Tablero incorrecto tras retroceder la segunda jugada.", arbitro.consultarTablero(), is(tablero1)),
		() -> arbitro.retroceder(),
		() -> assertThat("Número de piezas atacantes incorrecto en la partida al inicio.", arbitro.consultarTablero().consultarNumeroPiezas(TipoPieza.ATACANTE),is(8)),
		() -> assertThat("Número de piezas defensoras incorrecto en la partida al inicio.", arbitro.consultarTablero().consultarNumeroPiezas(TipoPieza.DEFENSOR),is(4)),
		() -> assertThat("Número de jugadas incorrecto tras retroceder la segunda jugada.", arbitro.consultarNumeroJugada(), is(0)),
		() -> assertThat("Tablero incorrecto tras retroceder la primera jugada.", arbitro.consultarTablero(), is(tablero0))
		);
	}
	
	
	/**
	 * Comprueba el estado al retroceder del tablero en formato texto con varias jugadas con una captura contra provincia.
	 * 
	 * @throws RuntimeException si hay un error grave en el código
	 * @throws CoordenadasIncorrectasException si hay una coordenada incorrecta
	 * @throws IllegalArgumentException si los argumentos son incorrectos o nulos
	 */
	@Test
	@DisplayName("Comprobar estado al retroceder con varias jugadas con una captura contra provincia.")
	void comprobarRealizarVariasJugadasUnaConCapturaContraProvinciaYRetrocederConFormatoTexto() throws IllegalArgumentException, CoordenadasIncorrectasException, RuntimeException {
		// given
		Tablero tablero0 = arbitro.consultarTablero(); // tablero sin movimiento alguno
		// when
		arbitro.mover(fabricarJugada(tablero0, 3, 6, 1, 6)); // jugada 1
		arbitro.realizarCapturasTrasMover();
		arbitro.cambiarTurno();
		Tablero tablero1 = arbitro.consultarTablero();
		// El tablero1 debería tener este estado...
		// @formatter:off
		/* Rellenaremos el tablero tal y como se muestra:	
		 * 7 - - - A - - -  
		 * 6 - - - A - - A 
		 * 5 - - - D - - - 
		 * 4 A A D R D A - 
		 * 3 - - - D - - -
		 * 2 - - - A - - - 
		 * 1 - - - A - - -
		 *   a b c d e f g   
		 */
		 // @formatter:on
		arbitro.mover(fabricarJugada(tablero1, 2, 3, 2, 6)); // jugada 2 realiza captura contra provincia
		arbitro.realizarCapturasTrasMover();
		arbitro.cambiarTurno();
		// El tablero debería tener este estado...
		// @formatter:off
		/* Rellenaremos el tablero tal y como se muestra:	
		 * 7 - - - A - - -  
		 * 6 - - - A - - - 
		 * 5 - - - - - - D 
		 * 4 A A D R D A - 
		 * 3 - - - D - - -
		 * 2 - - - A - - - 
		 * 1 - - - A - - -
		 *   a b c d e f g   
		 */
		 // @formatter:on	
		
		// when
		arbitro.retroceder();
		// then
		Tablero tableroRetrocedido = arbitro.consultarTablero();
		// eliminamos espacios/tabuladores para comparar
		String salida = tableroRetrocedido.aTexto().replaceAll("\\s", "");
		// Then		
		String cadenaEsperada = """
								7 - - - A - - -
								6 - - - A - - A
								5 - - - D - - -
								4 A A D R D A -
								3 - - - D - - -
								2 - - - A - - -
								1 - - - A - - -
						  		  a b c d e f g""";
		cadenaEsperada = cadenaEsperada.replaceAll("\\s", "");
		assertEquals(cadenaEsperada, salida, "La cadena de texto generada para el tablero retrocedido no es correcta.");
	}
	
	
	
	/**
	 * Comprueba que tras retroceder vuelve a capturar correctamente en relación a la última jugada.
	 * 
	 * @throws RuntimeException si hay un error grave en el código
	 * @throws CoordenadasIncorrectasException si hay una coordenada incorrecta
	 * @throws IllegalArgumentException si los argumentos son incorrectos o nulos
	 */
	@Test
	@DisplayName("Comprobar que tras retroceder vuelve a capturar correctamente en relación a la última jugada.")
	void comprobarCapturarTrasRetroceder() throws IllegalArgumentException, CoordenadasIncorrectasException, RuntimeException {
		// given
		Tablero tablero0 = arbitro.consultarTablero(); // tablero sin movimiento alguno
		// when
		arbitro.mover(fabricarJugada(tablero0, 3, 6, 1, 6)); // jugada 1
		arbitro.realizarCapturasTrasMover();
		arbitro.cambiarTurno();
		Tablero tablero1 = arbitro.consultarTablero();
		// El tablero1 debería tener este estado...
		// @formatter:off
		/* Rellenaremos el tablero tal y como se muestra:	
		 * 7 - - - A - - -  
		 * 6 - - - A - - A 
		 * 5 - - - D - - - 
		 * 4 A A D R D A - 
		 * 3 - - - D - - -
		 * 2 - - - A - - - 
		 * 1 - - - A - - -
		 *   a b c d e f g   
		 */
		 // @formatter:on
		arbitro.mover(fabricarJugada(tablero1, 2, 3, 2, 6)); // jugada 2 realiza captura contra provincia
		arbitro.realizarCapturasTrasMover();
		arbitro.cambiarTurno();
		// El tablero debería tener este estado...
		// @formatter:off
		/* Rellenaremos el tablero tal y como se muestra:	
		 * 7 - - - A - - -  
		 * 6 - - - A - - - 
		 * 5 - - - - - - D 
		 * 4 A A D R D A - 
		 * 3 - - - D - - -
		 * 2 - - - A - - - 
		 * 1 - - - A - - -
		 *   a b c d e f g   
		 */
		 // @formatter:on	
		
		// when
		arbitro.retroceder();		
		// El tablero debería tener este estado nuevamente...
		// @formatter:off
		/* Rellenaremos el tablero tal y como se muestra:	
		 * 7 - - - A - - -  
		 * 6 - - - A - - A 
		 * 5 - - - D - - - 
		 * 4 A A D R D A - 
		 * 3 - - - D - - -
		 * 2 - - - A - - - 
		 * 1 - - - A - - -
		 *   a b c d e f g   
		 */
		 // @formatter:on
		
		// se vuelve a repetir la captura
		arbitro.mover(fabricarJugada(tablero1, 2, 3, 2, 6)); // jugada 2 repetida nuevamente realiza captura contra provincia
		arbitro.realizarCapturasTrasMover();
		arbitro.cambiarTurno();
		// El tablero debería tener este estado...
		// @formatter:off
		/* Rellenaremos el tablero tal y como se muestra:	
		 * 7 - - - A - - -  
		 * 6 - - - A - - -
		 * 5 - - - - - - D 
		 * 4 A A D R D A - 
		 * 3 - - - D - - -
		 * 2 - - - A - - - 
		 * 1 - - - A - - -
		 *   a b c d e f g   
		 */
		 // @formatter:on
				
		// then
		Tablero tableroRetrocedido = arbitro.consultarTablero();
		// eliminamos espacios/tabuladores para comparar
		String salida = tableroRetrocedido.aTexto().replaceAll("\\s", "");
		// Then		
		String cadenaEsperada = """
								7 - - - A - - -
								6 - - - A - - -
								5 - - - - - - D
								4 A A D R D A -
								3 - - - D - - -
								2 - - - A - - -
								1 - - - A - - -
						  		  a b c d e f g""";
		cadenaEsperada = cadenaEsperada.replaceAll("\\s", "");
		assertEquals(cadenaEsperada, salida, "La cadena de texto generada para el tablero tras volver a capturar tras retroceder no es correcta.");
	}
}