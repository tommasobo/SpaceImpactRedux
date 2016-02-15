package spaceimpact.model;

import java.awt.Rectangle;

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
			
	/** Verify if there is a collision between the current .
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
			var2 < (otherEntity.getLocation().getArea().getHeight() + this.getLocation().getArea().getHeight()) / 2 ) {
			return true;
		}
		
		return false;
	}
		
	/** Move the entity for 1 tick
	 */
	void update();
	
}
