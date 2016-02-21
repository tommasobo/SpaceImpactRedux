package spaceimpact.controller;

import java.util.LinkedList;
import java.util.List;

import spaceimpact.model.Location;
import spaceimpact.model.Model;
import spaceimpact.model.ModelInterface;
import spaceimpact.model.entities.EntityType;
import spaceimpact.utilities.Pair;
import spaceimpact.view.ViewInterface;

/**
 * A "clock" for the game. As an animation, a game is composed of "frames":
 * every second the GameLoop will generate a fixed number of frames. The
 * GameLoop synchronizes View and Model.
 */
public class GameLoop extends Thread {

	/**
	 * Enum describing the possible states of the GameLoop
	 */
	private enum Status {
		READY, RUNNING, PAUSED, KILLED;
	}

	private volatile Status status;
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
		this.status = Status.READY;
		this.ticLenght = 1000 / fps;
		this.view = view;
		this.model = new Model(fps);
	}

	/**
	 * Causes the GameLoop to stop even if the game didn't reach an end.
	 */
	public void abort() {
		this.status = Status.KILLED;
	}

	/**
	 * Causes the GameLoop to pause. If it's not running, nothing happens
	 */
	public void pause() {
		if (this.status == Status.RUNNING) {
			this.status = Status.PAUSED;
		}
	}

	/**
	 * This method is called as soon as the GameLoop is started. The GameLoop
	 * asks the View to paint the current game scene while the Model updates the
	 * scene to the next frame. If necessary the GameLoop waits to keep a
	 * constant framerate.
	 */
	@Override
	public void run() {
		this.status = Status.RUNNING;
		while (this.status != Status.KILLED) {
			if (this.status == Status.RUNNING) {
				final long startTime = System.currentTimeMillis();
				final List<Pair<String, Location>> toDraw = new LinkedList<>();
				toDraw.add(new Pair<>("/Entities/Player.png", this.model.getPlayerLocation()));
				this.model.getEntitiesToDraw().forEach(e -> {
					if (e.getID() == EntityType.Projectile) {
						toDraw.add(new Pair<>("/Entities/Projectiles/beam_blue.png", e.getLocation()));
					} else if (e.getID() == EntityType.Enemy) {
						toDraw.add(new Pair<>("/Entities/Enemies/C15.png", e.getLocation()));
					} else if (e.getID() == EntityType.Debris) {
						toDraw.add(new Pair<>("/Entities/explosion.gif", e.getLocation()));
					}
				});
				final Thread t = new Thread() {
					@Override
					public void run() {
						GameLoop.this.view.draw(toDraw);
					}
				};
				t.start();
				this.model.informInputs(this.view.getInput());
				this.model.updateAll();
				try {
					t.join();
					final long timeSpent = System.currentTimeMillis() - startTime;
					if (timeSpent < this.ticLenght) {
						final double usage = (((double) 100 * timeSpent) / this.ticLenght);
						if (usage > 0) {
							System.out.println("Time usage: " + (((double) 100 * timeSpent) / this.ticLenght) + "%");
						}
						Thread.sleep(this.ticLenght - timeSpent);
					}
				} catch (final InterruptedException ex1) {
					this.status = Status.KILLED;
				}
			}
		}
		// operazioni una volta ucciso il gameloop
	}

	/**
	 * Causes the GameLoop to resume. If it wasn't paused nothing happens.
	 */
	public void unPause() {
		if (this.status == Status.PAUSED) {
			this.status = Status.RUNNING;
		}
	}

}