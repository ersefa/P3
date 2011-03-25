package es.ucm.fdi.lps.p3.command;

import java.util.Enumeration;
import java.util.LinkedHashSet;
import java.util.Properties;
import java.util.Scanner;
import java.util.Set;

import es.ucm.fdi.lps.p3.Game;
import es.ucm.fdi.lps.p3.Item;
import es.ucm.fdi.lps.p3.exception.UnexecutedCommandException;
import es.ucm.fdi.lps.p3.exception.UnparsedCommandException;

/**
 * Represents a Take command from the player that moves an item from the player
 * location to the player inventory.
 * 
 * <ul>
 * <li>It uses a command-specific property called "keyword.takeCommand" which
 * default value is "take".</li>
 * <li>It uses a command-specific property called "keyword.takeCommand.abbrev"
 * which default value is "t".</li>
 * <li>It uses a command-specific property called "keyword.takeCommand.alt"
 * which default value is "pick".</li>
 * <li>It uses a command-specific property called "message.takeCommand.help"
 * which default value is "(take|t|pick) <item name>".</li>
 * <li>It uses a command-specific property called
 * "message.takeCommand.itemNameNotInLocation" which default value is
 * "There is no item with that name in this location.".</li>
 * <li>It uses a command-specific property called
 * "message.takeCommand.itemNameRepeatedInLocation" which default value is
 * "There are several items with that name in this location.".</li>
 * <li>It uses a command-specific property called
 * "message.takeCommand.itemNameAlreadyInInventory" which default value is
 * "There is another item with that name in the inventory.".</li>
 * <li>It uses a command-specific property called "message.takeCommand.success"
 * which default value is "It has been taken.".</li>
 * <li>It uses a command-specific property called "message.takeCommand.failure"
 * which default value is "That item cannot be taken from this location.".</li>
 * <li>It uses a command-specific property called
 * "message.takeCommand.undoSuccess" which default value is
 * "It has returned to this location.".</li>
 * <li>It uses a command-specific property called
 * "message.takeCommand.undoFailure" which default value is
 * "The item cannot return to this location from the inventory.".</li>
 * <li>It uses a command-specific property called
 * "flag.takeCommand.allowFIFODisambiguationForItemNameRepeatedInLocation" which
 * default value is "false".</li>
 * <li>It uses a command-specific property called
 * "flag.takeCommand.allowRepetitionsInInventoryItemNames" which default value
 * is "false".</li>
 * </ul>
 */
public class TakeCommand extends Command {

	/**
	 * Platform-independent line separator
	 */
	private final String LINE_SEPARATOR = System
			.getProperty("line.separator");

	/**
	 * The TakeCommand keyword
	 * <ul>
	 * <li>This property is called "keyword.takeCommand" and its default value
	 * is "take".</li>
	 * </ul>
	 */
	private final String KEYWORD_TAKECOMMAND = "keyword.takeCommand";
	private String keywordTakeCommand;

	/**
	 * The TakeCommand keyword abbreviation
	 * <ul>
	 * <li>This property is called "keyword.takeCommand.abbrev" and its default
	 * value is "t".</li>
	 * </ul>
	 */
	private final String KEYWORD_TAKECOMMAND_ABBREV = "keyword.takeCommand.abbrev";
	private String keywordTakeCommandAbbrev;

	/**
	 * The TakeCommand keyword alternative
	 * <ul>
	 * <li>This property is called "keyword.takeCommand.alt" and its default
	 * value is "pick".</li>
	 * </ul>
	 */
	private final String KEYWORD_TAKECOMMAND_ALT = "keyword.takeCommand.alt";
	private String keywordTakeCommandAlt;

	/**
	 * The TakeCommand help message
	 * <ul>
	 * <li>This property is called "message.takeCommand.help" and its default
	 * value is "(take|t|pick) <item name>".</li>
	 * </ul>
	 */
	private final String MESSAGE_TAKECOMMAND_HELP = "message.takeCommand.help";
	private String msgTakeCommandHelp;

	/**
	 * The TakeCommand item not in this location message
	 * <ul>
	 * <li>This property is called "message.takeCommand.itemNameNotLocation" and
	 * its default value is "There is no item with that name in this location.".
	 * </li>
	 * </ul>
	 */
	private final String MESSAGE_TAKECOMMAND_ITEMNAMENOTINLOCATION = "message.takeCommand.itemNameNotInLocation";
	private String msgTakeCommandItemNameNotInLocation;

