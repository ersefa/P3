package es.ucm.fdi.lps.p3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StreamTokenizer;
import java.util.EnumMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import es.ucm.fdi.lps.p3.command.Command;
import es.ucm.fdi.lps.p3.exception.InvalidGameDefinitionException;
import es.ucm.fdi.lps.p3.exception.ItemAlreadyInRepositoryException;
import es.ucm.fdi.lps.p3.exception.ItemNotInRepositoryException;
import es.ucm.fdi.lps.p3.exception.NoConnectedLocationException;
import es.ucm.fdi.lps.p3.exception.NoExecutedCommandsException;
import es.ucm.fdi.lps.p3.exception.NoGameEventsException;

/**
 * Represents the state of all the elements of the game (locations, items,
 * player inventory, etc.), and relevant constants for the game (the available
 * movement directions, the keywords for a game textual definition, etc.).
 */
public class Game {

	/**
	 * Platform-independent line separator
	 */
	private static final String LINE_SEPARATOR = System
			.getProperty("line.separator");

	/**
	 * Game title
	 */
	private String title;

	/**
	 * Game author
	 */
	private String author;

	/**
	 * Game description.
	 */
	private String description;

	/**
	 * Game initialLocation;
	 */
	private Location currentLocation;

	/**
	 * Player inventory
	 */
	private ItemRepository playerInventory;

	/**
	 * Successfully executed command history
	 */
	protected Vector<Command> commandHistory;

	/**
	 * Vector with all gameEvent messages.
	 */
	protected Vector<String> gameEvents;

	/**
	 * Flag that decides when the game has finished.
	 */
	private boolean quitFlag = false;

	/**
	 * Flag that points if there are new events to report from the game
	 */
	private boolean eventFlag;

	private Map<String, Location> locations;

	private Map<String, EnumMap<Direction, String>> connections;

	private EnumMap<Direction, String> tempDirection;

	private String locationID;

	/**
	 * Represents all the possible directions for the connections between
	 * locations: NORTH, NORTHEAST, EAST, SOUTHEAST, SOUTH, SOUTHWEST, WEST,
	 * NORTHWEST, UP, DOWN, IN and OUT.
	 */
	public enum Direction {
		NORTH("n") {
			@Override
			public Direction getOppositeDirection() {
				return SOUTH;
			}
		},
		NORTHEAST("ne") {
			@Override
			public Direction getOppositeDirection() {
				return SOUTHWEST;
			}
		},
		EAST("e") {
			@Override
			public Direction getOppositeDirection() {
				return WEST;
			}
		},
		SOUTHEAST("se") {
			@Override
			public Direction getOppositeDirection() {
				return NORTHWEST;
			}
		},
		SOUTH("s") {
			@Override
			public Direction getOppositeDirection() {
				return NORTH;
			}
		},
		SOUTHWEST("sw") {
			@Override
			public Direction getOppositeDirection() {
				return NORTHEAST;
			}
		},
		WEST("w") {
			@Override
			public Direction getOppositeDirection() {
				return EAST;
			}
		},
		NORTHWEST("nw") {
			@Override
			public Direction getOppositeDirection() {
				return SOUTHEAST;
			}
		},
		UP("u") {
			@Override
			public Direction getOppositeDirection() {
				return DOWN;
			}
		},
		DOWN("d") {
			@Override
			public Direction getOppositeDirection() {
				return UP;
			}
		},
		IN("i") {
			@Override
			public Direction getOppositeDirection() {
				return OUT;
			}
		},
		OUT("o") {
			@Override
			public Direction getOppositeDirection() {
				return IN;
			}
		};

		/**
		 * Contains the Direction shorter form
		 */
		private String dir;

		/**
		 * Direction enum constructor
		 * 
		 * @param dir
		 *            the shorter Direction form
		 */
		private Direction(String dir) {
			this.dir = dir;
		}

		/**
		 * Gets the keyword string used for representing connection between
		 * locations in a game definition.
		 * 
		 * @return The keyword.
		 */
		public String getKeyword() {
			return this.dir;
		}

		/**
		 * It returns the opposite direction of a given one
		 * 
		 * @return The opposite direction
		 */
		public abstract Direction getOppositeDirection();
	}

