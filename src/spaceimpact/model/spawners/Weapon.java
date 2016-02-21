package spaceimpact.model.spawners;

import spaceimpact.model.Direction;
import spaceimpact.model.Location;
import spaceimpact.model.entities.EntityType;
import spaceimpact.model.entities.Projectile;

/**
 * Weapon Class
 * Factory to generate Projectile entities with a defined damage, location, direction
 * @author Davide
 */
public class Weapon implements WeaponInterface {

	private int damage; 
	private double projectilesvelocity;
	private Location location;
	private final EntityType parentID;
	
	/**
	 * Weapon's Constructor (Shooter Entity Type, Location, Damage)
	 * @param shooter EntityType of who the owner of the weapon
	 * @param location Current location of the weapon
	 * @param damage value of the damage
	 */
	public Weapon(final EntityType shooter, final Location location, final int damage) {
		this.parentID = shooter;
		this.location = new Location(location);
		this.damage = damage;
	}
	
	/**
	 * Weapon's Constructor (Shooter Entity Type, Location, Damage)
	 * @param shooter EntityType of who the owner of the weapon
	 * @param location Current location of the weapon
	 * @param damage value of the damage
	 * @param velocity projectile velocity
	 */
	public Weapon(final EntityType shooter, final Location location, final int damage, final double velocity) {
		this.parentID = shooter;
		this.location = new Location(location);
		this.damage = damage;
		this.projectilesvelocity = velocity;
	}
	
	@Override
	public Projectile shoot() {
		return new Projectile(this.parentID, location, Direction.E, projectilesvelocity, damage);
	}

	@Override
	public void enhance(int damageincrement, double shootingfrequency) {
		this.damage += damageincrement;
		this.projectilesvelocity += shootingfrequency;
	}
}
