package spaceimpact.controller;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import spaceimpact.model.Area;
import spaceimpact.model.Direction;
import spaceimpact.model.GameStatus;
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

    private static final int PAUSE_SLEEPMS = 500;

    private final long ticLenght;
    private final int fps;
    private final int diff;
    private final ViewInterface view;
    private final ControllerInterface ctrl;
    private final InputParserInterface inputParser;
    private volatile Status status;
    private volatile int score;
    private volatile int nLevel;
    private volatile ModelInterface model;

    /**
     * Constructor for GameLoop.
     *
     * @param framespersecond
     *            The number of frames per second
     * @param difficulty
     *            The difficulty multiplier
     * @param controller
     *            The Controller that created the GameLoop
     * @param v
     *            The View object
     * @param inParser
     *            The inputParser to be used
     */
    public GameLoop(final int framespersecond, final int difficulty, final ControllerInterface controller,
            final ViewInterface v, final InputParserInterface inParser) {
        this.status = Status.READY;
        this.fps = framespersecond;
        this.ticLenght = 1000 / framespersecond;
        this.view = v;
        this.nLevel = 1;
        this.diff = difficulty;
        this.model = new Model(framespersecond, this.nLevel, this.diff);
        this.ctrl = controller;
        this.score = 0;
        this.inputParser = inParser;
    }

    /**
     * Private method. Checks the current game status.
     *
     * @param stat
     *            The status to be compared.
     * @return True if the game is currently in the given Status, false
     *         otherwise.
     */
    private synchronized boolean isInState(final Status stat) {
        return this.status == stat;
    }

    /**
     * Private method. Sets the game status.
     *
     * @param stat
     *            The target status.
     */
    private synchronized void setState(final Status stat) {
        this.status = stat;
    }

    /**
     * Causes the GameLoop to stop even if the game didn't reach an end. The
     * halt could be delayed up to a tic later.
     */
    public void abort() {
        this.setState(Status.KILLED);
    }

    /**
     * Causes the GameLoop to pause. If it's not running, nothing happens. The
     * pause could be delayed up to a tic later.
     */
    public void pause() {
        if (this.isRunning()) {
            this.setState(Status.PAUSED);
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
        this.setState(Status.RUNNING);
        int timer = 0;
        while (!this.isInState(Status.KILLED)) {
            if (this.isInState(Status.RUNNING)) {
                if (this.model.getGameStatus() == GameStatus.Running) {
                    final long startTime = System.currentTimeMillis();
                    timer++;
                    if (timer > this.fps) {
                        timer = 0;
                        final Optional<String> s = this.model.getLatestPowerUp();
                        if (s.isPresent()) {
                            this.view.showText(s.get());
                        }
                    }
                    this.score += GameLoop.this.model.getScores() * this.diff / 2;
                    final List<Pair<Pair<String, Double>, Location>> toDraw = new LinkedList<>();
                    final Location pl = this.model.getPlayerLocation();
                    toDraw.add(new Pair<>(new Pair<>("Entities/Player.png", 0d), new Location(pl)));
                    if (this.model.getPlayerShield() > 0) {
                        final Area a = new Area(pl.getArea().getWidth() * 2, pl.getArea().getHeight() * 2);
                        toDraw.add(new Pair<>(new Pair<>("images/shield.png", 0d),
                                new Location(pl.getX() + a.getWidth() / 10, pl.getY(), a)));
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
                    final Pair<Optional<Direction>, Boolean> tmp = this.inputParser.parseInputs(this.view.getInput());
                    this.model.informInputs(tmp.getFirst(), tmp.getSecond());
                    this.model.updateAll();
                    try {
                        if (this.model.getPlayerLife() <= 0) {
                            this.setState(Status.KILLED);
                        }
                        t.join();
                        final long timeSpent = System.currentTimeMillis() - startTime;
                        if (timeSpent < this.ticLenght) {
                            Thread.sleep(this.ticLenght - timeSpent);
                        }
                    } catch (final InterruptedException ex1) {
                        this.setState(Status.KILLED);
                    }
                } else if (this.model.getGameStatus() == GameStatus.Over) {
                    this.setState(Status.KILLED);
                } else {
                    this.view.showText(this.nLevel);
                    this.nLevel++;
                    this.model = new Model(this.fps, this.nLevel, this.diff);
                }
            } else {
                try {
                    Thread.sleep(GameLoop.PAUSE_SLEEPMS);
                } catch (final InterruptedException e) {
                    this.setState(Status.KILLED);
                }
            }
        }
        this.ctrl.setScore(this.score);
        this.ctrl.abortGameLoop();
    }

    /**
     * Causes the GameLoop to resume. If it wasn't paused nothing happens.
     * Resume could be delayed up to 0.5 seconds later.
     */
    public void unPause() {
        if (this.isPaused()) {
            this.setState(Status.RUNNING);
        }
    }

    /**
     * Checks if the game is paused.
     *
     * @return True if the game is paused, false otherwise
     */
    public boolean isPaused() {
        return this.isInState(Status.PAUSED);
    }

    /**
     * Checks if the game is running.
     *
     * @return True if the game is running, false otherwise
     */
    public boolean isRunning() {
        return this.isInState(Status.RUNNING);
    }

    /**
     * Getter of the final score.
     *
     * @return The game score
     * @throws IllegalStateException
     *             If this method is used before game termination.
     */
    public int getScores() throws IllegalStateException {
        if (this.isInState(Status.KILLED)) {
            throw new IllegalStateException();
        }
        return this.score;
    }
}