	/**
	 * Constructs a game, based on a textual definition of it. A game definition
	 * consists on: <br>
	 * <ul>
	 * <li>The keyword 'game' plus three strings with the title, the author/s
	 * and the initial description of the game.</li>
	 * <li>One or more location definitions (the first one will be considered
	 * the initial location). A location definition consists on:
	 * <ul>
	 * <li>The keyword 'location' plus a unique id for the location, two strings
	 * with the name and the description of the location plus (optionally) an
	 * exit threshold and the string of the corresponding exit message for this
	 * location.</li>
	 * <li>Zero or more item definitions. An item definition consists on:</li>
	 * <ul>
	 * <li>The keyword 'item' plus two strings and an integer with the name, the
	 * description and the value of the item.</li>
	 * </ul>
	 * <li>Zero or more connections to other locations. A connection consists
	 * on:
	 * <ul>
	 * <li>A direction keyword plus the id of the connected location.</li>
	 * </ul>
	 * </ul>
	 * </ul>
	 * 
	 * @param gameDefinition
	 *            The game definition.
	 * @throws IllegalArgumentException
	 *             The argument 'gameDefinition' cannot be null.
	 * @throws InvalidGameDefinitionException
	 *             The game definition is invalid so a new game cannot be
	 *             created with it. Additional information is provided about the
	 *             error found in the definition and its position (e.g.
	 *             "Error in Token[location], line 14: First-level structures should be locations."
	 *             ).
	 */
	public Game(InputStream gameDefinition)
			throws InvalidGameDefinitionException {
		if (gameDefinition == null)
			throw new IllegalArgumentException(
					"Error: IllegalArgumentException");

		locations = new Hashtable<String, Location>();
		connections = new Hashtable<String, EnumMap<Direction, String>>();
		commandHistory = new Vector<Command>();
		playerInventory = new ItemRepository();
		gameEvents = new Vector<String>();

		createGameDefinition(gameDefinition);
	}

	/**
	 * Constructs a game using some basic information (a given title, a given
	 * author and a given description) and an initial location.
	 * 
	 * @param title
	 *            The title.
	 * @param author
	 *            The author.
	 * @param description
	 *            The description.
	 * @param initialLocation
	 *            The initial location.
	 * @throws IllegalArgumentException
	 *             The arguments 'title', 'author', 'description' and
	 *             'initialLocation' cannot be null.
	 */
	public Game(String title, String author, String description,
			Location initialLocation) {
		if ((title == null) || (author == null) || (description == null)
				|| (initialLocation == null))
			throw new IllegalArgumentException();
		this.title = title;
		this.author = author;
		this.description = description;
		this.currentLocation = initialLocation;

		locations = new Hashtable<String, Location>();
		connections = new Hashtable<String, EnumMap<Direction, String>>();
		commandHistory = new Vector<Command>();
		playerInventory = new ItemRepository();
		gameEvents = new Vector<String>();
	}

	private void createGameDefinition(InputStream gameDefinition)
			throws InvalidGameDefinitionException {
		Reader r = new BufferedReader(new InputStreamReader(gameDefinition));
		StreamTokenizer st = new StreamTokenizer(r);
		boolean firstLineParsed = false;
		st.commentChar(35);
		st.quoteChar(34);

		try {
			st.nextToken();

			if (st.ttype == StreamTokenizer.TT_EOF)
				throw new InvalidGameDefinitionException(
						"Error in definitions game. Empty file");

			while (st.ttype != StreamTokenizer.TT_EOF) {
				if (st.ttype == StreamTokenizer.TT_WORD) {
					if ((!firstLineParsed) && !(st.sval.equals("game"))) {
						throw new InvalidGameDefinitionException(
								"Error in line: " + st.lineno()
										+ " in definitions game"
										+ LINE_SEPARATOR
										+ "First token must be *game*");
					}

					if (st.sval.equals("game") && (!firstLineParsed)) {
						createDescription(st);
						firstLineParsed = true;
					} else if (st.sval.equals("location")) {
						createLocation(st);
					} else
						throw new InvalidGameDefinitionException(
								"Error in line: "
										+ st.lineno()
										+ " in definitions game"
										+ LINE_SEPARATOR
										+ "Second token after *game* must be *location*");
				} else
					throw new InvalidGameDefinitionException("Error in line: "
							+ st.lineno() + " in definitions game"
							+ LINE_SEPARATOR + "Not a word");
			}
			linkLocations();
			r.close();
		} catch (IOException e) {
			throw new InvalidGameDefinitionException("Error in line: "
					+ st.lineno() + " in definitions game");
		} catch (NullPointerException e) {
			throw new InvalidGameDefinitionException("Error in line: "
					+ st.lineno() + " in definitions game");
		}
	}

