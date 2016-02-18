package spaceimpact.model;

import java.util.List;
import spaceimpact.model.entities.Entity;
import spaceimpact.utilities.Input;

/**Interface for a generic model
 * <br>
 * The "main" method is inside the Controller class.
 */
public interface ModelInterface {
	
	/** Return player spaceship life
	 * 
	 * @return integer as current player's life. 
	 */
	int getPlayerLife();
	
	/** Return player spaceship shield
	 * 
	 * @return integer as current player's shield. 
	 */
	int getPlayerShield();
		
	/** Return player location
	 * 
	 * @return Location as current player's location. 
	 */
	Location getPlayerLocation();
	
	/** Return the full list of entities except for player
	 * 
	 * @return List<model.Entity> entire collection of entities currently active in the model 
	 */
	List<Entity> getEntitiesToDraw();
	
	/** Inform model about user inputs
	 * 
	 * @param List<utilities.Input> get list of user input to process
	 */
	void informInputs(List<Input> userinputs);
	
	/** Force complete update of the model
	 * <br>
	 * All active entities are moved by one tick.
	 */
	void updateAll();
}
