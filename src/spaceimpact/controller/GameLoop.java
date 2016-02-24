package spaceimpact.controller;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import spaceimpact.model.Area;
import spaceimpact.model.Direction;
import spaceimpact.model.GameStatus;
import spaceimpact.model.Level;
import spaceimpact.model.Location;
import spaceimpact.model.Model;
import spaceimpact.model.ModelInterface;
import spaceimpact.model.entities.EntityType;
import spaceimpact.utilities.Input;
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
	private final ControllerInterface controller;
	private volatile int score;
	private volatile int nLevel;
	private volatile ModelInterface model;

	/**
	 * Constructor for GameLoop
	 *
	 * @param fps
	 *            The number of frames per second
	 */
	public GameLoop(final int fps, final ControllerInterface controller, final ViewInterface view) {
		this.status = Status.READY;
		this.ticLenght = 1000 / fps;
		this.view = view;
		this.nLevel = 1;
		this.model = new Model(fps, this.createLevel(this.nLevel, fps));
		this.controller = controller;
		this.score = 0;
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
				if (this.model.getGameStatus() == GameStatus.Running) {
					final long startTime = System.currentTimeMillis();
					this.score += GameLoop.this.model.getScores();
					final List<Pair<Pair<String, Double>, Location>> toDraw = new LinkedList<>();
					toDraw.add(new Pair<>(new Pair<>("/Entities/Player.png", 0d), this.model.getPlayerLocation()));
					if (this.model.getPlayerShield() > 0) {
						final Location pl = this.model.getPlayerLocation();
						final Area a = new Area(pl.getArea().getWidth() * 2, pl.getArea().getHeight() * 2);
						toDraw.add(new Pair<>(new Pair<>("shield.png", 0d),
								new Location(pl.getX() + (a.getWidth() / 10), pl.getY(), a)));
					}
					this.model.getEntitiesToDraw().forEach(e -> {
						toDraw.add(new Pair<>(EntityType.getImage(e), e.getLocation()));
					});
					final Thread t = new Thread() {
						@Override
						public void run() {
							GameLoop.this.view.draw(toDraw);
							GameLoop.this.view.updateInfo(GameLoop.this.model.getPlayerLife(),
									GameLoop.this.model.getPlayerShield(), GameLoop.this.score);
						}
					};
					t.start();
					final Pair<Optional<Direction>, Boolean> tmp = this.parseInputs();
					this.model.informInputs(tmp.getFirst(), tmp.getSecond());
					this.model.updateAll();
					try {
						t.join();
						final long timeSpent = System.currentTimeMillis() - startTime;
						if (timeSpent < this.ticLenght) {
							final double usage = (((double) 100 * timeSpent) / this.ticLenght);
							if (usage > 0) {
								// System.out.println("Time usage: " +
								// (((double) 100 * timeSpent) / this.ticLenght)
								// + "%");
							}
							Thread.sleep(this.ticLenght - timeSpent);
						}
						if (this.model.getPlayerLife() <= 0) {
							this.status = Status.KILLED;
						}
					} catch (final InterruptedException ex1) {
						this.status = Status.KILLED;
					}
				} else if (this.model.getGameStatus() == GameStatus.Over) {
					this.status = Status.KILLED;
				} else {
					this.view.won(this.nLevel);
					this.nLevel++;
					final int fps = (int) (1000 / this.ticLenght);
					this.model = new Model(fps, this.createLevel(this.nLevel, fps));
				}
			}
		}
		this.controller.setScore(this.score);
		this.controller.abortGameLoop();
	}

	private Pair<Optional<Direction>, Boolean> parseInputs() {
		boolean n = false;
		boolean s = false;
		boolean e = false;
		boolean w = false;
		boolean shoot = false;
		final List<Input> tmp = this.view.getInput();

		for (final Input i : tmp) {
			if (i == Input.W) {
				n = true;
			} else if (i == Input.S) {
				s = true;
			} else if (i == Input.A) {
				w = true;
			} else if (i == Input.D) {
				e = true;
			} else {
				shoot = true;
			}
		}
		Optional<Direction> d;
		if (n) {
			if (e) {
				d = Optional.of(Direction.NE);
			} else if (w) {
				d = Optional.of(Direction.NW);
			} else {
				d = Optional.of(Direction.N);
			}
		} else if (s) {
			if (e) {
				d = Optional.of(Direction.SE);
			} else if (w) {
				d = Optional.of(Direction.SW);
			} else {
				d = Optional.of(Direction.S);
			}
		} else if (e) {
			d = Optional.of(Direction.E);
		} else if (w) {
			d = Optional.of(Direction.W);
		} else {
			d = Optional.empty();
		}

		return new Pair<Optional<Direction>, Boolean>(d, shoot);
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
	 * Checks if the game is paused
	 *
	 * @return True if the game is paused, false otherwise
	 */
	public boolean isPaused() {
		return this.status == Status.PAUSED;
	}

	/**
	 * Checks if the game is running
	 *
	 * @return True if the game is running, false otherwise
	 */
	public boolean isRunning() {
		return this.status == Status.RUNNING;
	}

	/**
	 * Getter of the final score
	 *
	 * @return The game score
	 * @throws IllegalStateException
	 *             If this method is used before game termination.
	 */
	public int getScores() throws IllegalStateException {
		if (this.status != Status.KILLED) {
			throw new IllegalStateException();
		}
		return this.score;
	}

	/**
	 * Private method, creates a new Level (the difficulty depends on the number
	 * of levels completed before)
	 *
	 * @param levelId
	 *            The number of level (i.e. 1 for first level, 2 for second...)
	 * @return The created level.
	 */
	private Level createLevel(final int levelId, final int fps) {
		final int totalEnemiesToSpawn = 10 * (levelId + 1);
		final int maxEnemyPerSpawn = 2 + ((levelId - 1) / 2);
		final int enemyDelay = (int) ((4 - (0.18333 * Math.min(10, levelId))) * fps);
		final int debrisDelay = (int) ((2.5 * new Random().nextDouble()) + (0.5 * fps));
		final int powerupDelay = (8 + (2 * levelId)) * fps;
		final double tmpvel = (0.15 / fps) * (0.9 + (0.1 * levelId));
		final Level tmp = new Level(totalEnemiesToSpawn, maxEnemyPerSpawn, enemyDelay, debrisDelay, powerupDelay,
				tmpvel);
		tmp.getEnemySpawner().setSpawnedEntityArea(new Area(0.125, 0.0972));
		tmp.getDebrisSpawner().setSpawnedEntityArea(new Area(0.125, 0.0972));
		tmp.getPowerUpSpawner().setSpawnedEntityArea(new Area(0.125, 0.0972));
		tmp.getEnemySpawner().setCoolDownEntityWeapon((int) ((1 - (0.1 * Math.min(5, levelId / 2))) * fps));
		tmp.getEnemySpawner().setEntityDamageRange(5 * (levelId + 1), 10 * (levelId + 1));
		tmp.getEnemySpawner().setEntityLifeRange(5 * levelId, 5 + (10 * levelId));
		tmp.getEnemySpawner().setEntityVelocityRange(tmpvel, tmpvel * 1.5);
		return tmp;
	}

}