package spaceimpact.model;

/** Projectile
 * <br>
 * Spawned when an entity shoot, damage and velocity are defined by the weapon that shoot it.
 * 
 * @author Davide
 */
public class Projectile implements Entity {

	private final int damage;
	private Location location;
	private final double velocity;
	
	public Projectile(final Location startinglocation, final double velocity, final int damage) {
		this.damage = damage;
		this.location = startinglocation;
		this.velocity = velocity;
	}
	
	/** Get the damage that the projectile inflicts
	 * 
	 * @return damage amount of damage as integer
	 */
	public int getDamage() {
		return this.damage;
	}
		
	@Override
	public Location getLocation() {
		return this.location;
	}

	@Override
	public Boolean collideWith(Entity otherEntity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void Update() {
		this.location.setX(this.location.getX() + this.location.getX() * velocity);
		this.location.setY(this.location.getY() + this.location.getY() * velocity);
	}

	@Override
	public void setLocation(Location location) {
		this.location = location;	
	}
}
