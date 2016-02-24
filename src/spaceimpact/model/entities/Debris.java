package spaceimpact.model.entities;

import spaceimpact.model.Direction;
import spaceimpact.model.Location;

/** 
 * Debris Class
 * <br>
 * Spawned when an entity dies, has a limited amount of life defined by a countdown
 * @author Davide
 */
public class Debris implements Entity {

	private final EntityType ID = EntityType.Debris; //entity type identifier
	private Direction direction = Direction.W; //direction of the entity
	private Location location; //current position
	private int countdown; //current countdown to death
	private double velocity; //current debris velocity
	private boolean removable; //determine if can be removed from gamescreen

	public Debris(final Location startinglocation, final double velocity, final int countdown) {
		this.removable = false;
		this.location = new Location(startinglocation);
		this.velocity = velocity;
		this.countdown = countdown;
	}
	
	@Override
	public boolean toRemove() {
		return this.removable;
	}
		
	@Override
	public Location getLocation() {
		return this.location;
	}
	
	@Override
	public void setLocation(Location location) {
		this.location = location;
	}
			
	@Override
	public EntityType getID() {
		return ID;
	}
	
	@Override
	public void update() {
		this.countdown--;
		if (this.countdown <= 0) {
			this.removable = true;
		} else if (this.velocity > 0) {
			this.direction.moveLocation(this.location, this.velocity);
		}
	}
	
	@Override
	public String toString() {
		return "[ " + this.ID + " -> X: " + this.location.getX() + 
				" | Y: " + this.location.getY() + " ]";		
	}
}
