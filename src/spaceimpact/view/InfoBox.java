package spaceimpact.view;

import javafx.stage.*;
import spaceimpact.utilities.ImageLoader;
import javafx.scene.*;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.geometry.*;

public class InfoBox {

    private static final double INFO_WIDTH = 520;
    private static final double INFO_HEIGHT = 650;
    private static final String SEP = System.getProperty("file.separator");

    public static void display() {
        final Stage window = new Stage();
        window.getIcons().add(ImageLoader.getLoader().getImageFromPath("Icons/info.png"));
        window.setResizable(false);
        window.centerOnScreen();
        window.setTitle("Info Box");
        window.setMinWidth(INFO_WIDTH);
        window.setMinHeight(INFO_HEIGHT);
        
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
        
        final VBox listInfo = new VBox(10);
        final Label view = new Label("Tommaso Bonato (View)");
        view.setId("whiteText");
        final Label model = new Label("Davide Giacomini (Model)");
        model.setId("whiteText");
        final Label controller = new Label("Nicola Cielo (Controller)");
        controller.setId("whiteText");
        
        final Label instructions = new Label();
        instructions.setTextAlignment(TextAlignment.CENTER);;
        instructions.setText("W - Move up\nA - Move left\nS - Move down\nD - Move right\nSPACE - Fire\nBACK SPACE - Go back to the menu\nP - Pause\nESC - Exits");
        instructions.setId("whiteTextInfo");
        
        final Label creditsTo = new Label();
        creditsTo.setText("Images released with a CC-BY 3.0 license from C-TOY \n(http://c-toy.blogspot.pt/), MillionthVector \n(http://millionthvector.blogspot.de), Bonsaiheldin\n(http://bonsaiheld.org), Qubodup and \nMartin Jelinek (jelinek.cz@gmail.com) | www.nyrthos.com.");
        creditsTo.setId("whiteTextInfo");
        
        listInfo.getStylesheets().add("style.css");
        listInfo.setAlignment(Pos.CENTER);
        listInfo.setId("whiteTextInfo");
        listInfo.setPadding(new Insets(10));
        listInfo.getChildren().addAll(instructionTitle, instructions, label, view, model, controller, otherCredits, creditsTo);

        final VBox layout = new VBox(10);
        final StackPane mainLayout = new StackPane();
        
        layout.getChildren().addAll(logo.getLogo(), listInfo);
        layout.setSpacing(10);
        layout.setPadding(new Insets(8));
        layout.setAlignment(Pos.TOP_CENTER);

        mainLayout.getChildren().addAll(layout);
        mainLayout.setId("infoPane");
        final Scene scene = new Scene(mainLayout);
        scene.getStylesheets().add("style.css");
        window.setScene(scene);
        window.showAndWait();
    }

}
