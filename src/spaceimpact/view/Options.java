package spaceimpact.view;

import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.collections.FXCollections;
import javafx.geometry.*;

public class Options extends Scene{
    
    private static final int WIDTH_LOGO_HIGHSCORS = 350;
    private static final int HEIGTH_LOGO_HIGHSCORES = 120;
    private static final String HARD = "Hard";
    private static final String EASY = "Easy";
    private static final String NORMAL = "Normal";
    
    private static Options mainScene = new Options();
    ChoiceBox<String> difficult = new ChoiceBox<>();
    ChoiceBox<Integer> velocity = new ChoiceBox<>();
    private static Stage mainStage;

    public Options() {
        super(new StackPane());

        final Pane mainLayout = new Pane();
        
        final VBox options = new VBox();
        options.setPadding(new Insets(30));
        options.setTranslateX((800 / 2) - (300 / 2));
        options.setTranslateY((800 / 2) - 100);
        options.setSpacing(50);
        
        final HBox difficultLayout = new HBox();
        difficultLayout.setSpacing(10);
        Text difficultText = new Text("Difficult");
        difficultText.setFill(Color.WHITE);
        difficult.setItems(FXCollections.observableArrayList(HARD, NORMAL, EASY));
        difficult.setValue("Normal");
        difficultLayout.getChildren().addAll(difficultText, difficult);
        
        final HBox velocityLayout = new HBox();
        velocityLayout.setSpacing(10);
        Text velocityText = new Text("Frame per Second:");
        velocityText.setFill(Color.WHITE);
        velocity.getItems().addAll(120, 60, 30);
        velocity.setValue(30);
        velocityLayout.getChildren().addAll(velocityText, velocity);
        
        final HBox resolutionLayout = new HBox();
        resolutionLayout.setSpacing(10);
        Text resolutionText = new Text("Resolution:");
        resolutionText.setFill(Color.WHITE);
        ChoiceBox<String> resolution = new ChoiceBox<>();
        resolution.getItems().addAll("1920x1080", "1600x900", "1280x720");
        resolution.setValue("1280x720");
        resolutionLayout.getChildren().addAll(resolutionText, resolution);
        
        options.getChildren().addAll(resolutionLayout, difficultLayout, velocityLayout);
        mainLayout.getChildren().add(options);
        
        final HBox bottomLayout = new HBox();
        bottomLayout.setSpacing(10);
        final Button save = new Button("Save");
        final Button back = new Button("Main Menu");
        bottomLayout.getChildren().addAll(save, back);
        
        mainLayout.getChildren().add(bottomLayout);
        
        mainLayout.setId("infoPane");
        this.setRoot(mainLayout);
        this.getStylesheets().add("style.css");    
        
        back.setOnAction(e -> mainStage.setScene(MainMenu.get(mainStage)));
        save.setOnAction(e -> this.save());
    }

    private void save() {
        if (this.difficult.getValue().equals(HARD)) {
            View.getController().setFPSDifficulty(this.velocity.getValue(), 3);
        } else if (this.difficult.getValue().equals(NORMAL)) {
            View.getController().setFPSDifficulty(this.velocity.getValue(), 2);
        } else {
            View.getController().setFPSDifficulty(this.velocity.getValue(), 1);
        }
    }

    public static Options get(Stage mainWindow){
        mainStage = mainWindow;
        mainStage.setTitle("Space Impact Redux - Options");
        return mainScene;
    }

}