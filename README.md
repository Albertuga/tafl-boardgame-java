# Tafl Games 

Implementación en Java de la familia de juegos abstractos **Tafl** (ajedreces vikingos), con soporte para variantes tradicionales como **Brandubh** y **ArdRi**. El proyecto incluye tanto una interfaz gráfica (GUI) como una versión interactiva en modo consola (TextUI). (Práctica implementada por la Universidad de Burgos).

---

## Sobre el Juego

Los juegos Tafl son juegos de tablero asimétricos para dos jugadores donde un bando defensor protege a su rey mientras un bando atacante intenta capturarlo.

### Variante Brandubh (7×7)
* **Tablero:** 7×7 casillas.
* **Fuerzas:** 
  * ⚪ **Defensor:** 5 piezas blancas (4 defensores + 1 Rey).
  * ⚫ **Atacante:** 8 piezas negras.
* **Casillas especiales:**
  * **Trono:** Casilla central donde inicia el rey.
  * **Provincias:** Cuatro casillas situadas en las esquinas del tablero.

### Objetivos de Victoria
* ⚪ **Defensor:** Lograr que el **Rey escape** alcanzando cualquiera de las cuatro provincias.
* ⚫ **Atacante:** **Capturar al Rey** antes de que logre escapar a una provincia.

### Mecánicas Principales
* **Movimiento:** Todas las piezas se mueven en horizontal o vertical tantas casillas libres como deseen (similar a la torre en ajedrez). No pueden saltar sobre otras piezas.
* **Restricción de Provincias:** Solo el Rey puede ocupar las provincias.
* **Capturas:** Por custodia/flanqueo. Se captura una pieza enemiga cuando queda atrapada en línea recta entre dos piezas del jugador que mueve. Las provincias actúan como piezas enemigas para resolver capturas.

---

## 📁 Estructura del Proyecto

```text
├── src/                # Código fuente Java (control, modelo, util, textui, gui)
├── lib/                # Librerías externas (.jar) para la GUI y tests
├── test/               # Pruebas unitarias
├── bin/                # Bytecode compilado (.class) [Ignorado en Git]
├── doc/                # Documentación Javadoc generada [Ignorado en Git]
├── compilar.bat        # Script de compilación por lotes
├── documentar.bat      # Generador de documentación Javadoc
├── ejecutar_gui.bat    # Lanzador de la interfaz gráfica
└── ejecutar_textui.bat # Lanzador de la interfaz por consola

Esta ha sido la última práctica de la asignatura Metodologia de la Programación
