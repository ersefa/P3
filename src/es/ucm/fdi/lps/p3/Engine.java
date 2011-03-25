package es.ucm.fdi.lps.p3;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Enumeration;
import java.util.Properties;

import es.ucm.fdi.lps.p3.command.Command;

/**
 * Represents the game engine that controls the execution of the game since the
 * beginning to the end, dealing with the configuration, the input stream and
 * the output stream.
 */
public class Engine {

	/**
	 * Platform-independent line separator
	 */
	private final String LINE_SEPARATOR = System.getProperty("line.separator");

	/**
	 * Message of prompt for the player (the name of the property).
	 * <ul>
	 * <li>This property is called "message.prompt" and its default value is
	 * "> ".</li>
	 * </ul>
	 */
	public static final String MESSAGE_PROMPT = "message.prompt";
	public String msgPrompt;

	/**
	 * Message of unknown command (the name of the property).
	 * <ul>
	 * <li>This property is called "message.unknownCommand" and its default
	 * value is "Pardon?".</li>
	 * </ul>
	 */
	public static final String MESSAGE_UNKNOWNCOMMAND = "message.unknownCommand";
	public String msgUnknownCommand;

	/**
	 * Message of help information from the engine (the name of the property).
	 * <ul>
	 * <li>This property is called "message.engineHelp" and its default value is
	 * "These are the available player commands:".</li>
	 * </ul>
	 */
	public static final String MESSAGE_ENGINEHELP = "message.engineHelp";
	public String msgEngineHelp;

	/**
	 * Message of location with items (the name of the property).
	 * <ul>
	 * <li>This property is called "message.locationWithItems" and its default
	 * value is "This location contains the following items: ".</li>
	 * </ul>
	 */
	public static final String MESSAGE_LOCATIONWITHITEMS = "message.locationWithItems";
	public String msgLocationWithItems;

	/**
	 * Message of location without items (the name of the property).
	 * <ul>
	 * <li>This property is called "message.locationWithoutItems" and its
	 * default value is "This location has no items.".</li>
	 * </ul>
	 */
	public static final String MESSAGE_LOCATIONWITHOUTITEMS = "message.locationWithoutItems";
	public String msgLocationWithoutItems;

	/**
	 * Message of the player score (the name of the property).
	 * <ul>
	 * <li>This property is called "message.playerScore" and its default value
	 * is "Player score: ".</li>
	 * </ul>
	 */
	public static final String MESSAGE_PLAYERSCORE = "message.playerScore";
	public String msgPlayerScore;

	/**
	 * Message of game over (the name of the property).
	 * <ul>
	 * <li>This property is called "message.gameOver" and its default value is
	 * "GAME OVER".</li>
	 * </ul>
	 */
	public static final String MESSAGE_GAMEOVER = "message.gameOver";
	public String msgGameOver;

	/**
	 * Flag for showing engine information (the name of the property).
	 * <ul>
	 * <li>This property is called "flag.showEngineInfo" and its default value
	 * is "true".</li>
	 * </ul>
	 */
	public static final String FLAG_SHOWENGINEINFO = "flag.showEngineInfo";
	public boolean flagShowEngineInfo;

	/**
	 * Flag for showing game information (the name of the property).
	 * <ul>
	 * <li>This property is called "flag.showGameInfo" and its default value is
	 * "true".</li>
	 * </ul>
	 */
	public static final String FLAG_SHOWGAMEINFO = "flag.showGameInfo";
	public boolean flagShowGameInfo;

	/**
	 * Flag for auto-describing first location (the name of the property).
	 * <ul>
	 * <li>This property is called "flag.autodescribeFirstLocation" and its
	 * default value is "true".</li>
	 * </ul>
	 */
	public static final String FLAG_AUTODESCRIBEFIRSTLOCATION = "flag.autodescribeFirstLocation";
	public boolean flagAutodescribeFirstLocation;

	/**
	 * Flag for showing the items of each location (the name of the property).
	 * <ul>
	 * <li>This property is called "flag.showLocationItems" and its default
	 * value is "true".</li>
	 * </ul>
	 */
	public static final String FLAG_SHOWLOCATIONITEMS = "flag.showLocationItems";
	public boolean flagShowLocationItems;

	/**
	 * Flag for showing the numerical value of the game items (the name of the
	 * property).
	 * <ul>
	 * <li>This property is called "flag.showItemValues" and its default value
	 * is "true".</li>
	 * </ul>
	 */
	public static final String FLAG_SHOWITEMVALUES = "flag.showItemValues";
	public boolean flagShowItemValues;

