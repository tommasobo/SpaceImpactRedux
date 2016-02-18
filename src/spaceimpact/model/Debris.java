package spaceimpact.model;

/** Debris
 * <br>
 * Spawned when an entity dies, has a limited amount of life defined by a countdown
 * 
 * @author Davide
 */
public class Debris implements Entity {

	private final EntityType ID = EntityType.Debris; //entity type identifier
	private Location location; //current position
	private final double velocity; //current velocity

	public Debris(final Location startinglocation, final double velocity) {
		this.location = startinglocation;
		this.velocity = velocity;
	}
		
	@Override
	public Location getLocation() {
		return this.location;
	}
	
	@Override
	public void setLocation(Location location) {
		this.location = location;
	}
	
	@Override
	public void update() {
		this.location.setX(this.location.getX() + delta * velocity);
		this.location.setY(this.location.getY() + delta * velocity);	
	}

	@Override
	public EntityType getID() {
		return ID;
	}
}
