package spaceimpact.view;

import java.util.List;

import spaceimpact.model.Location;
import spaceimpact.utilities.Input;
import spaceimpact.utilities.Pair;

/**
 * Interface for a generic View. 
 */
public interface ViewInterface {
    
    /**
     * It starts the JavaFX Application
     */
    void startView();

    /**
     * It prints on screen all the entities in their correct positions
     * 
     * @param listEntities
     *          a list of the Entities that will be printed on screen
     */
    void draw(List<Pair<Pair<String, Double>, Location>> listEntities);
        
    /**
     * It updates the view with the most recent information about the player
     * 
     * @param hp
     *          current hp (Health Points) of the player
     * @param shields
     *          current shields of the player
     * @param score
     *          current score of the player
     */
    void updateInfo(final int hp, final int shields, final int score);
    
    /**
     * It returns a list with the inputs detected during a game
     * 
     * @return
     *          a list of Input 
     */
    List<Input> getInput();

    /** 
     * It displays a label with the most recent completed level
     * 
     * @param nLevel
     *          the number of the level just completed
     */
    void showText(int nLevel);
    
    /** 
     * It displays a label with the current power up at the top of the screen
     * 
     * @param powerUp
     *          a String with the name of the power-up.
     */
    void showText(String powerUp);
}