	/**
	 * Limit of command history size for the undo command (the name of the
	 * property).
	 * <ul>
	 * <li>This property is called "limit.commandHistorySize" and its default
	 * value is "1".</li>
	 * </ul>
	 */
	public static final String LIMIT_COMMANDHISTORYSIZE = "limit.commandHistorySize";
	public int limitCommandHistorySize;

	/**
	 * A reference to the game that created the Interpreter
	 */
	private Game game;

	/**
	 * The engine configuration
	 */
	private Properties config;

	/**
	 * The engine description
	 */
	private String engineInfo = "GAME ENGINE FOR TEXT ADVENTURES"
			+ LINE_SEPARATOR + "Version 1.0 (January 2011)" + LINE_SEPARATOR
			+ "Designed by Guillermo Jiménez and Federico Peinado";

	/**
	 * Print stream
	 */
	private PrintStream ps;

	private Parser parser;
	private InputStream input;

	/**
	 * Constructs the game engine using a given game. Default configuration,
	 * standard input and standard output are assumed at this moment.
	 * 
	 * @param game
	 *            The game.
	 * @throws IllegalArgumentException
	 *             The argument 'game' cannot be null.
	 */
	public Engine(Game game) {
		if (game == null)
			throw new IllegalArgumentException();
		this.game = game;
		setInput(System.in);
		setOutput(System.out);
		parser = new Parser(this.input, this.game);
		setDefaultConfiguration();
	}

	/**
	 * Constructs the game engine using a given game and a given configuration.
	 * The properties defined in the given configuration override those of the
	 * default configuration. Standard input and standard output are assumed at
	 * this moment.
	 * 
	 * @param game
	 *            The game.
	 * @param config
	 *            The configuration.
	 * @throws IllegalArgumentException
	 *             The arguments 'game' and 'config' cannot be null.
	 */
	public Engine(Game game, Properties config) {
		if (config == null) {
			throw new IllegalArgumentException();
		}
		this.game = game;
		this.config = config;
		setInput(System.in);
		setOutput(System.out);
		setDefaultConfiguration();
		setConfig(this.config);
		parser = new Parser(this.input, this.game, this.config);
	}

	/**
	 * Constructs the game engine using a given game and a given input stream.
	 * Default configuration and standard output are assumed at this moment.
	 * 
	 * @param game
	 *            The game.
	 * @param input
	 *            The input.
	 * @throws IllegalArgumentException
	 *             The arguments 'game' and 'input' cannot be null.
	 */
	public Engine(Game game, InputStream input) {
		if (input == null) {
			throw new IllegalArgumentException();
		}
		this.game = game;
		setInput(input);
		setOutput(System.out);
		setDefaultConfiguration();
		parser = new Parser(this.input, this.game);
	}

	/**
	 * Constructs the game engine using a given game and a given output stream.
	 * Default configuration and standard input are assumed at this moment.
	 * 
	 * @param game
	 *            The game.
	 * @param output
	 *            The output.
	 * @throws IllegalArgumentException
	 *             The arguments 'game' and 'output' cannot be null.
	 */
	public Engine(Game game, OutputStream output) {
		if (output == null) {
			throw new IllegalArgumentException();
		}
		this.game = game;
		setInput(System.in);
		setOutput(output);
		setDefaultConfiguration();
		parser = new Parser(this.input, this.game);
	}

	/**
	 * Constructs the game engine using a given game, a given input stream and a
	 * given output stream. Default configuration is assumed at this moment.
	 * 
	 * @param game
	 *            The game.
	 * @param input
	 *            The input.
	 * @param output
	 *            The output.
	 * @throws IllegalArgumentException
	 *             The arguments 'game', 'input' and 'output' cannot be null.
	 */
	public Engine(Game game, InputStream input, OutputStream output) {
		if ((input == null) || (output == null))
			throw new IllegalArgumentException();
		this.game = game;
		setInput(input);
		setOutput(output);
		setDefaultConfiguration();
		parser = new Parser(this.input, this.game);
	}

