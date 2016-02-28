package spaceimpact.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * This class is responsible for showing to the user the Main Menu. It extends
 * the current Scene.
 *
 */
public final class MainMenu extends Scene {

    private static final double WIDTH = 800;
    private static final double HEIGHT = 800;
    private static final double WIDTH_LOGO = 600;
    private static final double HEIGHT_LOGO = 200;
    private static final double BUTTON_WIDTH = 250;
    private static final MainMenu MAINSCENE = new MainMenu();

    private static Stage mainStage;
    private final Button newGame = new Button("New Game");
    private final Button highScores = new Button("High Scores");
    private final Button options = new Button("Options");
    private final Button info = new Button("Info");
    private final Button exit = new Button("Exit");

    /**
     * Constructor of the class. It sets up the Scene.
     */
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
        vbox.setSpacing(10);
        vbox.setPadding(new Insets(170));

        this.newGame.setMinWidth(vbox.getPrefWidth());
        this.newGame.setId("dark-blue");
        this.newGame.setOnAction(e -> {
            InputHandler.getInputHandler().emptyList();
            View.getController().startGameLoop();
            final GameScreen gameScreen = new GameScreen();
            View.setGameScreen(gameScreen);
            mainStage.setScene(gameScreen.get(mainStage));
            mainStage.setFullScreen(gameScreen.isFullScreen());
        });
        this.highScores.setMinWidth(vbox.getPrefWidth());
        this.highScores.setId("dark-blue");
        this.highScores.setOnAction(e -> {
            mainStage.setScene(HighScores.get(MainMenu.mainStage));
        });
        this.options.setMinWidth(vbox.getPrefWidth());
        this.options.setId("dark-blue");
        this.options.setOnAction(e -> {
            mainStage.setScene(Options.get(MainMenu.mainStage));
            Options.update();
        });
        this.info.setMinWidth(vbox.getPrefWidth());
        this.info.setOnAction(e -> InfoBox.display());
        this.info.setId("dark-blue");
        this.exit.setMinWidth(vbox.getPrefWidth());
        ClosureHandler.getClosureHandler();
        this.exit.setOnAction(e -> ClosureHandler.closeProgram(mainStage));
        this.exit.setId("dark-blue");

        final StackPane layout = new StackPane();
        layout.getChildren().addAll(logoBox, vbox);
        layout.setId("mainPane");

        this.setRoot(layout);
        this.getStylesheets().add("style.css");
    }

    /**
     * Getter of this Scene.
     * 
     * @param mainWindow
     *            The Stage to place this Scene.
     * @return The current Scene.
     */
    static MainMenu get(final Stage mainWindow) {
        mainStage = mainWindow;
        mainStage.setFullScreen(false);
        mainStage.setWidth(WIDTH);
        mainStage.setHeight(HEIGHT);
        mainStage.centerOnScreen();
        mainStage.setTitle("Space Impact Redux - Menu");
        return MAINSCENE;
    }

}