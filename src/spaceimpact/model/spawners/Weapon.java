package spaceimpact.model.spawners;

import spaceimpact.model.Direction;
import spaceimpact.model.Location;
import spaceimpact.model.entities.EntityType;
import spaceimpact.model.entities.Projectile;

/**
 * Weapon Class
 * Factory to generate Projectile entities with a defined damage, location, direction
 * <b>damage</b> Projectiles damages
 * <b>
 * @author Davide
 */
public class Weapon implements WeaponInterface {

	private int damage; 
	private double projectilesvelocity;
	private final EntityType parentID;
	private final Direction direction;
	private int cooldowntime;
	private int cooldown;
		
	/**
	 * Weapon's Constructor (Shooter Entity Type, Direction, Cooldown time, Damage, velocity)
	 * @param shooter EntityType of who the owner of the weapon
	 * @param direction Direction of the shooted projectiles
	 * @param damage value of the damage
	 * @param velocity projectile velocity
	 */
	public Weapon(final EntityType shooter, final Direction direction, final int cooldowntime, final int damage, final double velocity) {
		this.parentID = shooter;
		this.direction = direction;
		this.damage = damage;
		this.projectilesvelocity = velocity;
		this.cooldowntime = cooldowntime;
		this.cooldown = 0;
	}
	
	@Override
	public Projectile shoot(final Location loc) {
		this.cooldown = this.cooldowntime;
		return new Projectile(this.parentID, new Location(loc), this.direction, this.projectilesvelocity, this.damage);		
	}

	@Override
	public void enhance(final int damageincrement, final double shootingfrequency, final int cooldowntime) {
		this.damage += damageincrement;
		this.projectilesvelocity += shootingfrequency;
		this.cooldowntime -= cooldowntime;
	}

	@Override
	public boolean isReadyToShoot() {
		if (this.cooldown == 0) {
			return true;
		} else {
			return false;	
		}
	}
	
	@Override
	public void coolDown() {
		if (this.cooldown > 0) {
			this.cooldown--;		
		}
	}	
}
