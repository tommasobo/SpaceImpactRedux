package spaceimpact.view;

import java.util.List;

import spaceimpact.controller.ControllerInterface;
import spaceimpact.utilities.Input;

public class View implements ViewInterface{

    private static ControllerInterface c;
    private final InputHandler inputHandler = InputHandler.getInputHandler();
    
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
        
    public List<Input> getInput() {
        return this.inputHandler.getInput();
    }
}