	/**
	 * The TakeCommand item name repeated in this location message
	 * <ul>
	 * <li>This property is called
	 * "message.takeCommand.itemNameRepeatedInLocation" and its default value is
	 * "There are several items with that name in this location.".</li>
	 * </ul>
	 */
	private final String MESSAGE_TAKECOMMAND_ITEMNAMEREPEATEDINLOCATION = "message.takeCommand.itemNameRepeatedInLocation";
	private String msgTakeCommandItemNameRepeatedInLocation;

	/**
	 * The TakeCommand item name already in the inventory message
	 * <ul>
	 * <li>This property is called
	 * "message.takeCommand.itemNameAlreadyInInventory" and its default value is
	 * "There is another item with that name in the inventory.".</li>
	 * </ul>
	 */
	private final String MESSAGE_TAKECOMMAND_ITEMNAMEALREADYININVENTORY = "message.takeCommand.itemNameAlreadyInInventory";
	private String msgTakeCommandItemNameAlreadyInInventory;

	/**
	 * The TakeCommand success message
	 * <ul>
	 * <li>This property is called "message.takeCommand.success" and its default
	 * value is "It has been taken.".</li>
	 * </ul>
	 */
	private final String MESSAGE_TAKECOMMAND_SUCCESS = "message.takeCommand.success";
	private String msgTakeCommandSuccess;

	/**
	 * The TakeCommand failure message
	 * <ul>
	 * <li>This property is called "message.takeCommand.failure" and its default
	 * value is "That item cannot be taken from this location.".</li>
	 * </ul>
	 */
	private final String MESSAGE_TAKECOMMAND_FAILURE = "message.takeCommand.failure";
	@SuppressWarnings("unused")
	private String msgTakeCommandFailure;

	/**
	 * The TakeCommand undo success message
	 * <ul>
	 * <li>This property is called "message.takeCommand.undoSuccess" and its
	 * default value is "It has returned to this location.".</li>
	 * </ul>
	 */
	private final String MESSAGE_TAKECOMMAND_UNDOSUCCESS = "message.takeCommand.undoSuccess";
	private String msgTakeCommandUndoSuccess;

	/**
	 * The TakeCommand undo failure message
	 * <ul>
	 * <li>This property is called "message.takeCommand.undoFailure" and its
	 * default value is
	 * "The item cannot return to this location from the inventory.".</li>
	 * </ul>
	 */
	private final String MESSAGE_TAKECOMMAND_UNDOFAILURE = "message.takeCommand.undoFailure";
	private String msgTakeCommandUndoFailure;

	/**
	 * The TakeCommand FIFO flag
	 * <ul>
	 * <li>This property is called
	 * "flag.takeCommand.allowFIFODisambiguationForItemNameRepeatedInLocation"
	 * and its default value is "false".</li>
	 * </ul>
	 */
	private final String FLAG_TAKECOMMAND_FIFO = "flag.takeCommand.allowFIFODisambiguationForItemNameRepeatedInLocation";
	private boolean flagTakeCommandFIFO;

	/**
	 * The TakeCommand allow repetition flag
	 * <ul>
	 * <li>This property is called
	 * "flag.takeCommand.allowRepetitionsInInventoryItemNames" and its default
	 * value is "false".</li>
	 * </ul>
	 */
	private final String FLAG_TAKECOMMAND_REPETITIONS = "flag.takeCommand.allowRepetitionsInInventoryItemNames";
	private boolean flagTakeCommandRepetitions;

	/**
	 * The name of the item to be taken
	 */
	private String itemName;
	private Item firstItem;

	/**
	 * Constructs a Take command (as an specific type of Command) that has
	 * access to a given game. Initially the command is unparsed and unexecuted.
	 * Default configuration is assumed at this moment.
	 * 
	 * @param game
	 *            The game.
	 * @throws IllegalArgumentException
	 *             The argument 'game' cannot be null.
	 */
	public TakeCommand(Game game) {
		super(game);
		if (game == null) {
			throw new IllegalArgumentException();
		}
		this.game = game;
		setDefaultConfiguration();
	}

