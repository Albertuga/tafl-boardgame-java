package tafl.control;

import java.util.ArrayList;
import java.util.List;

import tafl.excepcion.CoordenadasIncorrectasException;
import tafl.modelo.Celda;
import tafl.modelo.Jugada;
import tafl.modelo.Pieza;
import tafl.modelo.Tablero;
import tafl.util.Color;
import tafl.util.Coordenada;
import tafl.util.Sentido;
import tafl.util.TipoCelda;
import tafl.util.TipoPieza;

/**
 * ArbitroAbstracto Funcion: gestionar el funcionamiento del juego. Se encapsula
 * la logica basica para el manejo de jugadas, control del turno, y seguimiento
 * del estado actual del juego. Algunas funciones se mantienen abstractas para
 * ser implementadas detalladamente en el arbitro de cada juego
 * 
 * 
 * @author Alberto Rafael Muñoz Moreno
 * @version 2.2 Fecha 22/01/2024
 */
public abstract class ArbitroAbstracto implements Arbitro {
	/** numeroJugada: contine el numero de cada jugada */
	protected int numeroJugada;
	/** tablero: referencia hacia tablero */
	protected Tablero tablero;
	/** turnoActual: dicta quien tiene el turno en cada jugada */
	protected Color turnoActual;
	/** ultimaJugada: contiene informacion sobre la ultima jugada de tipo jugada */
	protected Jugada ultimaJugada;
	/** historial, almacen de registros de las jugadas */
	private Historial historial;
	/** trono: coordenada con la ubicacion exacta del trono en el tablero */
	private Coordenada trono = new Coordenada(3, 3);
	/**
	 * ultimoMovimiento: contiene la coordenada exacta del ultimo movimiento
	 * realizado
	 */
	protected Coordenada ultimoMovimiento;

	/**
	 * ArbitroAbstracto Constructor que inicializa el árbitro con un tablero de
	 * juego. Configura el estado inicial del juego, incluyendo el historial de
	 * jugadas y el establecimiento del turno inicial.
	 *
	 * @param tablero El tablero de juego a ser utilizado. No puede ser nulo.
	 * @throws IllegalArgumentException si el tablero proporcionado es nulo.
	 */
	public ArbitroAbstracto(Tablero tablero) {
		if (tablero == null) { // Inicializamos las excepciones
			throw new IllegalArgumentException("El tablero no puede ser nulo. ");
		}
		this.historial = new Historial();
		this.numeroJugada = 0; // Contador de jugadas a 0
		this.tablero = tablero; // inicializamos el tablero
		this.turnoActual = null; // damos el turno actual a las piezas negras
	}

	/**
	 * cambiarTurno Cambia el turno del contricante si el turno es null, cede el
	 * turno a las piezas negras
	 */
	public void cambiarTurno() {
		if (turnoActual == null) {
			turnoActual = Color.NEGRO;
		}
		this.turnoActual = turnoActual.consultarContrario();
	}

	/**
	 * colocarPiezas Coloca las piezas correspondientes a un array de tipos de
	 * pieza, con sus coordenadas correspondientes, e inicializando el turno actual
	 * al color indicado.
	 * 
	 * @param tipo        array del tipo de piezas a colocar
	 * @param coordenadas array de dos dimensiones para las coordenadas de la
	 *                    colocacion
	 * @param turnoActual variable de tipo color que contiene el color de la pieza
	 *                    que se coloca
	 * @throws CoordenadasIncorrectasException en caso de que las coordenadas sean
	 *                                         incorrectas
	 */
	public void colocarPiezas(TipoPieza[] tipo, int[][] coordenadas, Color turnoActual)
			throws CoordenadasIncorrectasException {
		if (tipo == null || coordenadas == null || turnoActual == null) { // si el turno, coordenadas y tipo de pieza
																			// sean nulas...
			throw new IllegalArgumentException(
					"Ni el turno, ni las coordenadas, ni el tipo de pieza pueden ser nulos. ");
		}
		for (int i = 0; i < coordenadas.length; i++) { // iteramos por las coordenadas
			this.tablero.colocar(new Pieza(tipo[i]), new Coordenada(coordenadas[i][0], coordenadas[i][1]));
		} // asignamos la pieza en las coordenadas especificas
		this.turnoActual = turnoActual; // Establece el turno al jugador que le corresponde
	}

	// mantenemos abstracto hasta implementarlo en cada modo de arbitro
	public abstract void colocarPiezasConfiguracionInicial();

	/**
	 * consultarNumeroJugada Indica el numero de jugada realizadas, es un contador.
	 * 
	 * @return numero de jugada actual.
	 */
	public int consultarNumeroJugada() {
		return this.numeroJugada;
	}

	/**
	 * consultarTablero retorna un clon del tablero en el estado actual
	 * 
	 * @return clon del tablero actual.
	 */
	public Tablero consultarTablero() {
		return tablero.clonar();
	}

	/**
	 * consultarTurno Retorna el color del jugador con el turno actual
	 * 
	 * @return Color del jugador con el turno actual
	 */
	public Color consultarTurno() {
		return turnoActual;
	}

	/**
	 * esMovimientoLegal Verifica la legalidad de la jugad asegun las normas del
	 * juego El metodo aplica las reglas descritas.
	 * 
	 * @param jugada a evaluar
	 * @return True si la jugada es legal, si no false
	 * @throws CoordenadasIncorrectasException Si las coordenadas de la jugada son
	 *                                         incorrectas.
	 * @throws IllegalArgumentException        Si la jugada es nula.
	 */
	public boolean esMovimientoLegal(Jugada jugada) throws CoordenadasIncorrectasException {
		if (jugada == null) {
			throw new IllegalArgumentException();// la jugada no se puede realizar si es nula
		}

		// celdas de origen y destino de la jugada
		Celda celdaOrigen = jugada.origen();
		Celda celdaDestino = jugada.destino();

		// Verificamos que la celda de origen no este vacia
		if (celdaOrigen.estaVacia()) {
			return false;
		}

		// Verificamos que la celda de destino si este vacia.
		if (!celdaDestino.estaVacia()) {
			return false;
		}

		// Si la jugada no es un movimiento horizontal o vertical entonces no se puede
		// hacer
		if (!jugada.esMovimientoHorizontalOVertical()) {
			return false;
		}

		// Verificamos que la pieza a mover le corresponda el turnoActual
		if (!celdaOrigen.consultarColorDePieza().equals(turnoActual)) {
			return false;
		}

		// La celda o jugada de destino no puede ser igual a la de origen.
		if (celdaDestino.equals(celdaOrigen)) {
			return false;
		}

		// Piezas distintas al rey no pueden estar en el trono o en provincias
		if (celdaOrigen.consultarPieza().consultarTipoPieza() != TipoPieza.REY
				&& (celdaDestino.consultarTipoCelda() == TipoCelda.PROVINCIA
						|| celdaDestino.consultarTipoCelda() == TipoCelda.TRONO)) {
			return false;
		}

		return noSaltos(jugada); // evitamos que las piezas puedan saltarse unas a otras (BRANDUBH)
	}

