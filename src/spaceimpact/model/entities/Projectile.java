package spaceimpact.model.entities;

import spaceimpact.model.Direction;
import spaceimpact.model.Location;

/** 
 * Projectile
 * <br>
 * Spawned when an entity shoot, damage and velocity are defined by the weapon that shoot it.
 * @author Davide
 */
public class Projectile implements Entity {

	private final EntityType ID = EntityType.Projectile;
	private final EntityType parentID; //EntityType that has shooted it
	private final int damage;
	private Location location;
	private final Direction direction;
	private final double velocity;
	
	public Projectile(final EntityType parentID, final Location startinglocation, final Direction direction, final double velocity, final int damage) {
		this.parentID = parentID;
		this.damage = damage;
		this.location = startinglocation;
		this.velocity = velocity;
		this.direction = direction;
	}
	
	/** Get the damage that the projectile inflicts
	 * @return damage amount of damage as integer
	 */
	public int getDamage() {
		return this.damage;
	}
			
	/**
	 * Get the entity type that has shooted this projectile
	 * @return EntityType that has shooted
	 */
	public EntityType getParentID() {
		return parentID;
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
	public void update() {
		this.direction.moveLocation(this.location, this.velocity);
	}
	
	@Override
	public EntityType getID() {
		return ID;
	}
	
	@Override
	public String toString() {
		return "[ " + this.ID + " -> X: " + this.location.getX() + 
				" | Y: " + this.location.getY() + " | Direction: " + 
				this.direction + " | Velocity: " + this.velocity + 
				" | Damage: " + this.damage + " | WhoShooted: " + 
				this.parentID + " ]";		
	}
}
