package spaceimpact.controller;

import java.util.List;

import spaceimpact.utilities.Pair;

/**
 * Interface for a generic Controller. The "main" method is inside the
 * Controller class.
 */
public interface ControllerInterface {

	/**
	 * Starts the "GameLoop" (launch new game). If a game is already running
	 * nothing happens.
	 */
	void startGameLoop();

	/**
	 * Abort the "GameLoop" (force stop the current game). If no game is present
	 * nothing happens
	 */
	void abortGameLoop();

	/**
	 * Pauses the "GameLoop" (game pause). If the game is already paused nothing
	 * happens.
	 */
	void pauseGameLoop();

	/**
	 * Resumes a paused "GameLoop". If the game is not paused nothing happens.
	 */
	void resumeGameLoop();

	/**
	 * Checks if there is a paused game (existing and not running).
	 *
	 * @return True if there is a paused GameLoop, false otherwise.
	 */
	boolean isGameLoopPaused();

	/**
	 * Checks if there is a running game (existing and not paused).
	 *
	 * @return True if there is a running GameLoop, false otherwise.
	 */
	boolean isGameLoopRunning();

	/**
	 * Returns the list of current highscores. If the current list cannot be
	 * loaded, an empty list is returned. The returned list is a defensive copy.
	 *
	 * @return A List of scores (Pair<String, Integer>, a player name and a
	 *         score)
	 */
	List<Pair<String, Integer>> getCurrentHighScores();

	/**
	 * * Saves the current score and player name to the highscores.
	 *
	 * @param s
	 *            The player name
	 * @return True if the operation was successful, false otherwise.
	 */
	boolean setCurrentPlayerName(String s);

	/**
	 * Clears the list of highscores
	 *
	 * @return True if the operation was successful, false otherwise.
	 */
	boolean emptyHighScores();

	/**
	 * Set the game score.
	 *
	 * @param score
	 *            The score reached.
	 */
	void setScore(int score);

	/**
	 * Getter of the FPS (Frames Per Second) value.
	 *
	 * @return The current FPS value.
	 */
	int getFPS();

	/**
	 * Getter of the current difficulty (in String form).
	 *
	 * @return A String (the current difficulty).
	 */
	String getDifficulty();

	/**
	 * Setter for FPS (Frames Per Second) and difficulty. If this method is
	 * never called default values are used.
	 *
	 * @param fps
	 *            The desired number of frames per second.
	 * @param diff
	 *            A Pair<String, Integer>. The String is the human-readable
	 *            difficulty setting, the Integer is the difficulty multiplier.
	 * @throws IllegalArgumentException
	 *             In case of invalid arguments.
	 */
	void setFPSDifficulty(int fps, Pair<String, Integer> diff) throws IllegalArgumentException;
}
