package spaceimpact.model.entities;

import spaceimpact.utilities.Input;
import spaceimpact.model.Direction;
import spaceimpact.model.Location;
import spaceimpact.model.spawners.WeaponInterface;

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
		this.isalive = true;
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
	public Spaceship(int maxlife, double velocity, Location location, Direction direction, int maxshield, WeaponInterface weapon){
		this(maxlife, velocity, location, direction, maxshield);
		this.weapon = weapon;
	}
	
	/* GETTERS */
	
	/** 
	 * Getter method to get remaining shield
	 * @return amount of remaining shield as integer
	 */
	public int getRemainingShield() {
		return this.currentshield;
	}
	
	/* MAIN METHODS */
	
	/** 
	 * Move the entity in the specified direction
	 * @param movetype Enum utilities.Input that define in which direction the spaceship must move
	 */
	public void move(final Input movetype) throws IllegalArgumentException {
		if (movetype.equals(Input.A)) { //move left
			this.direction = Direction.W;
			this.update();
		} else if (movetype.equals(Input.W)) { //move up
			this.direction = Direction.N;
			this.update();
		} else if (movetype.equals(Input.D)) { //move right
			this.direction = Direction.E;
			this.update();
		} else if (movetype.equals(Input.S)) { //move down
			this.direction = Direction.S;
			this.update();		
		} else {
			throw new IllegalArgumentException();
		}
		
		//spaceship game screen boundaries control
		if (this.location.getX() < 0) {
			this.location.setX(0);
		}
		if (this.location.getY() > 0.85) {
			this.location.setY(0.85);
		}
		if(this.location.getY() < 0) {
			this.location.setY(0);
		}
		if (this.location.getX() > 1.65) {
			this.location.setX(1.65);
		}
	}
}
