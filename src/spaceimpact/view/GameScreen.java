package spaceimpact.view;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import spaceimpact.model.Location;
import spaceimpact.utilities.Pair;

public class GameScreen extends Scene {

    private static final double WIDTH_GAME = 1280;
    private static final double HEIGHT_GAME = 720;
    private static final double WIDTH_POWER_UP = 800;
    private static final double HEIGHT_POWER_UP = 160;
    private static final double WIDTH_LEVEL = 800;
    private static final double HEIGHT_LEVEL = 250;
    private static final long DURATION_SHOW_TEXT = 2000;
    private static final String PAUSE = "Pause";
    private static final String RESUME = "Resume";

    private Stage mainStage;
    private final Group root = new Group();
    private final Pane backgroundLayer = new Pane();
    private final DrawEntities drawEntities = new DrawEntities();
    private final PlayerInfo playerInfo = new PlayerInfo();
    private final DropShadow dropShadow = new DropShadow();
    private final Label hp = new Label();
    private final Label shields = new Label();
    private final Label score = new Label();
    private final Timer timer = new java.util.Timer();

    public GameScreen() {
                super(new StackPane());

                final HBox buttonGame = new HBox();
                final Button pauseButton = new Button(PAUSE);
                pauseButton.setId("dark-blue");
                pauseButton.setDefaultButton(false);
                pauseButton.setOnMousePressed(e -> {
                        if (pauseButton.getText().equals(GameScreen.PAUSE)) {
                                View.getController().pauseGameLoop();
                                pauseButton.setText(GameScreen.RESUME);
                        } else {
                                View.getController().resumeGameLoop();
                                pauseButton.setText(GameScreen.PAUSE);
                        }
                });
                final Button infoButton = new Button("Info");
                infoButton.setId("dark-blue");
                infoButton.setOnMousePressed(e -> InfoBox.display("Info Box"));
                buttonGame.getChildren().addAll(pauseButton, infoButton);
                buttonGame.setSpacing(10);
                buttonGame.setAlignment(Pos.TOP_CENTER);
                buttonGame.setPadding(new Insets(10, 0, 0, 0));

                final HBox topLayout = new HBox();
                final VBox topBox = new VBox();
                topBox.getChildren().addAll(buttonGame, topLayout);
                topBox.getStylesheets().add("style.css");
                topLayout.setPadding(new Insets(5, 15, 15, 15));
                topLayout.setSpacing(4);

                this.hp.setTextFill(Color.GREEN);
                this.shields.setTextFill(Color.BLUE);
                this.score.setTextFill(Color.YELLOW);
                this.hp.setId("score");
                this.score.setId("score");
                this.shields.setId("score");

                final VBox verticalInfoBox = new VBox();
                verticalInfoBox.setAlignment(Pos.CENTER);
                verticalInfoBox.setSpacing(2);
                final HBox infoBox = new HBox();
                infoBox.setSpacing(12);
                infoBox.getChildren().addAll(this.hp, this.shields);
                verticalInfoBox.getChildren().addAll(infoBox, this.score);
                infoBox.setAlignment(Pos.CENTER);
                infoBox.setId("hpBox");
                infoBox.setMinWidth(280);
                infoBox.setMaxSize(280, 50);
                infoBox.setMinHeight(50);
                infoBox.setPadding(new Insets(0, 4, 0, 4));
                
                this.dropShadow.setColor(Color.DODGERBLUE);
                this.dropShadow.setRadius(25);
                this.dropShadow.setSpread(0.25);
                this.dropShadow.setBlurType(BlurType.GAUSSIAN);

                topLayout.getChildren().add(verticalInfoBox);
                topBox.setId("gameScreen");

                final InputHandler inputHandler = InputHandler.getInputHandler();
                inputHandler.emptyList();
                this.root.getChildren().addAll(this.backgroundLayer, topBox);
                this.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
                        inputHandler.press(event.getCode());
                });
                this.addEventFilter(KeyEvent.KEY_RELEASED, event -> {
                        inputHandler.release(event.getCode());
                });
                this.setRoot(this.root);
        }

    void drawOnScreen(final List<Pair<Pair<String, Double>, Location>> listEntities) {
        this.drawEntities.draw(this.backgroundLayer, listEntities, GameScreen.HEIGHT_GAME);
    }

    void updateInfo(final int hp, final int shields, final int score) {
        if (hp <= 0) {
            this.mainStage.setScene(GameOverScreen.get(this.mainStage));
        } else {
            this.playerInfo.update(this.hp, this.shields, this.score, hp, shields, score);
        }
    }

    public GameScreen get(final Stage mainWindow) {
        this.mainStage = mainWindow;
        this.mainStage.setWidth(GameScreen.WIDTH_GAME);
        this.mainStage.setHeight(GameScreen.HEIGHT_GAME);
        this.mainStage.centerOnScreen();
        this.mainStage.setTitle("Space Impact Redux");
        return this;
    }

    public void won(final int nLevel) {
        final StackPane levelWon = new StackPane();
        final Label textLevelWon = new Label();
        
        textLevelWon.setId("FX2");
        levelWon.setPrefSize(WIDTH_LEVEL, HEIGHT_LEVEL);
        levelWon.setAlignment(Pos.CENTER);
        textLevelWon.setEffect(dropShadow);
        textLevelWon.setFont(Font.font(null, FontWeight.BOLD, 72));
        textLevelWon.setVisible(true);
        textLevelWon.setTextFill(Color.WHITE);
        textLevelWon.setText("Level " + nLevel + " completed");
        levelWon.getChildren().add(textLevelWon);
        levelWon.setLayoutX((GameScreen.WIDTH_GAME / 2) - (WIDTH_LEVEL / 2));
        levelWon.setLayoutY((GameScreen.HEIGHT_GAME / 2) - (HEIGHT_LEVEL / 2));
        this.root.getChildren().add(levelWon);
        this.showText(textLevelWon);
    }

    void powerUp(final String powerUp) {      
        final StackPane powerUpPane = new StackPane();
        final Label powerUpText = new Label();
        
        powerUpText.setId("powerUp");
        powerUpPane.setPrefSize(WIDTH_POWER_UP, HEIGHT_POWER_UP);
        powerUpPane.setAlignment(Pos.CENTER);
        powerUpText.setEffect(dropShadow);
        powerUpText.setFont(Font.font(null, FontWeight.BOLD, 50));
        powerUpText.setVisible(true);
        powerUpText.setTextFill(Color.LIGHTSKYBLUE);
        powerUpText.setText(powerUp);
        powerUpPane.getChildren().add(powerUpText);
        powerUpPane.setLayoutX((GameScreen.WIDTH_GAME / 2) - (WIDTH_POWER_UP / 2));
        this.root.getChildren().add(powerUpPane);
        this.showText(powerUpText);
    }
    
    private void showText(Label text) {
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                    Platform.runLater(() -> text.setVisible(false));
            }
    }, DURATION_SHOW_TEXT, 1);
    }
    
}