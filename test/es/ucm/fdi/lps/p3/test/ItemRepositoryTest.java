package es.ucm.fdi.lps.p3.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import es.ucm.fdi.lps.p3.Item;
import es.ucm.fdi.lps.p3.ItemRepository;
import es.ucm.fdi.lps.p3.exception.ItemAlreadyInRepositoryException;
import es.ucm.fdi.lps.p3.exception.ItemNotInRepositoryException;

public class ItemRepositoryTest {

	private Item item1, item2, item3;

	@Before
	public void setUp() throws Exception {
		item1 = new Item("Item1", "Item1 para pruebas", 10);
		item2 = new Item("Item2", "Item2 para pruebas", 20);
		item3 = new Item("Item3", "Item3 para pruebas", 30);
	}

	// AddItem
	@Test
	public void testAddItem() {
		ItemRepository testRepository = new ItemRepository();
		testRepository.addItem(item1);
		Item temp = (Item) testRepository.getItems(item1.getName()).toArray()[0];
		assertTrue("ERROR in addItem: addItem not performed",
				temp.equals(item1));
	}

	@Test(expected = java.lang.IllegalArgumentException.class)
	public void testAddNullItem() {
		ItemRepository testRepository = new ItemRepository();
		testRepository.addItem((Item) null);
	}

	@Test(expected = ItemAlreadyInRepositoryException.class)
	public void testAddRepeatedtem() {
		ItemRepository testRepository = new ItemRepository();
		testRepository.addItem(item1);
		testRepository.addItem(item1);
	}

	// GetItems
	@Test(expected = java.lang.IllegalArgumentException.class)
	public void testGetNullItems() {
		ItemRepository testRepository = new ItemRepository();
		testRepository.getItems((String) null);
	}

	@Test
	public void testGetItems() {
		ItemRepository testRepository = new ItemRepository();
		testRepository.addItem(item1);
		Item temp = (Item) testRepository.getItems(item1.getName()).toArray()[0];
		assertEquals("Wrong item returned", item1.getName(), temp.getName());
	}

	// GetAllItems
	@Test
	public void testGetAllItems() {
		ItemRepository testRepository = new ItemRepository();
		testRepository.addItem(item1);
		testRepository.addItem(item2);
		assertEquals("Wrong item number", 2, testRepository.getAllItems()
				.size());
	}

	// GetTotalValue
	@Test
	public void testGetTotalValue() {
		ItemRepository testRepository = new ItemRepository();
		testRepository.addItem(item1);
		testRepository.addItem(item2);
		testRepository.addItem(item3);
		assertEquals("Wrong Repository value", 60,
				testRepository.getTotalValue());
	}

	// RemoveItem
	@Test(expected = java.lang.IllegalArgumentException.class)
	public void testRemoveNullItem() {
		ItemRepository testRepository = new ItemRepository();
		testRepository.removeItem((Item) null);
	}

	@Test(expected = ItemNotInRepositoryException.class)
	public void testRemoveWrongItem() {
		ItemRepository testRepository = new ItemRepository();
		testRepository.addItem(item1);
		testRepository.removeItem(item2);
	}

	@Test
	public void testRemoveItem() {
		ItemRepository testRepository = new ItemRepository();
		testRepository.addItem(item1);
		testRepository.removeItem(item1);
		assertEquals("Item Remove Error", 0, testRepository.getAllItems()
				.size());
	}

	// IsEmpty
	@Test
	public void testIsEmpty() {
		ItemRepository testRepository = new ItemRepository();
		assertTrue("The repository is empty", testRepository.isEmpty());
	}

	// HasItem
	@Test(expected = java.lang.IllegalArgumentException.class)
	public void testHasNullItem() {
		ItemRepository testRepository = new ItemRepository();
		testRepository.hasItem((Item) null);
	}

	@Test
	public void testHasItem() {
		ItemRepository testRepository = new ItemRepository();
		testRepository.addItem(item1);
		assertTrue("Item notFound", testRepository.hasItem(item1));
	}

	// ToString
	@Test
	public void testToString() {
		ItemRepository testRepository = new ItemRepository();
		assertEquals("Repository toString value don´t MATCH",
				"ItemRepository[]", testRepository.toString());
	}

}