	/**
	 * noSaltos Verifica si no hay saltos entre las piezas en una jugada en el juego
	 * Brandubh. En caso de que haya una pieza intermedia entre la celda de origen y
	 * destino no se realizara el movimiento
	 * 
	 * @param jugada La jugada que se está verificando.
	 * @return true si no hay saltos entre las piezas, false si hay algún salto.
	 * @throws CoordenadasIncorrectasException Si las coordenadas son incorrectas.
	 */
	protected boolean noSaltos(Jugada jugada) throws CoordenadasIncorrectasException {
		Sentido direccion = jugada.consultarSentido();
		Coordenada coordOrigen = jugada.origen().consultarCoordenada();
		Coordenada coordDestino = jugada.destino().consultarCoordenada();

		// genero una lista que tendra las coordenadas intermedias de cada jugada
		List<Coordenada> coordenadasIntermedias = new ArrayList<>();

		switch (direccion) {
		case VERTICAL_N:
			for (int i = coordOrigen.fila() - 1; i > coordDestino.fila(); i--) {
				coordenadasIntermedias.add(new Coordenada(i, coordOrigen.columna()));
			}
			break;
		case VERTICAL_S:
			for (int i = coordOrigen.fila() + 1; i < coordDestino.fila(); i++) {
				coordenadasIntermedias.add(new Coordenada(i, coordOrigen.columna()));
			}
			break;
		case HORIZONTAL_E:
			for (int i = coordOrigen.columna() + 1; i < coordDestino.columna(); i++) {
				coordenadasIntermedias.add(new Coordenada(coordOrigen.fila(), i));
			}
			break;
		case HORIZONTAL_O:
			for (int i = coordOrigen.columna() - 1; i > coordDestino.columna(); i--) {
				coordenadasIntermedias.add(new Coordenada(coordOrigen.fila(), i));
			}
			break;
		}
		// Verificar si alguna de las coordenadas intermedias no estan vacias
		for (Coordenada coordenada : coordenadasIntermedias) {
			if (!this.tablero.consultarCelda(coordenada).estaVacia()) {
				return false;
			}
		}
		return true;
	}

	/**
	 * coordenadaRey
	 * 
	 * Busca y retorna la coordenada del rey en el tablero
	 * 
	 * @throws CoordenadasIncorrectasException si las coordenadas del rey son
	 *                                         incorrectas.
	 * @return coordenadaRey retorna la coordenada exacta del rey en el tablero,
	 *         null en caso que no lo encuentre
	 */
	protected Coordenada coordenadaRey() throws CoordenadasIncorrectasException {
		Coordenada coordenadaRey = null; // inicializamos la coordenada del rey

		for (int fila = 0; fila < tablero.consultarNumeroFilas(); fila++) {
			for (int columna = 0; columna < tablero.consultarNumeroColumnas(); columna++) { // recorremos todo el
																							// tablero
				Celda celda = tablero.consultarCelda(new Coordenada(fila, columna)); // consultamos la celda donde se
																						// encuentra
				Pieza pieza = celda.consultarPieza(); // consultamos si la pieza es el rey

				if (pieza != null && pieza.consultarTipoPieza() == TipoPieza.REY) { // en caso de que sea el rey
					coordenadaRey = new Coordenada(fila, columna); // Almacenamos la coordenada
					return coordenadaRey; // retornamos la coordenada del rey
				} // if
			} // for
		} // for
		return null; // en caso que no se encuentre el rey retornamos null
	}

	/**
	 * Coordenadas exactas de las provincias dle tablero almacenadas en un array
	 */
	protected Coordenada[] provincias = { new Coordenada(0, 0), new Coordenada(6, 0), new Coordenada(0, 6),
			new Coordenada(6, 6) };

