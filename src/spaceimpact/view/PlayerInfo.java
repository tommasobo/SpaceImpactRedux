package spaceimpact.view;

import javafx.scene.control.Label;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;

/**
 * Class responsible for handling the information about the current status of
 * the player.
 *
 */
public class PlayerInfo {

    private static final int CRITICAL_HP = 20;
    private static final int HALF_HP = 50;
    private static final int MIN_VALUE = 100;
    private static final int MID_VALUE = 700;
    private final DropShadow dropShadow = new DropShadow();

    /**
     * Constructor of the class. It defines the effect for texts to be used
     * later.
     */
    public PlayerInfo() {
        this.dropShadow.setColor(Color.LAWNGREEN);
        this.dropShadow.setRadius(35);
        this.dropShadow.setSpread(0.30);
        this.dropShadow.setBlurType(BlurType.GAUSSIAN);
    }

    /**
     * It updates the view with the current information about the player.
     * 
     * @param hp
     *            The label storing the hp value
     * @param shields
     *            The label storing the shields level
     * @param score
     *            The label storing the score.
     * @param hpValue
     *            The current hp of the player
     * @param shieldsValue
     *            The current level of the shield
     * @param scoreValue
     *            The current score.
     */
    void update(final Label hp, final Label shields, final Label score, final int hpValue, final int shieldsValue,
            final int scoreValue) {

        shields.setEffect(this.dropShadow);
        hp.setEffect(this.dropShadow);
        score.setEffect(this.dropShadow);

        if (hpValue <= CRITICAL_HP) {
            this.dropShadow.setColor(Color.RED);
            hp.setTextFill(Color.RED);
        } else if (hpValue <= HALF_HP) {
            this.dropShadow.setColor(Color.ORANGE);
            hp.setTextFill(Color.ORANGE);
        } else {
            this.dropShadow.setColor(Color.LAWNGREEN);
            hp.setTextFill(Color.GREEN);
        }

        if (shieldsValue <= CRITICAL_HP) {
            shields.setTextFill(Color.RED);
        } else if (shieldsValue <= HALF_HP) {
            shields.setTextFill(Color.ORANGE);
        } else {
            shields.setTextFill(Color.BLUE);
        }

        if (scoreValue >= MIN_VALUE && scoreValue <= MID_VALUE) {
            score.setTextFill(Color.LIGHTYELLOW);
        } else if (scoreValue >= MID_VALUE) {
            score.setTextFill(Color.YELLOW);
        } else {
            score.setTextFill(Color.WHITE);
        }

        hp.setText("HP: " + Integer.toString(hpValue));
        shields.setText("SHIELDS: " + Integer.toString(shieldsValue));
        score.setText("SCORE: " + Integer.toString(scoreValue));
    }

}
