package spaceimpact.model;

import spaceimpact.utilities.Input;

/** Spaceship (User controlled)
 * <br>
 * Define User driven Spaceship.
 * @author Davide
 */
public class Spaceship extends LivingEntity {
	
	/*CONSTRUCTORS*/
	
	/** Spaceship's Constructor (Maximum Life, Velocity)<br>
	 *  Set isalive boolean as true.
	 *  Set currentlife with maximumlife value.
	 *  Set currentshield with maximumshield value.
	 * @param maxlife Spaceship's Maximum Life 
	 * @param velocity Spaceship's Velocity
	*/
	public Spaceship(int maxlife, double velocity){
		super.ID = EntityType.Spaceship;
		this.currentlife = maxlife;
		this.maxlife = maxlife;
		this.velocity = velocity;
		this.isalive = true;
	}
		
	/** Spaceship's Constructor (Maximum Life, Velocity, Weapon)<br>
	 *  Set isalive boolean as true.
	 *  Set currentlife with maximumlife value.
	 *  Set currentshield with maximumshield value.
	 * @param maxlife Spaceship's Maximum Life Value
	 * @param velocity Spaceship's Velocity
	 * @param maxshield Spaceship's Maximum Shield Value
	*/
	public Spaceship(int maxlife, double velocity, int maxshield){
		this(maxlife, velocity);
		super.currentshield = maxshield;
		super.maxshield = maxshield;
	}
	
	/** Spaceship's Constructor (Maximum Life, Velocity, Weapon)<br>
	 *  Set isalive boolean as true.
	 *  Set currentlife with maximumlife value.
	 *  Set currentshield with maximumshield value.
	 * @param maxlife Spaceship's Maximum Life Value
	 * @param velocity Spaceship's Velocity
	 * @param maxshield Spaceship's Maximum Shield Value
	 * @param weapon Spaceship's Weapon
	*/
	public Spaceship(int maxlife, double velocity, int maxshield, Weapon weapon){
		this(maxlife, velocity);
		this.maxshield = maxshield;
	}
	
	/** Spaceship's Constructor (Maximum Life, Velocity, Weapon, Location)<br>
	 *  Set isalive boolean as true.
	 *  Set currentlife with maximumlife value.
	 *  Set currentshield with maximumshield value.
	 * @param maxlife Spaceship's Maximum Life 
	 * @param velocity Spaceship's Velocity
	 * @param maxshield Spaceship's Maximum Shield Value
	 * @param weapon Spaceship's Weapon
	 * @param location Location of the Spaceship
	*/
	public Spaceship(int maxlife, double velocity, int maxshield, Weapon weapon, Location location) {
		this(maxlife, velocity, maxshield, weapon);
		this.location = location;
	}

	/** Move the entity in the specified direction
	 * @param movetype Enum utilities.Input that define in which direction the spaceship must move
	 */
	public void moveOrAttack(final Input movetype) {
		if (movetype.equals(Input.A)) { //move left
			this.location.setX(this.location.getX() - delta * velocity);
		} else if (movetype.equals(Input.W)) { //move up
			this.location.setY(this.location.getY() + delta * velocity);
		} else if (movetype.equals(Input.S)) { //move right
			this.location.setX(this.location.getX() + delta * velocity);
		} else if (movetype.equals(Input.D)) { //move down
			this.location.setY(this.location.getY() - delta * velocity);
		} else if (movetype.equals(Input.SPACE)) {
			this.attack();
		} else {
			throw new IllegalArgumentException();
		}
	}
}
