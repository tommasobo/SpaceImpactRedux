package spaceimpact.view;

import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.geometry.*;

public class InfoBox {

    public static void display(String title) {
        final Stage window = new Stage();
        window.getIcons().add(new Image("file:res/info.png"));
        window.setResizable(false);
        window.centerOnScreen();
        window.setTitle(title);
        window.setMinWidth(500);
        window.setMinHeight(650);
        
        final Logo logo = new Logo(220,55);
        
        final Text label = new Text();
        label.setText("Developed and Created by");
        label.setId("titleInfo");
        final Text instructionTitle = new Text();
        instructionTitle.setText("Instructions");
        instructionTitle.setId("titleInfo");
        final Text otherCredits = new Text();
        otherCredits.setText("Credits to");
        otherCredits.setId("titleInfo");
        
        final VBox listDeveloper = new VBox(10);
        final Label view = new Label("Tommaso Bonato (View)");
        view.setId("whiteText");
        final Label model = new Label("Davide Giacomini (Model)");
        model.setId("whiteText");
        final Label controller = new Label("Nicola Cielo (Controller)");
        controller.setId("whiteText");
        
        final Label instructions = new Label();
        instructions.setText("W - Move up\nA - Move left\nS - Move down\nD - Move right\nSPACE - Fire");
        instructions.setId("whiteTextInfo");
        
        final Label creditsTo = new Label();
        creditsTo.setText("Immagini Rilasciate con licenza CC-BY 3.0 da C-TOY \n(http://c-toy.blogspot.pt/), MillionthVector \n(http://millionthvector.blogspot.de), Bonsaiheldin\n(http://bonsaiheld.org), Qubodup and \nMartin Jelinek (jelinek.cz@gmail.com) | www.nyrthos.com.");
        creditsTo.setId("whiteTextInfo");
        
        listDeveloper.getStylesheets().add("style.css");
        listDeveloper.setAlignment(Pos.CENTER);
        listDeveloper.setId("whiteTextInfo");
        listDeveloper.setPadding(new Insets(10));
        listDeveloper.getChildren().addAll(instructionTitle, instructions, label, view, model, controller, otherCredits, creditsTo);

        final VBox layout = new VBox(10);
        final StackPane descLayout = new StackPane();
        
        layout.getChildren().addAll(logo.getLogo(), listDeveloper);
        layout.setSpacing(10);
        layout.setPadding(new Insets(8));
        layout.setAlignment(Pos.TOP_CENTER);

        descLayout.getChildren().addAll(layout);
        descLayout.setId("infoPane");
        final Scene scene = new Scene(descLayout);
        scene.getStylesheets().add("style.css");
        window.setScene(scene);
        window.showAndWait();
    }

}
