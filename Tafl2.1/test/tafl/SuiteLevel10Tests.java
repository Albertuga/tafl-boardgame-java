package tafl;


import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;

/**
 * Suite ejecutando los tests de nivel 10 de la práctica Tafl-2.0 (ver README.txt).
 * Equivalente a ejecutar {@link tafl.SuiteAllTests} con todos lo tests.
 * 
 * @author <a href="rmartico@ubu.es">Raúl Marticorena</a>
 * @since 1.0
 * @version 1.0
 */
@SelectPackages({	
	"tafl.control.ardri.basico",
	"tafl.control.ardri.medio",
	"tafl.control.ardri.avanzado",
	"tafl.control.ardri.historial",
	"tafl.control.brandubh.basico",
	"tafl.control.brandubh.medio",
	"tafl.control.brandubh.avanzado",
	"tafl.control.brandubh.historial",
	"tafl.control.historial",
	"tafl.excepcion",
	"tafl.modelo",
	"tafl.util"})

@Suite
@SuiteDisplayName("Tests de paquetes control (completo con historial), excepcion, modelo y util completos.")
public class SuiteLevel10Tests {

}