	/**
	 * Constructs the game engine using a given game, a given configuration and
	 * a given output stream. Standard output is assumed at this moment.
	 * 
	 * @param game
	 *            The game.
	 * @param config
	 *            The configuration.
	 * @param input
	 *            The input.
	 * @throws IllegalArgumentException
	 *             The arguments 'game', 'config' and 'input' cannot be null.
	 */
	public Engine(Game game, Properties config, InputStream input) {
		if (input == null) {
			throw new IllegalArgumentException();
		}
		this.game = game;
		this.config = config;
		setInput(input);
		setOutput(System.out);
		setDefaultConfiguration();
		setConfig(this.config);
		parser = new Parser(this.input, this.game, this.config);
	}

	/**
	 * Constructs the game engine using a given game, a given configuration and
	 * a given input stream. Standard input is assumed at this moment.
	 * 
	 * @param game
	 *            The game.
	 * @param config
	 *            The configuration.
	 * @param output
	 *            The output.
	 * @throws IllegalArgumentException
	 *             The arguments 'game', 'config' and 'output' cannot be null.
	 */
	public Engine(Game game, Properties config, OutputStream output) {
		if (output == null) {
			throw new IllegalArgumentException();
		}
		this.game = game;
		this.config = config;
		setInput(System.in);
		setOutput(output);
		setDefaultConfiguration();
		setConfig(this.config);
		parser = new Parser(this.input, this.game, this.config);
	}

	/**
	 * Constructs the game engine using a given game, a given configuration, a
	 * given input stream and a given output stream.
	 * 
	 * @param game
	 *            The game.
	 * @param config
	 *            The configuration.
	 * @param input
	 *            The input.
	 * @param output
	 *            The output.
	 * @throws IllegalArgumentException
	 *             The arguments 'game', 'config', 'input' and 'output' cannot
	 *             be null.
	 */
	public Engine(Game game, Properties config, InputStream input,
			OutputStream output) {
		if ((input == null) || (output == null))
			throw new IllegalArgumentException();
		this.game = game;
		this.config = config;
		setInput(input);
		setOutput(output);
		setDefaultConfiguration();
		setConfig(this.config);
		parser = new Parser(this.input, this.game, this.config);
	}

	private void setDefaultConfiguration() {
		msgPrompt = "> ";
		msgUnknownCommand = "Pardon?";
		msgEngineHelp = "These are the available player commands: ";
		msgLocationWithItems = "This location contains the following items: ";
		msgLocationWithoutItems = "This location has no items.";
		msgPlayerScore = "Player score: ";
		msgGameOver = "GAME OVER";

		flagShowEngineInfo = true;
		flagShowGameInfo = true;
		flagAutodescribeFirstLocation = true;
		flagShowLocationItems = true;
		flagShowItemValues = true;

		limitCommandHistorySize = 1;
	}

	/**
	 * Sets a new configuration. Properties that are not overridden maintain
	 * their previous values.
	 * 
	 * @param config
	 *            The config.
	 * @throws IllegalArgumentException
	 *             The argument 'config' cannot be null.
	 */
	public void setConfig(Properties config) {
		if (config == null) {
			throw new IllegalArgumentException();
		}

		for (Enumeration<Object> e = config.keys(); e.hasMoreElements();) {
			Object obj = e.nextElement();
			if (obj.toString().equalsIgnoreCase(MESSAGE_PROMPT))
				msgPrompt = config.getProperty(obj.toString());
			else if (obj.toString().equalsIgnoreCase(MESSAGE_UNKNOWNCOMMAND))
				msgUnknownCommand = config.getProperty(obj.toString());
			else if (obj.toString().equalsIgnoreCase(MESSAGE_ENGINEHELP))
				msgEngineHelp = config.getProperty(obj.toString());
			else if (obj.toString().equalsIgnoreCase(MESSAGE_LOCATIONWITHITEMS))
				msgLocationWithItems = config.getProperty(obj.toString());
			else if (obj.toString().equalsIgnoreCase(
					MESSAGE_LOCATIONWITHOUTITEMS))
				msgLocationWithoutItems = config.getProperty(obj.toString());
			else if (obj.toString().equalsIgnoreCase(MESSAGE_PLAYERSCORE))
				msgPlayerScore = config.getProperty(obj.toString());
			else if (obj.toString().equalsIgnoreCase(MESSAGE_GAMEOVER))
				msgGameOver = config.getProperty(obj.toString());
			else if (obj.toString().equalsIgnoreCase(FLAG_SHOWENGINEINFO))
				flagShowEngineInfo = Boolean.parseBoolean(config
						.getProperty(obj.toString()));
			else if (obj.toString().equalsIgnoreCase(FLAG_SHOWGAMEINFO))
				flagShowGameInfo = Boolean.parseBoolean(config.getProperty(obj
						.toString()));
			else if (obj.toString().equalsIgnoreCase(
					FLAG_AUTODESCRIBEFIRSTLOCATION))
				flagAutodescribeFirstLocation = Boolean.parseBoolean(config
						.getProperty(obj.toString()));
			else if (obj.toString().equalsIgnoreCase(FLAG_SHOWLOCATIONITEMS))
				flagShowLocationItems = Boolean.parseBoolean(config
						.getProperty(obj.toString()));
			else if (obj.toString().equalsIgnoreCase(FLAG_SHOWITEMVALUES))
				flagShowItemValues = Boolean.parseBoolean(config
						.getProperty(obj.toString()));
			else if (obj.toString().equalsIgnoreCase(LIMIT_COMMANDHISTORYSIZE))
				limitCommandHistorySize = Integer.parseInt(config
						.getProperty(obj.toString()));
		}
	}

