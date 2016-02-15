package spaceimpact.view;

import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.geometry.*;

public class InfoBox {

    public static void display(String title) {
        Stage window = new Stage();
        window.setResizable(false);
        window.centerOnScreen();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(400);
        window.setMinHeight(600);
        Pane root = new Pane();
        
        Text label = new Text();
        label.setText("Developed and Created by");
        label.setId("titleInfo");
        Text developers = new Text();
        developers.setText("Tommaso Bonato, Nicola Cielo, Davide Giacomini");

        VBox layout = new VBox(10);
        StackPane descLayout = new StackPane();
        
        layout.getChildren().addAll(label, developers);
        layout.setSpacing(10);
        layout.setPadding(new Insets(8));
        layout.setAlignment(Pos.TOP_CENTER);

        //Add buttons
        root.getChildren().addAll(layout,descLayout);
        Scene scene = new Scene(root);
        scene.getStylesheets().add("spaceimpact/view/style.css");
        window.setScene(scene);
        window.showAndWait();
    }

}