	/**
	 * Constructs a Take command (as an specific type of Command) that has
	 * access to a given game. Initially the command is unparsed and unexecuted.
	 * The properties defined in the given configuration override those of the
	 * default configuration.
	 * 
	 * @param game
	 *            The game.
	 * @param config
	 *            The configuration.
	 * @throws IllegalArgumentException
	 *             The arguments 'game' and 'config' cannot be null.
	 */
	public TakeCommand(Game game, Properties config) {
		this(game);
		if (config == null) {
			throw new IllegalArgumentException();
		}
		this.config = config;
		setConfiguration();
	}

	private void setConfiguration() {
		for (Enumeration<Object> e = config.keys(); e.hasMoreElements();) {
			Object obj = e.nextElement();
			if (obj.toString().equalsIgnoreCase(KEYWORD_TAKECOMMAND))
				keywordTakeCommand = config.getProperty(obj.toString());
			else if (obj.toString()
					.equalsIgnoreCase(KEYWORD_TAKECOMMAND_ABBREV))
				keywordTakeCommandAbbrev = config.getProperty(obj.toString());
			else if (obj.toString().equalsIgnoreCase(KEYWORD_TAKECOMMAND_ALT))
				keywordTakeCommandAlt = config.getProperty(obj.toString());
			else if (obj.toString().equalsIgnoreCase(MESSAGE_TAKECOMMAND_HELP))
				msgTakeCommandHelp = config.getProperty(obj.toString());
			else if (obj.toString().equalsIgnoreCase(
					MESSAGE_TAKECOMMAND_ITEMNAMENOTINLOCATION))
				msgTakeCommandItemNameNotInLocation = config.getProperty(obj
						.toString());
			else if (obj.toString().equalsIgnoreCase(
					MESSAGE_TAKECOMMAND_ITEMNAMEREPEATEDINLOCATION))
				msgTakeCommandItemNameRepeatedInLocation = config
						.getProperty(obj.toString());
			else if (obj.toString().equalsIgnoreCase(
					MESSAGE_TAKECOMMAND_ITEMNAMEALREADYININVENTORY))
				msgTakeCommandItemNameAlreadyInInventory = config
						.getProperty(obj.toString());
			else if (obj.toString().equalsIgnoreCase(
					MESSAGE_TAKECOMMAND_SUCCESS))
				msgTakeCommandSuccess = config.getProperty(obj.toString());
			else if (obj.toString().equalsIgnoreCase(
					MESSAGE_TAKECOMMAND_FAILURE))
				msgTakeCommandFailure = config.getProperty(obj.toString());
			else if (obj.toString().equalsIgnoreCase(
					MESSAGE_TAKECOMMAND_UNDOSUCCESS))
				msgTakeCommandUndoSuccess = config.getProperty(obj.toString());
			else if (obj.toString().equalsIgnoreCase(
					MESSAGE_TAKECOMMAND_UNDOFAILURE))
				msgTakeCommandUndoFailure = config.getProperty(obj.toString());
			else if (obj.toString().equalsIgnoreCase(FLAG_TAKECOMMAND_FIFO))
				flagTakeCommandFIFO = Boolean.parseBoolean(config
						.getProperty(obj.toString()));
			else if (obj.toString().equalsIgnoreCase(
					FLAG_TAKECOMMAND_REPETITIONS))
				flagTakeCommandRepetitions = Boolean.parseBoolean(config
						.getProperty(obj.toString()));
		}
	}

	private void setDefaultConfiguration() {
		keywordTakeCommand = "take";
		keywordTakeCommandAbbrev = "t";
		keywordTakeCommandAlt = "pick";

		msgTakeCommandHelp = "(take|t|pick) <item name>";
		msgTakeCommandItemNameNotInLocation = "There is no item with that name in this location.";
		msgTakeCommandItemNameRepeatedInLocation = "There are several items with that name in this location.";
		msgTakeCommandItemNameAlreadyInInventory = "There is another item with that name in the inventory.";
		msgTakeCommandSuccess = "It has been taken.";
		msgTakeCommandFailure = "That item cannot be taken from this location.";
		msgTakeCommandUndoSuccess = "It has returned to this location.";
		msgTakeCommandUndoFailure = "The item cannot return to this location from the inventory.";

		flagTakeCommandFIFO = false;
		flagTakeCommandRepetitions = false;
	}