	private void createDescription(StreamTokenizer st) throws IOException {

		st.nextToken();
		title = st.sval;
		st.nextToken();
		author = st.sval;
		st.nextToken();
		description = st.sval;
		st.nextToken();

	}

	private Location readLocation(StreamTokenizer st) throws IOException {

		st.nextToken();
		locationID = st.sval;
		st.nextToken();
		String locationName = st.sval;
		st.nextToken();
		String locationDescription = st.sval;

		st.nextToken();
		if (st.ttype == StreamTokenizer.TT_NUMBER) {
			int threshold = (int) st.nval;
			st.nextToken();
			String endMsg = st.sval;
			st.nextToken();
			Location tempLocation = new Location(locationName,
					locationDescription, threshold, endMsg);
			// Agregar localización inicial
			if (currentLocation == null)
				currentLocation = tempLocation;
			return tempLocation;
		} else {
			Location tempLocation = new Location(locationName,
					locationDescription);
			// Agregar localización inicial
			if (currentLocation == null)
				currentLocation = tempLocation;
			return tempLocation;
		}
	}

	private void readItems(StreamTokenizer st, Location tempLocation)
			throws IOException {

		if (st.sval.equals("item")) {
			while (st.sval.equals("item")) {
				st.nextToken();
				String itemName = st.sval;
				st.nextToken();
				String itemDescription = st.sval;
				st.nextToken();
				int itemValue = (int) st.nval;
				st.nextToken();

				tempLocation.addItem(new Item(itemName, itemDescription,
						itemValue));
			}
		}
	}

	private void readConnections(StreamTokenizer st) throws IOException {
		while (!st.sval.equals("location")) {
			String shortDirection = st.sval;
			st.nextToken();
			String connectedLocationID = st.sval;
			for (Direction dir : Direction.values()) {
				if (dir.getKeyword().equals(shortDirection)) {
					tempDirection.put(dir, connectedLocationID);
					break;
				}
			}
			if (st.nextToken() == StreamTokenizer.TT_EOF) {
				break;
			}
		}
	}

	private void createLocation(StreamTokenizer st) throws IOException {
		tempDirection = new EnumMap<Direction, String>(Direction.class);

		Location tempLocation = readLocation(st);
		readItems(st, tempLocation);
		readConnections(st);

		connections.put(locationID, tempDirection);
		locations.put(locationID, tempLocation);
	}

	private void linkLocations() {
		Location connectedLocation;
		String connectedLocationName;
		tempDirection = new EnumMap<Direction, String>(Direction.class);

		for (String name : locations.keySet()) {
			tempDirection = connections.get(name);

			for (Direction dir : Direction.values()) {
				if (tempDirection.containsKey(dir)) {
					connectedLocationName = tempDirection.get(dir);
					if (locations.containsKey(connectedLocationName)) {
						connectedLocation = locations
								.get(connectedLocationName);
						locations.get(name).setConnection(dir,
								connectedLocation);
					}
				}
			}
		}
	}

	/**
	 * Adds an executed command to the command history.
	 * 
	 * @param command
	 *            The executed command.
	 * @throws IllegalArgumentException
	 *             The argument 'command' cannot be null.
	 */
	public void addExecutedCommand(Command command) {
		if (command == null)
			throw new IllegalArgumentException();
		commandHistory.add(command);
	}

	/**
	 * Clears the history of executed commands.
	 */
	public void clearExecutedCommands() {
		commandHistory.clear();
	}

	/**
	 * Gets the number of executed commands that are in the command history.
	 * 
	 * @return The number of executed commands.
	 */
	public int getNumberOfExecutedCommands() {
		return commandHistory.size();
	}

	/**
	 * Removes the newest executed command of the command history.
	 * 
	 * @return The command.
	 * @throws NoExecutedCommandsException
	 *             There are not executed commands in the command history so the
	 *             newest executed command cannot be removed.
	 */
	public Command removeNewestExecutedCommand() {
		if (commandHistory.isEmpty())
			throw new NoExecutedCommandsException(
					"Error: NoExecutedCommandsException");
		Command temp = commandHistory.lastElement();
		commandHistory.remove(commandHistory.lastElement());
		return temp;
	}

	/**
	 * Removes the oldest executed command of the command history.
	 * 
	 * @return The command.
	 * @throws NoExecutedCommandsException
	 *             There are not executed commands in the command history so the
	 *             oldest executed command cannot be removed.
	 */
	public Command removeOldestExecutedCommand() {
		if (commandHistory.isEmpty())
			throw new NoExecutedCommandsException(
					"Error: NoExecutedCommandsException");
		Command temp = commandHistory.firstElement();
		commandHistory.remove(commandHistory.firstElement());
		return temp;
	}

