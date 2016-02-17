package spaceimpact.view;

import spaceimpact.controller.ControllerInterface;

public class View {

    private static ControllerInterface c;
    
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
}
