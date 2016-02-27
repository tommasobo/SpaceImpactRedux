package spaceimpact.model;

import java.util.List;
import java.util.Optional;

import spaceimpact.model.entities.Entity;

/**
 * Interface for a generic model<br>
 * Defines the main methods of the class model, that's effectively 
 * the game itself with all active entities and all statistics.
 */
public interface ModelInterface {
	
	/** 
	 * Return player spaceship life.
	 * <br>
	 * @return integer as current player's life. 
	 */
	int getPlayerLife();
	
	/** 
	 * Return player spaceship shield.
	 * <br>
	 * @return integer as current player's shield. 
	 */
	int getPlayerShield();
		
	/** 
	 * Return player location.
	 * <br>
	 * @return Location as current player's location. 
	 */
	Location getPlayerLocation();
	
	/** Getter method to get player score.
	 * @return amount of remaining shield as integer
	 */
	int getScores();
	
	/**
	 * Get Game Status and reset it.
	 * @return GameStatus Current game status
	 */
	GameStatus getGameStatus();
	
	/**
	 * Return latest PowerUp acquired by the player.
	 * @return string As Optional of String if the player has acquired a powerup and it's not been already signaled
	 */
	Optional<String> getLatestPowerUp();
	
	/** 
	 * Return the full list of entities except for player.<br>
	 * @return List of model.Entity entire collection of entities currently active in the model 
	 */
	List<Entity> getEntitiesToDraw();
	
	/** 
	 * Inform model about user inputs.<br>
	 * @param direction Optional of Direction of the movement
	 * @param shoot True if user shoot
	 * @throws IllegalStateException if the player inside the model is null
	 */
	void informInputs(Optional<Direction> direction, boolean shoot) throws IllegalStateException;
	
	/** 
	 * Force complete update of the model
	 * <br>
	 * All active entities are moved by one tick.
	 * @throws IllegalStateException if the player and/or the level of the model are null.
	 */
	void updateAll() throws IllegalStateException;
}
