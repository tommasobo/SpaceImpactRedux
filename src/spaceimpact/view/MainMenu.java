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
    
    private static final MainMenu mainScene = new MainMenu();
    private static Stage mainStage;
 
    private final Button newGame = new Button("New Game");
    private final Button highScores = new Button ("High Scores");
    private final Button options = new Button("Options");
    private final Button info = new Button("Info");
    private final Button exit = new Button("Exit");

    private MainMenu() {   
        super(new StackPane(), WIDTH, HEIGHT);
        final StackPane logoBox = new StackPane();
        final Logo logo = new Logo(WIDTH_LOGO, HEIGHT_LOGO);
        logoBox.setAlignment(Pos.TOP_CENTER);
        logoBox.getChildren().add(logo.getLogo());
        logoBox.setPadding(new Insets(60));

        final VBox vbox = new VBox(newGame, highScores, options, info, exit);
        vbox.setPrefWidth(BUTTON_WIDTH);
        vbox.setAlignment(Pos.BOTTOM_CENTER);

        this.newGame.setMinWidth(vbox.getPrefWidth());
        this.newGame.setId("dark-blue");
        this.highScores.setMinWidth(vbox.getPrefWidth());
        this.highScores.setId("dark-blue");
        this.highScores.setOnAction(e -> {
            //HighScores.setController(controller);
            mainStage.setScene(HighScores.get(MainMenu.mainStage));
        }); //Modificare
        this.options.setMinWidth(vbox.getPrefWidth());
        this.options.setId("dark-blue");
        this.options.setOnAction(e -> mainStage.setScene(Options.get(MainMenu.mainStage))); //Modificare
        this.info.setMinWidth(vbox.getPrefWidth());
        this.info.setOnAction(e -> InfoBox.display("Info Box")); //Modificare
        this.info.setId("dark-blue");
        this.exit.setMinWidth(vbox.getPrefWidth());
        this.exit.setOnAction(e -> this.closeProgram()); //Modificare
        this.exit.setId("dark-blue");
            
        vbox.setSpacing(10);
        vbox.setPadding(new Insets(170));
            
        final StackPane layout = new StackPane();
        layout.getChildren().addAll(logoBox, vbox);
        this.setRoot(layout);
        layout.setId("mainPane");
           
        this.getStylesheets().add("style.css");
        
    }
    
    public static MainMenu get(Stage mainWindow) {
        mainStage = mainWindow;
        mainStage.setTitle("Space Impact Redux - Menu");    
        return mainScene;
    }
    
    private void closeProgram() {
        final Boolean answer = ConfirmBox.display("Alert", "Are you sure you want to exit the game?");
        if (answer) {
            mainStage.close();
        }
    }

}