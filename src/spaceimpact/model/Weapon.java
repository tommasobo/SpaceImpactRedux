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
	 * @param parentID EntityType of the entity that has shooted
	 * @return projectile A new projective object
	 */
	Projectile shoot(EntityType parentID);
	
	/** Enhance weapon capabilities
	 * 
	 * @param damageincrement Projectiles damage
	 * @param projectilesvelocity Velocity of shooted projectiles
	 */
	void enhance(int damageincrement, double projectilesvelocity);	
}
