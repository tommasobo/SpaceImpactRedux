package spaceimpact.view;

import javafx.stage.*;
import spaceimpact.utilities.Pair;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.LinkedList;
import java.util.List;
import javafx.geometry.*;

public class Options extends Scene{
    
    private static final double WIDTH = 800;
    private static final double HEIGHT = 800;
    private static final String EASY = "Easy";
    private static final int EASY_INT = 1;
    private static final String NORMAL = "Normal";
    private static final int NORMAL_INT = 2;
    private static final String HARD = "Hard";
    private static final int HARD_INT = 4;
    private static final String EXTREME = "Extreme";
    private static final int EXTREME_INT = 8;
    private static final String LOWEST_RES = "1024x576";
    private static final double LOWEST_RES_WIDTH = 1024;
    private static final double LOWEST_RES_HEIGHT = 576;
    private static final double LOW_RES_WIDTH = 1280;
    private static final double LOW_RES_HEIGHT = 720;
    private static final String LOW_RES = "1280x720";
    private static final double MID_RES_WIDTH = 1600;
    private static final double MID_RES_HEIGHT = 900;
    private static final String MID_RES = "1600x900";
    private static final double HIGH_RES_WIDTH = 1920;
    private static final double HIGH_RES_HEIGHT = 1080;
    private static final String HIGH_RES = "1920x10800";
    
    private final List<Pair<String, Integer>> listDifficulties = new LinkedList<>();
    private final List<Pair<String, Pair<Double, Double>>> listResolutions = new LinkedList<>();
    private boolean fullScreen = false;
 
    private static Options mainScene = new Options();
    private static ChoiceBox<String> difficult;
    private static ChoiceBox<Integer> velocity;
    private static ChoiceBox<String> resolution;
    private static Stage mainStage;

    public Options() {
        super(new StackPane());

        this.listDifficulties.add(new Pair<String, Integer>(EASY, EASY_INT));
        this.listDifficulties.add(new Pair<String, Integer>(NORMAL, NORMAL_INT));
        this.listDifficulties.add(new Pair<String, Integer>(HARD, HARD_INT));
        this.listDifficulties.add(new Pair<String, Integer>(EXTREME, EXTREME_INT));
        
        this.listResolutions.add(new Pair<>(LOWEST_RES, new Pair<>(LOWEST_RES_WIDTH, LOWEST_RES_HEIGHT)));
        this.listResolutions.add(new Pair<>(LOW_RES, new Pair<>(LOW_RES_WIDTH, LOW_RES_HEIGHT)));
        this.listResolutions.add(new Pair<>(MID_RES, new Pair<>(MID_RES_WIDTH, MID_RES_HEIGHT)));
        this.listResolutions.add(new Pair<>(HIGH_RES, new Pair<>(HIGH_RES_WIDTH, HIGH_RES_HEIGHT)));
        
        final DropShadow dropShadow = new DropShadow();
        dropShadow.setColor(Color.DODGERBLUE);
        dropShadow.setRadius(25);
        dropShadow.setSpread(0.25);
        dropShadow.setBlurType(BlurType.GAUSSIAN);

        final Text optionsText = new Text("Options");
        optionsText.setEffect(dropShadow);
        optionsText.setTranslateX(WIDTH / 2 - 130);
        optionsText.setTranslateY(100);
        optionsText.setFont(Font.font(null, FontWeight.BOLD, 72));
        optionsText.setId("FX2");
        
        final Pane mainLayout = new Pane();    
        final VBox options = new VBox();
        options.setPadding(new Insets(30));
        options.setTranslateX((800 / 2) - (300 / 2 + 50));
        options.setTranslateY((800 / 2) - 150);
        options.setSpacing(50);
        
        this.setBox();
        final HBox difficultLayout = new HBox();
        difficultLayout.setSpacing(10);
        Text difficultText = new Text("Difficulty");
        difficultText.setFill(Color.WHITE);
        difficult.setValue(NORMAL);
        this.listDifficulties.forEach(e -> {
            difficult.getItems().add(e.getFirst());
        });
        difficultLayout.getChildren().addAll(difficultText, difficult);
        
        final HBox velocityLayout = new HBox();
        velocityLayout.setSpacing(10);
        Text velocityText = new Text("Frame per Second:");
        velocityText.setFill(Color.WHITE);
        velocity.getItems().addAll(30, 60, 120);
        velocityLayout.getChildren().addAll(velocityText, velocity);
        
        final HBox resolutionLayout = new HBox();
        resolutionLayout.setSpacing(10);
        Text resolutionText = new Text("In-Game Resolution:");
        resolutionText.setFill(Color.WHITE);
        this.listResolutions.forEach(e -> {
            resolution.getItems().add(e.getFirst());
        });
        resolution.setValue(LOW_RES);
        resolutionLayout.getChildren().addAll(resolutionText, resolution);
        
        options.getChildren().addAll(resolutionLayout, difficultLayout, velocityLayout);
        mainLayout.getChildren().add(options);
        
        final HBox bottomLayout = new HBox();
        bottomLayout.setPrefWidth(400);
        bottomLayout.setTranslateY(HEIGHT - 180);
        bottomLayout.setTranslateX(WIDTH / 2 - (400 / 2 - 50));
        bottomLayout.setSpacing(30);
        bottomLayout.setPadding(new Insets(20));
        final Button save = new Button("Save");
        save.setId("dark-blue");
        final Button back = new Button("Main Menu");
        back.setId("dark-blue");
        bottomLayout.getChildren().addAll(back, save);
        mainLayout.getChildren().addAll(optionsText, bottomLayout);
        
        mainLayout.setId("infoPane");
        this.setRoot(mainLayout);
        this.getStylesheets().add("style.css");    
        
        back.setOnAction(e -> mainStage.setScene(MainMenu.get(mainStage)));
        save.setOnAction(e -> {
            GenericBox.display(BoxType.SUCCESS, "Success", "Settings saved", "Ok");
            this.save();       
        });
    }

