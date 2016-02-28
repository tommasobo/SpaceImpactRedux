package spaceimpact.controller;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import spaceimpact.model.Direction;
import spaceimpact.model.ModelInterface;
import spaceimpact.utilities.Input;
import spaceimpact.utilities.Pair;
import spaceimpact.view.ViewInterface;

/**
 * Implementation of ControllerInterface.
 */
public final class Controller implements ControllerInterface {
    private static final String HS_FILENAME = "hiscores";
    private static final int HS_NSCORES = 10;

    private final HighScoresManagerInterface hsManager;
    private final InputParserInterface inputParser;
    private int fps = 60;
    private Pair<String, Integer> diff = new Pair<>("Normal", 2);
    private Optional<GameLoop> gl;
    private ViewInterface view;
    private ModelInterface model;
    private volatile int score;

    /**
     * Creates a new Controller. The method setView must be called!
     */
    public Controller() {
        this.hsManager = new HighScoresManager(Controller.HS_FILENAME, Controller.HS_NSCORES);
        this.gl = Optional.empty();
        this.inputParser = list -> {
            final boolean n = list.contains(Input.W);
            final boolean s = list.contains(Input.S);
            final boolean e = list.contains(Input.D);
            final boolean w = list.contains(Input.A);
            final boolean shoot = list.contains(Input.SPACE);
            Optional<Direction> d = Optional.empty();
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
            }
            return new Pair<Optional<Direction>, Boolean>(d, shoot);
        };
    }

    @Override
    public void setView(final ViewInterface v) {
        this.view = v;
    }

    @Override
    public void startGameLoop() throws IllegalStateException {
        if (this.gl.isPresent()) {
            throw new IllegalStateException();
        }
        final GameLoop game = new GameLoop(this.fps, this.diff.getSecond(), this, this.view, this.inputParser);
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