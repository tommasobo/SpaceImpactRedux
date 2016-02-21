package spaceimpact.view;

import java.util.List;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import spaceimpact.model.Location;
import spaceimpact.utilities.Pair;

public class GameScreen extends Scene {

    private static final double WIDTH_GAME = 1280;
    private static final double HEIGHT_GAME = 720;
    private static final String PAUSE = "Pause";
    private static final String RESUME = "Resume";

    private Stage mainStage;
    private final Group root = new Group();
    private final Pane backgroundLayer = new Pane();
    private final InputHandler inputHandler = InputHandler.getInputHandler();
    private final DrawEntities drawEntities = new DrawEntities();
    private final PlayerInfo playerInfo = new PlayerInfo();
    private final Label hp = new Label();
    private final Label shields = new Label();
    private final Label score = new Label();
    
	public GameScreen() {
		super(new StackPane());
		
		final HBox buttonGame = new HBox();
		final Button pauseButton = new Button("Pause");
		pauseButton.setId("dark-blue");
		pauseButton.setDefaultButton(false);
		pauseButton.setOnMousePressed(e -> {
		    if (pauseButton.getText().equals(PAUSE)) {
		        View.getController().pauseGameLoop();
		        pauseButton.setText(RESUME);
		    } else {
		        View.getController().resumeGameLoop();
		        pauseButton.setText(PAUSE);
		    }		    
		});
		final Button infoButton = new Button("Info");
		infoButton.setId("dark-blue");
		infoButton.setOnMousePressed(e -> InfoBox.display("Info Box"));
		buttonGame.getChildren().addAll(pauseButton, infoButton);
		buttonGame.setSpacing(10);
		buttonGame.setAlignment(Pos.TOP_CENTER);
		buttonGame.setPadding(new Insets(10,0,0,0));
		
		final HBox topLayout = new HBox();
		final VBox topBox = new VBox();
		topBox.getChildren().addAll(buttonGame, topLayout);
		topBox.getStylesheets().add("style.css");
		topLayout.setPadding(new Insets(5,15,15,15));
		topLayout.setSpacing(4);

		this.hp.setTextFill(Color.GREEN);
		this.shields.setTextFill(Color.BLUE);
		this.score.setTextFill(Color.YELLOW);
		this.score.setId("score");
		this.shields.setId("score");
		
		final HBox hpBox = new HBox();
		hpBox.setSpacing(5);
		hpBox.getChildren().addAll(this.hp, this.shields, this.score);
		hpBox.setAlignment(Pos.CENTER);
		hpBox.setId("hpBox");
		hpBox.setMinWidth(300);
		hpBox.setMaxSize(300, 50);
		hpBox.setMinHeight(50);
		hpBox.setPadding(new Insets(0,4,4,4));

		topLayout.getChildren().add(hpBox);
		topBox.setId("gameScreen");

		this.root.getChildren().addAll(this.backgroundLayer, topBox);
		this.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
		    if (!inputHandler.getList().contains(inputHandler.singleKey(event.getCode()))) {
		        inputHandler.getList().add(inputHandler.singleKey(event.getCode()));
		    }
		});
		this.addEventFilter(KeyEvent.KEY_RELEASED, event -> {
		    if (inputHandler.getList().contains(inputHandler.singleKey(event.getCode()))) {
                        inputHandler.getList().remove(inputHandler.singleKey(event.getCode()));
                    }
		});
		this.setRoot(this.root);
	}

	void drawOnScreen(final List<Pair<Pair<String, Double>, Location>> listEntities) {
		this.drawEntities.draw(this.backgroundLayer, listEntities, HEIGHT_GAME);
	}
	
	void updateInfo(final int hp, final int shields, final int score) {
	    this.playerInfo.update(this.hp, this.shields, this.score, hp, shields, score);
	}

	public GameScreen get(final Stage mainWindow) {
		this.mainStage = mainWindow;
		this.mainStage.setWidth(GameScreen.WIDTH_GAME);
		this.mainStage.setHeight(GameScreen.HEIGHT_GAME);
		this.mainStage.centerOnScreen();
		this.mainStage.setTitle("Space Impact Redux");
		return this;
	}

}