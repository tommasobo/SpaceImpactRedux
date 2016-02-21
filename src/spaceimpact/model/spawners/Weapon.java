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
	private final EntityType parentID;
		
	/**
	 * Weapon's Constructor (Shooter Entity Type, Location, Damage)
	 * @param shooter EntityType of who the owner of the weapon
	 * @param damage value of the damage
	 * @param velocity projectile velocity
	 */
	public Weapon(final EntityType shooter, final int damage, final double velocity) {
		this.parentID = shooter;
		this.damage = damage;
		this.projectilesvelocity = velocity;
	}
	
	@Override
	public Projectile shoot(final Location loc) {
		return new Projectile(this.parentID, new Location(loc), Direction.E, projectilesvelocity, damage);
	}

	@Override
	public void enhance(int damageincrement, double shootingfrequency) {
		this.damage += damageincrement;
		this.projectilesvelocity += shootingfrequency;
	}
}
