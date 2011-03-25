package es.ucm.fdi.lps.p3;

/**
 * Represents an item that can be placed in the player inventory or in the
 * locations of the game.
 */
public class Item {

	/**
	 * The item name.
	 */
	private String name;

	/**
	 * The item description.
	 */
	private String description;

	/**
	 * The item value.
	 */
	private int value;

	/**
	 * Constructs an item using a given name, a given description and a given
	 * value.
	 * 
	 * @param name
	 *            The name.
	 * @param description
	 *            The description.
	 * @param value
	 *            The value.
	 * @throws IllegalArgumentException
	 *             The arguments 'name' and 'description' cannot be null.
	 */
	public Item(String name, String description, int value) {
		if ((name == null) || (description == null))
			throw new IllegalArgumentException();
		this.name = name;
		this.description = description;
		this.value = value;
	}

	/**
	 * Returns the description.
	 * 
	 * @return The description.
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Returns the name.
	 * 
	 * @return The name.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Returns the value.
	 * 
	 * @return The value.
	 */
	public int getValue() {
		return value;
	}

	/**
	 * Returns a String representation for this object: Item[]. This is useful
	 * for debugging purposes.
	 * 
	 * @see Object#toString()
	 */
	public String toString() {
		return this.getClass().getSimpleName() + "[" + getName() + "]";
	}
}
