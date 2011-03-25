/**
 * LocationMock.java  04/01/2010
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

import es.ucm.fdi.lps.p3.Location;

/**
 * Mocks the Location class.
 * @author Federico Peinado
 */
public class LocationMock extends Location {
    
    /**
     * Mocks the constructor (without arguments).
     */
    // Se asume que el constructor más sencillo de Location funciona
    public LocationMock() {
	super("Mock Name", "Mock Description");
    }   
    
    /**
     * Mocks the constructor (with an int argument, for an exit location).
     */
    // Se asume que este otro constructor de Location funciona
    public LocationMock(int exitThreshold) {
	super("Mock Name", "Mock Description", exitThreshold, "Mock Exit Message");
    }   
}