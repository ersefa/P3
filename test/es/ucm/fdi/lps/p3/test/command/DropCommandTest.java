package es.ucm.fdi.lps.p3.test.command;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.junit.Test;

import es.ucm.fdi.lps.p3.command.Command;
import es.ucm.fdi.lps.p3.command.DropCommand;
import es.ucm.fdi.lps.p3.exception.UnparsedCommandException;
import es.ucm.fdi.lps.p3.test.mock.GameMock;

public class DropCommandTest {
	// Constructor
	@Test(expected = java.lang.IllegalArgumentException.class)
	public void testNullDropCommand() {
		new DropCommand(null);
	}

	@Test(expected = java.lang.IllegalArgumentException.class)
	public void testDropCommandNullConfig() {
		new DropCommand(new GameMock(), null);
	}

	// GetHelp
	@Test
	public void testGetHelp() {
		Command command = new DropCommand(new GameMock());
		assertEquals("(drop|d|unpick) <item name>", command.getHelp());
	}

	// Execute
	@Test(expected = UnparsedCommandException.class)
	public void testExecute() {
		Command command = new DropCommand(new GameMock());
		command.execute();
	}

	// Parse
	@Test
	public void testParse() {
		Command command = new DropCommand(new GameMock());
		assertFalse(command.parse(""));
	}
	
	// ToString
	@Test
	public void testToString() {
		Command command = new DropCommand(new GameMock());
		assertEquals("DropCommand[]", command.toString());
	}
}
