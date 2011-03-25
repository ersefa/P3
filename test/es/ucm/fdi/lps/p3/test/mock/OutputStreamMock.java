/**
 * OutputStreamMock.java  04/01/2010
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

import java.io.IOException;
import java.io.OutputStream;

/**
 * Mocks the OutputStream class.
 * @author Guillermo Jiménez
 * @author Federico Peinado
 */
// Nos inspiramos en el NullOutputStream de Guillermo Jiménez
public class OutputStreamMock extends OutputStream {
    
    /**
     * Writes nothing.
     */
    @Override
    public void write(int arg0) throws IOException {
    } 
}