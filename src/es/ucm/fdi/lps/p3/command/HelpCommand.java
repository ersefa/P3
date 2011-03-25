package es.ucm.fdi.lps.p3.command;

import java.util.Enumeration;
import java.util.Properties;
import java.util.Scanner;

import es.ucm.fdi.lps.p3.Game;
import es.ucm.fdi.lps.p3.Parser;
import es.ucm.fdi.lps.p3.exception.UnparsedCommandException;

/**
 * Represents a Help command from the player that shows the help information of
 * the game.
 * 
 * <ul>
 * <li>It uses a command-specific property called "keyword.helpCommand" which
 * default value is "help".</li>
 * <li>It uses a command-specific property called "keyword.helpCommand.abbrev"
 * which default value is "info".</li>
 * <li>It uses a command-specific property called "keyword.helpCommand.alt"
 * which default value is "about".</li>
 * <li>It uses a command-specific property called "message.helpCommand.help"
 * which default value is "help|info|about".</li>
 * </ul>
 */
public class HelpCommand extends Command {
	
	private final String LINE_SEPARATOR = System
			.getProperty("line.separator");

	/**
	 * The HelpCommand keyword
	 * <ul>
	 * <li>This property is called "keyword.helpCommand" and its default value
	 * is "help".</li>
	 * </ul>
	 */
	private final String KEYWORD_HELPCOMMAND = "keyword.helpCommand";
	private String keywordHelpCommand;

	/**
	 * The HelpCommand keyword abbreviation
	 * <ul>
	 * <li>This property is called "keyword.helpCommand.abbrev" and its default
	 * value is "info".</li>
	 * </ul>
	 */
	private final String KEYWORD_HELPCOMMAND_ABBREV = "keyword.helpCommand.abbrev";
	private String keywordHelpCommandAbbrev;

	/**
	 * The HelpCommand keyword alternative
	 * <ul>
	 * <li>This property is called "keyword.helpCommand.alt" and its default
	 * value is "about".</li>
	 * </ul>
	 */
	private final String KEYWORD_HELPCOMMAND_ALT = "keyword.helpCommand.alt";
	private String keywordHelpCommandAlt;

	/**
	 * The HelpCommand help message
	 * <ul>
	 * <li>This property is called "message.helpCommand.help" and its default
	 * value is "help|info|about".</li>
	 * </ul>
	 */
	private final String MESSAGE_HELPCOMMAND_HELP = "message.helpCommand.help";
	private String msgHelpCommandHelp;

	/**
	 * The HelpCommand help message
	 * <ul>
	 * <li>This property is called "message.helpCommand.help" and its default
	 * value is "help|info|about".</li>
	 * </ul>
	 */
	private final String MESSAGE_ENGINE_HELP = "message.engineHelp";
	private String msgEngineHelp;

	private Parser parser;

	/**
	 * Constructs a Help command (as an specific type of Command) that has
	 * access to a given game. Being a meta-command, it requires access to the
	 * parser of the game engine for getting the help information of other
	 * commands. Initially the command is unparsed and unexecuted. Default
	 * configuration is assumed at this moment.
	 * 
	 * @param game
	 *            The game.
	 * @param parser
	 *            The parser.
	 * @throws IllegalArgumentException
	 *             The arguments 'game' and 'parser' cannot be null.
	 */
	public HelpCommand(Game game, Parser parser) {
		super(game);
		if ((game == null) || (parser == null)) {
			throw new IllegalArgumentException();
		}
		this.game = game;
		this.parser = parser;
		setDefaultConfiguration();
	}

	/**
	 * Constructs a Help command (as an specific type of Command) that has
	 * access to a given game. Being a meta-command, it requires access to the
	 * parser of the game engine for getting the help information of other
	 * commands. Initially the command is unparsed and unexecuted. The
	 * properties defined in the given configuration override those of the
	 * default configuration.
	 * 
	 * @param game
	 *            The game.
	 * @param parser
	 *            The parser.
	 * @param config
	 *            The configuration.
	 * @throws IllegalArgumentException
	 *             The arguments 'game', 'parser' and 'config' cannot be null.
	 */
	public HelpCommand(Game game, Parser parser, Properties config) {
		this(game, parser);
		if (config == null) {
			throw new IllegalArgumentException();
		}
		this.config = config;
		setConfiguration();
	}

	private void setDefaultConfiguration() {
		keywordHelpCommand = "help";
		keywordHelpCommandAbbrev = "info";
		keywordHelpCommandAlt = "about";

		msgHelpCommandHelp = "help|info|about";
		msgEngineHelp = "These are the available player commands:";
	}

	private void setConfiguration() {
		for (Enumeration<Object> e = config.keys(); e.hasMoreElements();) {
			Object obj = e.nextElement();
			if (obj.toString().equalsIgnoreCase(KEYWORD_HELPCOMMAND))
				keywordHelpCommand = config.getProperty(obj.toString());
			else if (obj.toString()
					.equalsIgnoreCase(KEYWORD_HELPCOMMAND_ABBREV))
				keywordHelpCommandAbbrev = config.getProperty(obj.toString());
			else if (obj.toString().equalsIgnoreCase(KEYWORD_HELPCOMMAND_ALT))
				keywordHelpCommandAlt = config.getProperty(obj.toString());
			else if (obj.toString().equalsIgnoreCase(MESSAGE_HELPCOMMAND_HELP))
				msgHelpCommandHelp = config.getProperty(obj.toString());
			else if (obj.toString().equalsIgnoreCase(MESSAGE_ENGINE_HELP))
				msgEngineHelp = config.getProperty(obj.toString());
		}
	}

	/**
	 * Executes the Help command, obtaining as a result the help information of
	 * the game.
	 * 
	 * @see Command#execute()
	 */
	public boolean execute() {
		if (!parsed)
			throw new UnparsedCommandException(
					"Error: UnparsedCommandException");
		executed = true;
		if (parser.reportHelp() != null) {
			result = msgEngineHelp + LINE_SEPARATOR + parser.reportHelp();
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Gets the help information about this Help command.
	 * 
	 * @see Command#getHelp()
	 */
	@Override
	public String getHelp() {
		return msgHelpCommandHelp;
	}

	/**
	 * Parses a text line trying to identify a player invocation to this Help
	 * command (e.g. "help").
	 * 
	 * @see Command#parse(String)
	 */
	@Override
	public boolean parse(String line) {
		Scanner reader = new Scanner(line);
		if (reader.hasNext()) {
			String firstCommand = reader.next();
			if (firstCommand.equalsIgnoreCase(keywordHelpCommand)
					|| firstCommand.equalsIgnoreCase(keywordHelpCommandAbbrev)
					|| firstCommand.equalsIgnoreCase(keywordHelpCommandAlt)) {
				parsed = true;
				return true;
			}
		}
		return false;
	}

	/**
	 * Returns a String representation for this object: HelpCommand. This is
	 * useful for debugging purposes.
	 * 
	 * @see Command#toString()
	 */
	@Override
	public String toString() {
		return this.getClass().getSimpleName();
	}
}