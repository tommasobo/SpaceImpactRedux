package spaceimpact.controller;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import spaceimpact.utilities.Pair;
import spaceimpact.view.View;
import spaceimpact.view.ViewInterface;

/**
 * Implementation of ControllerInterface.
 */
public final class Controller implements ControllerInterface {
	private static final String HS_FILENAME = "hiscores";
	private static final int HS_NSCORES = 10;

	private final HighScoresManagerInterface hsManager;
	private int fps = 60;
	private Pair<String, Integer> diff = new Pair<>("Normal", 2);
	private Optional<GameLoop> gl;
	private ViewInterface view;
	private volatile int score;

	private Controller() {
		this.hsManager = new HighScoresManager(Controller.HS_FILENAME, Controller.HS_NSCORES);
		this.gl = Optional.empty();
	}

	/**
	 * Start a new application.
	 */
	public static void main(final String args[]) {
		final Controller c = new Controller();
		c.view = new View(c);
		c.view.startView();
	}

	@Override
	public void startGameLoop() throws IllegalStateException {
		if (this.gl.isPresent()) {
			throw new IllegalStateException();
		}
		final GameLoop game = new GameLoop(this.fps, this.diff.getSecond(), this, this.view);
		this.gl = Optional.of(game);
		game.start();
	}

	@Override
	public void abortGameLoop() {
		if (this.gl.isPresent()) {
			this.gl.get().abort();
			this.gl = Optional.empty();
		}
	}

	@Override
	public void pauseGameLoop() {
		if (this.gl.isPresent()) {
			this.gl.get().pause();
		}
	}

	@Override
	public void resumeGameLoop() {
		if (this.gl.isPresent()) {
			this.gl.get().unPause();
		}
	}

	@Override
	public boolean isGameLoopPaused() {
		if (!this.gl.isPresent()) {
			return false;
		}
		return this.gl.get().isPaused();
	}

	@Override
	public boolean isGameLoopRunning() {
		if (!this.gl.isPresent()) {
			return false;
		}
		return this.gl.get().isRunning();
	}

	@Override
	public List<Pair<String, Integer>> getCurrentHighScores() {
		return this.hsManager.getScores();
	}

	@Override
	public boolean setCurrentPlayerName(final String s) {
		this.hsManager.addScore(new Pair<>(s, this.score));
		try {
			this.hsManager.saveData();
		} catch (IllegalStateException | IOException e) {
			return false;
		}
		return true;
	}

	@Override
	public boolean emptyHighScores() {
		this.hsManager.emptyScores();
		try {
			this.hsManager.saveData();
		} catch (IllegalStateException | IOException e) {
			return false;
		}
		return true;
	}

	@Override
	public void setScore(final int s) {
		this.score = s;
	}

	@Override
	public int getFPS() {
		return this.fps;
	}

	@Override
	public String getDifficulty() {
		return this.diff.getFirst();
	}

	@Override
	public void setFPSDifficulty(final int f, final Pair<String, Integer> d) throws IllegalArgumentException {
		if (d.getSecond() <= 0 || d.getFirst() == null || d.getFirst().equals("")) {
			throw new IllegalArgumentException("Cannot set a difficulty <= 0 (or empty string)");
		}
		if (f <= 0) {
			throw new IllegalArgumentException("Cannot set fps <= 0");
		}
		this.diff = d;
		this.fps = f;
	}

}