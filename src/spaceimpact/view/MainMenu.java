package spaceimpact.view;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainMenu extends Application {

    private Button newGame;
    private Button highScores;
    private Button options;
    private Button exit;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Menu");
        
        newGame = new Button();
        highScores = new Button();
        options = new Button();
        exit = new Button();

        VBox vbox = new VBox(newGame, highScores, options, exit);
        vbox.setPrefWidth(150);
        vbox.setAlignment(Pos.CENTER);

        newGame.setText("New Game");
        newGame.setMinWidth(vbox.getPrefWidth());
        highScores.setText("High Scores");
        highScores.setMinWidth(vbox.getPrefWidth());
        options.setText("Options");
        options.setMinWidth(vbox.getPrefWidth());
        exit.setText("Exit");
        exit.setMinWidth(vbox.getPrefWidth());
        
        vbox.setSpacing(10);
        vbox.setPadding(new Insets(20));
        StackPane layout = new StackPane();
        layout.getChildren().add(vbox);
        
        Scene scene = new Scene(layout, 500, 450);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

}
