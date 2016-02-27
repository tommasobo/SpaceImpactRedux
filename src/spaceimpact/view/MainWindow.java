package spaceimpact.view;

import javafx.application.Application;
import javafx.stage.Stage;
import spaceimpact.utilities.ImageLoader;

/**
 * Class responsible for creating the main window (Stage) of the application.
 * Its size is fixed (800x800) and not resizable. The first time it displays the
 * main menu
 *
 */
public class MainWindow extends Application {

    private static final double WIDTH = 800;
    private static final double HEIGHT = 800;
    private final Stage mainWindow = new Stage();

    /**
     * Constructor of the class. It sets up the Stage.
     */
    public MainWindow() {
    }

    /**
     * It starts the JavaFX application.
     */
    @Override
    public void start(final Stage primaryStage) {
        this.mainWindow.getIcons().add(ImageLoader.getLoader().getImageFromPath("Icons/icon.png"));
        this.mainWindow.setHeight(HEIGHT);
        this.mainWindow.setWidth(WIDTH);
        this.mainWindow.setTitle("Space Impact Redux");
        this.mainWindow.centerOnScreen();
        this.mainWindow.setResizable(false);

        this.mainWindow.setOnCloseRequest(e -> {
            e.consume();
            if (View.getController().isGameLoopRunning()) {
                View.getController().pauseGameLoop();
            }
            ClosureHandler.closeProgram(this.mainWindow);
        });

        this.mainWindow.setScene(MainMenu.get(this.mainWindow));
        this.mainWindow.show();
    }
}