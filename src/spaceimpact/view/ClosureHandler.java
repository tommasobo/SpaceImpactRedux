package spaceimpact.view;

import javafx.stage.Stage;

/**
 * This class is responsible for closing the application correctly.
 *
 */
public final class ClosureHandler {

    private static final ClosureHandler CLOSUREHANDLER = new ClosureHandler();

    private ClosureHandler() {
    };

    /**
     * Getter of the singleton.
     * 
     * @return The singleton instance of the class.
     */
    static ClosureHandler getClosureHandler() {
        return ClosureHandler.CLOSUREHANDLER;
    }

    /**
     * It prompts a dialog box where the user can choice either to close the
     * application or not. If the answer is yes, it closes the application and
     * aborts the game loop if it is running.
     * 
     * @param mainWindow
     *            The current window of the application
     */
    static void closeProgram(final Stage mainWindow) {
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
