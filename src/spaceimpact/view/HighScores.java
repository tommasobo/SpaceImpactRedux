package spaceimpact.view;

import java.util.List;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import spaceimpact.utilities.Pair;

/**
 * This class is responsible for showing to the user the High Score screen. It
 * extends the current Scene.
 *
 */
public class HighScores extends Scene {

    private static final int WIDTH_LOGO_HIGHSCORS = 450;
    private static final int HEIGTH_LOGO_HIGHSCORES = 150;
    private static final HighScores MAINSCENE = new HighScores();

    private static VBox listHighScores;
    private static Stage mainStage;

    /**
     * Constructor for the scene. It sets up the scene.
     */
    public HighScores() {
        super(new StackPane());
        final Logo logo = new Logo(WIDTH_LOGO_HIGHSCORS, HEIGTH_LOGO_HIGHSCORES);

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

        listHighScores = new VBox();
        listHighScores.getStylesheets().add("style.css");
        listHighScores.setAlignment(Pos.CENTER);
        listHighScores.setId("whiteText");
        listHighScores.setPadding(new Insets(10));

        final VBox layout = new VBox(10);
        final Button back = new Button("Main Menu");
        final Button reset = new Button("Reset HighScores");
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

        final StackPane mainLayout = new StackPane();

        layout.getChildren().addAll(logo.getLogo(), mainTitle, listHighScores);
        layout.setSpacing(10);
        layout.setPadding(new Insets(8));
        layout.setAlignment(Pos.TOP_CENTER);

        mainLayout.getChildren().addAll(layout, bottomLayout);
        mainLayout.setId("infoPane");
        this.setRoot(mainLayout);
        this.getStylesheets().add("style.css");
        back.setOnAction(e -> {
            listHighScores.getChildren().clear();
            mainStage.setScene(MainMenu.get(mainStage));
        });
        reset.setOnAction(e -> {
            this.resetHighScores();
        });
    }

    /**
     * Private method. If there are no highScores it shows a simple Label with a
     * message, otherwise the list of the highScores.
     */
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

    /**
     * Getter of this Scene.
     * 
     * @param mainWindow
     *            The Stage to place this Scene.
     * @return The current Scene.
     */
    static Scene get(final Stage mainWindow) {
        showHighScores();
        mainStage = mainWindow;
        mainStage.setTitle("Space Impact Redux - High Scores");
        return MAINSCENE;
    }

    /**
     * Private method. It resets the High Scores after asking a confirmation to
     * the user.
     */
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
