package spaceimpact.view;

import javafx.scene.control.Label;

public class PlayerInfo {

    void update(Label hp, Label shields, Label score, int hp2, int shields2, int score2) {
        hp.setText("HP: " + Integer.toString(hp2));
        shields.setText("SHIELDS: " + Integer.toString(shields2));
        score.setText("SCORE: " + Integer.toString(score2));
    }

}
