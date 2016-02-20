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
	private boolean isalive;

	public Debris(final Location startinglocation, final int countdown) {
		this.isalive = true;
		this.location = startinglocation;
		this.countdown = countdown;
	}
	
	/**
	 * Indicates the current life state of the debris
	 * @return boolean that indicates if the debris needs to be deleted or not
	 */
	public boolean isAlive() {
		return this.isalive;
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
			this.isalive = false;
		}
	}
	
	@Override
	public String toString() {
		return "[ " + this.ID + " -> X: " + this.location.getX() + 
				" | Y: " + this.location.getY() + " ]";		
	}
}
