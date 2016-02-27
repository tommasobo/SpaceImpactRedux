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

	private final EntityType id = EntityType.Projectile;
	private final EntityType parentID;
	private final Direction direction;
	private final double velocity;
	private final int damage;
	private Location location;
	private boolean removable;

	/**
	 * Projectile Constructor.
	 * @param initparentid As the EntityType of the shooter
	 * @param initlocation As the starting location of the projectile
	 * @param initdirection As the direction of the projectile
	 * @param initvelocity As the velocity of the projectile
	 * @param initdamage As the damage of the projectile
	 */
	public Projectile(final EntityType initparentid, final Location initlocation, final Direction initdirection,
			final double initvelocity, final int initdamage) {
		this.removable = false;
		this.parentID = initparentid;
		this.damage = initdamage;
		this.location = initlocation;
		this.velocity = initvelocity;
		this.direction = initdirection;
	}

	/**
	 * Get the damage that the projectile inflicts.
	 * @return damage amount of damage as integer
	 */
	public int getDamage() {
		return this.damage;
	}

	/**
	 * Get the entity type that has shooted this projectile.
	 * @return EntityType that has shooted
	 */
	public EntityType getParentID() {
		return this.parentID;
	}

	@Override
	public Location getLocation() {
		return this.location;
	}

	/**
	 * Get Projectile current direction.
	 * @return direction Current projectile direction 
	 */
	public Direction getDirection() {
		return this.direction;
	}

	@Override
	public void setLocation(final Location newlocation) throws IllegalArgumentException {
		if (newlocation == null) {
			throw new IllegalArgumentException("Projectile's location cannot be set as null.");
		}
		this.location = newlocation;
	}

	@Override
	public void update() throws IllegalStateException {
		if (this.direction == null) {
			throw new IllegalStateException("Cannot update projectile if his direction is undefined.");
		}
		if (this.location == null) {
			throw new IllegalStateException("Cannot update projectile if his location is undefined.");
		}
			
		this.direction.moveLocation(this.location, this.velocity);

		if (this.location.getX() > 2d || this.location.getX() < -0.3d 
		        || this.location.getY() > 1.3d || this.location.getY() < -0.3d) {
			this.removable = true;
		}
	}

	@Override
	public EntityType getID() {
		return this.id;
	}
	
	@Override
	public void setRemovable() {
		this.removable = true;
	}

	@Override
	public boolean toRemove() {
		return this.removable;
	}
	
	@Override
	public String toString() {
		return "[ " + this.id + " -> X: " + this.location.getX() + " | Y: " + this.location.getY() + " | Direction: "
				+ this.direction + " | Velocity: " + this.velocity + " | Damage: " + this.damage + " | WhoShooted: "
				+ this.parentID + " ]";
	}

}
