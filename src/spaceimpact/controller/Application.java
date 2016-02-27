package spaceimpact.controller;

import spaceimpact.view.View;
import spaceimpact.controller.Controller;;

/**
 * Class used to start the application.
 */
public final class Application {

    static Controller c;
    static View v;
    
    /**
     * Start a new application.
     */
    public static void main(final String[] args) {
        c = new Controller();
        v = new View(c);
        c.setView(v);
        v.startView();
    }
}
