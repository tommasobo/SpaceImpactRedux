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
 * A simple and generic dialog box to show a message to the user.
 *
 */
public final class GenericBox {

    private static final double MIN_WIDTH = 350;

    private GenericBox() {
    };

    /**
     * It displays the dialog box.
     * 
     * @param boxType
     *            The type of this dialog box. This will change the icon of the
     *            box.
     * @param title
     *            The title of this dialog box.
     * @param message
     *            The message of the dialog box
     * @param buttonMessage
     *            The text of the button
     */
    static void display(final BoxType boxType, final String title, final String message, final String buttonMessage) {
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
        window.setMinWidth(MIN_WIDTH);
        final Label label = new Label();
        label.setText(message);

        final Button yesButton = new Button("Continue");
        yesButton.setOnAction(e -> {
            window.close();
        });

        final VBox layout = new VBox(10);
        layout.setMinWidth(MIN_WIDTH);
        final HBox layoutButton = new HBox(50);

        layoutButton.getChildren().addAll(yesButton);
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
    }

}