	/**
	 * adyacenteAProvincia Verifica si la coordenada es adyacente a una de las 4
	 * provincias del tablero
	 * 
	 * @param coordenada Coordenada a verificar.
	 * @return true en caso de que la coordenada es adyacente a la provincia. False
	 *         en caso contrario
	 */
	protected boolean adyacenteAProvincia(Coordenada coordenada) {
		for (Coordenada provincia : provincias) { // Iteramos por el array de provincias
			if (esCeldaAdyacente(coordenada, provincia)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * esCeldaAdyacente Verifica si dos coordenadas son adyacentes, contiguas.
	 * 
	 * @param coordenada          coordenada de entrada
	 * @param coordenadaAdyacente coordenada con la que se desea comparar si es
	 *                            adyacente
	 * @return true si las coordenadas son adyacentes, false en caso de que no lo
	 *         sean
	 */
	protected boolean esCeldaAdyacente(Coordenada coordenada, Coordenada coordenadaAdyacente) {
		return Math.abs(coordenada.fila() - coordenadaAdyacente.fila()) <= 1
				&& Math.abs(coordenada.columna() - coordenadaAdyacente.columna()) <= 1;
	}// verificamos si la diferencia en filas y columnas entre las 2 coordenadas es
		// menor o igual a 1

	/**
	 * haGanadoAtacante Verifica si el jugador atacante ha ganado la partida en el
	 * juego. Se considera que los atacantes ganan si:
	 * 
	 * 1.El rey se encuentra adyacente a una provincia y tiene un solo atacante en
	 * la dirección horizontal o vertical. 2.La última jugada realizada está
	 * adyacente al rey. 3.El rey está rodeado por piezas atacantes en los bordes
	 * del tablero. 4.El rey está en el trono y está rodeado por 4 atacantes. 5.El
	 * rey no está en el trono y está rodeado por 3 atacantes en el resto del
	 * tablero.
	 * 
	 * @return true si el jugador atacante ha ganado, false en caso contrario.
	 * @throws CoordenadasIncorrectasException Si las coordenadas son incorrectas.
	 */
	public boolean haGanadoAtacante() throws CoordenadasIncorrectasException {
		Coordenada ubicacionRey = coordenadaRey(); // Obtenemos las coordenadas del rey

		if (consultarTurno().equals(Color.BLANCO)) { // si la ubicacion del rey es nula y el turno es de las piezas
														// blancas retornar falso
			return false;
		}

		if (adyacenteAProvincia(ubicacionRey)) {
			// si el rey se encuentra adyacente a provincia
			List<Celda> atacantesHorizontal = tablero.consultarCeldasContiguasEnHorizontal(ubicacionRey);
			int atacanteHorizontal = contarAtacantes(atacantesHorizontal);
			if (atacanteHorizontal == 1) // en caso de que tenga un atacante por horizontal
				return true;

			// Si el rey es adyacente a provincia
			List<Celda> atacantesVertical = tablero.consultarCeldasContiguasEnVertical(ubicacionRey);
			int atacanteVertical = contarAtacantes(atacantesVertical);
			if (atacanteVertical == 1) // en caso de que tenga un atacante por horizontal
				return true;
		}
		// si la ultima jugada no esta adyacente al rey no ganan los atacantes.
		if (!esCeldaAdyacente(ubicacionRey, ultimaJugada.destino().consultarCoordenada())) {
			return false;
		}

		Celda celdaRey = tablero.consultarCelda(ubicacionRey);

		// comprueba que si el rey esta rodeado por piezas atacantes en los bordes del
		// tablero
		if (ubicacionRey.columna() == 0 || ubicacionRey.columna() == 6 && (piezasArribaAbajoDelRey(celdaRey) == 2)) {
			return true;
		} else if (ubicacionRey.fila() == 0
				|| ubicacionRey.fila() == 6 && (piezasDerechaIzquierdaDelRey(celdaRey) == 2)) {
			return true;
		} else if (ubicacionRey.columna() == 1
				|| ubicacionRey.columna() == 5 && (piezasArribaAbajoDelRey(celdaRey) == 2)) {
			return true;
		} else if (ubicacionRey.fila() == 1
				|| ubicacionRey.fila() == 5 && (piezasDerechaIzquierdaDelRey(celdaRey) == 2)) {
			return true;
		}
		// Hacemos una lista de las celdas de alrededor del rey
		List<Celda> alrededorRey = tablero.consultarCeldasContiguas(ubicacionRey);

		int contajeAtacantes = contarAtacantes(alrededorRey);

		if (ubicacionRey.equals(trono)) { // Si el rey está en el trono
			return contajeAtacantes == 4; // Victoria si el rey está rodeado por 4 atacantes en el trono
		} else {
			return contajeAtacantes == 3; // Victoria si el rey no está en el trono y está rodeado por 3 atacantes en el
											// resto del tablero.
		}

	}

	/**
	 * contarAtacantes Cuenta el numero de atacantes almacenado en una lista de
	 * celdas adyacentes
	 * 
	 * @param atacanteAdyacente lista de las celdas contiguas a evaluar que
	 *                          contienen un atacante
	 * @return numero total de atacantes (contajeAtacantes es un contador).
	 */
	private int contarAtacantes(List<Celda> atacanteAdyacente) {
		int contajeAtacantes = 0; // Inicializa el contador de atacantes

		for (Celda celda : atacanteAdyacente) { // Itera sobre las celdas contiguas
			// Verificamos que la celda actual no este vacia y que contenga un atacante
			if (!celda.estaVacia() && celda.consultarPieza().consultarTipoPieza() == TipoPieza.ATACANTE) 
				contajeAtacantes++; // Incrementa el contador si hay un atacante en la celda
		}
		return contajeAtacantes; // Retorna el número total de atacantes encontrados en las celdas contiguas
	}

	/**
	 * piezasArribaAbajoDelRey cuenta el numero de piezas atacantes que tiene el rey
	 * arriba y abajo
	 * 
	 * @param celda Celda que contiene el rey
	 * @return numero total de atacantes que hay arriba y/o abajo del rey
	 * @throws CoordenadasIncorrectasException en caso de tener un error con las
	 *                                         coordenadas del rey
	 */
	private int piezasArribaAbajoDelRey(Celda celda) throws CoordenadasIncorrectasException {
		int contajeAtacantes = 0;

		// Obtenemos la coordenada del rey
		Coordenada ubicacionRey = coordenadaRey();

		// Filas contiguas arriba y abajo
		int filaArriba = ubicacionRey.fila() - 1;
		int filaAbajo = ubicacionRey.fila() + 1;

		// Consultamos las celdas contiguas arriba y abajo
		Celda celdaArriba = tablero.consultarCelda(new Coordenada(filaArriba, ubicacionRey.columna()));
		Celda celdaAbajo = tablero.consultarCelda(new Coordenada(filaAbajo, ubicacionRey.columna()));

		// Contamos las piezas atacantes arriba y abajo
		if (celdaArriba.consultarPieza().consultarTipoPieza() == TipoPieza.ATACANTE) {
			contajeAtacantes++;
		}
		if (celdaAbajo.consultarPieza().consultarTipoPieza() == TipoPieza.ATACANTE) {
			contajeAtacantes++;
		}

		return contajeAtacantes;
	}

	/**
	 * piezasDerechaIzquierdaDelRey cuenta el numero de piezas atacantes que tiene
	 * el rey a la izquierda o a la derecha
	 * 
	 * @param celda Celda que contiene el rey
	 * @return numero total de atacantes que hay izquierda y/o derecha del rey
	 * @throws CoordenadasIncorrectasException en caso de tener un error con las
	 *                                         coordenadas del rey
	 */
	private int piezasDerechaIzquierdaDelRey(Celda celda) throws CoordenadasIncorrectasException {
		int contajeAtacantes = 0;

		// Obtenemos la coordenada del rey
		Coordenada ubicacionRey = coordenadaRey();

		// Filas contiguas arriba y abajo
		int colIzquierda = ubicacionRey.columna() - 1;
		int colDerecha = ubicacionRey.columna() + 1;

		// Consultamos las celdas contiguas arriba y abajo
		Celda celdaIzquierda = tablero.consultarCelda(new Coordenada(ubicacionRey.fila(), colIzquierda));
		Celda celdaDerecha = tablero.consultarCelda(new Coordenada(ubicacionRey.fila(), colDerecha));

		// Contamos las piezas atacantes arriba y abajo
		if (celdaIzquierda.consultarPieza().consultarTipoPieza() == TipoPieza.ATACANTE) {
			contajeAtacantes++;
		}
		if (celdaDerecha.consultarPieza().consultarTipoPieza() == TipoPieza.ATACANTE) {
			contajeAtacantes++;
		}

		return contajeAtacantes;
	}

	// mantenemos abstracto hasta implementarlo en cada modo de arbitro
	public abstract boolean haGanadoRey() throws CoordenadasIncorrectasException; 
    // tenemos formas distintas de ganar en ambos juegos

	/**
	 * mover Realiza el movimiento de una pieza del tablero segun la jugada
	 * especificada
	 * 
	 * @param jugada Jugada que contiene la informacion del movimiento que se quiere
	 *               realizar
	 * @throws CoordenadasIncorrectasException Si las coordenadas de la jugada son
	 *                                         incorrectas
	 * @throws IllegalArgumentException        en caso de que la jugada sea nula
	 */
	public void mover(Jugada jugada) throws CoordenadasIncorrectasException {
		if (jugada == null) { // verificamos si la jugada es nula
			throw new IllegalArgumentException();
		}
		// Obtenemos las coordenadas de origen y destino de la jugada
		Coordenada origen = jugada.origen().consultarCoordenada();
		Coordenada destino = jugada.destino().consultarCoordenada();

		// Establecemos la ultima posicion de movimiento como la coordenada de destino
		this.ultimoMovimiento = destino;

		// verificamos si la coordenada de origen y destino estan en el tablero
		if (!tablero.estaEnTablero(origen) || !tablero.estaEnTablero(destino)) {
			throw new CoordenadasIncorrectasException();
		}
		// registramos el movimiento en el histrial
		Registro registro = new Registro(tablero.clonar(), jugada);
		historial.añadirUltimoRegistro(registro);

		// Obtenemos las celdas de origen y destino
		Celda celdaOrigen = jugada.origen();
		Celda celdaDestino = jugada.destino();

		// Cogemos el tipo de pieza que va a realizar el movimiento
		Pieza pieza = celdaOrigen.consultarPieza();
		// Colocamos la pieza en la nueva coordenada
		tablero.colocar(pieza, celdaDestino.consultarCoordenada());
		// Eliminamos la pieza de la coordenada de origen
		tablero.eliminarPieza(celdaOrigen.consultarCoordenada());

		ultimaJugada = jugada; // Guardamos la ultima jugada
		numeroJugada++; // aumentamos el numero de jugadas global
	}

	/**
	 * realizarCapturasTrasMover realiza las capturas correspondientes dependiendo
	 * de ciertas condiciones al realizar una jugada sobre el tablero.
	 * 
	 * @throws CoordenadasIncorrectasException en caso de que las coordenadas sean
	 *                                         incorrecta dentro de los metodos
	 */
	public void realizarCapturasTrasMover() throws CoordenadasIncorrectasException {
		// tomamos la celda que contiene la pieza y la jugada final
		Celda jugadaFinal = tablero.consultarCelda(this.ultimaJugada.destino().consultarCoordenada());

		// Verificamos si la ultima jugada la ha hecho un defensor
		if (jugadaFinal.consultarPieza().consultarTipoPieza() == TipoPieza.DEFENSOR) {

			// Captura de atacante en provincias por defensor
			capturaDeAtacanteDefensorVerticalProvinciaSuperior();
			capturaDeAtacanteDefensorVerticalProvinciaInferior();
			capturaDeAtacanteEnProvinciaDerechaHorizontal();
			capturaDeAtacanteEnProvinciaIzquierdaHorizontal();

			// Captura del atacante en trono
			capturaDeAtacanteContraTronoHorizontal();
			capturaDeAtacanteContraTronoVertical();

			// Captura en otras ubicaciones del tablero
			capturaAtacanteRestoTableroDestinoIzquierdo();
			capturaAtacanteRestoTableroDestinoDerecha();
			capturaAtacanteRestoTableroDestinoArriba();
			capturaAtacanteRestoTableroDestinoAbajo();

		} // Defensor
			// Verificamos si la ultima jugada la ha realizado un Atacante
		if (jugadaFinal.consultarPieza().consultarTipoPieza() == TipoPieza.ATACANTE) {

			// Capturas del defensor en trono
			if (!this.coordenadaRey().equals(trono)) { // En caso de que el rey no este en el trono
				capturaDeDefensorContraTronoHorizontal();
				capturaDeDefensorContraTronoVertical();
			}

			// Capturas del defensor en provincias
			capturaDeDefensorEnProvinciaDerechaIzquierdaHorizontal();
			capturaDeDefensorEnProvinciaArribaAbajoVertical();

			// Capturas del atacante en el resto del tablero:
			capturaDefensorRestoTableroDestinoIzquierdo(); // Comprueba captura de defensor e cuadrante izquierdo superior e inferior en horizontal
			
			capturaDefensorRestoTableroDestinoDerecha(); // Comprueba captura de defensor e cuadrante derecho superior e inferior en horizontal
															
			capturaDefensorRestoTableroDestinoAbajo(); // Comprueba captura de defensor e cuadrante izquierdo y derecho superior en vertical
														
			capturaDefensorRestoTableroDestinoArriba(); // Comprueba captura de defensor e cuadrante izquierdo y derecho inferior en vertical
														

		} // esAtacante
	}

	/**
	 * capturaDeAtacanteDefensorVerticalProvinciaSuperior
	 * 
	 * Realiza las capturas a atacantes por parte del defensor hacia arriba cuando
	 * un defensor se mueve a una posicion especifica en la provincia y tiene un
	 * atacante entre la provincia y justo encima de el
	 * 
	 * @throws CoordenadasIncorrectasException Si las coordenadas utilizadas para
	 *                                         verificar las celdas no son válidas.
	 */

	private void capturaDeAtacanteDefensorVerticalProvinciaSuperior() throws CoordenadasIncorrectasException {
		// obtenemos la coordenada del defensor el cual sera el que haga la ultima jugada
		Coordenada coordDefensor = ultimaJugada.destino().consultarCoordenada();
		// Si la coordenada se ubica en la segunda fila, y en las columnas 0 o 6
		if (coordDefensor.fila() == 2 && (coordDefensor.columna() == 0 || coordDefensor.columna() == 6)) {
			// y el atacante se encuentra a una posicion mas arriba
			Celda arriba = tablero.consultarCelda(new Coordenada(coordDefensor.fila() - 1, coordDefensor.columna()));
			if (!arriba.estaVacia() && arriba.consultarPieza().consultarTipoPieza().equals(TipoPieza.ATACANTE)) {
				// eliminamos al atacante del tablero
				tablero.eliminarPieza(arriba.consultarCoordenada());
			}
		}
	}

	/**
	 * capturaDeAtacanteDefensorVerticalProvinciaInferior Realiza la captura del
	 * atacante por parte del defensor cuando este se mueve a una posicion
	 * especifica del tablero y encierra a un atacante con la provincia.
	 * 
	 * @throws CoordenadasIncorrectasException Si las coordenadas utilizadas para
	 *                                         verificar las celdas no son válidas.
	 */
	private void capturaDeAtacanteDefensorVerticalProvinciaInferior() throws CoordenadasIncorrectasException {
		// obtenemos la coordenada del defensor el cual sera el que haga la ultima jugada
		Coordenada coordDefensor = ultimaJugada.destino().consultarCoordenada();
		// Si la coordenada se ubica en la cuarta fila, y en las columnas 0 o 6
		if (coordDefensor.fila() == 4 && (coordDefensor.columna() == 0 || coordDefensor.columna() == 6)) {
			// Y el atacante se encuentra una celda mas abajo de este
			Celda abajo = tablero.consultarCelda(new Coordenada(coordDefensor.fila() + 1, coordDefensor.columna()));
			if (!abajo.estaVacia() && abajo.consultarPieza().consultarTipoPieza() == TipoPieza.ATACANTE) {
				// eliminamos al atacante del tablero
				tablero.eliminarPieza(abajo.consultarCoordenada());
			}
		}
	}

	/**
	 * capturaDeAtacanteEnProvinciaDerechaHorizontal Realiza la captura del atacante
	 * por parte del defensor cuando este se mueve a una posicion especifica del
	 * tablero y encierra a un atacante con la provincia. Comprueba la captura del
	 * atacante en la esquina inferior y superior derecha del tablero.
	 * 
	 * @throws CoordenadasIncorrectasException Si las coordenadas utilizadas para
	 *                                         verificar las celdas no son válidas.
	 */
	private void capturaDeAtacanteEnProvinciaDerechaHorizontal() throws CoordenadasIncorrectasException {
		// obtenemos la coordenada del defensor el cual sera el que haga la ultima jugada
		Coordenada coordDefensor = ultimaJugada.destino().consultarCoordenada();
		// Si la coordenada se ubica en la cuarta columna, y en las filas 0 o 6
		if (coordDefensor.columna() == 4 && (coordDefensor.fila() == 0 || coordDefensor.fila() == 6)) {
			// comprueba la captura del atacante en la esquina inferior y superior derecha
			Celda derecha = tablero.consultarCelda(new Coordenada(coordDefensor.fila(), coordDefensor.columna() + 1));
			if (!derecha.estaVacia() && derecha.consultarPieza().consultarTipoPieza().equals(TipoPieza.ATACANTE)) {
				tablero.eliminarPieza(derecha.consultarCoordenada()); // eliminamos al atacante del tablero
			}
		}
	}

	/**
	 * capturaDeAtacanteEnProvinciaIzquierdaHorizontal Realiza la captura del
	 * atacante por parte del defensor cuando este se mueve a una posicion
	 * especifica del tablero y encierra a un atacante con la provincia. Comprueba
	 * la captura del atacante en la esquina inferior y superior izquierda del
	 * tablero.
	 * 
	 * @throws CoordenadasIncorrectasException Si las coordenadas utilizadas para
	 *                                         verificar las celdas no son válidas.
	 */
	private void capturaDeAtacanteEnProvinciaIzquierdaHorizontal() throws CoordenadasIncorrectasException {
		// obtenemos la coordenada del defensor el cual sera el que haga la ultima jugada
		Coordenada coordDefensor = ultimaJugada.destino().consultarCoordenada();
		// Si la coordenada se ubica en la segunda columna, y en las filas 0 o 6
		if (coordDefensor.columna() == 2 && (coordDefensor.fila() == 0 || coordDefensor.fila() == 6)) {
			// comprueba la captura del atacante en la esquina inferior y superior izquierda
			Celda izquierda = tablero.consultarCelda(new Coordenada(coordDefensor.fila(), coordDefensor.columna() - 1));
			if (!izquierda.estaVacia() && izquierda.consultarPieza().consultarTipoPieza() == TipoPieza.ATACANTE) {
				tablero.eliminarPieza(izquierda.consultarCoordenada()); // eliminamos al atacante del tablero
			}
		}
	}

	/**
	 * capturaDeAtacanteContraTronoHorizontal Realiza la captura de un atacante por
	 * parte del defensor encerrandolo con el trono En este caso se evalua las
	 * capturas del atacante a los lados del trono (HORIZONTALMENTE)
	 * 
	 * @throws CoordenadasIncorrectasException Si las coordenadas utilizadas para
	 *                                         verificar las celdas no son válidas.
	 */
	private void capturaDeAtacanteContraTronoHorizontal() throws CoordenadasIncorrectasException {
		// obtenemos la coordenada del defensor el cual sera el que haga la ultima jugada
		Coordenada coordDefensor = ultimaJugada.destino().consultarCoordenada();

		// Si la coordenada se ubica en la segunda columna, y en las filas 0 o 5
		if (coordDefensor.fila() == 3 && (coordDefensor.columna() == 1 || coordDefensor.columna() == 5)) {
			if (coordDefensor.columna() > 3) { // En caso de que el defensor se encuentre a la derecha del trono
				Celda atacIzquierda = tablero.consultarCelda(new Coordenada(coordDefensor.fila(), coordDefensor.columna() - 1));// el
				//El atacante se encuentra a la izquierda adyacente del defensor
				if (!atacIzquierda.estaVacia() && atacIzquierda.consultarPieza().consultarTipoPieza() == TipoPieza.ATACANTE) {
					tablero.eliminarPieza(atacIzquierda.consultarCoordenada()); // Realiza la captura eliminando al
																				// atacante del tablero
				}
			} else { // en caso de que el defensor se encuentre a la izquierda del trono
				Celda atacDerecha = tablero.consultarCelda(new Coordenada(coordDefensor.fila(), coordDefensor.columna() + 1));
				//y Haya un atacante a la derecha del defensor
				if (!atacDerecha.estaVacia() && atacDerecha.consultarPieza().consultarTipoPieza() == TipoPieza.ATACANTE) {
					tablero.eliminarPieza(atacDerecha.consultarCoordenada()); // realizar captura
				}
			}
		}
	}

	/**
	 * capturaDeAtacanteContraTronoVertical Realiza la captura de un atacante por
	 * parte del defensor, encerrandolo con el trono en sentido vertical
	 * 
	 * @throws CoordenadasIncorrectasException Si las coordenadas utilizadas para
	 *                                         verificar las celdas no son válidas.
	 */
	private void capturaDeAtacanteContraTronoVertical() throws CoordenadasIncorrectasException {
		Coordenada coordDefensor = ultimaJugada.destino().consultarCoordenada();
		// obtenemos coordenadas del defensor
		if (coordDefensor.columna() == 3 && (coordDefensor.fila() == 1 || coordDefensor.fila() == 5)) { 
			// si el defensor esta por encima o debajo del trono casi adyacente
			if (coordDefensor.fila() > 3) { // comprobamos la victoria del defensor por debajo del trono
				Celda atacArriba = tablero
						.consultarCelda(new Coordenada(coordDefensor.fila() - 1, coordDefensor.columna()));
				// Ubicamos un atacante encima de este
				if (!atacArriba.estaVacia() && atacArriba.consultarPieza().consultarTipoPieza() == TipoPieza.ATACANTE) {
					tablero.eliminarPieza(atacArriba.consultarCoordenada());// Retiramos al atacante capturado del tablero
				}
			} else { //En caso contrario
				Celda atacAbajo = tablero
						.consultarCelda(new Coordenada(coordDefensor.fila() + 1, coordDefensor.columna()));
				if (!atacAbajo.estaVacia() && atacAbajo.consultarPieza().consultarTipoPieza() == TipoPieza.ATACANTE) {
					tablero.eliminarPieza(atacAbajo.consultarCoordenada());// Retiramos al atacante capturado del tablero
				}
			}
		}
	}

	/**
	 * capturaAtacanteRestoTableroDestinoIzquierdo Comprueba la captura de atacante
	 * en las demas posibles posiciones del tablero Almacena la cantidad de
	 * defensores que hay alrededor del atacante, se comprueba si son 2 y en caso de
	 * ser asi, eliminar la pieza atacante.
	 * 
	 * @throws CoordenadasIncorrectasException Si las coordenadas utilizadas para
	 *                                         verificar las celdas no son válidas.
	 */
	private void capturaAtacanteRestoTableroDestinoIzquierdo() throws CoordenadasIncorrectasException {
		// Obtenemos las coordenadas del defensor
		Coordenada coordDefensor = ultimaJugada.destino().consultarCoordenada();
		// Verificamos que el destino del defensor es una celda donde puede capturar a
		// la izquierda
		if (coordDefensor.columna() < 6) {
			Coordenada derechaAtacante = new Coordenada(coordDefensor.fila(), coordDefensor.columna() + 1); // si la
			//La pieza atacante se encuentra a la derecha
			Celda celdaAtacante = tablero.consultarCelda(derechaAtacante); // consultamos la celda derecha del atacante
			if (!celdaAtacante.estaVacia()
					&& celdaAtacante.consultarPieza().consultarTipoPieza() == TipoPieza.ATACANTE) {
				List<Celda> adyacentes = tablero.consultarCeldasContiguasEnHorizontal(derechaAtacante); 
				int defensores = 0;
				for (Celda ubicacionDefensor : adyacentes) {
					if (!ubicacionDefensor.estaVacia()
							&& ubicacionDefensor.consultarPieza().consultarTipoPieza() == TipoPieza.DEFENSOR) {
						defensores++;// se cuentan los defensores
					}
				}
				if (defensores == 2) { // si el atacante esta rodeado por dos defensores
					tablero.eliminarPieza(derechaAtacante); // capturar
				}
			}
		}
	}

	/**
	 * capturaAtacanteRestoTableroDestinoDerecha Comprueba la captura de atacante en
	 * las demas posibles posiciones del tablero Almacena la cantidad de defensores
	 * que hay alrededor del atacante, se comprueba si son 2 y en caso de ser asi,
	 * eliminar la pieza atacante.
	 * 
	 * @throws CoordenadasIncorrectasException Si las coordenadas utilizadas para
	 *                                         verificar las celdas no son válidas.
	 */
	private void capturaAtacanteRestoTableroDestinoDerecha() throws CoordenadasIncorrectasException {
		Coordenada coordDefensor = ultimaJugada.destino().consultarCoordenada(); // Coordenada defensor
		if (coordDefensor.columna() > 0) { // en caso de que el defensor este en una posicion en la que puede tener un
											// atacante a la izquierda
			Coordenada izquierdaAtacante = new Coordenada(coordDefensor.fila(), coordDefensor.columna() - 1); 
			// ubicamos celda y coordenada del atacante
			Celda celdaAtacante = tablero.consultarCelda(izquierdaAtacante);
			if (!celdaAtacante.estaVacia()
					&& celdaAtacante.consultarPieza().consultarTipoPieza() == TipoPieza.ATACANTE) {
				List<Celda> adyacentes = tablero.consultarCeldasContiguasEnHorizontal(izquierdaAtacante);
				// En caso que sea defensor, almacenar en lista
				int defensores = 0;
				for (Celda ubicacionDefensor : adyacentes) { // en caso de que tenga dos defensores rodeandole
					if (!ubicacionDefensor.estaVacia()
							&& ubicacionDefensor.consultarPieza().consultarTipoPieza() == TipoPieza.DEFENSOR) {
						defensores++;
					}
				}
				if (defensores == 2) { // Eliminar atacante
					tablero.eliminarPieza(izquierdaAtacante);
				}
			}
		}
	}

	/**
	 * capturaAtacanteRestoTableroDestinoArriba Comprueba la captura de atacante en
	 * las demas posibles posiciones del tablero Almacena la cantidad de defensores
	 * que hay alrededor del atacante, se comprueba si son 2 y en caso de ser asi,
	 * eliminar la pieza atacante.
	 * 
	 * @throws CoordenadasIncorrectasException Si las coordenadas utilizadas para
	 *                                         verificar las celdas no son válidas.
	 */
	private void capturaAtacanteRestoTableroDestinoArriba() throws CoordenadasIncorrectasException {
		Coordenada coordDefensor = ultimaJugada.destino().consultarCoordenada();
		if (coordDefensor.fila() < 6) {
			Coordenada abajoAtacante = new Coordenada(coordDefensor.fila() + 1, coordDefensor.columna());
			Celda celdaAtacante = tablero.consultarCelda(abajoAtacante);
			if (!celdaAtacante.estaVacia()
					&& celdaAtacante.consultarPieza().consultarTipoPieza() == TipoPieza.ATACANTE) {
				List<Celda> adyacentes = tablero.consultarCeldasContiguasEnVertical(abajoAtacante);
				int defensores = 0;
				for (Celda ubicacionDefensor : adyacentes) {
					if (!ubicacionDefensor.estaVacia()
							&& ubicacionDefensor.consultarPieza().consultarTipoPieza() == TipoPieza.DEFENSOR) {
						defensores++;
					}
				}
				if (defensores == 2) { // atacante rodeado
					tablero.eliminarPieza(abajoAtacante); //Hacer captura
				}
			}
		}
	}

	/**
	 * capturaAtacanteRestoTableroDestinoAbajo Comprueba la captura de atacante en
	 * las demas posibles posiciones del tablero Almacena la cantidad de defensores
	 * que hay alrededor del atacante, se comprueba si son 2 y en caso de ser asi,
	 * eliminar la pieza atacante.
	 * 
	 * @throws CoordenadasIncorrectasException Si las coordenadas utilizadas para
	 *                                         verificar las celdas no son válidas.
	 */
	private void capturaAtacanteRestoTableroDestinoAbajo() throws CoordenadasIncorrectasException {
		Coordenada coordDefensor = ultimaJugada.destino().consultarCoordenada(); // obtenemos coordenadas del defensor
		if (coordDefensor.fila() > 0) { // en caso de que el defensor se encuentre en posicion donde puede capturar
										// atacante hacia arriba
			Coordenada arribaAtacante = new Coordenada(coordDefensor.fila() - 1, coordDefensor.columna());
			Celda celdaAtacante = tablero.consultarCelda(arribaAtacante); // consultar posicion y celda del atacante
			if (!celdaAtacante.estaVacia()
					&& celdaAtacante.consultarPieza().consultarTipoPieza() == TipoPieza.ATACANTE) {
				List<Celda> adyacentes = tablero.consultarCeldasContiguasEnVertical(arribaAtacante);
				int defensores = 0;// en caso que sean 2 defensores en las ubicaciones adyacentes al atacante
				for (Celda ubicacionDefensor : adyacentes) {
					if (!ubicacionDefensor.estaVacia()
							&& ubicacionDefensor.consultarPieza().consultarTipoPieza() == TipoPieza.DEFENSOR) {
						defensores++;
					}
				}
				if (defensores == 2) { // eliminar pieza atacante
					tablero.eliminarPieza(arribaAtacante);
				}
			}
		}
	}
	// Metodos privados para captura de defensores por parte de atacantes/////////////

	/**
	 * capturaDeDefensorContraTronoHorizontal Realiza la captura de un defensor por
	 * parte del atacante encerrandolo con el trono En este caso se evalua las
	 * capturas del atacante a los lados del trono (HORIZONTALMENTE)
	 * 
	 * @throws CoordenadasIncorrectasException Si las coordenadas utilizadas para
	 *                                         verificar las celdas no son válidas.
	 */
	private void capturaDeDefensorContraTronoHorizontal() throws CoordenadasIncorrectasException {
		Coordenada coordAtacante = ultimaJugada.destino().consultarCoordenada(); // Obtenemos la coordenada del atacante
		if (coordAtacante.fila() == 3 && (coordAtacante.columna() == 1 || coordAtacante.columna() == 5)) { 
			// en caso de que se ubiqie a la izquierda o derecha del trono
			if (coordAtacante.columna() > 3) {// si el atacante se encuentra a la derecha
				Celda izquierdaDefensor = tablero.consultarCelda(new Coordenada(coordAtacante.fila(), coordAtacante.columna() - 1));
				// Y el defensor a la derecha del atacante																									
				if (!izquierdaDefensor.estaVacia()
						&& izquierdaDefensor.consultarPieza().consultarTipoPieza() == TipoPieza.DEFENSOR) {
					tablero.eliminarPieza(izquierdaDefensor.consultarCoordenada());// Eliminar pieza defensora atrapada
				}
			} else { // En caso del sentido contrario, que el atacante se encuentre a la izquierda del trono
				Celda derechaDefensor = tablero
						.consultarCelda(new Coordenada(coordAtacante.fila(), coordAtacante.columna() + 1));
				if (!derechaDefensor.estaVacia()
						&& derechaDefensor.consultarPieza().consultarTipoPieza() == TipoPieza.DEFENSOR) {
					tablero.eliminarPieza(derechaDefensor.consultarCoordenada());
				}
			}
		}
	}

	/**
	 * capturaDeDefensorContraTronoVertical Realiza la captura de un defensor por
	 * parte del atacante encerrandolo con el trono En este caso se evalua las
	 * capturas del atacante a los lados del trono (VERTICALMENTE)
	 * 
	 * @throws CoordenadasIncorrectasException Si las coordenadas utilizadas para
	 *                                         verificar las celdas no son válidas.
	 */
	private void capturaDeDefensorContraTronoVertical() throws CoordenadasIncorrectasException {
		Coordenada coordAtacante = ultimaJugada.destino().consultarCoordenada();
		// Si la columna es la del trono y las ubicaciones del atacante son las filas 1 y 5
		if (coordAtacante.columna() == 3 && (coordAtacante.fila() == 1 || coordAtacante.fila() == 5)) {
			if (coordAtacante.fila() > 3) {
				Celda arribaDefensor = tablero
						.consultarCelda(new Coordenada(coordAtacante.fila() - 1, coordAtacante.columna()));
				if (!arribaDefensor.estaVacia()
						&& arribaDefensor.consultarPieza().consultarTipoPieza() == TipoPieza.DEFENSOR) { 
					tablero.eliminarPieza(arribaDefensor.consultarCoordenada());
				}
			} else {
				Celda abajoDefensor = tablero
						.consultarCelda(new Coordenada(coordAtacante.fila() + 1, coordAtacante.columna()));
				if (!abajoDefensor.estaVacia()
						&& abajoDefensor.consultarPieza().consultarTipoPieza() == TipoPieza.DEFENSOR) { 
					tablero.eliminarPieza(abajoDefensor.consultarCoordenada());
				}
			}
		}
	}

	/**
	 * capturaDeDefensorEnProvinciaDerechaIzquierdaHorizontal Realiza la captura del
	 * defensor por parte del atacante cuando este se mueve a una posicion
	 * especifica del tablero y encierra a un defensor con la provincia. Comprueba
	 * la captura del defensor en sentido HORIZONTAL en la esquina inferior y
	 * superior izquierda del tablero y luego verifica la captura del atacante por
	 * por las provincias de la derecha tanto la superior como la inferior
	 * 
	 * @throws CoordenadasIncorrectasException Si las coordenadas utilizadas para
	 *                                         verificar las celdas no son válidas.
	 */
	private void capturaDeDefensorEnProvinciaDerechaIzquierdaHorizontal() throws CoordenadasIncorrectasException {
		Coordenada coordAtacante = ultimaJugada.destino().consultarCoordenada();
		// En caso de que la columna sea 4 y las filas 0 y 6 (DEL LADO DERECHO)
		if (coordAtacante.columna() == 4 && (coordAtacante.fila() == 0 || coordAtacante.fila() == 6)) {
			// si el defensor se encuentra una celda a la derecha
			Celda derechaDefensor = tablero
					.consultarCelda(new Coordenada(coordAtacante.fila(), coordAtacante.columna() + 1));
			if (!derechaDefensor.estaVacia()
					&& derechaDefensor.consultarPieza().consultarTipoPieza() == TipoPieza.DEFENSOR) {
				tablero.eliminarPieza(derechaDefensor.consultarCoordenada()); // Eliminar del tablero
			}
		} // Verificamos las provincias del lado contrario (DEL LADO IZQUIERDO)
		if (coordAtacante.columna() == 2 && (coordAtacante.fila() == 0 || coordAtacante.fila() == 6)) {
			Celda izquierdaDefensor = tablero
					.consultarCelda(new Coordenada(coordAtacante.fila(), coordAtacante.columna() - 1));
			if (!izquierdaDefensor.estaVacia()
					&& izquierdaDefensor.consultarPieza().consultarTipoPieza() == TipoPieza.DEFENSOR) {
				tablero.eliminarPieza(izquierdaDefensor.consultarCoordenada());
			}
		}
	}

	/**
	 * capturaDeDefensorEnProvinciaArribaAbajoVertical Realiza la captura del
	 * defensor por parte del atacante cuando este se mueve a una posicion
	 * especifica del tablero y encierra a un defensor con la provincia. Comprueba
	 * la captura del defensor en sentido VERTICAL en la esquina inferior derecha e
	 * izquierda del tablero y luego verifica la captura del atacante por por las
	 * provincias superiores, tanto la izquierda como la derecha.
	 * 
	 * @throws CoordenadasIncorrectasException Si las coordenadas utilizadas para
	 *                                         verificar las celdas no son válidas.
	 */
	private void capturaDeDefensorEnProvinciaArribaAbajoVertical() throws CoordenadasIncorrectasException {
		// Asignamos la coordenada del atacante como la ultima jugada que se realiza
		Coordenada coordAtacante = ultimaJugada.destino().consultarCoordenada();
		// si esta se ubica en la cuarta fila y en las columnas 0 o 6 (aproximado a la
		// provincia)
		if (coordAtacante.fila() == 4 && (coordAtacante.columna() == 0 || coordAtacante.columna() == 6)) {
			// Y el defensor se ubica una celda mas abajo (entre atacante y provincia)
			Celda abajoDefensor = tablero
					.consultarCelda(new Coordenada(coordAtacante.fila() + 1, coordAtacante.columna()));
			if (!abajoDefensor.estaVacia()
					&& abajoDefensor.consultarPieza().consultarTipoPieza() == TipoPieza.DEFENSOR) {
				tablero.eliminarPieza(abajoDefensor.consultarCoordenada());// realizar captura del defensor
			}
		}
		// En las provincias superiores
		if (coordAtacante.fila() == 2 && (coordAtacante.columna() == 0 || coordAtacante.columna() == 6)) {
			Celda arribaDefensor = tablero
					.consultarCelda(new Coordenada(coordAtacante.fila() - 1, coordAtacante.columna()));
			if (!arribaDefensor.estaVacia()
					&& arribaDefensor.consultarPieza().consultarTipoPieza() == TipoPieza.DEFENSOR) {
				tablero.eliminarPieza(arribaDefensor.consultarCoordenada());// realizar captura del defensor
			}
		}
	}

	/**
	 * capturaDefensorRestoTableroDestinoIzquierdo Comprueba la captura de defensor
	 * en las demas posibles posiciones del tablero Almacena la cantidad de
	 * atacantes que hay alrededor del defensor, se comprueba si son 2 y en caso de
	 * ser asi, eliminar la pieza defensora. comprueba capturas del cuadrante
	 * inferior y superior izquierdo en horizontal
	 * 
	 * @throws CoordenadasIncorrectasException Si las coordenadas utilizadas para
	 *                                         verificar las celdas no son válidas.
	 */
	private void capturaDefensorRestoTableroDestinoIzquierdo() throws CoordenadasIncorrectasException {
		// obtenemos la coordenada del atacante como la ultima que se realiza en la partida
		Coordenada coordAtacante = ultimaJugada.destino().consultarCoordenada();
		// comprobamos capturas horizontales del defensor rodeado por 2 atacantes mitad izquierda del tablero
		if (coordAtacante.columna() < 6) { // atacante a la izquierda del defensor
			Coordenada derechaDefensor = new Coordenada(coordAtacante.fila(), coordAtacante.columna() + 1);
			Celda celdaDefensor = tablero.consultarCelda(derechaDefensor);
			if (!celdaDefensor.estaVacia() && celdaDefensor.consultarPieza().consultarTipoPieza() == TipoPieza.DEFENSOR) {
				List<Celda> adyacentes = tablero.consultarCeldasContiguasEnHorizontal(derechaDefensor);
				int atacantes = 0; // Creamos una lista donde se almacenan los atacantes que rodean contiguamente
									// al defensor
				for (Celda celdaAtacante : adyacentes) {
					if (!celdaAtacante.estaVacia() && celdaAtacante.consultarPieza().consultarTipoPieza() == TipoPieza.ATACANTE) {
						atacantes++;
					}
				}
				if (atacantes == 2) { // defensor rodeado
					tablero.eliminarPieza(derechaDefensor); // Realizar captura defensor
				}
			}
		}
	}

	/**
	 * capturaDefensorRestoTableroDestinoDerecha Comprueba la captura de defensor en
	 * las demas posibles posiciones del tablero Almacena la cantidad de atacantes
	 * que hay alrededor del defensor, se comprueba si son 2 y en caso de ser asi,
	 * eliminar la pieza defensora. comprueba capturas del cuadrante inferior y
	 * superior derecho en horizontal
	 * 
	 * @throws CoordenadasIncorrectasException Si las coordenadas utilizadas para
	 *                                         verificar las celdas no son válidas.
	 */
	private void capturaDefensorRestoTableroDestinoDerecha() throws CoordenadasIncorrectasException {
		Coordenada coordAtacante = ultimaJugada.destino().consultarCoordenada(); // coordenada atacante
		if (coordAtacante.columna() > 0) { // atacante la derecha del defensor
			Coordenada izquierdaDefensor = new Coordenada(coordAtacante.fila(), coordAtacante.columna() - 1);
			Celda celdaDefensor = tablero.consultarCelda(izquierdaDefensor); // Si el defensor se ubica a su izquierda
			if (!celdaDefensor.estaVacia() && celdaDefensor.consultarPieza().consultarTipoPieza() == TipoPieza.DEFENSOR) {
				List<Celda> adyacentes = tablero.consultarCeldasContiguasEnHorizontal(izquierdaDefensor);
				int atacantes = 0;
				for (Celda celdaAtacante : adyacentes) {
					if (!celdaAtacante.estaVacia() && celdaAtacante.consultarPieza().consultarTipoPieza() == TipoPieza.ATACANTE) {
						atacantes++;
					}
				}
				if (atacantes == 2) { // defensor rodeado
					tablero.eliminarPieza(izquierdaDefensor); // hacer captura
				}
			}
		}
	}

	/**
	 * capturaDefensorRestoTableroDestinoAbajo Comprueba la captura de defensor en
	 * las demas posibles posiciones del tablero Almacena la cantidad de atacantes
	 * que hay alrededor del defensor, se comprueba si son 2 y en caso de ser asi,
	 * eliminar la pieza defensora. Comprueba capturas del defensor en cuadrante
	 * izquierdo y derecho superior en vertical
	 * 
	 * @throws CoordenadasIncorrectasException Si las coordenadas utilizadas para
	 *                                         verificar las celdas no son válidas.
	 */
	private void capturaDefensorRestoTableroDestinoArriba() throws CoordenadasIncorrectasException {
		Coordenada coordAtacante = ultimaJugada.destino().consultarCoordenada(); // coordenada atacante
		if (coordAtacante.fila() < 6) { // atacante encima del defensor
			Coordenada arribaDefensor = new Coordenada(coordAtacante.fila() + 1, coordAtacante.columna());
			Celda celdaDefensor = tablero.consultarCelda(arribaDefensor); // defensor debajo del atacante
			if (!celdaDefensor.estaVacia() && celdaDefensor.consultarPieza().consultarTipoPieza() == TipoPieza.DEFENSOR) {
				List<Celda> adyacentes = tablero.consultarCeldasContiguasEnVertical(arribaDefensor);
				int atacantes = 0;
				for (Celda celdaAtacante : adyacentes) { // en caso de que este rodeado hacer captura
					if (!celdaAtacante.estaVacia() && celdaAtacante.consultarPieza().consultarTipoPieza() == TipoPieza.ATACANTE) {
						atacantes++;
					}
				}
				if (atacantes == 2) { // defensor rodeado
					tablero.eliminarPieza(arribaDefensor); // Hacer captura del defensor
				}
			}
		}
	}

	/**
	 * capturaDefensorRestoTableroDestinoAbajo Comprueba la captura de defensor en
	 * las demas posibles posiciones del tablero Almacena la cantidad de atacantes
	 * que hay alrededor del defensor, se comprueba si son 2 y en caso de ser asi,
	 * eliminar la pieza defensora. Comprueba capturas del defensor en cuadrante
	 * izquierdo y derecho inferior en vertical
	 * 
	 * @throws CoordenadasIncorrectasException Si las coordenadas utilizadas para
	 *                                         verificar las celdas no son válidas.
	 */
	private void capturaDefensorRestoTableroDestinoAbajo() throws CoordenadasIncorrectasException {
		// Coordenada del atacante
		Coordenada coordAtacante = ultimaJugada.destino().consultarCoordenada();
		if (coordAtacante.fila() > 0) { // atacante debajo del defensor
			Coordenada abajoDefensor = new Coordenada(coordAtacante.fila() - 1, coordAtacante.columna());
			Celda celdaDefensor = tablero.consultarCelda(abajoDefensor);// defensor encima del atacante
			if (!celdaDefensor.estaVacia()
					&& celdaDefensor.consultarPieza().consultarTipoPieza() == TipoPieza.DEFENSOR) {
				List<Celda> adyacentes = tablero.consultarCeldasContiguasEnVertical(abajoDefensor);
				int atacantes = 0;
				for (Celda celdaAtacante : adyacentes) {
					if (!celdaAtacante.estaVacia()
							&& celdaAtacante.consultarPieza().consultarTipoPieza() == TipoPieza.ATACANTE) {
						atacantes++;
					}
				}
				if (atacantes == 2) { // defensor rodeado
					tablero.eliminarPieza(abajoDefensor); // hacer captura del defensor
				}
			}
		}
	}

	/**
	 * Retroceder Retrocede una jugada en la partida, restaurando el tablero al
	 * estado anterior y cambiando el turno al jugador anterior. No tiene efecto si
	 * no hay jugadas previas en el historial.
	 */

	public void retroceder() {
		int numRegistro = historial.consultarNumeroRegistros();
		if (numRegistro > 0) { // Verificamos que haya al menos una jugada que retroceder

			// Extraemos el ultimo registro del historial
			Registro ultimoRegistro = historial.extraerUltimoRegistro();

			// Restauramos el tablero al estado anterior
			this.tablero = ultimoRegistro.tablero();
			this.numeroJugada--;

			// regresamos el turno al jugador anterior
			cambiarTurno();
		}
	}
}
