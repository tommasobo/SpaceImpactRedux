package spaceimpact.controller;

import java.util.LinkedList;
import java.util.List;

import spaceimpact.model.Area;
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
		this.model = new Model(fps, 20);
	}

	/**
	 * Causes the GameLoop to stop even if the game didn't reach an end. The
	 * halt could be delayed up to a tic later.
	 */
	public void abort() {
		this.status = Status.KILLED;
	}

	/**
	 * Causes the GameLoop to pause. If it's not running, nothing happens. The
	 * pause could be delayed up to a tic later.
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
				final List<Pair<Pair<String, Double>, Location>> toDraw = new LinkedList<>();
				toDraw.add(new Pair<>(new Pair<>("/Entities/Player.png", 0d), this.model.getPlayerLocation()));
				if (this.model.getPlayerShield() > 0) {
					final Location pl = this.model.getPlayerLocation();
					final Area a = new Area(pl.getArea().getWidth() * 2, pl.getArea().getHeight() * 2);
					toDraw.add(new Pair<>(new Pair<>("shield.png", 0d),
							new Location(pl.getX() - (a.getWidth() / 32), pl.getY(), a)));
				}
				this.model.getEntitiesToDraw().forEach(e -> {
					toDraw.add(new Pair<>(EntityType.getImage(e), e.getLocation()));
				});
				final Thread t = new Thread() {
					@Override
					public void run() {
						GameLoop.this.view.draw(toDraw);
						GameLoop.this.view.updateInfo(GameLoop.this.model.getPlayerLife(),
								GameLoop.this.model.getPlayerShield(), GameLoop.this.model.getScores());
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
	 * Resume could be delayed up to a tic later.
	 */
	public void unPause() {
		if (this.isPaused()) {
			this.status = Status.RUNNING;
		}
	}

	/**
	 * Checks if the game status is
	 *
	 * @param s
	 * @return
	 */
	public boolean isPaused() {
		return this.status == Status.PAUSED;
	}

}