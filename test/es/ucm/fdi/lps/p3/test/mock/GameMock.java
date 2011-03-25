/**
 * GameMock.java  04/01/2010
 * 
 * Laboratorio de Programación de Sistemas (2010/2011)
 * Ingeniería Técnica en Informática de Sistemas
 * Lecturers: Guillermo Jiménez Díaz
 *            Federico Peinado Gil
 *            Borja Manero Iglesias
 *
 * Facultad de Informática
 * Universidad Complutense de Madrid
 */
package es.ucm.fdi.lps.p3.test.mock;

import es.ucm.fdi.lps.p3.Game;
import es.ucm.fdi.lps.p3.Location;

/**
 * Mocks the Game class.
 * @author Federico Peinado
 */
public class GameMock extends Game {
    
    /**
     * Mocks the constructor (without arguments).
     */
    // Se asume que el constructor más sencillo de Game funciona
    public GameMock() {
	super("Mock Title", "Mock Author", "Mock Description", new LocationMock());
    }   
    
    /**
     * Mocks the constructor (with a location argument).
     */
    // Se asume que este otro constructor de Game funciona
    public GameMock(Location location) {
	super("Mock Title", "Mock Author", "Mock Description", location);
    }   
}