package spaceimpact.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import spaceimpact.utilities.ImageLoader;

/**
 * A simple dialog box where the user can choose an option.
 *
 */
public final class ConfirmBox {

    private static final double MIN_WIDTH = 350;
    private static boolean answer;

    private ConfirmBox() {
    };

    /**
     * It displays the dialog box.
     * 
     * @param title
     *            The title of the dialog box.
     * @param message
     *            The message of the dialog box.
     * @return The choice of the user where true equals yes and false equals no.
     */
    public static boolean display(final String title, final String message) {
        final Stage window = new Stage();
        window.getIcons().add(ImageLoader.getLoader().getImageFromPath("Icons/alert.png"));
        window.setResizable(false);
        window.centerOnScreen();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(MIN_WIDTH);
        
        final Label label = new Label();
        label.setText(message);

        final Button yesButton = new Button("Yes");
        final Button noButton = new Button("No");

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

        layout.setMinWidth(MIN_WIDTH);
        layout.getChildren().addAll(label, layoutButton);
        layout.setAlignment(Pos.CENTER);
        final Scene scene = new Scene(layout);
        scene.getStylesheets().add("style.css");
        window.setScene(scene);
        window.showAndWait();

        return answer;
    }

}