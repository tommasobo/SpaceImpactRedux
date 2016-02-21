package spaceimpact.model;

import java.util.List;
import spaceimpact.model.entities.Entity;
import spaceimpact.utilities.Input;

/**
 * Interface for a generic model
 * <br>
 * The "main" method is inside the Controller class.
 */
public interface ModelInterface {
	
	/** 
	 * Return player spaceship life
	 * <br>
	 * @return integer as current player's life. 
	 */
	int getPlayerLife();
	
	/** 
	 * Return player spaceship shield
	 * <br>
	 * @return integer as current player's shield. 
	 */
	int getPlayerShield();
		
	/** 
	 * Return player location
	 * <br>
	 * @return Location as current player's location. 
	 */
	Location getPlayerLocation();
	
	/** Getter method to get player score
	 * @return amount of remaining shield as integer
	 */
	int getScores();
	
	/** 
	 * Return the full list of entities except for player
	 * <br>
	 * @return List of model.Entity entire collection of entities currently active in the model 
	 */
	List<Entity> getEntitiesToDraw();
	
	/** 
	 * Inform model about user inputs
	 * <br>
	 * @param userinputs List of utilities.Input get list of user input to process
	 */
	void informInputs(List<Input> userinputs);
	
	/** 
	 * Force complete update of the model
	 * <br>
	 * All active entities are moved by one tick.
	 */
	void updateAll();
}
