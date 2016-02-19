package spaceimpact.model.entities;

import spaceimpact.model.Location;

/**
 * Weapon Class
 * Factory to generate Projectile entities with a defined damage, location, direction
 * @author Davide
 */
public class Weapon implements WeaponInterface {

	private int damage; 
	private double projectilesvelocity = 0.5;
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
		this.location = location;
		this.damage = damage;
	}
	
	@Override
	public Projectile shoot(final Direction direction) {
		return new Projectile(this.parentID, location, direction, projectilesvelocity, damage);
	}

	@Override
	public void enhance(int damageincrement, double shootingfrequency) {
		this.damage += damageincrement;
		this.projectilesvelocity += shootingfrequency;
	}
}
