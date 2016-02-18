package spaceimpact.controller;

import java.util.List;

import spaceimpact.utilities.Pair;

/**
 * Interface for a generic Controller The "main" method is inside the Controller
 * class.
 */
public interface ControllerInterface {

	/**
	 * Abort the "GameLoop" (force stop the current game)
	 *
	 * @throws IllegalStateException
	 *             If no game is currently running
	 */
	void abortGameLoop() throws IllegalStateException;

	/**
	 * Returns the list of current highscores. If the current list cannot be
	 * loaded, an empty list is returned. The returned list is a defensive copy.
	 *
	 * @return A List of scores (Pair<String, Integer>, a player name and a
	 *         score)
	 */
	List<Pair<String, Integer>> getCurrentHighScores();

	/**
	 * Starts the "GameLoop" (launch new game)
	 *
	 * @throws IllegalStateException
	 *             If another game is currently running
	 */
	void startGameLoop() throws IllegalStateException;

}
