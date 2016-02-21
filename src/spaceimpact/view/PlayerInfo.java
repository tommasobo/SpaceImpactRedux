package spaceimpact.view;

import javafx.scene.control.Label;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;

public class PlayerInfo {
    
    private final static int CRITICAL_HP = 20;
    private final static int HALF_HP = 50;
    private final static int MIN_VALUE = 100;
    private final static int MID_VALUE = 700;
    private final DropShadow dropShadow = new DropShadow();
    
    public PlayerInfo() {
        this.dropShadow.setColor(Color.LAWNGREEN);
        this.dropShadow.setRadius(30);
        this.dropShadow.setSpread(0.28);
        this.dropShadow.setBlurType(BlurType.GAUSSIAN);
    }

    void update(Label hp, Label shields, Label score, int hpValue, int shieldsValue, int scoreValue) {       
        shields.setEffect(this.dropShadow);
        hp.setEffect(this.dropShadow);
        score.setEffect(this.dropShadow);
        
        if (hpValue < CRITICAL_HP) {
            this.dropShadow.setColor(Color.RED);
            hp.setTextFill(Color.RED);
        } else if (hpValue < HALF_HP) {
            this.dropShadow.setColor(Color.ORANGE);
            hp.setTextFill(Color.ORANGE);
        }
        
        if (scoreValue > MIN_VALUE && scoreValue < MID_VALUE) {
            score.setTextFill(Color.LIGHTYELLOW);
        } else if (scoreValue > MID_VALUE) {
            score.setTextFill(Color.YELLOW);
        } else {
            score.setTextFill(Color.WHITE);
        }
            
        hp.setText("HP: " + Integer.toString(hpValue));
        shields.setText("SHIELDS: " + Integer.toString(shieldsValue));
        score.setText("SCORE: " + Integer.toString(scoreValue));
        
    }

}
