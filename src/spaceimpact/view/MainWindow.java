package spaceimpact.view;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import spaceimpact.controller.ControllerInterface;

public class MainWindow extends Application{
    
    private static final int WIDTH = 800;
    private static final int HEIGHT = 800;
    private static ControllerInterface c;
    
    private final Stage mainWindow = new Stage();
    
    public static void main(final String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage){
        this.mainWindow.getIcons().add(new Image("file:res/icon.png"));
        this.mainWindow.setHeight(HEIGHT);
        this.mainWindow.setWidth(WIDTH);
        this.mainWindow.setTitle("Space Impact Redux");
        this.mainWindow.centerOnScreen();
        this.mainWindow.setResizable(false);
        
        this.mainWindow.setOnCloseRequest(e -> {
            e.consume();
            closeProgram();
        });
        
        this.setScene(MainMenu.get(this.mainWindow));
        this.mainWindow.show();
    }
    
    public void setScene(final Scene scene) {
        this.mainWindow.setScene(scene);
    }
    
    private void closeProgram() {
        final Boolean answer = ConfirmBox.display("Alert", "Are you sure you want to exit the game?");
        if (answer) {
            this.mainWindow.close();
        }
    }

    public static void setController(ControllerInterface controller) {
        c = controller;   
    }
}