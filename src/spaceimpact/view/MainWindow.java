package spaceimpact.view;

import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class MainWindow extends Application{
    
    private static final int WIDTH = 800;
    private static final int HEIGHT = 800;
    
    private Stage mainWindow;
    private MainMenu mainMenu = new MainMenu();
    
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.mainWindow = primaryStage;
        mainWindow.setTitle("Menu");
        this.mainWindow.getIcons().add(new Image("file:res/icon.png"));
        
        this.mainMenu.display(this.mainWindow);
        
        Image image = new Image("icon.jpg");
        ImageView iv1 = new ImageView();
        iv1.setImage(image);
        iv1.setFitHeight(200);
        iv1.setFitWidth(600);
        
        this.mainWindow.setHeight(HEIGHT);
        this.mainWindow.setWidth(WIDTH);
        this.mainWindow.setTitle("Space Impact Redux");
        this.mainWindow.centerOnScreen();
        this.mainWindow.setResizable(false);
        this.mainWindow.show();
    }


}
