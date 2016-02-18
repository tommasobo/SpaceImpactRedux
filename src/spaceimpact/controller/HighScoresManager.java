package spaceimpact.controller;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import spaceimpact.utilities.Pair;

/**
 * A manager for HighScores. It can save/load the data to/from the filesystem.
 */
public class HighScoresManager {

	private static final boolean REPORT_ERRORS_ON_WRITE = true;

	private final String filename;
	private final int numMaxScores;
	private Optional<List<Pair<String, Integer>>> cache;
	private boolean editedNotSaved;

	/**
	 * Constructor for this class.
	 *
	 * @param fileName
	 *            The name of the file used for writing/reading scores
	 * @param nscores
	 *            The number of highscores saved
	 */
	public HighScoresManager(final String fileName, final int nscores) throws IllegalArgumentException {
		if ((nscores <= 0) || fileName.isEmpty()) {
			throw new IllegalArgumentException();
		}
		this.cache = Optional.empty();
		this.filename = fileName;
		this.editedNotSaved = false;
		this.numMaxScores = nscores;
	}

	/**
	 * Adds a new Pair <playername-score> to the highscores. The list is then
	 * sorted, and excess scores are cut away. The list will be saved only when
	 * the HighScoreManager is deleted by the garbage collector, or if
	 * explicitly asked with the method "saveData()".
	 *
	 * @param p
	 *            The new highscore
	 */
	public void addScore(final Pair<String, Integer> p) {
		if (!this.cache.isPresent()) {
			this.loadData();
		}
		final List<Pair<String, Integer>> list = this.cache.get();
		list.add(p);
		this.sortList(list);
		this.editedNotSaved = true;
		this.removeExcessScores(list);
		this.cache = Optional.of(list);
	}

	/**
	 * Removes all previous HighScores
	 */
	public void emptyScores() {
		this.cache = Optional.of(new LinkedList<Pair<String, Integer>>());
		this.editedNotSaved = true;
	}

	/**
	 * Makes a defensive copy of the current highscores and returns it.
	 * Highscores are cached to minimize disc accesses.
	 *
	 * @return The list of highscores (may be empty, it's a defensive copy)
	 */
	public List<Pair<String, Integer>> getScores() {
		if (!this.cache.isPresent()) {
			this.loadData();
		}
		return new ArrayList<>(this.cache.get());
	}

	/**
	 * Private method, reads highscores from disc and caches them.
	 */
	private void loadData() {
		final List<Pair<String, Integer>> list = new LinkedList<>();
		try (DataInputStream in = new DataInputStream(new FileInputStream(this.filename))) {
			while (true) {
				final String name = in.readUTF();
				final Integer score = Integer.valueOf(in.readInt());
				list.add(new Pair<String, Integer>(name, score));
			}
		} catch (final Exception ex) {
		}
		this.sortList(list);
		if (this.removeExcessScores(list)) {
			this.editedNotSaved = true;
		}
		this.cache = Optional.of(list);
	}

	/**
	 * Private method, remove excess scores from the given list
	 *
	 * @param l
	 *            The starting list
	 * @return True if the list was modified, False otherwise
	 */
	private boolean removeExcessScores(final List<Pair<String, Integer>> l) {
		final boolean changed = (l.size() > this.numMaxScores);
		while (l.size() > this.numMaxScores) {
			l.remove(this.numMaxScores);
		}
		return changed;
	}

	/**
	 * Saves the currently cached data to disc. To minimize write on disc it's
	 * advised to save only if necessary.
	 *
	 * @throws IllegalStateException
	 *             If there's no cached data, or if it's already saved
	 * @throws IOException
	 *             If unable to save data on disc
	 */
	public void saveData() throws IllegalStateException, IOException {
		if (!this.cache.isPresent() || !this.editedNotSaved) {
			if (HighScoresManager.REPORT_ERRORS_ON_WRITE) {
				throw new IllegalStateException();
			}
		} else {
			try (DataOutputStream out = new DataOutputStream(new FileOutputStream(this.filename))) {
				for (final Pair<String, Integer> p : this.cache.get()) {
					out.writeUTF(p.getFirst());
					out.writeInt(p.getSecond().intValue());
				}
				this.editedNotSaved = false;
			} catch (final Exception e) {
				if (HighScoresManager.REPORT_ERRORS_ON_WRITE) {
					throw new IOException();
				}
			}
		}
	}

	/**
	 * Private method, sorts the given list
	 *
	 * @param l
	 *            The starting list
	 */
	private void sortList(final List<Pair<String, Integer>> l) {
		Collections.sort(l, (a, b) -> b.getSecond() - a.getSecond());
	}

}
