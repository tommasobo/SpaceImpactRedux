package spaceimpact.controller;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import spaceimpact.utilities.Pair;
import spaceimpact.view.View;
import spaceimpact.view.ViewInterface;

public final class Controller implements ControllerInterface {
	private static final String HS_FILENAME = "hiscores";
	private static final int HS_NSCORES = 10;
	private static int fps = 120;
	private static int diff = 2;

	private final HighScoresManagerInterface hsManager;
	private Optional<GameLoop> gl;
	private ViewInterface view;
	private volatile int score;

	private Controller() {
		this.hsManager = new HighScoresManager(Controller.HS_FILENAME, Controller.HS_NSCORES);
		this.gl = Optional.empty();
	}

	@Override
	public void startGameLoop() throws IllegalStateException {
		if (this.gl.isPresent()) {
			throw new IllegalStateException();
		}
		final GameLoop game = new GameLoop(Controller.fps, Controller.diff, this, this.view);
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
	public List<Pair<String, Integer>> getCurrentHighScores() {
		return this.hsManager.getScores();
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

	/**
	 * Start a new application
	 */
	public static void main(final String args[]) {
		final Controller c = new Controller();
		c.view = new View(c);
		c.view.startView();
	}

	@Override
	public void setScore(final int s) {
		this.score = s;
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
	public int getFPS() {
		return Controller.fps;
	}

	@Override
	public int getDifficulty() {
		return Controller.diff;
	}

	@Override
	public void setFPSDifficulty(final int fps, final int diff) throws IllegalArgumentException {
		if (diff <= 0) {
			throw new IllegalArgumentException("Cannot set a difficulty <= 0");
		}
		if (fps <= 0) {
			throw new IllegalArgumentException("Cannot set fps <= 0");
		}
		Controller.diff = diff;
		Controller.fps = fps;
	}

}