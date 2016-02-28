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
import javafx.scene.input.KeyCode;
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

/**
 * This class is responsible for handling the game screen and everything in it.
 * It extends Scene.
 *
 */
public class GameScreen extends Scene {

    private static final double BASIC_FONT = 18;
    private static final double BASIC_BUTTON_WIDTH = 110;
    private static final double BASIC_BUTTON_HEIGHT = 25;
    private static final double BASIC_RES_WIDTH = 1280;
    private static final double BASIC_RES_HEIGHT = 768;
    private static final double WIDTH_POWER_UP = 800;
    private static final double HEIGHT_POWER_UP = 160;
    private static final double WIDTH_LEVEL = 800;
    private static final double HEIGHT_LEVEL = 250;
    private static final long DURATION_SHOW_TEXT = 2000;
    private static final String PAUSE = "Pause";
    private static final String RESUME = "Resume";

    private Stage mainStage;
    private static double resConstantWidth = 1;
    private static double resConstantHeight = 1;
    private static double inGameWidth = BASIC_RES_WIDTH;
    private static double inGameHeight = BASIC_RES_HEIGHT;
    private final Group root = new Group();
    private final Pane backgroundLayer = new Pane();
    private final DrawEntities drawEntities = new DrawEntities(inGameWidth, inGameHeight);
    private final PlayerInfo playerInfo = new PlayerInfo();
    private final DropShadow dropShadow = new DropShadow();
    private final HBox infoBox = new HBox();
    private final Button pauseButton = new Button(PAUSE);
    private final Button infoButton = new Button("Info");
    private final Label hp = new Label();
    private final Label shields = new Label();
    private final Label score = new Label();
    private final Timer timer = new java.util.Timer();

    /**
     * Constructor for GameScreen. It sets up the scene.
     */
    public GameScreen() {
        super(new StackPane());

        final HBox buttonGame = new HBox();
        pauseButton.setId("dark-blue");
        pauseButton.setDefaultButton(false);
        pauseButton.setFocusTraversable(false);
        pauseButton.setOnAction(e -> {
            this.pause();
        });
        infoButton.setId("dark-blue");
        infoButton.setFocusTraversable(false);
        infoButton.setOnAction(e -> InfoBox.display());
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

        final VBox verticalInfoBox = new VBox();
        verticalInfoBox.setAlignment(Pos.CENTER);
        verticalInfoBox.setSpacing(2);
        infoBox.getChildren().addAll(this.hp, this.shields);
        verticalInfoBox.getChildren().addAll(infoBox, this.score);
        infoBox.setAlignment(Pos.CENTER);
        infoBox.setId("hpBox");
        infoBox.setPadding(new Insets(0, 4, 0, 4));

        this.dropShadow.setColor(Color.DODGERBLUE);
        this.dropShadow.setRadius(25);
        this.dropShadow.setSpread(0.25);
        this.dropShadow.setBlurType(BlurType.GAUSSIAN);

        topLayout.getChildren().add(verticalInfoBox);
        topBox.setId("gameScreen");

        this.root.getChildren().addAll(this.backgroundLayer, topBox);
        this.getInput();
        this.resize();
        this.setRoot(this.root);
    }

