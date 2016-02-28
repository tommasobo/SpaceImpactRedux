package spaceimpact.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import spaceimpact.utilities.ImageLoader;

/**
 * This class is responsible for the Game Over screen.
 *
 */
public class GameOverScreen extends Scene {

    private static final GameOverScreen MAINSCENE = new GameOverScreen();
    private static final double GAME_OVER_LOGO_WIDTH = 500;
    private static final double GAME_OVER_LOGO_HEIGHT = 250;
    private static final double WINDOW_SIZE = 800;

    private static Stage mainStage;
    private final TextField name = new TextField();
    private final Button enter = new Button("Enter");
    private final Label saved = new Label("Score Saved");

    /**
     * Constructor for the GameOverScreen. It sets up the scene.
     */
    public GameOverScreen() {
        super(new StackPane());

        final VBox mainLayout = new VBox();
        mainLayout.setAlignment(Pos.CENTER);

        final ImageView gameOver = new ImageView(ImageLoader.getLoader().getImageFromPath("images/gameover.gif"));
        gameOver.setFitWidth(GAME_OVER_LOGO_WIDTH);
        gameOver.setFitHeight(GAME_OVER_LOGO_HEIGHT);

        this.name.setFocusTraversable(false);
        this.enter.setFocusTraversable(false);
        final Label insertName = new Label("Insert your name: ");
        insertName.setId("whiteText");
        this.enter.setId("dark-blue");
        this.enter.setOnAction(e -> {
            if (this.checkName()) {
                this.name.setDisable(true);
                this.enter.setDisable(true);
                this.saved.setText("Score Saved");
                this.saved.setTextFill(Color.GREEN);
                this.saved.setVisible(true);
                if (!View.getController().setCurrentPlayerName(name.getText())) {
                    GenericBox.display(BoxType.ERROR, "Error", "An error occurred while saving the score", "Continue");
                }
            }
        });
        final HBox insertLayout = new HBox();
        insertLayout.setAlignment(Pos.CENTER);
        insertLayout.setSpacing(10);
        insertLayout.getChildren().addAll(insertName, name, enter);
        insertLayout.setPadding(new Insets(100, 0, 70, 0));

        final Button retry = new Button("Retry");
        retry.setFocusTraversable(false);
        retry.setId("dark-blue");
        retry.setOnAction(e -> {
            this.resetSaved();
            final GameScreen gameScreen = new GameScreen();
            View.setGameScreen(gameScreen);
            mainStage.setScene(gameScreen.get(mainStage));
            View.getController().startGameLoop();
        });
        final Button exit = new Button("Exit");
        exit.setFocusTraversable(false);
        final Button mainMenu = new Button("Menu");
        mainMenu.setFocusTraversable(false);
        mainMenu.setId("dark-blue");
        mainMenu.setOnAction(e -> {
            this.resetSaved();
            mainStage.setScene(MainMenu.get(mainStage));
        });
        exit.setId("dark-blue");
        exit.setOnAction(e -> {
            this.resetSaved();
            ClosureHandler.getClosureHandler();
            ClosureHandler.closeProgram(mainStage);
        });
        final Button hiScores = new Button("High Scores");
        hiScores.setFocusTraversable(false);
        hiScores.setId("dark-blue");
        hiScores.setOnAction(e -> {
            this.resetSaved();
            mainStage.setScene(HighScores.get(mainStage));
        });
        final HBox bottomLayout = new HBox();
        bottomLayout.setPadding(new Insets(150, 0, 10, 0));
        bottomLayout.setSpacing(25);
        bottomLayout.setAlignment(Pos.BOTTOM_CENTER);
        bottomLayout.getChildren().addAll(retry, mainMenu, hiScores, exit);

        saved.setVisible(false);
        saved.setTextFill(Color.GREEN);

        mainLayout.getChildren().addAll(gameOver, insertLayout, saved, bottomLayout);
        mainLayout.setId("gameOver");
        mainLayout.getStylesheets().add("style.css");
        this.setRoot(mainLayout);
    }

    /**
     * Private method. Check if the input is empty. If it is empty it displays
     * an error.
     * 
     * @return True if the name is valid, false otherwise.
     */
    private boolean checkName() {
        if (this.name.getText().isEmpty()) {
            this.saved.setText("Enter a valide name");
            this.saved.setTextFill(Color.RED);
            this.saved.setVisible(true);
            return false;
        }
        return true;

    }

    /**
     * Private method. It resets the state of the buttons and TextField after
     * the user exit this screen
     * 
     */
    private void resetSaved() {
        this.enter.setDisable(false);
        this.name.setDisable(false);
        this.saved.setVisible(false);
        this.name.setText("");
    }

    /**
     * Getter of this Scene.
     * 
     * @param mainWindow
     *            The current Stage of the game that needs to be used.
     * @return The current scene.
     */
    public static Scene get(final Stage mainWindow) {
        mainStage = mainWindow;
        mainStage.setFullScreen(false);
        mainStage.setWidth(WINDOW_SIZE);
        mainStage.setHeight(WINDOW_SIZE);
        mainStage.centerOnScreen();
        mainStage.setTitle("Space Impact Redux - Game Over");
        return MAINSCENE;
    }
}
