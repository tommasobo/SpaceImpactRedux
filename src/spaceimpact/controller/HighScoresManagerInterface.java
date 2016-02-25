package spaceimpact.controller;

import java.io.IOException;
import java.util.List;

import spaceimpact.utilities.Pair;

public interface HighScoresManagerInterface {

	/**
	 * Adds a new Pair <playername-score> to the highscores. The list is then
	 * sorted, and excess scores are cut away. The list will be saved only if
	 * explicitly asked with the method "saveData()".
	 *
	 * @param p
	 *            The new highscore (Pair<String, Integer>)
	 */
	void addScore(final Pair<String, Integer> p);

	/**
	 * Removes all previous HighScores
	 */
	void emptyScores();

	/**
	 * Returns the list of highscores (may be empty).
	 *
	 * @return The list of highscores (Pair<String, Integer>)
	 */
	List<Pair<String, Integer>> getScores();

	/**
	 * Saves data to disc.
	 *
	 * @throws IllegalStateException
	 *             If there's no need of saving
	 * @throws IOException
	 *             If unable to save data
	 */
	void saveData() throws IllegalStateException, IOException;

}
