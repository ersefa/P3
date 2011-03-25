/**
 * Practica 3 LPS 2010/2011 Grupo 12
 * 
 * @author Jose Luis Garcia Martinez
 * @author Cristina Sanchez Albarran
 * 
 */
package es.ucm.fdi.lps.p3;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import es.ucm.fdi.lps.p3.exception.InvalidGameDefinitionException;

import jargs.gnu.CmdLineParser;
import jargs.gnu.CmdLineParser.IllegalOptionValueException;
import jargs.gnu.CmdLineParser.UnknownOptionException;

/**
 * The main entrance of the application that launches the game engine according
 * to the command line options.
 */
public class Main {
	/**
	 * File paths
	 */
	private static String configFilePath;
	private static String inputFilePath;
	private static String outputFilePath;
	private static String gameDefinitionPath;

	/**
	 * File streams
	 */
	private static FileInputStream gameDefinition;
	private static FileInputStream config;
	private static FileInputStream inputStream;
	private static FileOutputStream outputStream;
	private static Properties properties;

	/**
	 * Main method. Usage: run [{-c,--config} configFilePath] gameFilePath
	 * [{-i,--input} inputFilePath] [{-o,--output} outputFilePath]. <br>
	 * (config, game, input and output file paths should include their file
	 * extensions)
	 * 
	 * @param args
	 *            Execution arguments read from console.
	 * @throws IllegalArgumentException
	 *             The argument 'args' cannot be null.
	 */
	public static void main(String[] args) {
		if ((args.length < 1) || (args.length > 7) || !parseArgs(args))
			throw new IllegalArgumentException("Error: Wrong arguments number");

		try {
			assignFiles();
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			Game game = new Game(gameDefinition);
			Engine engine;

			if ((configFilePath == null) && (inputFilePath == null)
					&& (outputFilePath == null))
				engine = new Engine(game);
			else if ((inputFilePath == null) && (outputFilePath == null))
				engine = new Engine(game, properties);
			else if ((configFilePath == null) && (inputFilePath == null))
				engine = new Engine(game, inputStream);
			else if ((configFilePath == null) && (inputFilePath == null))
				engine = new Engine(game, outputStream);
			else if (configFilePath == null)
				engine = new Engine(game, inputStream, outputStream);
			else if (outputFilePath == null)
				engine = new Engine(game, properties, inputStream);
			else if (inputFilePath == null)
				engine = new Engine(game, properties, outputStream);
			else
				engine = new Engine(game, properties, inputStream, outputStream);

			engine.run();

		} catch (InvalidGameDefinitionException e1) {
			e1.printStackTrace();
		} catch (NullPointerException e1) {
			e1.printStackTrace();
		}

	}

	/**
	 * To parse command line arguments
	 * 
	 * @param args
	 *            The command line arguments
	 * @return true if gameDefinition is provided, false otherwise
	 */
	private static boolean parseArgs(String[] args) {

		CmdLineParser parser = new CmdLineParser();
		CmdLineParser.Option configFilePathArg = parser.addStringOption('c',
				"config");
		CmdLineParser.Option inputFilePathArg = parser.addStringOption('i',
				"input");
		CmdLineParser.Option outputFilePathArg = parser.addStringOption('o',
				"output");
		String[] remainingArgs = parser.getRemainingArgs();

		try {
			parser.parse(args);
		} catch (IllegalOptionValueException e) {
			e.printStackTrace();
		} catch (UnknownOptionException e) {
			e.printStackTrace();
		}

		configFilePath = (String) parser.getOptionValue(configFilePathArg);
		inputFilePath = (String) parser.getOptionValue(inputFilePathArg);
		outputFilePath = (String) parser.getOptionValue(outputFilePathArg);
		remainingArgs = parser.getRemainingArgs();

		if (remainingArgs.length > 0) {
			gameDefinitionPath = remainingArgs[0];
			return true;
		} else
			return false;
	}

	/**
	 * Method that creates Input, Output and Properties streams from files
	 */
	private static void assignFiles() throws IOException {

		if (gameDefinitionPath != null) {
			File gameDefinitionFile = new File(gameDefinitionPath);
			gameDefinition = new FileInputStream(gameDefinitionFile);
		}

		if (configFilePath != null) {
			File configFile = new File(configFilePath);
			config = new FileInputStream(configFile);
		}

		if (inputFilePath != null) {
			File inputFile = new File(inputFilePath);
			inputStream = new FileInputStream(inputFile);
		}
		if (outputFilePath != null) {
			File outputFile = new File(outputFilePath);
			outputStream = new FileOutputStream(outputFile);
		}

		if (config != null) {
			properties = new Properties();
			properties.load(config);
		}
	}
}