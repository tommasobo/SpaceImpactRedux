package spaceimpact.view;

import java.util.List;

import spaceimpact.controller.ControllerInterface;
import spaceimpact.model.Location;
import spaceimpact.utilities.Input;
import spaceimpact.utilities.Pair;

public class View implements ViewInterface{

    private static ControllerInterface c;
    private final InputHandler inputHandler = InputHandler.getInputHandler();
    private final GameScreen gameScreen = new GameScreen();
    
    public View(ControllerInterface controller) {
        this.setController(controller);
        MainWindow.launch(MainWindow.class); 
    }

    private synchronized void setController(ControllerInterface controller) {
        View.c = controller;
    }
    
    public static ControllerInterface getController() {
        return c;
    }

    @Override
    public List<Input> getInput() {
        return this.inputHandler.getInput();
    }
    
    @Override
    public void draw(List<Pair<String, Location>> listEntities) {
        this.gameScreen.drawOnScreen(listEntities);
    }
}
