package es.ucm.fdi.lps.p3.test;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import es.ucm.fdi.lps.p3.Item;

public class ItemTest {

	private Item item1;

	@Before
	public void setUp() throws Exception {
		item1 = new Item("Item1", "Item1 para pruebas", 10);
	}
	
	@Test
	public void testItem() {
		Item fakeItem = new Item("fakeItem", "Fake items for test purpouse", 0);
		assertEquals("Error Item Constructor: wrong description",
				fakeItem.getDescription(), "Fake items for test purpouse");
		assertEquals("Error Item Constructor: wrong value",
				fakeItem.getValue(), 0);
		assertEquals("Error Item Constructor: wrong name", fakeItem.getName(),
				"fakeItem");
	}

	@Test
	public void testGetDescription() {
		assertEquals("Error getDesc: descriptions don´t match",
				item1.getDescription(), "Item1 para pruebas");
	}
	
	@Test
	public void testGetName() {
		assertEquals("Error getName: names don´t match", item1.getName(),
				"Item1");
	}

	@Test
	public void testGetValue() {
		assertEquals("Error getValue: values don´t match", item1.getValue(), 10);
	}

	
	@Test
	public void testToString() {
		assertEquals("Item[Item1]", item1.toString());
	}


}