	/**
	 * Reports the basic information of the game (title, author and
	 * description).
	 * 
	 * @return The basic information of the game.
	 */
	public String reportInformation() {
		return title + LINE_SEPARATOR + author + LINE_SEPARATOR + description;
	}

	/**
	 * Reports the value of the player inventory.
	 * 
	 * @return The value.
	 */
	public int reportInventoryValue() {
		return playerInventory.getTotalValue();
	}

	/**
	 * Checks whether the player location has a connected location in a given
	 * direction.
	 * 
	 * @param direction
	 *            The direction.
	 * @return true if there is a connected location in the given direction,
	 *         false otherwise.
	 * @throws IllegalArgumentException
	 *             The argument 'direction' cannot be null.
	 */
	public boolean hasConnectedLocation(Direction direction) {
		if (direction == null)
			throw new IllegalArgumentException();
		return currentLocation.hasConnectedLocation(direction);
	}

	/**
	 * Moves the player to a location connected to the player location in a
	 * given direction.
	 * 
	 * @param direction
	 *            The direction.
	 * @return true if the player was correctly moved; false otherwise.
	 * @throws IllegalArgumentException
	 *             The argument 'direction' cannot be null.
	 * @throws NoConnectedLocationException
	 *             There is no connected location to the player location in
	 *             direction .
	 */
	public boolean movePlayer(Direction direction) {
		if (direction == null)
			throw new IllegalArgumentException();

		if (currentLocation.hasConnectedLocation(direction)) {
			currentLocation = currentLocation.getConnectedLocation(direction);
			if (currentLocation.hasExitThreshold()) {
				if (playerInventory.getTotalValue() >= currentLocation
						.getExitThreshold()) {
					this.eventFlag = true;
					gameEvents.add(currentLocation.getExitMessage());
					end();
				}
			}
			return true;
		} else
			throw new NoConnectedLocationException(
					"Error: NoConnectedLocationException");
	}

	/**
	 * Checks whether there are new events to report from the game.
	 * 
	 * @return true if there are events to report; false otherwise.
	 */
	public boolean hasEvents() {
		return eventFlag;
	}

	/**
	 * Reports the new events from the game. These 'events' are implemented as a
	 * simple text that can be shown to the player, adding some extra
	 * information to the result of a previously executed command (i.e. the exit
	 * message of a location where the game ends, after a Go command).
	 * 
	 * @return The events.
	 * @throws NoGameEventsException
	 *             The game has no new events to report.
	 */
	public String reportEvents() {
		if (gameEvents.isEmpty())
			throw new NoGameEventsException("Error: NoGameEventsException");
		String temp = "";
		boolean firstElementReaded = false;
		for (String it : gameEvents) {
			if (!firstElementReaded) {
				temp += it;
				firstElementReaded = true;
			} else {
				temp += LINE_SEPARATOR + it;
			}
		}
		return temp;
	}

	/**
	 * Clears the events to report from the game .
	 */
	public void clearEvents() {
		gameEvents.clear();
	}

	/**
	 * Reports the description of the player location.
	 * 
	 * @return The description.
	 */
	public String reportLocationDescription() {
		return currentLocation.getDescription();
	}

	/**
	 * Reports the name of the player location.
	 * 
	 * @return The name.
	 */
	public String reportLocationName() {
		return currentLocation.getName();
	}

	/**
	 * Reports all the items of the player location.
	 * 
	 * @return The set of items.
	 */
	public Set<Item> reportAllLocationItems() {
		return currentLocation.getAllItems();
	}

	/**
	 * Moves an item from the player location to the player inventory.
	 * 
	 * @param item
	 *            The item.
	 * @return true if the item was correctly moved; false otherwise.
	 * @throws IllegalArgumentException
	 *             The argument 'item' cannot be null.
	 * @throws ItemAlreadyInRepositoryException
	 *             The item is already in this repository.
	 * @throws ItemNotInRepositoryException
	 *             The item is not in this repository.
	 */
	public boolean moveItemFromLocationToInventory(Item item) {
		if (item == null)
			throw new IllegalArgumentException();

		if (!playerInventory.hasItem(item)) {
			if (currentLocation.hasItem(item)) {
				playerInventory.addItem(item);
				currentLocation.removeItem(item);
				return true;
			} else
				throw new ItemNotInRepositoryException(
						"Error: ItemNotInRepositoryException");
		} else
			throw new ItemAlreadyInRepositoryException(
					"Error: ItemAlreadyInRepositoryException");
	}

