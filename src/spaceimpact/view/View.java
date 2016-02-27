package spaceimpact.view;

import java.util.List;

import javafx.application.Application;
import javafx.application.Platform;
import spaceimpact.controller.ControllerInterface;
import spaceimpact.model.Location;
import spaceimpact.utilities.Input;
import spaceimpact.utilities.Pair;

/**
 * Main class of the View part of the application. It implements the method of
 * the ViewInterface.
 *
 */
public class View implements ViewInterface {

    private static ControllerInterface controller;
    private final InputHandler inputHandler = InputHandler.getInputHandler();
    private static GameScreen gameScreen;

    /**
     * Constructor of the class. It saves the controller of the game.
     * 
     * @param c
     *            The controller of the game.
     */
    public View(final ControllerInterface c) {
        this.setController(c);
    }

    /**
     * Setter of the controller (thread safe)
     * 
     * @param c
     *            The controller of the game
     */
    private synchronized void setController(final ControllerInterface c) {
        View.controller = c;
    }

    @Override
    public void startView() {
        Application.launch(MainWindow.class);
    }

    @Override
    public List<Input> getInput() {
        return this.inputHandler.getList();
    }

    @Override
    public void draw(final List<Pair<Pair<String, Double>, Location>> listEntities) {
        Platform.runLater(() -> View.gameScreen.drawOnScreen(listEntities));
    }

    @Override
    public void updateInfo(final int hp, final int shields, final int score) {
        Platform.runLater(() -> View.gameScreen.updateInfo(hp, shields, score));
    }

    @Override
    public void showText(final int nLevel) {
        Platform.runLater(() -> View.gameScreen.won(nLevel));
    }

    @Override
    public void showText(final String powerUp) {
        Platform.runLater(() -> View.gameScreen.powerUp(powerUp));
    }

    /**
     * Setter for the Game Screen. It is necessary to save it in order to call
     * some methods in it.
     * 
     * @param gamescreen
     *            The GameScreen
     */
    static void setGameScreen(final GameScreen gamescreen) {
        View.gameScreen = gamescreen;
    }

    /**
     * Getter of the controller
     * 
     * @return The controller of the game
     */
    static ControllerInterface getController() {
        return View.controller;
    }

}