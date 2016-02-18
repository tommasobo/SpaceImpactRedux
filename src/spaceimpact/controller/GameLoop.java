package spaceimpact.controller;

import java.util.LinkedList;
import java.util.List;

import spaceimpact.model.Location;
import spaceimpact.model.Model;
import spaceimpact.model.ModelInterface;
import spaceimpact.utilities.Pair;
import spaceimpact.view.ViewInterface;

/**
 * A "clock" for the game. As an animation, a game is composed of "frames":
 * every second the GameLoop will generate a fixed number of frames. The
 * GameLoop synchronizes View and Model.
 */
public class GameLoop extends Thread {

	private volatile boolean stopped;
	private final long ticLenght;
	private final ViewInterface view;
	private final ModelInterface model;

	/**
	 * Constructor for GameLoop
	 *
	 * @param fps
	 *            The number of frames per second
	 */
	public GameLoop(final int fps, final ViewInterface view) {
		this.stopped = false;
		this.ticLenght = 1 / fps;
		this.view = view;
		this.model = new Model();
	}

	/**
	 * Causes the GameLoop to stop even if the game didn't reach an end.
	 */
	public void abort() {
		this.stopped = true;
	}

	/**
	 * This method is called as soon as the GameLoop is started. The GameLoop
	 * asks the View to paint the current game scene while the Model updates the
	 * scene to the next frame. If necessary the GameLoop waits to keep a
	 * constant framerate.
	 */
	@Override
	public void run() {
		final String sep = System.getProperty("file.separator");
		final String resFolder = sep + "res" + sep + "Entities" + sep;
		while (!this.stopped) {
			final long startTime = System.currentTimeMillis();
			final List<Pair<String, Location>> toDraw = new LinkedList<>();
			this.model.getEntitiesToDraw().forEach(e -> {
				/*
				 * if (e.getID() == EntityType.Spaceship) { toDraw.add(new
				 * Pair<>(resFolder + "Player.png", e.getLocation())); }
				 */
			});
			toDraw.add(new Pair<>(resFolder + "Player", this.model.getPlayerLocation()));

			final Thread t = new Thread() {
				@Override
				public void run() {
					// ordina la stampa a video dei dati passando toDraw
				}
			};
			t.start();
			this.model.informInputs(this.view.getInput());
			this.model.updateAll();
			try {
				t.join();
				final long timeSpent = System.currentTimeMillis() - startTime;
				if (timeSpent < this.ticLenght) {
					Thread.sleep(this.ticLenght - timeSpent);
				}
			} catch (final InterruptedException ex1) {
				this.stopped = true;
			}
		}
	}

}
