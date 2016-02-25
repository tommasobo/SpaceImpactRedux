package spaceimpact.model.spawners;

import java.util.List;

import spaceimpact.model.Location;
import spaceimpact.model.entities.Projectile;

/** 
 * Weapon Interface
 * <br>
 * Weapon as projectiles factory. 
 * Provides method to enhance near all weapon parameters.
 * @author Davide
 */
public interface WeaponInterface {

	/** Shoot a Projectile
	 * <br>
	 * Create and shoot a new Projectile.
	 * @param loc Location from which shoot
	 * @return A new projective object
	 */
	List<Projectile> shoot(final Location loc);
	
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
	 * @return int As the current damage value
	 */
	int getDamage();
	
	/**
	 * Increase number of shooted projectiles
	 */
	void increaseProjectiles();
	
	/**
	 * Increase power (projectiles damage) of the weapon
	 * @param increment As the damage increment (Integer)
	 */
	void increaseDamage(final int increment);
	
	/**
	 * Decrease cooldown time of the weapon
	 * @param decrement As the decrement (in ticks) in the cooldown countdown
	 */
	void decreaseCoolDown(final int decrement);
	
	/**
	 * Getter for Weapon Current Number of Shooted Projectiles
	 * @return projectiles Number of Shooted Projectiles
	 */
	int getProjectilesCount();
	
}
