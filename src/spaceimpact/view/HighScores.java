package spaceimpact.view;

import javafx.stage.*;
import spaceimpact.controller.ControllerInterface;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.geometry.*;

public class HighScores extends Scene{
    
    private static final int WIDTH_LOGO_HIGHSCORS = 450;
    private static final int HEIGTH_LOGO_HIGHSCORES = 150;
    
    private static final HighScores mainScene = new HighScores();
    //private static ControllerInterface controller;
    private static Stage mainStage;

    public HighScores() {
        super(new StackPane());
        final Logo logo = new Logo(WIDTH_LOGO_HIGHSCORS,HEIGTH_LOGO_HIGHSCORES);
            
        final Text label = new Text();
        label.setText("High Scores");
        label.setId("highScores");
                  
        final VBox listHighScores = new VBox(10);
        if (View.getController().getCurrentHighScores().isEmpty()) {
            listHighScores.getChildren().add(new Label("No HighScores yet"));
        } else {
            for (int i = 0; i < View.getController().getCurrentHighScores().size(); i++) { 
                final Label player = new Label();
                player.setText(i + View.getController().getCurrentHighScores().get(i).getFirst() + View.getController().getCurrentHighScores().get(i).getSecond());
                listHighScores.getChildren().add(player);
            }
        }    
             
        listHighScores.getStylesheets().add("style.css");
        listHighScores.setAlignment(Pos.CENTER);
        listHighScores.setId("whiteText");
        listHighScores.setPadding(new Insets(10));

        final VBox layout = new VBox(10);
        final Button back = new Button("Go back");
        back.setId("dark-blue");
        final StackPane bottomLayout = new StackPane();
        bottomLayout.setPadding(new Insets(0, 0, 100, 0));
        bottomLayout.getChildren().add(back);
        bottomLayout.setAlignment(Pos.BOTTOM_CENTER);
        final StackPane descLayout = new StackPane();
            
        layout.getChildren().addAll(logo.getLogo(), label, listHighScores);
        layout.setSpacing(10);
        layout.setPadding(new Insets(8));
        layout.setAlignment(Pos.TOP_CENTER);

        descLayout.getChildren().addAll(layout, bottomLayout);
        descLayout.setId("infoPane");
        this.setRoot(descLayout);
        this.getStylesheets().add("style.css");
        back.setOnAction(e -> mainStage.setScene(MainMenu.get(mainStage)));       
    }

    public static HighScores get(Stage mainWindow){
        mainStage = mainWindow;
        mainStage.setTitle("Space Impact Redux - High Scores");  
        return mainScene;
    }
    
    /*public static void setController(ControllerInterface controlle) {
        controller = controlle;
    }*/

}
