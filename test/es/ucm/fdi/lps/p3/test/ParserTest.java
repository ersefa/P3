package es.ucm.fdi.lps.p3.test;

import static org.junit.Assert.assertFalse;

import java.io.InputStream;
import java.util.Properties;

import org.junit.Before;
import org.junit.Test;

import es.ucm.fdi.lps.p3.Game;
import es.ucm.fdi.lps.p3.Parser;
import es.ucm.fdi.lps.p3.exception.NoNextCommandException;
import es.ucm.fdi.lps.p3.test.mock.GameMock;
import es.ucm.fdi.lps.p3.test.mock.InputStreamMock;

public class ParserTest {

	private Game mockGame;
	private InputStream mockInputStream;

	@Before
	public void setUp() throws Exception {
		mockGame = new GameMock();
		mockInputStream = new InputStreamMock();
	}

	// Parser without Properties Constructor
	@Test(expected = IllegalArgumentException.class)
	public void testNullInputParser() {
		new Parser(null, mockGame);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testNullGameParser() {
		new Parser(mockInputStream, null);
	}

	@Test
	public void testParser() {
		new Parser(mockInputStream, mockGame);

	}

	// Parser with Properties Constructor
	@Test(expected = IllegalArgumentException.class)
	public void testNullInputParserProperties() {
		new Parser(null, mockGame, new Properties());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testNullGameParserProperties() {
		new Parser(mockInputStream, null, new Properties());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testNullPropertiesParserProperties() {
		new Parser(mockInputStream, mockGame, null);
	}

	@Test
	public void testParserProperties() {
		new Parser(mockInputStream, mockGame, new Properties());
	}

	// SetConfig
	@Test(expected = IllegalArgumentException.class)
	public void testSetNullConfig() {
		Parser testParser = new Parser(mockInputStream, mockGame,
				new Properties());
		testParser.setConfig(null);
	}

	@Test
	public void testSetConfig() {
		Parser testParser = new Parser(mockInputStream, mockGame,
				new Properties());
		testParser.setConfig(new Properties());
	}

	// SetInput
	@Test(expected = java.lang.IllegalArgumentException.class)
	public void testSetNullInput() {
		Parser testParser = new Parser(mockInputStream, mockGame,
				new Properties());
		testParser.setInput(null);
	}

	@Test
	public void testSetInput() {
		Parser testParser = new Parser(mockInputStream, mockGame,
				new Properties());
		testParser.setInput(new InputStreamMock());
	}

	// HasNextCommand
	@Test
	public void testHasNextCommand() {
		Parser testParser = new Parser(mockInputStream, mockGame,
				new Properties());
		assertFalse(testParser.hasNextCommand());
	}

	// ParseNextCommand
	@Test(expected = NoNextCommandException.class)
	public void testParseNextCommand() {
		Parser testParser = new Parser(mockInputStream, mockGame,
				new Properties());
		testParser.parseNextCommand();
	}
}
