package spaceimpact.model.entities;

import spaceimpact.model.Direction;
import spaceimpact.model.Location;

public class PowerUp implements Entity {

	private final EntityType ID = EntityType.PowerUp; //entity type identifier
	private Direction direction = Direction.W; //direction of the entity
	private Location location; //current position
	private double velocity; //current debris velocity
	private boolean removable; //determine if can be removed from gamescreen

	public PowerUp(final Location startinglocation, final double velocity, final int countdown) {
		this.removable = false;
		this.location = new Location(startinglocation);
		this.velocity = velocity;
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
		return this.ID;
	}
	
	@Override
	public void update() {
		if (location.getX() < 0) {
			this.removable = true;
		} else {
			this.direction.moveLocation(this.location, this.velocity);		
		}
	}
	
	@Override
	public String toString() {
		return "[ " + this.ID + " -> X: " + this.location.getX() + 
				" | Y: " + this.location.getY() + " ]";		
	}

}