	/**
	 * Sets the input stream for the game engine.
	 * 
	 * @param input
	 *            The input.
	 * @throws IllegalArgumentException
	 *             The argument 'input' cannot be null.
	 */
	public void setInput(InputStream input) {
		if (input == null) {
			throw new IllegalArgumentException(
					"Error: IllegalArgumentException");
		}
		this.input = input;
	}

	/**
	 * Sets the output stream for the game engine.
	 * 
	 * @param output
	 *            The output.
	 * @throws IllegalArgumentException
	 *             The argument 'output' cannot be null.
	 */
	public void setOutput(OutputStream output) {
		if (output == null) {
			throw new IllegalArgumentException(
					"Error: IllegalArgumentException");
		}
		ps = new PrintStream(output);
	}


	private void printEvents() {
		if (flagShowItemValues) {
			ps.println(LINE_SEPARATOR + msgPlayerScore
					+ game.reportInventoryValue() + LINE_SEPARATOR
					+ msgGameOver);
		} else {
			ps.println(msgGameOver);
		}
	}
	
	/**
	 * Runs the main loop of the game execution. Firstly (before entering into
	 * the loop) the description of the engine and the description of the game
	 * can be shown (depending on the configuration). Then the description of
	 * the initial location or the description plus the items that are in that
	 * location can be shown (depending on the configuration). Finally three
	 * steps are repeated until the game ends:
	 * <ol type=”1” start=”1”>
	 * <li>Parsing the input (i.e. identifying a valid next command),</li>
	 * <li>Trying to execute the next command (recording it if it was executed
	 * successfully) and</li>
	 * <li>Reporting the result of the command (and the events that have ocurred
	 * in the game after the command execution) to the output stream.</li>
	 * </ol>
	 * At the end of the game the player score can be shown (depending on the
	 * configuration).
	 */
	public void run() {
		Command command = null;
		boolean executed = false;
		game.clearExecutedCommands();
		game.clearEvents();

		if (flagShowEngineInfo) {
			ps.println(engineInfo);
			ps.println();
		}

		if (flagShowGameInfo) {
			ps.println(game.reportInformation());
			ps.println();
		}

		if (flagAutodescribeFirstLocation) {
			ps.println(game.reportLocationName());
			ps.println(game.reportLocationDescription());
			ps.println();
		}

		if ((game.reportAllLocationItems().isEmpty())
				&& (flagShowLocationItems)) {
			ps.println(msgLocationWithoutItems);
			ps.println();
		} else if ((!game.reportAllLocationItems().isEmpty())
				&& (flagShowLocationItems)) {
			ps.println(msgLocationWithItems
					+ game.setToString(game.reportAllLocationItems(),
							flagShowItemValues));
			ps.println();
		}

		while (!game.isEnded()) {
			ps.println(msgPrompt);
			command = parser.parseNextCommand();

			if (command != null) {
				executed = command.execute();
				if (executed) {
					if (game.getNumberOfExecutedCommands() < limitCommandHistorySize) {
						game.addExecutedCommand(command);
					} else {
						game.removeOldestExecutedCommand();
						game.addExecutedCommand(command);
					}
				}
				if (command.hasResult())
					ps.println(command.getResult());
				if (game.hasEvents()){
					ps.println(game.reportEvents());
					printEvents();
				}
			} else
				ps.println(msgUnknownCommand + LINE_SEPARATOR);
		}
	}
}