    private synchronized void setBox() {
        difficult = new ChoiceBox<>();
        velocity = new ChoiceBox<>(); 
        resolution = new ChoiceBox<>();
    }

    private void save() {
        if (Options.difficult.getValue().equals(EXTREME)){
            View.getController().setFPSDifficulty(Options.velocity.getValue(), this.listDifficulties.get(this.listDifficulties.size() - 1));
        } else if (Options.difficult.getValue().equals(HARD)) {
            View.getController().setFPSDifficulty(Options.velocity.getValue(), this.listDifficulties.get(this.listDifficulties.size() - 2));
        } else if (Options.difficult.getValue().equals(NORMAL)) {
            View.getController().setFPSDifficulty(Options.velocity.getValue(), this.listDifficulties.get(this.listDifficulties.size() - 3));
        } else {
            View.getController().setFPSDifficulty(Options.velocity.getValue(), this.listDifficulties.get(this.listDifficulties.size() - 4));
        }
        this.changeResolution();
    }
    
    private void changeResolution() {
        double currentWidth = LOW_RES_WIDTH, currentHeight = LOW_RES_HEIGHT;  
        for (int i = 0; i < this.listResolutions.size(); i++) {
            if (this.listResolutions.get(i).getFirst().equals(resolution.getValue())) {
                currentWidth = this.listResolutions.get(i).getSecond().getFirst();
                currentHeight = this.listResolutions.get(i).getSecond().getSecond();
                if (this.checkRes(currentWidth, currentHeight)) {
                    GameScreen.setResolution(currentWidth, currentHeight, this.fullScreen);  
                } else {
                    GenericBox.display(BoxType.ERROR, "Error", "Your screen is too small for this resolution!", "Back to settings");
                    break;
                }
            }
        }
            }

    private boolean checkRes(double currentWidth, double currentHeight) {
        final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double screenWidth = screenSize.getWidth();
        double screenHeight = screenSize.getHeight();;
        if (currentWidth > screenWidth || currentHeight > screenHeight) {
            return false;
        }
        if (currentWidth == screenWidth && currentHeight == screenHeight) {
            this.fullScreen = true;
        }
        return true;
    }

    static void update() {
        difficult.setValue(View.getController().getDifficulty());
        velocity.setValue(View.getController().getFPS());
    }

    static Options get(Stage mainWindow){
        mainStage = mainWindow;
        mainStage.setTitle("Space Impact Redux - Options");
        return mainScene;
    }

}