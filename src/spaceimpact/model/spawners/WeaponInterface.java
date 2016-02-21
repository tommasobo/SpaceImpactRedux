package spaceimpact.model.spawners;

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
	Projectile shoot(Location loc);
	
	/** Enhance weapon capabilities
	 * <br>
	 * @param damageincrement Projectiles damage
	 * @param projectilesvelocity Velocity of shooted projectiles
	 */
	void enhance(int damageincrement, double projectilesvelocity);	
}
