package spaceimpact.view;

import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class MainWindow extends Application{
    
    private static final int WIDTH = 800;
    private static final int HEIGHT = 800;
    
    private Stage mainWindow;
    private final MainMenu mainMenu = new MainMenu();
    
    
    public static void main(final String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.mainWindow = primaryStage;
        mainWindow.setTitle("Menu");
        this.mainWindow.getIcons().add(new Image("file:res/icon.png"));
        
        this.mainMenu.display(this.mainWindow);

        this.mainWindow.setHeight(HEIGHT);
        this.mainWindow.setWidth(WIDTH);
        this.mainWindow.setTitle("Space Impact Redux");
        this.mainWindow.centerOnScreen();
        this.mainWindow.setResizable(false);
        this.mainWindow.show();
    }
}
