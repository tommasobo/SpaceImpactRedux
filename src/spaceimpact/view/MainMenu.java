package spaceimpact.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class MainMenu{

    private static final int WIDTH = 800;
    private static final int HEIGHT = 800;
    
    private Stage mainWindow;
    private final Button newGame = new Button("New Game");
    private final Button highScores = new Button ("High Scores");
    private final Button options = new Button("Options");
    private final Button info = new Button("Info");
    private final Button exit = new Button("Exit");

    public void display(Stage mainWindow) throws Exception {
        //Font.loadFont(MainMenu.class.getResource("space_invaders.ttf").toExternalForm(), 10);
        
        this.mainWindow = mainWindow;
        mainWindow.setTitle("Menu");
        
        Image image = new Image("logo.png");
        ImageView iv1 = new ImageView();
        iv1.setImage(image);
        iv1.setFitHeight(250);
        iv1.setFitWidth(650);
        
        final StackPane logoBox = new StackPane();
        logoBox.setAlignment(Pos.TOP_CENTER);
        logoBox.getChildren().add(iv1);
        logoBox.setPadding(new Insets(40));
        
        mainWindow.setOnCloseRequest(e -> {
            e.consume();
            this.closeProgram();
        });

        final VBox vbox = new VBox(newGame, highScores, options, info, exit);
        vbox.setPrefWidth(200);
        vbox.setAlignment(Pos.BOTTOM_CENTER);

        this.newGame.setMinWidth(vbox.getPrefWidth());
        this.newGame.setId("dark-blue");
        this.highScores.setMinWidth(vbox.getPrefWidth());
        this.highScores.setId("dark-blue");
        this.options.setMinWidth(vbox.getPrefWidth());
        this.options.setId("dark-blue");
        this.info.setMinWidth(vbox.getPrefWidth());
        this.info.setOnAction(e -> InfoBox.display("Info Box"));
        this.info.setId("dark-blue");
        this.exit.setMinWidth(vbox.getPrefWidth());
        this.exit.setOnAction(e -> this.closeProgram());
        this.exit.setId("dark-blue");
        
        vbox.setSpacing(10);
        vbox.setPadding(new Insets(200));
        final StackPane layout = new StackPane();
        layout.getChildren().addAll(logoBox, vbox);
        
        final Scene scene = new Scene(layout, WIDTH, HEIGHT);
        scene.getStylesheets().add("style.css");
        
        this.mainWindow.setScene(scene);
        this.mainWindow.setHeight(HEIGHT);
        this.mainWindow.setWidth(WIDTH);
        this.mainWindow.setTitle("Space Impact Redux");
        this.mainWindow.centerOnScreen();
        this.mainWindow.setResizable(false);
        this.mainWindow.show();
    }
    
    private void closeProgram() {
        final Boolean answer = ConfirmBox.display("Alert", "Are you sure you want to exit the game?");
        if (answer) {
            this.mainWindow.close();
        }
    }

}