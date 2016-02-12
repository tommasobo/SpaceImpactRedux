package spaceimpact.model;

/** Weapon Interface
 * 
 * Basically a projectiles factory
 * 
 * @author Davide
 *
 */
public interface Weapon {

	/** Shoot a Projectile
	 * 
	 * create and shoot a new Projectile
	 * 
	 */
	void shoot();
	
	/** Enhance weapon capabilities
	 * 
	 * @param damageincrement Projectiles damage
	 * @param shootingfrequency How many projectiles can shoot in one tick
	 */
	void enhance(int damageincrement, int shootingfrequency);	
}
