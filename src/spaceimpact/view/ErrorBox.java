package spaceimpact.view;

import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.geometry.*;

public class ErrorBox {

    //Create variable
    private static final String SEP = System.getProperty("file.separator");

    public static void display(String title, String message) {
        final Stage window = new Stage();
        window.getIcons().add(new Image("file:res" + SEP + "icons" + SEP + "alert.png"));
        window.setResizable(false);
        window.centerOnScreen();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(350);
        Label label = new Label();
        label.setText(message);

        //Create two buttons
        Button yesButton = new Button("Continue");
        
        //Clicking will set answer and close window
        yesButton.setOnAction(e -> {
            window.close();
        });

        final VBox layout = new VBox(10);
        final HBox layoutButton = new HBox(50);
        
        layoutButton.getChildren().addAll(yesButton);
        layoutButton.setSpacing(10);
        layoutButton.setPadding(new Insets(8));
        layoutButton.setAlignment(Pos.CENTER);

        //Add buttons
        layout.getChildren().addAll(label, layoutButton);
        layout.setAlignment(Pos.CENTER);
        final Scene scene = new Scene(layout);
        scene.getStylesheets().add("style.css");
        window.setScene(scene);
        window.showAndWait();
    }

}