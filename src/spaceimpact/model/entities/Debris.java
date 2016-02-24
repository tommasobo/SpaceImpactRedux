package spaceimpact.model.entities;

import spaceimpact.model.Direction;
import spaceimpact.model.Location;

/** 
 * Debris
 * <br>
 * There are four different debris types:<br>
 * <b>Explosion</b> that it's spawned when an entity dies, has a 
 * limited amount of life defined by a countdown.<br>
 * <b>Hit</b> that it's spawned when an entity gets damage, has a 
 * limited amount of life defined by a countdown.<br>
 * <b>Sparkle</b> that it's spawned when the Spaceship get a PowerUp, 
 * has a limited amount of life defined by a countdown.<br>
 * <b>Asteroid</b> that it's spawned randomly and moves across 
 * the gamescreen. He dies as soon as his x location is less than -0.30.<br>
 * @author Davide
 */
public class Debris implements Entity {
	
	/**
	 * The type of the debris
	 * @author Davide
	 */
	public enum DebrisType {
		Explosion,
		Hit,
		Sparkle,
		Asteroid;
	}

	private final EntityType ID = EntityType.Debris; //entity type identifier
	private final DebrisType type; //Debris Type
	private Direction direction = Direction.W; //direction of the entity
	private Location location; //current position
	private double velocity; //current debris velocity
	private int countdown; //current countdown to death
	private boolean removable; //determine if can be removed from gamescreen

	/**
	 * Constructor For Asteroid
	 * @param location Starting Location of the Asteroid
	 * @param velocity Velocity of the Asteroid
	 */
	public Debris(final Location location, final double velocity) {
		this.type = DebrisType.Asteroid;
		this.removable = false;
		this.location = new Location(location);
		this.velocity = velocity;
		this.countdown = 0;
	}
	
	/**
	 * Constructor for Static Debris
	 * @param type DebrisType of the Debris
	 * @param location Starting Location of the Debris
	 * @param countdown Lifetime of the Debris
	 */
	public Debris(final DebrisType type, final Location location, final int countdown) {
		this.type = type;
		this.removable = false;
		this.location = new Location(location);
		this.velocity = 0;
		this.countdown = countdown;
	}
	
	/**
	 * Getter for DebrisType
	 * @return DebrisType As the type of the debris
	 */
	public DebrisType getType() {
		return this.type;
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
		if (!this.type.equals(DebrisType.Asteroid)) {
			this.countdown--;
			if (this.countdown <= 0) {
				this.removable = true;
			} 
		} else {
			if (location.getX() < -0.30d) {
				this.removable = true;
			} else if (this.velocity > 0) {		
				this.direction.moveLocation(this.location, this.velocity);
			}	
		}
	}
	
	@Override
	public String toString() {
		return "[ " + this.ID + " -> X: " + this.location.getX() + 
				" | Y: " + this.location.getY() + " ]";		
	}
}
