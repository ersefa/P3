package es.ucm.fdi.lps.p3;

import java.util.EnumMap;
import java.util.Map;
import java.util.Set;

import es.ucm.fdi.lps.p3.Game.Direction;
import es.ucm.fdi.lps.p3.exception.ItemAlreadyInRepositoryException;
import es.ucm.fdi.lps.p3.exception.ItemNotInRepositoryException;
import es.ucm.fdi.lps.p3.exception.NoConnectedLocationException;
import es.ucm.fdi.lps.p3.exception.NoExitThresholdException;

/**
 * Represents a location of the game, that may have items and connections to
 * other locations.
 */
public class Location {

	/**
	 * The name of the location
	 */
	private String name;

	/**
	 * The description of the location
	 */
	private String description;

	/**
	 * The exit message of the location
	 */
	private String exitMessage;

	/**
	 * The exit threshold of the location
	 */
	private int exitThreshold;

	/**
	 * The exit threshold flag
	 */
	private boolean exitThresholdFlag;

	/**
	 * Adjacent locations. It there will be a room in every direction. If
	 * locations.get(X) == null then there is not any adjacent room in direction
	 * X
	 */
	private Map<Direction, Location> locations;

	/**
	 * The items contained in the location
	 */
	private ItemRepository items;

	/**
	 * Constructs a location with a given name and a given description. By
	 * default, it is not an exit location (it has no exit threshold nor exit
	 * message).
	 * 
	 * @param name
	 *            The name.
	 * @param description
	 *            The description.
	 * @throws IllegalArgumentException
	 *             The arguments 'name' and 'description' cannot be null.
	 */
	public Location(String name, String description) {
		if ((name == null) || (description == null))
			throw new IllegalArgumentException();

		this.name = name;
		this.description = description;
		this.exitThresholdFlag = false;

		locations = new EnumMap<Direction, Location>(Direction.class);
		items = new ItemRepository();
	}

	/**
	 * Constructs a location with a given name, a given description, a given
	 * exit threshold and a given exit message.
	 * 
	 * @param name
	 *            The name of the location
	 * @param description
	 *            The description of the location
	 * @param exitThreshold
	 *            The exit threshold of the location
	 * @param exitMessage
	 *            The exit message of the location
	 * @throws IllegalArgumentException
	 *             The arguments 'name', 'description' and 'exitMessage' cannot
	 *             be null.
	 */
	public Location(String name, String description, int exitThreshold,
			String exitMessage) {
		this(name, description);
		if ((name == null) || (description == null) || (exitMessage == null))
			throw new IllegalArgumentException();

		this.exitThreshold = exitThreshold;
		this.exitThresholdFlag = true;
		this.exitMessage = exitMessage;
	}

	/**
	 * Adds an item to this location.
	 * 
	 * @param item
	 *            The item.
	 * @throws ItemAlreadyInRepositoryException
	 *             The item is already in this repository.
	 */
	public void addItem(Item item) {
		if (items.hasItem(item)) {
			throw new ItemAlreadyInRepositoryException(
					"Error: ItemAlreadyInRepositoryException");
		} else
			items.addItem(item);
	}

	/**
	 * Gets a set of items from this location, identified by a common name. This
	 * method does not remove any item from the location.
	 * 
	 * @param name
	 *            The common name of the items.
	 * @return The set of items.
	 * @throws IllegalArgumentException
	 *             The argument 'name' cannot be null.
	 */
	public Set<Item> getItems(String name) {
		if (name == null)
			throw new IllegalArgumentException();
		return items.getItems(name);
	}

	/**
	 * Reports the names of all the items of the location.
	 * 
	 * @return The list of the names of all the items.
	 */
	public Set<Item> getAllItems() {
		return items.getAllItems();
	}

	/**
	 * Reports the name of this location.
	 * 
	 * @return The name.
	 */
	public String getName() {
		return name;
	}
	

	/**
	 * Reports the description of this location.
	 * 
	 * @return location The description.
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Checks whether there is a connected location in a given direction.
	 * 
	 * @param direction
	 *            The direction
	 * @return true if there is a connected location in that direction; false
	 *         otherwise.
	 * @throws IllegalArgumentException
	 *             The argument 'direction' cannot be null.
	 */
	public boolean hasConnectedLocation(Direction direction) {
		if (direction == null)
			throw new IllegalArgumentException();
		return (locations.get(direction) != null);
	}

	/**
	 * Checks if this location has exit threshold (and its corresponding exit
	 * message).
	 * 
	 * @return true if this location has exit threshold ; false otherwise.
	 */
	public boolean hasExitThreshold() {
		return (exitThresholdFlag);
	}

	/**
	 * Gets the exit threshold for the location. If the score of the player is
	 * greater or equals to the exit threshold of the player location, the game
	 * ends and the exit message is shown.
	 * 
	 * @return The exit threshold.
	 * @throws NoExitThresholdException
	 *             This location has no exit threshold.
	 */
	public int getExitThreshold() {
		if (!exitThresholdFlag)
			throw new NoExitThresholdException(
					"Error: NoExitThresholdException");
		return exitThreshold;
	}

	/**
	 * Reports the exit message for the location.
	 * 
	 * @return The exit message.
	 * @throws NoExitThresholdException
	 *             This location has no exit threshold.
	 */
	public String getExitMessage() {
		if (!exitThresholdFlag)
			throw new NoExitThresholdException(
					"Error: NoExitThresholdException");
		return exitMessage;
	}

	/**
	 * Gets the connected location to a given direction.
	 * 
	 * @param direction
	 *            The direction.
	 * @return The connected location in the given direction.
	 * @throws NoConnectedLocationException
	 *             There is no connected location to the player location in
	 *             direction .
	 * @throws IllegalArgumentException
	 *             The argument 'direction' cannot be null.
	 */
	public Location getConnectedLocation(Direction direction) {
		if (direction == null)
			throw new IllegalArgumentException();
		if (locations.get(direction) == null)
			throw new NoConnectedLocationException(
					"Error: NoConnectedLocationException");
		return locations.get(direction);
	}

	/**
	 * Removes an item from this location.
	 * 
	 * @param item
	 *            The item.
	 * @throws ItemNotInRepositoryException
	 *             The item is not in this repository.
	 */
	public void removeItem(Item item) {
		if (items.hasItem(item)) {
			items.removeItem(item);
		} else
			throw new ItemNotInRepositoryException(
					"Error: ItemNotInRepositoryException");
	}

	/**
	 * Checks whether this location contains an specific item.
	 * 
	 * @param item
	 *            The item.
	 * @return true if the location contains the item; false otherwise.
	 */
	public boolean hasItem(Item item) {
		return items.hasItem(item);
	}

	/**
	 * Sets the connections between this location and other one. This method
	 * overrides a possible connection already established in that direction.
	 * 
	 * @param direction
	 *            The direction.
	 * @param location
	 *            The other location.
	 * @throws IllegalArgumentException
	 *             The arguments 'direction' and 'location' cannot be null.
	 */
	public void setConnection(Direction direction, Location location) {
		if ((direction == null) || (location == null))
			throw new IllegalArgumentException();
		locations.put(direction, location);
	}


	/**
	 * Returns a String representation for this object: Location[]. This is
	 * useful for debugging purposes.
	 * 
	 * @see Object#toString()
	 */
	public String toString() {
		return this.getClass().getSimpleName() + "[" + getName() + "]";
	}
}