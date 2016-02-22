package spaceimpact.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import spaceimpact.utilities.ImageLoader;

public class GameOverScreen extends Scene{
    
    private static final GameOverScreen mainScene = new GameOverScreen();
    private static Stage mainStage;
    private final Label score = new Label();
    private final TextField name = new TextField();
    private final Button enter = new Button("Enter");
    private final Label saved = new Label("Score Saved");


    public GameOverScreen() {
        super(new StackPane());

        final VBox mainLayout = new VBox();
        mainLayout.setAlignment(Pos.CENTER);
        
        ImageView gameOver = new ImageView(ImageLoader.getLoader().getImageFromPath("gameOver.jpg"));
        gameOver.setFitWidth(500);
        gameOver.setFitHeight(250);
                
        final Label insertName = new Label("Insert your name: ");
        insertName.setId("whiteText");
        enter.setId("dark-blue");
        enter.setOnAction(e -> {
            name.setDisable(true);
            enter.setDisable(true);
            saved.setVisible(true);
            View.getController().setCurrentPlayerName(name.getText());
        });
        final HBox insertLayout = new HBox();
        insertLayout.setAlignment(Pos.CENTER);
        insertLayout.setSpacing(10);
        insertLayout.getChildren().addAll(insertName, name, enter);
        insertLayout.setPadding(new Insets(100, 0 ,70 ,0));
        
        final Button retry = new Button("Retry");
        retry.setId("dark-blue");
        retry.setOnAction(e -> {
            this.resetSaved();
            final GameScreen gameScreen = new GameScreen();
            View.setGameScreen(gameScreen);
            mainStage.setScene(gameScreen.get(mainStage));
            View.getController().startGameLoop();   
        });
        final Button exit = new Button("Exit");
        final Button mainMenu = new Button("Menu");
        mainMenu.setId("dark-blue");
        mainMenu.setOnAction(e -> {
            this.resetSaved();
            mainStage.setScene(MainMenu.get(mainStage));
        });
        exit.setId("dark-blue");
        final HBox bottomLayout = new HBox();
        bottomLayout.setPadding(new Insets(150, 0 , 10, 0));
        bottomLayout.setSpacing(25);
        bottomLayout.setAlignment(Pos.BOTTOM_CENTER);
        bottomLayout.getChildren().addAll(retry, mainMenu, exit);
        
        saved.setVisible(false);
        saved.setTextFill(Color.GREEN);
        
        mainLayout.getChildren().addAll(gameOver, score, insertLayout, saved, bottomLayout);
        mainLayout.setId("gameOver");
        mainLayout.getStylesheets().add("style.css");
        this.setRoot(mainLayout);
    }
    
    private void resetSaved() {
        this.enter.setDisable(false);
        this.name.setDisable(false);
        this.saved.setVisible(false);
        this.name.setText("");
    }

    public static Scene get(Stage mainWindow){
        mainStage = mainWindow;
        mainStage.setWidth(800);
        mainStage.setHeight(800);
        mainStage.centerOnScreen();
        mainStage.setTitle("Space Impact Redux - Game Over");  
        return mainScene;
    }
    
    /*public static void gameOver(int scoreValue) {
        score.setText("Your score is: " + Integer.toString(scoreValue));
    }*/

}
