package es.ucm.fdi.lps.p3.test;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

import org.junit.Test;

import es.ucm.fdi.lps.p3.Engine;
import es.ucm.fdi.lps.p3.Game.Direction;
import es.ucm.fdi.lps.p3.Location;
import es.ucm.fdi.lps.p3.test.mock.EngineMock;
import es.ucm.fdi.lps.p3.test.mock.GameMock;
import es.ucm.fdi.lps.p3.test.mock.InputStreamMock;
import es.ucm.fdi.lps.p3.test.mock.OutputStreamMock;

public class EngineTest {

	// Engine0 - Game Constructor
	@Test(expected = java.lang.IllegalArgumentException.class)
	public void testNullGameEngine0() {
		new Engine(null);
	}

	@Test
	public void testEngine0() {
		new Engine(new GameMock());
	}

	// Engine1 - Game, InputStream Constructor
	@Test(expected = java.lang.IllegalArgumentException.class)
	public void testEngineInputNullInput() {
		new Engine(new GameMock(), (InputStream) null);
	}

	@Test(expected = java.lang.IllegalArgumentException.class)
	public void testEngine1NullGame() {
		new Engine(null, new InputStreamMock());
	}

	@Test
	public void testEngine1() {
		new Engine(new GameMock(), new InputStreamMock());
	}

	// Engine2 - Game, Input, Output Constructor
	@Test(expected = java.lang.IllegalArgumentException.class)
	public void testEngine2NullOutput() {
		new Engine(new GameMock(), new InputStreamMock(), (OutputStream) null);
	}

	@Test(expected = java.lang.IllegalArgumentException.class)
	public void testEngine2NullGame() {
		new Engine(null, new InputStreamMock(), new OutputStreamMock());
	}

	@Test(expected = java.lang.IllegalArgumentException.class)
	public void testEngine2NullInput() {
		new Engine(new GameMock(), (InputStream) null, new OutputStreamMock());
	}

	@Test
	public void testEngine2() {
		new Engine(new GameMock(), new InputStreamMock(),
				new OutputStreamMock());
	}

	// Engine3 - Game, Input, Output Constructor
	@Test(expected = java.lang.IllegalArgumentException.class)
	public void testEngine3NullOutput() {
		new Engine(new GameMock(), (OutputStream) null);
	}

	@Test(expected = java.lang.IllegalArgumentException.class)
	public void testEngine3NullGame() {
		new Engine(null, new OutputStreamMock());
	}

	@Test
	public void testEngine3() {
		new Engine(new GameMock(), new OutputStreamMock());
	}

	// Engine4 - Game, Properties Constructor
	@Test(expected = java.lang.IllegalArgumentException.class)
	public void testEngine4NullConfig() {
		new Engine(new GameMock(), (Properties) null);
	}

	@Test(expected = java.lang.IllegalArgumentException.class)
	public void testEngine4NullGame() {
		new Engine(null, new Properties());
	}

	@Test
	public void testEngine4() {
		new Engine(new GameMock(), new Properties());
	}

	// Engine5 - Game, Input, Properties Constructor
	@Test(expected = java.lang.IllegalArgumentException.class)
	public void testEngine5NullConfig() {
		new Engine(new GameMock(), (Properties) null, new InputStreamMock());
	}

	@Test(expected = java.lang.IllegalArgumentException.class)
	public void testEngine5NullInput() {
		new Engine(new GameMock(), new Properties(), (InputStream) null);
	}

	@Test(expected = java.lang.IllegalArgumentException.class)
	public void testEngine5NullGame() {
		new Engine(null, new Properties(), new InputStreamMock());
	}

	@Test
	public void testEngine5() {
		new Engine(new GameMock(), new Properties(), new InputStreamMock());
	}

	// Engine6 - Game, Configuration Input, Output Constructor
	@Test(expected = java.lang.IllegalArgumentException.class)
	public void testEngin6NullConfig_() {
		new Engine(new GameMock(), (Properties) null, new InputStreamMock(),
				new OutputStreamMock());
	}

	@Test(expected = java.lang.IllegalArgumentException.class)
	public void testEngine6NullOutput() {
		new Engine(new GameMock(), new Properties(), new InputStreamMock(),
				(OutputStream) null);
	}

	@Test(expected = java.lang.IllegalArgumentException.class)
	public void testEngine6NullGamet() {
		new Engine(null, new Properties(), new InputStreamMock(),
				new OutputStreamMock());
	}

	@Test(expected = java.lang.IllegalArgumentException.class)
	public void testEngine6NullInput() {
		new Engine(new GameMock(), new Properties(), (InputStream) null,
				new OutputStreamMock());
	}

	@Test
	public void testEngine6() {
		new Engine(new GameMock(), new Properties(), new InputStreamMock(),
				new OutputStreamMock());
	}

	// Engine7 - Game, Configuration, Output Constructor
	@Test(expected = java.lang.IllegalArgumentException.class)
	public void testEngine7NullConfig() {
		new Engine(new GameMock(), (Properties) null, new OutputStreamMock());
	}

	@Test(expected = java.lang.IllegalArgumentException.class)
	public void testEngine7NullOutput() {
		new Engine(new GameMock(), new Properties(), (OutputStream) null);
	}

	@Test(expected = java.lang.IllegalArgumentException.class)
	public void testEngine7NullGam() {
		new Engine(null, new Properties(), new OutputStreamMock());
	}

	@Test
	public void testEngine7() {
		new Engine(new GameMock(), new Properties(), new OutputStreamMock());
	}

	// Run
	@Test
	public void testRun() {
		Location a = new Location("testA", "testDescA", 0, "testExitA");
		Location b = new Location("testB", "testB");

		b.setConnection(Direction.SOUTH, a);

		InputStreamMock input = new InputStreamMock("go south");

		Engine engine = new EngineMock(new GameMock(b), input);
		engine.run();
	}

	// SetConfig
	@Test(expected = java.lang.IllegalArgumentException.class)
	public void testSetNullConfig() {
		Engine engine = new Engine(new GameMock(), new Properties());
		engine.setConfig(null);
	}

	@Test
	public void testSetConfig() {
		Engine engine = new Engine(new GameMock(), new Properties());
		engine.setConfig(new Properties());
	}

	// SetInput
	@Test(expected = java.lang.IllegalArgumentException.class)
	public void testSetNullInput() {
		Engine engine = new Engine(new GameMock(), new InputStreamMock());
		engine.setInput(null);
	}

	@Test
	public void testSetInput() {
		Engine engine = new Engine(new GameMock(), new InputStreamMock());
		engine.setInput(new InputStreamMock());
	}

	// SetOutput
	@Test(expected = java.lang.IllegalArgumentException.class)
	public void testSetNullOutput() {
		Engine engine = new Engine(new GameMock(), new OutputStreamMock());
		engine.setOutput(null);
	}

	@Test
	public void testSetOutpu() {
		Engine engine = new Engine(new GameMock(), new OutputStreamMock());
		engine.setOutput(new OutputStreamMock());
	}
}
