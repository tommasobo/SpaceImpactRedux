package spaceimpact.view;

import java.util.List;

import javafx.application.Application;
import javafx.application.Platform;
import spaceimpact.controller.ControllerInterface;
import spaceimpact.model.Location;
import spaceimpact.utilities.Input;
import spaceimpact.utilities.Pair;

public class View implements ViewInterface {

	private static ControllerInterface c;
	private final InputHandler inputHandler = InputHandler.getInputHandler();
	private static GameScreen gameScreen;

	public View(final ControllerInterface controller) {
		this.setController(controller);
	}

	private synchronized void setController(final ControllerInterface controller) {
		View.c = controller;
	}

	static ControllerInterface getController() {
		return View.c;
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
	
	public void updateInfo(final int hp, final int shields, final int score) {
	    Platform.runLater(() -> View.gameScreen.updateInfo(hp, shields, score));
	}

	static void setGameScreen(final GameScreen gamescreen) {
	    View.gameScreen = gamescreen;
	}
}