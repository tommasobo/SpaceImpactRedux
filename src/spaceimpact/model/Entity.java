package spaceimpact.model;

/** General Entity Interface
 * 
 * @author Davide
 *
 */
public interface Entity {
	
	/** Getter method for Location
	 * @return Location
	 */
	Location getLocation();
	
	/** Setter method for entity location
	 * @param location Current entity location
	 */
	void setLocation(final Location location);
			
	/** Verify if there is a collision between this entity and the otherEntity.
	 * @param otherEntity	The entity which need to be controlled.
	 * @return true if collide, otherwise false.
	 */
	Boolean collideWith(final Entity otherEntity);
		
	/** Move the entity for 1 tick
	 */
	void Update();
	
}
