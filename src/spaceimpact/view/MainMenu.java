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

    private Stage window;
    private Button newGame = new Button("New Game");
    private Button highScores = new Button ("High Scores");
    private Button options = new Button("Options");
    private final Button info = new Button("Info");
    private Button exit = new Button("Exit");

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.window = primaryStage;
        window.setTitle("Menu");
        window.setOnCloseRequest(e -> {
            e.consume();
            this.closeProgram();
        });

        VBox vbox = new VBox(newGame, highScores, options, info, exit);
        vbox.setPrefWidth(150);
        vbox.setAlignment(Pos.CENTER);

        newGame.setMinWidth(vbox.getPrefWidth());
        highScores.setMinWidth(vbox.getPrefWidth());
        options.setMinWidth(vbox.getPrefWidth());
        info.setMinWidth(vbox.getPrefWidth());
        exit.setMinWidth(vbox.getPrefWidth());
        this.exit.setOnAction(e -> this.closeProgram());
        
        vbox.setSpacing(10);
        vbox.setPadding(new Insets(20));
        StackPane layout = new StackPane();
        layout.getChildren().add(vbox);
        
        Scene scene = new Scene(layout, 500, 450);
        window.setScene(scene);
        window.show();
    }
    
    private void closeProgram() {
        Boolean answer = ConfirmBox.display("Alert", "Are you sure you want to exit the game?");
        if (answer) {
            window.close();
        }
    }

}
