package spaceimpact.controller;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOError;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import spaceimpact.utilities.Pair;

/** A manager for HighScores. It can save/load the data to/from the filesystem.
 */
public class HighScoresManager {
	
	private static final boolean REPORT_ERRORS_ON_WRITE=true; // For debug and other purposes
	
	private final String filename; // where to save highscores
	private final int numMaxScores; // how many highscores will be saved
	private Optional<List<Pair<String, Integer>>> cache; // cached data to limit read/write to filesystem
	private boolean editedNotSaved; // flag, active if edited and not saved
	
	/** Constructor for this class.
	 * 
	 * @param fileName The name of the file used for writing/reading scores
	 * @param nscores The number of highscores saved
	 */
	public HighScoresManager(String fileName, int nscores) throws IllegalArgumentException {
		if (nscores <= 0 || fileName.isEmpty()) {
			throw new IllegalArgumentException();
		}
		this.cache = Optional.empty();
		this.filename = fileName;
		this.editedNotSaved = false;
		this.numMaxScores = nscores;
	}
	
	/**Makes a defensive copy of the current highscores and returns it.
	 * Highscores are cached to minimize disc accesses.
	 * 
	 * @return The list of highscores (may be empty, it's a defensive copy)
	 */
	public List<Pair<String, Integer>> getScores() {
		if (!this.cache.isPresent()) {
			loadData();
		}
		return new ArrayList<>(this.cache.get());
	}
	
	/** Adds a new Pair <playername-score> to the highscores.
	 * The list is then sorted, and excess scores are cut away.
	 * The list will be saved only when the HighScoreManager is deleted by the garbage collector,
	 * or if explicitly asked with the method "saveData()".
	 * 
	 * @param p The new highscore
	 */
	public void addScore(Pair<String, Integer> p) {
		if (!this.cache.isPresent()) {
			loadData();
		}
		List<Pair<String, Integer>> list = this.cache.get();
		list.add(p);
		sortList(list);
		this.editedNotSaved = true;
		removeExcessScores(list);
		this.cache = Optional.of(list);
	}
	
	/**
	 * Removes all previous HighScores 
	 */
	public void emptyScores() {
		this.cache = Optional.of(new LinkedList<Pair<String, Integer>>());
		this.editedNotSaved = true;
	}
	
	/** Saves the currently cached data to disc. To minimize write on disc it's advised
	 * to save only if necessary.
	 * 
	 * @throws IllegalStateException If there's no cached data, or if it's already saved
	 * @throws IOException If unable to save data on disc
	 */
	public void saveData() throws IllegalStateException, IOException {
		if (!this.cache.isPresent() || !this.editedNotSaved) {
			if (HighScoresManager.REPORT_ERRORS_ON_WRITE) {
				throw new IllegalStateException();
			}
		} else {
			try (DataOutputStream out = new DataOutputStream(new FileOutputStream(this.filename))) {
				for (Pair<String, Integer> p : this.cache.get()) {
						out.writeUTF(p.getFirst());
						out.writeInt(p.getSecond().intValue());
				}
				this.editedNotSaved = false;
			} catch (Exception e) {
				if (HighScoresManager.REPORT_ERRORS_ON_WRITE) {
					throw new IOException();
				}
			}
		}
	}
	
	/**
	 * Private method, reads highscores from disc and caches them.
	 */
	private void loadData() {
		List<Pair<String, Integer>> list = new LinkedList<>();
		try (DataInputStream in = new DataInputStream(new FileInputStream(this.filename))) {
			while (true) {
				String name = in.readUTF();
				Integer score = new Integer(in.readInt());
				list.add(new Pair<String, Integer>(name, score));
			}
		} catch (Exception ex) {}
		sortList(list);
		if (removeExcessScores(list)) {
			this.editedNotSaved = true;
		}
		this.cache = Optional.of(list);
	}
	
	/**
	 * Private method, remove excess scores from the given list
	 * @param l The starting list
	 * @return True if the list was modified, False otherwise
	 */
	private boolean removeExcessScores(List<Pair<String, Integer>> l) {
		boolean changed = (l.size() > this.numMaxScores);
		while (l.size() > this.numMaxScores) {
			l.remove(this.numMaxScores);
		}
		return changed;
	}
	
	/**
	 * Private method, sorts the given list
	 * @param l The starting list
	 */
	private void sortList(List<Pair<String, Integer>> l) {
		Collections.sort(l, (a, b) -> b.getSecond() - a.getSecond());
	}
	
}
