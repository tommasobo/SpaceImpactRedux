package spaceimpact.view;

import javafx.stage.*;
import spaceimpact.utilities.ImageLoader;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.geometry.*;

public class GenericBox {

    private static final double MIN_WIDTH = 350;
    
    public static void display(final BoxType boxType, final String title, final String message, final String buttonMessage) {
        final Stage window = new Stage();
        if (boxType == BoxType.ERROR) {
            window.getIcons().add(ImageLoader.getLoader().getImageFromPath("Icons/error.png"));
        } else {
            window.getIcons().add(ImageLoader.getLoader().getImageFromPath("Icons/success.png"));
        }
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
        layout.setMinWidth(350);
        final HBox layoutButton = new HBox(50);
        
        layoutButton.getChildren().addAll(yesButton);
        layoutButton.setSpacing(10);
        layoutButton.setPadding(new Insets(8));
        layoutButton.setAlignment(Pos.CENTER);

        //Add buttons
        layout.setMinWidth(MIN_WIDTH);
        layout.getChildren().addAll(label, layoutButton);
        layout.setAlignment(Pos.CENTER);
        final Scene scene = new Scene(layout);
        scene.getStylesheets().add("style.css");
        window.setScene(scene);
        window.showAndWait();
    }

}