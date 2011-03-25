/**
 * EngineMock.java  04/01/2010
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

import java.io.InputStream;
import java.util.Properties;

import es.ucm.fdi.lps.p3.Engine;
import es.ucm.fdi.lps.p3.Game;

/**
 * Mocks the Engine class.
 * @author Federico Peinado
 */
public class EngineMock extends Engine {
    
    /**
     * Mocks the constructor (without arguments).
     */
    // Se asume que el constructor más sencillo de Engine funciona
    public EngineMock() {
	super(new GameMock());
    }   
    
    /**
     * Mocks the constructor (with a game argument).
     */
    // Se asume que este otro constructor de Engine funciona
    public EngineMock(Game game) {
	super(game);
    }  
    
    /**
     * Mocks the constructor (with a game argument and an input).
     */
    // Se asume que este otro constructor de Engine funciona
    public EngineMock(Game game, InputStream input) {
	super(game, input);
    }  
    
    /**
     * Mocks the constructor (with a game argument, a config and an input).
     */
    // Se asume que este otro constructor de Engine funciona
    public EngineMock(Game game, Properties config, InputStream input) {
	super(game, config, input);
    }
}