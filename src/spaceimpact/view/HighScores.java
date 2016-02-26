package spaceimpact.view;

import javafx.stage.*;
import spaceimpact.utilities.Pair;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.util.List;

import javafx.geometry.*;

public class HighScores extends Scene{
    
    private static final int WIDTH_LOGO_HIGHSCORS = 450;
    private static final int HEIGTH_LOGO_HIGHSCORES = 150;
    private static final VBox listHighScores = new VBox(10); 
    private static final HighScores MAINSCENE = new HighScores();
    
    private static Stage mainStage;

    public HighScores() {
        super(new StackPane());
        final Logo logo = new Logo(WIDTH_LOGO_HIGHSCORS,HEIGTH_LOGO_HIGHSCORES);
            
        Text mainTitle = new Text("High Scores");
        mainTitle.setFont(Font.font(null, FontWeight.BOLD, 46));
        mainTitle.setText("High Scores");
        mainTitle.setId("FX2");
        
        final DropShadow dropShadow = new DropShadow();
        dropShadow.setColor(Color.DODGERBLUE);
        dropShadow.setRadius(25);
        dropShadow.setSpread(0.25);
        dropShadow.setBlurType(BlurType.GAUSSIAN);
        mainTitle.setEffect(dropShadow);
        
        listHighScores.getStylesheets().add("style.css");
        listHighScores.setAlignment(Pos.CENTER);
        listHighScores.setId("whiteText");
        listHighScores.setPadding(new Insets(10));

        final VBox layout = new VBox(10);
        final Button back = new Button("Main Menu");
        final Button reset = new Button ("Reset HighScores");
        final StackPane bottomLayout = new StackPane();
        final HBox bottomBox = new HBox();
        
        reset.setId("dark-blue");
        back.setId("dark-blue");
        
        bottomLayout.setAlignment(Pos.BOTTOM_CENTER);
        bottomLayout.setPadding(new Insets(0, 0, 100, 0));
        bottomBox.setSpacing(15);
        bottomBox.setAlignment(Pos.BOTTOM_CENTER);
        bottomBox.getChildren().addAll(back, reset);
        bottomLayout.getChildren().add(bottomBox);
        
        final StackPane descLayout = new StackPane();
            
        layout.getChildren().addAll(logo.getLogo(), mainTitle, listHighScores);
        layout.setSpacing(10);
        layout.setPadding(new Insets(8));
        layout.setAlignment(Pos.TOP_CENTER);

        descLayout.getChildren().addAll(layout, bottomLayout);
        descLayout.setId("infoPane");
        this.setRoot(descLayout);
        this.getStylesheets().add("style.css");
        back.setOnAction(e -> {
            listHighScores.getChildren().clear();
            mainStage.setScene(MainMenu.get(mainStage));      
        });
        reset.setOnAction(e -> {
            this.resetHighScores();
        });
    }

    private static void showHighScores() {
        final List<Pair<String, Integer>> listScores = View.getController().getCurrentHighScores();
        if (listScores.isEmpty()) {
            listHighScores.getChildren().add(new Label("No HighScores yet"));
        } else {
            for (int i = 0; i < listScores.size(); i++) { 
                final Label player = new Label();
                player.setId("whiteText");
                player.setText(listScores.get(i).getFirst() + " - " + listScores.get(i).getSecond());
                listHighScores.getChildren().add(player);
            }
        }     
    }

    public static Scene get(Stage mainWindow){
        showHighScores();
        mainStage = mainWindow;
        mainStage.setTitle("Space Impact Redux - High Scores");  
        return MAINSCENE;
    }
    
    private void resetHighScores() {
        final Boolean answer = ConfirmBox.display("Alert", "Are you sure you want to reset the High Scores?");
        if (answer) {
            if (View.getController().emptyHighScores()) {
                HighScores.listHighScores.getChildren().clear();
            } else {
                GenericBox.display(BoxType.ERROR, "Error", "An error occurred while emptying the scores", "Continue");
            }
            mainStage.setScene(HighScores.get(mainStage));
        }
    }

}
