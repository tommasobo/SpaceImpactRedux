package spaceimpact.view;

import java.util.List;

import spaceimpact.controller.ControllerInterface;
import spaceimpact.model.Location;
import spaceimpact.utilities.Input;
import spaceimpact.utilities.Pair;

public class View implements ViewInterface{

    private static ControllerInterface c;
    private final InputHandler inputHandler = InputHandler.getInputHandler();
    private GameScreen gameScreen;
    
    public View(ControllerInterface controller) {
        this.setController(controller);
    }

    private synchronized void setController(ControllerInterface controller) {
        View.c = controller;
    }
    
    public static ControllerInterface getController() {
        return c;
    }
    
    public void startView() {
        MainWindow.launch(MainWindow.class); 
    }

    @Override
    public List<Input> getInput() {
        System.out.println("CIAO" + this.inputHandler.getInput());
        return this.inputHandler.getInput();
    }
    
    @Override
    public void draw(List<Pair<String, Location>> listEntities) {
        this.gameScreen = new GameScreen();
        this.gameScreen.drawOnScreen(listEntities);
        System.out.println("dd");
    }
}
