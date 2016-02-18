package spaceimpact.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeLineCap;
import javafx.stage.Stage;

public class GameScreen extends Scene {
    
    private static GameScreen mainScene = new GameScreen();
    private static Stage mainStage;

    public GameScreen() {
        super(new StackPane());
        
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
        hpBox.getChildren().addAll(hp,shields);
        hpBox.setAlignment(Pos.CENTER);
        hpBox.setId("hpBox");
        hpBox.setMinWidth(220);
        hpBox.setMaxSize(220, 60);
        hpBox.setMinHeight(60);
        hpBox.setPadding(new Insets(5));
        
        topLayout.getChildren().addAll(playerImgView, hpBox);
        topLayout.setId("gameScreen");
        //this.setOnKeyPressed(InputHandler);
        
        this.setRoot(topLayout);
    }
    
    public static GameScreen get(Stage mainWindow){
        mainStage = mainWindow;
        mainStage.setTitle("Space Impact Redux");
        return mainScene;
    }

}
