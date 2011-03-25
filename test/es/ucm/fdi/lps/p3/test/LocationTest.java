package es.ucm.fdi.lps.p3.test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import es.ucm.fdi.lps.p3.Game.Direction;
import es.ucm.fdi.lps.p3.Item;
import es.ucm.fdi.lps.p3.Location;
import es.ucm.fdi.lps.p3.exception.ItemAlreadyInRepositoryException;
import es.ucm.fdi.lps.p3.exception.ItemNotInRepositoryException;
import es.ucm.fdi.lps.p3.exception.NoConnectedLocationException;
import es.ucm.fdi.lps.p3.exception.NoExitThresholdException;

public class LocationTest {
	private Item item1, item2;

	@Before
	public void setUp() throws Exception {
		// Items in Rooms
		item1 = new Item("Item1", "Item1 para pruebas", 10);
		item2 = new Item("Item2", "Item2 para pruebas", 30);
	}

	// Location without THRESHOLD
	@Test
	public void testLocation() {
		Location testLocation = new Location("testLocationConstructor",
				"Location Constructor Test");
		assertEquals("testLocationConstructor", testLocation.getName());
		assertEquals("Location Constructor Test", testLocation.getDescription());
	}

	@Test(expected = java.lang.IllegalArgumentException.class)
	public void testLocationNullName() {
		new Location((String) null, "Location Constructor Test");
	}

	@Test(expected = java.lang.IllegalArgumentException.class)
	public void testLocationNullDescription() {
		new Location("testLocationConstructor", (String) null);
	}

	// Location with THRESHOLD
	@Test
	public void testLocationThreshold() {
		Location testLocation = new Location("testLocationConstructor",
				"Location Constructor Test", 40, "Location exit message");
		assertEquals("testLocationConstructor", testLocation.getName());
		assertEquals("Location Constructor Test", testLocation.getDescription());
		assertEquals(40, testLocation.getExitThreshold());
		assertEquals("Location exit message", testLocation.getExitMessage());
	}

	@Test(expected = java.lang.IllegalArgumentException.class)
	public void testLocationThresholdNullName() {
		new Location((String) null, "Location Constructor Test", 40,
				"Location exit message");
	}

	@Test(expected = java.lang.IllegalArgumentException.class)
	public void testLocationThresholdNullDescription() {
		new Location("testLocationConstructor", (String) null, 40,
				"Location exit message");
	}

	@Test(expected = java.lang.IllegalArgumentException.class)
	public void testLocationThresholdNullExitMessage() {
		new Location("testLocationConstructor", "Location Constructor Test",
				40, (String) null);
	}

	// AddItem
	@Test(expected = java.lang.IllegalArgumentException.class)
	public void testAddNullItem() {
		Location testLocation = new Location("testLocationConstructor",
				"Location Constructor Test");
		testLocation.addItem(null);
	}

	@Test(expected = ItemAlreadyInRepositoryException.class)
	public void testAddRepeatedItem() {
		Location testLocation = new Location("testLocationConstructor",
				"Location Constructor Test");
		testLocation.addItem(item1);
		testLocation.addItem(item1);
	}

	@Test
	public void testAddItem() {
		Location testLocation = new Location("testLocationConstructor",
				"Location Constructor Test");
		testLocation.addItem(item1);
		assertTrue(testLocation.getAllItems().size() == 1);
		assertTrue(testLocation.getAllItems().toArray()[0].equals(item1));
	}

	// GetItems
	@Test(expected = java.lang.IllegalArgumentException.class)
	public void testGetNullItem() {
		Location testLocation = new Location("testLocationConstructor",
				"Location Constructor Test");
		testLocation.getItems(null);
	}

	@Test
	public void testGetItem() {
		Location testLocation = new Location("testLocationConstructor",
				"Location Constructor Test");
		testLocation.addItem(item1);
		assertSame(testLocation.getItems(item1.getName()).toArray()[0], item1);
	}

	// GetAllItems
	@Test
	public void testGetAllItems() {
		Location testLocation = new Location("testLocationConstructor",
				"Location Constructor Test");
		testLocation.addItem(item1);
		testLocation.addItem(item2);
		assertEquals(2, testLocation.getAllItems().size());
	}

	// RemoveItem
	@Test(expected = java.lang.IllegalArgumentException.class)
	public void testRemoveNullItem() {
		Location testLocation = new Location("testLocationConstructor",
				"Location Constructor Test");
		testLocation.removeItem(null);
	}

	@Test(expected = ItemNotInRepositoryException.class)
	public void testRemoveNotItem() {
		Location testLocation = new Location("testLocationConstructor",
				"Location Constructor Test");
		testLocation.removeItem(item1);
	}