	/**
	 * Moves an item from the player inventory to the player location.
	 * 
	 * @param item
	 *            The item.
	 * @return true if the item was correctly moved; false otherwise.
	 * @throws IllegalArgumentException
	 *             The argument 'item' cannot be null.
	 * @throws ItemAlreadyInRepositoryException
	 *             The item is already in this repository.
	 * @throws ItemNotInRepositoryException
	 *             The item is not in this repository.
	 */
	public boolean moveItemFromInventoryToLocation(Item item) {
		if (item == null)
			throw new IllegalArgumentException();

		if (playerInventory.hasItem(item)) {
			if (!currentLocation.hasItem(item)) {
				currentLocation.addItem(item);
				playerInventory.removeItem(item);
				return true;
			} else
				throw new ItemAlreadyInRepositoryException(
						"Error: ItemAlreadyInRepositoryException");
		} else
			throw new ItemNotInRepositoryException(
					"Error: ItemNotInRepositoryException");
	}

	/**
	 * Checks whether an specific item is in the player inventory.
	 * 
	 * @param item
	 *            The item.
	 * @return true if the item is in the player inventory; false otherwise.
	 * @throws IllegalArgumentException
	 *             The argument 'item' cannot be null.
	 */
	public boolean isItemInInventory(Item item) {
		if (item == null) {
			throw new IllegalArgumentException(
					"Error: IllegalArgumentException");
		}
		return playerInventory.hasItem(item);
	}

	/**
	 * Checks whether an specific item is in the player location.
	 * 
	 * @param item
	 *            The item.
	 * @return true if the item is in the player location; false otherwise.
	 * @throws IllegalArgumentException
	 *             The argument 'item' cannot be null.
	 */
	public boolean isItemInLocation(Item item) {
		if (item == null) {
			throw new IllegalArgumentException(
					"Error: IllegalArgumentException");
		}
		return currentLocation.hasItem(item);
	}

	/**
	 * Gets items with a common name from the player inventory.
	 * 
	 * @param name
	 *            The common name.
	 * @return The set of items with a common name.
	 * @throws IllegalArgumentException
	 *             The argument 'name' cannot be null.
	 */
	public Set<Item> getItemsFromInventory(String name) {
		if (name == null)
			throw new IllegalArgumentException();
		return playerInventory.getItems(name);
	}

	/**
	 * Reports all the items of the player inventory.
	 * 
	 * @return The set of items.
	 */
	public Set<Item> reportAllInventoryItems() {
		return playerInventory.getAllItems();
	}

	/**
	 * Gets items with a common name from the player location.
	 * 
	 * @param name
	 *            The common name.
	 * @return The set of items with a common name.
	 * @throws IllegalArgumentException
	 *             The argument 'name' cannot be null.
	 */
	public Set<Item> getItemsFromLocation(String name) {
		if (name == null)
			throw new IllegalArgumentException();
		return currentLocation.getItems(name);
	}

	/**
	 * Checks whether the game is ended.
	 * 
	 * @return true if the game is ended; false otherwise.
	 */
	public boolean isEnded() {
		return quitFlag;
	}

	/**
	 * Ends the game.
	 */
	public void end() {
		quitFlag = true;
	}

	/**
	 * Returns a String representation for this object
	 * 
	 * @see Object#toString()
	 */
	public String toString() {
		return this.getClass().getSimpleName() + "[" + title + "]";
	}

	/**
	 * Transforms a set into a item String list
	 * 
	 * @param set
	 *            The item set
	 * @return The String list
	 */
	public String setToString(Set<Item> set, boolean flagValue) {
		if (set == null)
			throw new IllegalArgumentException();

		boolean firstItemReaded = false;
		Iterator<Item> itr = set.iterator();
		String temp = "";
		Item it;

		if (flagValue) {
			while (itr.hasNext()) {
				it = itr.next();
				if (!firstItemReaded) {
					temp += it.getName() + " (" + it.getValue() + ")";
					firstItemReaded = true;
				} else
					temp += ", " + it.getName() + " (" + it.getValue() + ")";
			}
		} else
			while (itr.hasNext()) {
				it = itr.next();
				if (!firstItemReaded) {
					temp += it.getName();
					firstItemReaded = true;
				} else
					temp += ", " + it.getName();
			}
		temp += ".";
		return temp;
	}
}
