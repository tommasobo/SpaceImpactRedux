package spaceimpact.model;

/** 
 * Weapon Interface
 * <br>
 * Basically a projectiles factory
 * 
 * @author Davide
 *
 */
public interface Weapon {

	/** Shoot a Projectile
	 * 
	 * create and shoot a new Projectile
	 * @return projectile A new projective object
	 */
	Projectile shoot();
	
	/** Enhance weapon capabilities
	 * 
	 * @param damageincrement Projectiles damage
	 * @param projectilesvelocity Velocity of shooted projectiles
	 */
	void enhance(int damageincrement, double projectilesvelocity);	
}
