package spaceimpact.view;

import javafx.stage.*;
import spaceimpact.utilities.ImageLoader;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.geometry.*;

public class ConfirmBox {

    //Create variable
    private static final double MIN_WIDTH = 350;
    private static boolean answer;

    public static boolean display(final String title, final String message) {
        final Stage window = new Stage();
        window.getIcons().add(ImageLoader.getLoader().getImageFromPath("Icons/alert.png"));
        window.setResizable(false);
        window.centerOnScreen();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(MIN_WIDTH);
        Label label = new Label();
        label.setText(message);

        //Create two buttons
        Button yesButton = new Button("Yes");
        Button noButton = new Button("No");

        //Clicking will set answer and close window
        yesButton.setOnAction(e -> {
            answer = true;
            window.close();
        });
        noButton.setOnAction(e -> {
            answer = false;
            window.close();
        });

        final VBox layout = new VBox(10);
        final HBox layoutButton = new HBox(50);
        
        layoutButton.getChildren().addAll(yesButton, noButton);
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

        //Make sure to return answer
        return answer;
    }

}