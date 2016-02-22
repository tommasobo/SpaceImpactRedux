package spaceimpact.model.spawners;

import java.util.List;

import spaceimpact.model.Location;
import spaceimpact.model.entities.Projectile;

/** 
 * Weapon Interface
 * <br>
 * Basically a projectiles factory
 * @author Davide
 */
public interface WeaponInterface {

	/** Shoot a Projectile
	 * <br>
	 * Create and shoot a new Projectile
	 * @param loc Location from which shoot
	 * @return A new projective object
	 */
	List<Projectile> shoot(final Location loc);
	
	/** Enhance weapon capabilities
	 * <br>
	 * @param damageincrement Increment in projectiles damage
	 * @param projectilesvelocityincrement Increment of velocity of shooted projectiles
	 * @param cooldowntimedecrement Cool Down time decrement (as number of ticks)
	 */
	void enhance(final int damageincrement, final double projectilesvelocityincrement, final int cooldowntimedecrement);	
	
	/**
	 * Control if Weapon is ready to shoot or need to cool down
	 * @return boolean True if the weapon is ready to shoot, False if needs to cool down
	 */
	boolean isReadyToShoot();
	
	/**
	 * CoolDown the Weapon
	 */
	void coolDown();
	
	/**
	 * Getter for Weapon Damage
	 */
	int getDamage();
	
	/**
	 * Setter for number of shooted projectiles
	 */
	void setShootedProjectiles(final int count);
}
