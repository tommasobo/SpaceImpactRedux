package spaceimpact.controller;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import spaceimpact.utilities.Pair;
import spaceimpact.view.View;
import spaceimpact.view.ViewInterface;

public class Controller implements ControllerInterface {
	private static final String HS_FILENAME = "hiscores";
	private static final int HS_NSCORES = 10;
	private static final int FPS = 30;

	private final HighScoresManagerInterface hsManager;
	private Optional<GameLoop> gl;
	private ViewInterface view;

	private Controller() {
		this.hsManager = new HighScoresManager(Controller.HS_FILENAME, Controller.HS_NSCORES);
		this.gl = Optional.empty();
	}

	@Override
	public void startGameLoop() throws IllegalStateException {
		if (this.gl.isPresent()) {
			throw new IllegalStateException();
		}
		final GameLoop game = new GameLoop(Controller.FPS, this.view);
		this.gl = Optional.of(game);
		game.start();
	}

	@Override
	public void abortGameLoop() throws IllegalStateException {
		this.gl.orElseThrow(() -> new IllegalStateException());
		this.gl.get().abort();
	}

	@Override
	public List<Pair<String, Integer>> getCurrentHighScores() {
		return this.hsManager.getScores();
	}

	@Override
	public void pauseGameLoop() throws IllegalStateException {
		this.gl.orElseThrow(() -> new IllegalStateException());
		this.gl.get().pause();
	}

	@Override
	public void resumeGameLoop() throws IllegalStateException {
		this.gl.orElseThrow(() -> new IllegalStateException());
		this.gl.get().unPause();
	}

	@Override
	public void emptyHighScores() {
		this.hsManager.emptyScores();
		try {
			this.hsManager.saveData();
		} catch (IllegalStateException | IOException e) {
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

	/**
	 * Start a new application
	 */
	public static void main(final String args[]) {
		final Controller c = new Controller();
		c.view = new View(c);
		c.view.startView();
	}

}