    /**
     * Private method responsible for getting the inputs from the user.
     */
    private void getInput() {
        final InputHandler inputHandler = InputHandler.getInputHandler();
        inputHandler.emptyList();
        this.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.BACK_SPACE) {
                View.getController().pauseGameLoop();
                this.backMenu();
            } else if (event.getCode() == KeyCode.P) {
                this.pause();
            } else if (event.getCode() == KeyCode.ESCAPE) {
                View.getController().pauseGameLoop();
                ClosureHandler.getClosureHandler();
                ClosureHandler.closeProgram(this.mainStage);
            }
            inputHandler.press(event.getCode());
        });
        this.addEventFilter(KeyEvent.KEY_RELEASED, event -> {
            inputHandler.release(event.getCode());
        });
    }

    /**
     * Private method. It's called when the user wants to pause or resume the
     * game.
     * 
     */
    private void pause() {
        if (View.getController().isGameLoopPaused()) {
            InputHandler.getInputHandler().emptyList();
            View.getController().resumeGameLoop();
            this.pauseButton.setText(PAUSE);
        } else {
            View.getController().pauseGameLoop();
            this.pauseButton.setText(RESUME);
        }

    }

    /**
     * It draws on screen the entities that are present on the current frame.
     * 
     * @param listEntities
     *            List of the active entities.
     */
    void drawOnScreen(final List<Pair<Pair<String, Double>, Location>> listEntities) {
        this.drawEntities.draw(this.backgroundLayer, listEntities);
    }

    /**
     * It updates the view with the current information about the player. If the
     * game is over (hp <= 0) it displays the Game Over screen.
     * 
     * 
     * @param hpValue
     *            Current hp of the player.
     * @param shieldsValue
     *            Current level status of the shields.
     * @param scoreValue
     *            Current score.
     */
    void updateInfo(final int hpValue, final int shieldsValue, final int scoreValue) {
        if (hpValue <= 0) {
            this.mainStage.setScene(GameOverScreen.get(this.mainStage));
        } else {
            this.playerInfo.update(this.hp, this.shields, this.score, hpValue, shieldsValue, scoreValue);
        }
    }

    /**
     * Getter of this Scene.
     * 
     * @param mainWindow
     *            The Stage to place this Scene.
     * @return The current Scene.
     */
    GameScreen get(final Stage mainWindow) {
        this.mainStage = mainWindow;
        this.mainStage.setWidth(GameScreen.inGameWidth);
        this.mainStage.setHeight(GameScreen.inGameHeight);
        this.mainStage.centerOnScreen();
        this.mainStage.setTitle("Space Impact Redux");
        this.mainStage.setFullScreen(Options.getIsFullScreen());
        return this;
    }

    /**
     * This method is called when a level is completed and it displays at the
     * center of the screen a message.
     * 
     * @param nLevel
     *            The number of the level just completed.
     */
    void won(final int nLevel) {
        final StackPane levelWon = new StackPane();
        final Label textLevelWon = new Label();

        textLevelWon.setId("FX2");
        levelWon.setPrefSize(WIDTH_LEVEL * resConstantWidth, HEIGHT_LEVEL * resConstantHeight);
        levelWon.setAlignment(Pos.CENTER);
        textLevelWon.setEffect(dropShadow);
        textLevelWon.setFont(Font.font(null, FontWeight.BOLD, 72 * resConstantWidth));
        textLevelWon.setVisible(true);
        textLevelWon.setTextFill(Color.WHITE);
        textLevelWon.setText("Level " + nLevel + " completed");
        levelWon.getChildren().add(textLevelWon);
        levelWon.setLayoutX((GameScreen.inGameWidth / 2) - ((WIDTH_LEVEL * resConstantWidth) / 2));
        levelWon.setLayoutY((GameScreen.inGameHeight / 2) - ((HEIGHT_LEVEL * resConstantHeight) / 2));
        this.root.getChildren().add(levelWon);
        this.showText(textLevelWon);
    }

    /**
     * It displays at the top of the screen the name of the powerUp picked up by
     * the player.
     * 
     * @param powerUp
     *            A String with the name of the current powerUp.
     */
    void powerUp(final String powerUp) {
        final StackPane powerUpPane = new StackPane();
        final Label powerUpText = new Label();

        powerUpText.setId("powerUp");
        powerUpPane.setPrefSize(WIDTH_POWER_UP * resConstantWidth, HEIGHT_POWER_UP * resConstantHeight);
        powerUpPane.setAlignment(Pos.CENTER);
        powerUpText.setEffect(dropShadow);
        powerUpText.setFont(Font.font(null, FontWeight.BOLD, 50 * resConstantWidth));
        powerUpText.setVisible(true);
        powerUpText.setTextFill(Color.LIGHTSKYBLUE);
        powerUpText.setText(powerUp);
        powerUpPane.getChildren().add(powerUpText);
        powerUpPane.setLayoutX((GameScreen.inGameWidth / 2) - ((WIDTH_POWER_UP * resConstantWidth) / 2));
        this.root.getChildren().add(powerUpPane);
        this.showText(powerUpText);
    }

    /**
     * Private method. It displays a text for a certain amount of time.
     * 
     * @param text
     *            The text to display
     */
    private void showText(final Label text) {
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> text.setVisible(false));
            }
        }, DURATION_SHOW_TEXT, 1);
    }

    /**
     * Setter for the current resolution.
     * 
     * @param width
     *            Current width of the game screen
     * @param height
     *            Current height of the game screen
     * @param fullScreen
     *            The mode of the screen.
     */
    static synchronized void setResolution(final double width, final double height, final boolean fullScreen) {
        inGameWidth = width;
        inGameHeight = height;
        resConstantWidth = GameScreen.inGameWidth / BASIC_RES_WIDTH;
        resConstantHeight = GameScreen.inGameHeight / BASIC_RES_HEIGHT;
    }

    /**
     * Private method. It's called every time a new Game Screen is created. It
     * resizes everything in it according to the current resolution.
     */
    private void resize() {
        this.infoBox.setMinWidth((280 * resConstantWidth));
        this.infoBox.setMaxSize((280 * resConstantWidth), (50 * resConstantHeight));
        this.infoBox.setMinHeight((50 * resConstantHeight));
        this.infoBox.setSpacing(12 * resConstantWidth);
        this.score.setFont(Font.font(null, FontWeight.BOLD, BASIC_FONT * resConstantWidth));
        this.hp.setFont(Font.font(null, FontWeight.BOLD, BASIC_FONT * resConstantWidth));
        this.shields.setFont(Font.font(null, FontWeight.BOLD, BASIC_FONT * resConstantWidth));
        this.infoButton.setPrefSize(BASIC_BUTTON_WIDTH * resConstantWidth, BASIC_BUTTON_HEIGHT * resConstantHeight);
        this.infoButton.setPrefSize(BASIC_BUTTON_WIDTH * resConstantWidth, BASIC_BUTTON_HEIGHT * resConstantHeight);
        this.pauseButton.setPrefSize(BASIC_BUTTON_WIDTH * resConstantWidth, BASIC_BUTTON_HEIGHT * resConstantHeight);
        this.infoButton.setFont(Font.font(15 * resConstantHeight));
        this.infoButton.setOnMouseEntered(e -> this.infoButton.setFont(Font.font(15 * resConstantHeight)));
        this.pauseButton.setOnMouseEntered(e -> this.pauseButton.setFont(Font.font(15 * resConstantHeight)));
        this.pauseButton.setFont(Font.font(15 * resConstantHeight));
    }

    /**
     * Getter of the status of the Stage.
     * 
     * @return True if the current window (Stage) is in full screen mode
     */
    boolean isFullScreen() {
        return Options.getIsFullScreen();
    }

    /**
     * Private method. Called when the user wants to go back to the main menu
     * while the game is running. It prompts a dialog box where the user can
     * choose to go back to the menu or not.
     */
    private void backMenu() {
        final Boolean answer = ConfirmBox.display("Alert", "Are you sure you want to go back to the menu?");
        if (answer) {
            View.getController().abortGameLoop();
            InputHandler.getInputHandler().emptyList();
            // isFullScreen = false;
            this.mainStage.setScene(MainMenu.get(this.mainStage));
        } else {
            InputHandler.getInputHandler().emptyList();
            View.getController().resumeGameLoop();
        }
    }

}