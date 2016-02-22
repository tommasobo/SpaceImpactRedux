package spaceimpact.model.entities;

import spaceimpact.model.Direction;
import spaceimpact.model.Location;
import spaceimpact.model.spawners.Weapon;

/** 
 * Spaceship (User controlled) Class
 * <br>
 * Define User driven Spaceship.
 * @author Davide
 */
public class Spaceship extends LivingEntity {
		
	/*CONSTRUCTORS*/
	
	/** 
	 * Spaceship's Constructor (Maximum Life, Velocity, Location, Direction)
	 * <br>
	 * Set isalive boolean as true.
	 * Set currentlife with maximumlife value.
	 * Set currentshield with maximumshield value.
	 * @param maxlife Spaceship's Maximum Life 
	 * @param velocity Spaceship's Velocity
	 * @param location Spaceship's start location
	 * @param direction Spaceship's start direction
	*/
	public Spaceship(int maxlife, double velocity, Location location, Direction direction){
		super.ID = EntityType.Spaceship;
		this.currentlife = maxlife;
		this.maxlife = maxlife;
		this.velocity = velocity;
		this.location = location;
		this.direction = direction;
		this.removable = false;
	}
		
	/** 
	 * Spaceship's Constructor (Maximum Life, Velocity, Location, Direction, Max Shield)
	 * <br>
	 * Set isalive boolean as true.
	 * Set currentlife with maximumlife value.
	 * Set currentshield with maximumshield value.
	 * @param maxlife Spaceship's Maximum Life Value
	 * @param velocity Spaceship's Velocity
	 * @param location Spaceship's start location
	 * @param direction Spaceship's start direction
	 * @param maxshield Spaceship's Maximum Shield Value
	*/
	public Spaceship(int maxlife, double velocity, Location location, Direction direction, int maxshield){
		this(maxlife, velocity, location, direction);
		super.currentshield = maxshield;
		super.maxshield = maxshield;
	}
	
	/** 
	 * Spaceship's Constructor (Maximum Life, Velocity, Location, Direction, Max Shield, Weapon)
	 * <br>
	 * Set isalive boolean as true.
	 * Set currentlife with maximumlife value.
	 * Set currentshield with maximumshield value.
	 * @param maxlife Spaceship's Maximum Life Value
	 * @param velocity Spaceship's Velocity
	 * @param location Spaceship's start location
	 * @param direction Spaceship's start direction
	 * @param maxshield Spaceship's Maximum Shield Value
	 * @param weapon Spaceship's Weapon
	*/
	public Spaceship(int maxlife, double velocity, Location location, Direction direction, int maxshield, Weapon weapon){
		this(maxlife, velocity, location, direction, maxshield);
		this.weapon = weapon;
	}
	
	/* MAIN METHODS */
	
	@Override
	public void update() {		
		coolDownWeapon();			
	}
	
	/** 
	 * Move the entity in the specified direction
	 * @param direction Direction of the movement
	 */
	public void move(final Direction direction) {
		this.direction = direction;			
		updateLocation();
		boundaryControl();
	}

	/**
	 * Control that the ship does not go over the screen boundaries
	 */
	private void boundaryControl() {	
		if (this.location.getX() < 0.05) {
			this.location.setX(0.05);
		}
		if (this.location.getY() > 0.91) {
			this.location.setY(0.91);
		}
		if(this.location.getY() < 0.05) {
			this.location.setY(0.05);
		}
		if (this.location.getX() > 1.70) {
			this.location.setX(1.70);
		}
	}

	
	/* GETTERS */
	
	/** 
	 * Getter method to get remaining shield
	 * @return amount of remaining shield as integer
	 */
	public int getRemainingShield() {
		return this.currentshield;
	}

}
