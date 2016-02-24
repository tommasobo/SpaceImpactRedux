package spaceimpact.model.entities;

import spaceimpact.model.Location;

/** 
 * General Entity Interface
 * <br>
 * Define default methods for all entities inside the model.
 * @author Davide
 */
public interface Entity {
	
	/** 
	 * Getter method for Location
	 * @return Location
	 */
	Location getLocation();
		
	/** 
	 * Setter method for entity location
	 * @param location new entity location
	 */
	void setLocation(final Location location);
				
	/** 
	 * Verify if there is a collision between the current.
	 * @param otherEntity	First entity.
	 * @return true if collide, otherwise false.
	 */
	default Boolean collideWith(Entity otherEntity) {
		
		if (otherEntity.getLocation().equals(this.getLocation())) {
			return true;
		}
						
		double var1 = Math.abs(otherEntity.getLocation().getX() - this.getLocation().getX());
		double var2 = Math.abs(otherEntity.getLocation().getY() - this.getLocation().getY());
		
		if (var1 < (otherEntity.getLocation().getArea().getWidth() + this.getLocation().getArea().getWidth()) / 2 && 
			var2 < (otherEntity.getLocation().getArea().getHeight() + this.getLocation().getArea().getHeight()) / 2) {
			return true;
		}
		
		return false;
	}
		
	/** 
	 * Update the entity entirely and execute specific methods
	 */
	void update();
	
	/** 
	 * Get ID of the current entity
	 * @return entitytype the identifier of the type of this entity.
	 */
	EntityType getID();
	
	/** 
	 * Determine whether the entity must be removed from the model
	 * @return boolean True if the entity need to be removed, false if it's still active
	 */
	boolean toRemove();
}
