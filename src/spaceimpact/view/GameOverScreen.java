package spaceimpact.view;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class GameOverScreen extends Scene{
    
    private static final GameOverScreen mainScene = new GameOverScreen();
    private static Stage mainStage;
    private final Label score = new Label();

    public GameOverScreen() {
        super(new StackPane());

        final VBox mainLayout = new VBox();
        
        final Logo logo = new Logo(200, 200);
                
        final Label insertName = new Label("Insert your name: ");
        final TextField name = new TextField();
        final Button enter = new Button();
        final HBox insertLayout = new HBox();
        insertLayout.getChildren().addAll(insertName, name, enter);
        
        final Button retry = new Button("Retry");
        retry.setOnAction(e -> {
            View.getController().startGameLoop();
            final GameScreen gameScreen = new GameScreen();
            View.setGameScreen(gameScreen);
            mainStage.setScene(gameScreen.get(mainStage));
        });
        final Button exit = new Button("Exit");
        final HBox bottomLayout = new HBox();
        bottomLayout.getChildren().addAll(retry, exit);
        
        mainLayout.getChildren().addAll(logo.getLogo(), this.score, insertLayout, bottomLayout);
    }
    
    public static Scene get(Stage mainWindow){
        mainStage = mainWindow;
        mainStage.setTitle("Space Impact Redux - Game Over");  
        return mainScene;
    }
    
    public void gameOver(int score) {
        this.score.setText("Your score is: " + score);
    }

}
