package tafl.control;

import tafl.modelo.Jugada;
import tafl.modelo.Tablero;

/**
 * Registro Clase de registro que almacena informacion sobre el estado del
 * tablero y de la jugada en cada jugada realizada.
 * 
 * @author Alberto Rafael Muñoz Moreno
 * @version 2.2 FECHA 22/01/2024
 * 
 * @param tablero Contiene informacion acual del tablero para almacenar al
 *                registro
 * @param jugada  Contiene informacion de la jugada y la asocia al registro.
 */
public record Registro(Tablero tablero, Jugada jugada) {

}
