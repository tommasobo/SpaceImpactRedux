package spaceimpact.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainMenu extends Scene{

    private static final int WIDTH = 800;
    private static final int HEIGHT = 800;
    private static final int WIDTH_LOGO = 600;
    private static final int HEIGHT_LOGO = 200;
    private static final int BUTTON_WIDTH = 250;
    
    private static MainMenu mainScene = new MainMenu();
    private static Stage mainStage;
    
    private final Logo logo = new Logo(WIDTH_LOGO, HEIGHT_LOGO);
    private final StackPane layout = new StackPane();
    private final Button newGame = new Button("New Game");
    private final Button highScores = new Button ("High Scores");
    private final Button options = new Button("Options");
    private final Button info = new Button("Info");
    private final Button exit = new Button("Exit");

    private MainMenu() {   
        super(new StackPane(), WIDTH, HEIGHT);
        try {
            final StackPane logoBox = new StackPane();
            logoBox.setAlignment(Pos.TOP_CENTER);
            logoBox.getChildren().add(this.logo.getLogo());
            logoBox.setPadding(new Insets(60));

            final VBox vbox = new VBox(newGame, highScores, options, info, exit);
            vbox.setPrefWidth(BUTTON_WIDTH);
            vbox.setAlignment(Pos.BOTTOM_CENTER);

            this.newGame.setMinWidth(vbox.getPrefWidth());
            this.newGame.setId("dark-blue");
            this.highScores.setMinWidth(vbox.getPrefWidth());
            this.highScores.setId("dark-blue");
            this.highScores.setOnAction(e -> mainStage.setScene(HighScores.get(MainMenu.mainStage)));

            this.options.setMinWidth(vbox.getPrefWidth());
            this.options.setId("dark-blue");
            this.info.setMinWidth(vbox.getPrefWidth());
            this.info.setOnAction(e -> InfoBox.display("Info Box"));
            this.info.setId("dark-blue");
            this.exit.setMinWidth(vbox.getPrefWidth());
            //this.exit.setOnAction(e -> this.closeProgram());
            this.exit.setId("dark-blue");
            
            vbox.setSpacing(10);
            vbox.setPadding(new Insets(170));
            
            layout.getChildren().addAll(logoBox, vbox);
            this.setRoot(layout);
            layout.setId("mainPane");
            
            this.getStylesheets().add("style.css");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static MainMenu get(Stage mainWindow) {
        try {
            mainStage = mainWindow;
            return mainScene;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return mainScene;
    }
    
    /*private void closeProgram() {
        final Boolean answer = ConfirmBox.display("Alert", "Are you sure you want to exit the game?");
        if (answer) {
            this.mainWindow.close();
        }
    }*/

}