	@Test
	public void testRemoveItem() {
		Location testLocation = new Location("testLocationConstructor",
				"Location Constructor Test");
		testLocation.addItem(item1);
		testLocation.removeItem(item1);
		assertEquals(0, testLocation.getAllItems().size());
	}

	// HasItem
	@Test(expected = java.lang.IllegalArgumentException.class)
	public void hasNullItem() {
		Location testLocation = new Location("testLocationConstructor",
				"Location Constructor Test");
		testLocation.hasItem(null);
	}

	@Test
	public void hasItem() {
		Location testLocation = new Location("testLocationConstructor",
				"Location Constructor Test");
		assertFalse(testLocation.hasItem(item1));
		testLocation.addItem(item1);
		assertTrue(testLocation.hasItem(item1));
	}

	// GetName
	@Test
	public void testGetName() {
		Location testLocation = new Location("testLocationConstructor",
				"Location Constructor Test");
		assertEquals("testLocationConstructor", testLocation.getName());
	}

	// GetDescription
	@Test
	public void testGetDescription() {
		Location testLocation = new Location("testLocationConstructor",
				"Location Constructor Test");
		assertEquals("Location Constructor Test", testLocation.getDescription());
	}

	// HasConnectedLocation
	@Test(expected = java.lang.IllegalArgumentException.class)
	public void testHasConnectedLocationNullDirection() {
		Location testLocation = new Location("testLocationConstructor",
				"Location Constructor Test");
		testLocation.hasConnectedLocation(null);
	}

	@Test
	public void testHasConnectedLocation() {
		Location testLocation = new Location("testLocationConstructor",
				"Location Constructor Test");
		Location temp = new Location("temp", "temp");
		assertFalse(testLocation.hasConnectedLocation(Direction.NORTH));

		testLocation.setConnection(Direction.NORTH, temp);
		assertTrue(testLocation.hasConnectedLocation(Direction.NORTH));
	}

	// HasExitThreshold
	@Test
	public void testHasExitThreshold() {
		Location testLocation = new Location("testLocationConstructor",
				"Location Constructor Test", 25, "Exit");
		assertTrue(testLocation.hasExitThreshold());
	}

	@Test
	// GetExitThreshold
	public void testGetExitThreshold() {
		Location testLocation = new Location("testLocationConstructor",
				"Location Constructor Test", 25, "Exit");
		assertEquals(25, testLocation.getExitThreshold());
	}

	@Test(expected = NoExitThresholdException.class)
	public void testGetExitThresholdError() {
		Location testLocation = new Location("testLocationConstructor",
				"Location Constructor Test");
		testLocation.getExitThreshold();
	}

	// GetConnectedLocation
	@Test
	public void testGetConnectedLocation() {
		Location testLocation = new Location("testLocationConstructor",
				"Location Constructor Test");
		Location temp = new Location("temp", "temp");
		testLocation.setConnection(Direction.NORTH, temp);
		assertSame(testLocation.getConnectedLocation(Direction.NORTH), temp);
	}

	@Test(expected = NoConnectedLocationException.class)
	public void testGetNotConnectedLocation() {
		Location testLocation = new Location("testLocationConstructor",
				"Location Constructor Test");
		testLocation.getConnectedLocation(Direction.NORTH);
	}

	@Test(expected = java.lang.IllegalArgumentException.class)
	public void testGetConnectedNullDirectionLocation() {
		Location testLocation = new Location("testLocationConstructor",
				"Location Constructor Test");
		testLocation.getConnectedLocation(null);
	}

	// SetConnection
	@Test(expected = java.lang.IllegalArgumentException.class)
	public void testSetConnectionNullDirection() {
		Location testLocation = new Location("testLocationConstructor",
				"Location Constructor Test");
		Location temp = new Location("temp", "temp");
		testLocation.setConnection(null, temp);
	}

	@Test(expected = java.lang.IllegalArgumentException.class)
	public void testSetConnectionNullLocation() {
		Location testLocation = new Location("testLocationConstructor",
				"Location Constructor Test");
		testLocation.setConnection(Direction.NORTH, null);
	}

	@Test
	public void testSetConnection() {
		Location testLocation = new Location("testLocationConstructor",
				"Location Constructor Test");
		Location temp = new Location("temp", "temp");
		testLocation.setConnection(Direction.NORTH, temp);
		assertSame(testLocation.getConnectedLocation(Direction.NORTH), temp);
	}

	// ToString
	@Test
	public void testToString() {
		Location testLocation = new Location("testLocationConstructor",
				"Location Constructor Test");
		assertEquals(testLocation.toString(),
				"Location[testLocationConstructor]");
	}

}
