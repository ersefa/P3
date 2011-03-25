package es.ucm.fdi.lps.p3.test;

import junit.framework.JUnit4TestAdapter;
import junit.framework.Test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import es.ucm.fdi.lps.p3.test.command.DropCommandTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({ ItemTest.class, ItemRepositoryTest.class, LocationTest.class, ParserTest.class, EngineTest.class, GameTest.class, DropCommandTest.class })

public class AllTests {
	/**
	 * For executing all the test in JUnit < 4 version.
	 */
	public static Test suite() {
		return new JUnit4TestAdapter(AllTests.class);
	}

	/**
	 * For executing all the test in console.
	 */
	public static void main(String[] args) {
		org.junit.runner.JUnitCore.runClasses(AllTests.class);
	}
}