package spaceimpact.model.entities;

import spaceimpact.model.Direction;
import spaceimpact.model.Location;

/**
 * Projectile<br>
 * Spawned when an entity shoot, damage and velocity are defined by the 
 * weapon that shoot it.<br>
 * If collide with other living entities the projectile is destroyed and 
 * the damage is applied to the other entity.
 */
public class Projectile implements Entity {

	private final EntityType ID = EntityType.Projectile;
	private final EntityType parentID;
	private final Direction direction;
	private final double velocity;
	private final int damage;
	private Location location;
	private boolean removable;

	/**
	 * Projectile Constructor
	 * @param parentID As the EntityType of the shooter
	 * @param location As the starting location of the projectile
	 * @param direction As the direction of the projectile
	 * @param velocity As the velocity of the projectile
	 * @param damage As the damage of the projectile
	 */
	public Projectile(final EntityType parentID, final Location location, final Direction direction,
			final double velocity, final int damage) {
		this.removable = false;
		this.parentID = parentID;
		this.damage = damage;
		this.location = location;
		this.velocity = velocity;
		this.direction = direction;
	}

	/**
	 * Get the damage that the projectile inflicts
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
		return this.parentID;
	}

	@Override
	public Location getLocation() {
		return this.location;
	}

	public Direction getDirection() {
		return this.direction;
	}

	@Override
	public void setLocation(final Location location) throws IllegalArgumentException {
		if (location == null) {
			throw new IllegalArgumentException("Projectile's location cannot be set as null.");
		}
		this.location = location;
	}

	@Override
	public void update() throws IllegalStateException {
		if (this.direction == null) {
			throw new IllegalStateException("Cannot update projectile if his direction is undefined.");
		}
		if(this.location == null) {
			throw new IllegalStateException("Cannot update projectile if his location is undefined.");
		}
			
		this.direction.moveLocation(this.location, this.velocity);

		if (this.location.getX() > 2) {
			this.removable = true;
		}
	}

	@Override
	public EntityType getID() {
		return this.ID;
	}

	@Override
	public boolean toRemove() {
		return this.removable;
	}
	
	@Override
	public String toString() {
		return "[ " + this.ID + " -> X: " + this.location.getX() + " | Y: " + this.location.getY() + " | Direction: "
				+ this.direction + " | Velocity: " + this.velocity + " | Damage: " + this.damage + " | WhoShooted: "
				+ this.parentID + " ]";
	}

}
