package spaceimpact.model.entities;

import spaceimpact.model.Location;

/** 
 * Debris Class
 * <br>
 * Spawned when an entity dies, has a limited amount of life defined by a countdown
 * @author Davide
 */
public class Debris implements Entity {

	private final EntityType ID = EntityType.Debris; //entity type identifier
	private Location location; //current position
	private int countdown; //current countdown to death
	private boolean removable; //determine if 

	public Debris(final Location startinglocation, final int countdown) {
		this.removable = false;
		this.location = new Location(startinglocation);
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
		}
	}
	
	@Override
	public String toString() {
		return "[ " + this.ID + " -> X: " + this.location.getX() + 
				" | Y: " + this.location.getY() + " ]";		
	}
}
