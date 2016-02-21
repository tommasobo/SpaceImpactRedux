package spaceimpact.model.spawners;

import spaceimpact.model.Direction;
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
	 * @return A new projective object
	 */
	Projectile shoot();
	
	/** Enhance weapon capabilities
	 * <br>
	 * @param damageincrement Projectiles damage
	 * @param projectilesvelocity Velocity of shooted projectiles
	 */
	void enhance(int damageincrement, double projectilesvelocity);	
}
