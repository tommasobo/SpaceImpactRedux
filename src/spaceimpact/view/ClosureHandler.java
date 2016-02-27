package spaceimpact.view;

import javafx.stage.Stage;

public class ClosureHandler {
    
    private static final ClosureHandler CLOSUREHANDLER = new ClosureHandler();
    
    private ClosureHandler() {};

    public static ClosureHandler getClosureHandler() {
        return ClosureHandler.CLOSUREHANDLER;
    }

    public static void closeProgram(final Stage mainWindow) {
        final Boolean answer = ConfirmBox.display("Alert", "Are you sure you want to exit the game?");
        if (View.getController().isGameLoopPaused()) {
            if (answer) {
                View.getController().abortGameLoop();
                System.exit(0);
                mainWindow.close();
            } else {
                View.getController().resumeGameLoop();
            }
        }
        if (answer) {
            System.exit(0);
            mainWindow.close();
        }
    }
    
}
