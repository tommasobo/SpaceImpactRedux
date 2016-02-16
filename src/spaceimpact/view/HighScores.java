package spaceimpact.view;

import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.geometry.*;

public class HighScores extends Scene{
    
    private static final int WIDTH_LOGO_HIGHSCORS = 450;
    private static final int HEIGTH_LOGO_HIGHSCORES = 150;
    
    private static HighScores mainScene = new HighScores();
    private static Stage mainStage;

    public HighScores() {
        super(new StackPane());
        /*mainWindow.getIcons().add(new Image("file:res/info.png"));
        mainWindow.setTitle("High Scores");*/

        
        try {
            Button back;
            final Logo logo = new Logo(WIDTH_LOGO_HIGHSCORS,HEIGTH_LOGO_HIGHSCORES);
            
            final Text label = new Text();
            label.setText("High Scores");
            label.setId("highScores");
            
            final VBox listHighScores = new VBox(10);
            for (int i = 0; i < 5; i++) { // Modificare con controller
                listHighScores.getChildren().add(new Label(i+1 +"° - LOLOLOL"));
            }
            
            listHighScores.getStylesheets().add("style.css");
            listHighScores.setAlignment(Pos.CENTER);
            listHighScores.setId("whiteText");
            listHighScores.setPadding(new Insets(10));

            final VBox layout = new VBox(10);
            back = new Button("Go back");
            
            back.setId("dark-blue");
            final StackPane bottomLayout = new StackPane();
            bottomLayout.setPadding(new Insets(0, 0, 100, 0));
            bottomLayout.getChildren().add(back);
            bottomLayout.setAlignment(Pos.BOTTOM_CENTER);
            final StackPane descLayout = new StackPane();
            
            
            layout.getChildren().addAll(logo.getLogo(), label, listHighScores);
            layout.setSpacing(10);
            layout.setPadding(new Insets(8));
            layout.setAlignment(Pos.TOP_CENTER);

            descLayout.getChildren().addAll(layout, bottomLayout);
            descLayout.setId("infoPane");
            this.setRoot(descLayout);
            this.getStylesheets().add("style.css");
            back.setOnAction(e -> mainStage.setScene(MainMenu.get(mainStage)));
        } catch (Exception e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
       
    }

    public static HighScores get(Stage mainWindow){
        try {
            mainStage = mainWindow;
           
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return mainScene;
    }

}
