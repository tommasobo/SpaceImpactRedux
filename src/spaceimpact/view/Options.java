package spaceimpact.view;

import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.geometry.*;

public class Options extends Scene{
    
    private static final int WIDTH_LOGO_HIGHSCORS = 450;
    private static final int HEIGTH_LOGO_HIGHSCORES = 150;
    
    private static Options mainScene = new Options();
    private static Stage mainStage;

    public Options() {
        super(new StackPane());

        final Logo logo = new Logo(WIDTH_LOGO_HIGHSCORS,HEIGTH_LOGO_HIGHSCORES);
            
        final Text label = new Text();
        label.setText("Options");
        label.setId("options");
            
        final VBox layout = new VBox(10);
        final Button back = new Button("Go back");
            
        back.setId("dark-blue");
        final StackPane bottomLayout = new StackPane();
        bottomLayout.setPadding(new Insets(0, 0, 100, 0));
        bottomLayout.getChildren().add(back);
        bottomLayout.setAlignment(Pos.BOTTOM_CENTER);
        final StackPane descLayout = new StackPane();
            
            
        layout.getChildren().addAll(logo.getLogo(), label);
        layout.setSpacing(10);
        layout.setPadding(new Insets(8));
        layout.setAlignment(Pos.TOP_CENTER);

        descLayout.getChildren().addAll(layout, bottomLayout);
        descLayout.setId("infoPane");
        this.setRoot(descLayout);
        this.getStylesheets().add("style.css");
        back.setOnAction(e -> mainStage.setScene(MainMenu.get(mainStage)));       
    }

    public static Options get(Stage mainWindow){
        mainStage = mainWindow;
        mainStage.setTitle("Space Impact Redux - Options");
        return mainScene;
    }

}