package spaceimpact.view;

import java.util.List;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import spaceimpact.model.Location;
import spaceimpact.utilities.Pair;

public class GameScreen extends Scene {

	private static final double WIDTH_GAME = 1280;
	private static final double HEIGHT_GAME = 720;

	private Stage mainStage;
	private final Group root = new Group();
	private final Pane backgroundLayer = new Pane();
	private final InputHandler inputHandler = InputHandler.getInputHandler();
	private final DrawEntities drawEntities = new DrawEntities();

	public GameScreen() {
		super(new StackPane());

		this.backgroundLayer.setMinSize(800, 800);
		final Image playerImg = new Image("icon.png");
		final ImageView playerImgView = new ImageView();
		playerImgView.setImage(playerImg);
		playerImgView.setFitHeight(50);
		playerImgView.setFitWidth(60);

		final HBox topLayout = new HBox();
		topLayout.getStylesheets().add("style.css");
		topLayout.setPadding(new Insets(20));
		topLayout.setSpacing(10);

		final Label hp = new Label("HP: ");
		hp.setTextFill(Color.GREEN);
		final Label shields = new Label("Shields: ");
		shields.setTextFill(Color.BLUE);

		final HBox hpBox = new HBox();
		hpBox.setOpacity(0.9);
		hpBox.getChildren().addAll(hp, shields);
		hpBox.setAlignment(Pos.CENTER);
		hpBox.setId("hpBox");
		hpBox.setMinWidth(180);
		hpBox.setMaxSize(180, 50);
		hpBox.setMinHeight(50);
		hpBox.setPadding(new Insets(5));

		topLayout.getChildren().addAll(playerImgView, hpBox);
		topLayout.setId("gameScreen");

		this.root.getChildren().addAll(this.backgroundLayer, topLayout);
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

	public void drawOnScreen(final List<Pair<String, Location>> listEntities) {
		this.drawEntities.draw(this.backgroundLayer, listEntities, HEIGHT_GAME);
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