	/**
	 * Executes the Take command, moving the item from the player location to
	 * the player inventory, if it is possible. When the item name is repeated
	 * in the player location, this command may fail or allow a FIFO
	 * disambiguation (chosing the first item with that name that was added to
	 * the location), depending on the configuration. When there is already an
	 * item with that name in the inventory, this command may fail or succeed
	 * (taking the item anyway), depending on the configuration.
	 * 
	 * @see Command#execute()
	 */
	public boolean execute() {
		if (!parsed) {
			throw new UnparsedCommandException(
					"Error: UnparsedCommandException");
		}
		executed = true;

		Set<Item> itemsFoundedinInventory = new LinkedHashSet<Item>();
		Set<Item> itemsFoundedinLocation = new LinkedHashSet<Item>();

		// False = Si no hay ningún Item en la localización
		if (game.reportAllLocationItems().isEmpty()) {
			result = msgTakeCommandItemNameNotInLocation + LINE_SEPARATOR;
			return false;
		}

		itemsFoundedinLocation = game.getItemsFromLocation(itemName);

		// False = Si el elemento a coger no existe en la localizacion
		if (itemsFoundedinLocation.isEmpty()) {
			result = msgTakeCommandItemNameNotInLocation + LINE_SEPARATOR;
			return false;
		}

		// False = Tengo mas de un elemento en la localizacion
		if ((itemsFoundedinLocation.size() > 1) && (!flagTakeCommandFIFO)) {
			result = msgTakeCommandItemNameRepeatedInLocation + LINE_SEPARATOR;
			return false;
		}

		// Como al menos tengo un elemento con ese nombre en la localizacion
		// cojo el primero
		firstItem = (Item) itemsFoundedinLocation.toArray()[0];

		itemsFoundedinInventory = game.getItemsFromInventory(itemName);

		// False = El elemento que intento coger ya existe en el inventario
		// y no se permiten repeticiones
		if (itemsFoundedinInventory.size() >= 1 && !flagTakeCommandRepetitions) {
			result = msgTakeCommandItemNameAlreadyInInventory + LINE_SEPARATOR;;
			return false;
		}

		// True = Si he llegado hasta aqui es que puedo dropear el objeto
		game.moveItemFromLocationToInventory(firstItem);
		result = msgTakeCommandSuccess + LINE_SEPARATOR;
		return true;
	}

	/**
	 * Gets the help information about this Help command.
	 * 
	 * @see Command#getHelp()
	 */
	@Override
	public String getHelp() {
		return msgTakeCommandHelp;
	}

	/**
	 * Parses a text line trying to identify a player invocation to this Take
	 * command (e.g. "take sword").
	 * 
	 * @see Command#parse(String)
	 */
	@Override
	public boolean parse(String line) {
		Scanner reader = new Scanner(line);
		if (reader.hasNext()) {
			String firstCommand = reader.next();
			if ((firstCommand.equalsIgnoreCase(keywordTakeCommand)
					|| firstCommand.equalsIgnoreCase(keywordTakeCommandAbbrev) || firstCommand
					.equalsIgnoreCase(keywordTakeCommandAlt))
					&& reader.hasNext()) {
				parsed = true;
				itemName = reader.next();
				return true;
			}
		}
		return false;
	}

	/**
	 * Undoes the Take command execution, moving the item back from the player
	 * inventory to the player location, if it is possible.
	 * 
	 * @see Command#undo()
	 */
	public boolean undo() {
		if (!parsed)
			throw new UnparsedCommandException(
					"Error: UnparsedCommandException");
		if (!executed)
			throw new UnexecutedCommandException(
					"Error: UnexecutedCommandException");

		if (game.moveItemFromInventoryToLocation(firstItem)) {
			result = msgTakeCommandUndoSuccess + LINE_SEPARATOR;
			return true;
		} else {
			result = msgTakeCommandUndoFailure + LINE_SEPARATOR;
			return false;
		}

	}

	/**
	 * Returns a String representation for this object: TakeCommand[item]. This
	 * is useful for debugging purposes.
	 * 
	 * @see Command#toString()
	 */
	@Override
	public String toString() {
		return this.getClass().getSimpleName() + "[]";
	}
}