package spaceimpact.view;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class MainWindow extends Application{
    
    private static final double WIDTH = 800;
    private static final double HEIGHT = 800;
    private static final String SEP = System.getProperty("file.separator");
    
    private final Stage mainWindow = new Stage();

    @Override
    public void start(Stage primaryStage){
        this.mainWindow.getIcons().add(new Image("file:res" + SEP + "icons" + SEP + "icon.png"));
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
            this.closeProgram();
        });
        
        this.setScene(MainMenu.get(this.mainWindow));
        this.mainWindow.show();
    }
    
    public void setScene(final Scene scene) {
        this.mainWindow.setScene(scene);
    }
    
    private void closeProgram() {
        final Boolean answer = ConfirmBox.display("Alert", "Are you sure you want to exit the game?");
        if (View.getController().isGameLoopPaused()) {
            if (answer) {
                View.getController().abortGameLoop();
                System.exit(0);
                this.mainWindow.close();
            } else {
                View.getController().resumeGameLoop();
            }
        }
        if (answer) {
            this.mainWindow.close();
        }
    }
}