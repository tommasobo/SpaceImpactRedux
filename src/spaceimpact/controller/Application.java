package spaceimpact.controller;

import spaceimpact.view.View;
import spaceimpact.view.ViewInterface;

/**
 * Class used to start the application.
 */
public final class Application {

    /**
     * Start a new application.
     */
    public static void main(final String[] args) {
        final ControllerInterface c = new Controller();
        final ViewInterface v = new View(c);
        c.setView(v);
        v.startView();
    }

    private Application() {
    }
}