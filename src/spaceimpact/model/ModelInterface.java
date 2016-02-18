package spaceimpact.model;

import java.util.List;

import spaceimpact.utilities.Input;

/**Interface for a generic model
 * <br>
 * The "main" method is inside the Controller class.
 */
public interface ModelInterface {

	/** Return the full list of entities
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
