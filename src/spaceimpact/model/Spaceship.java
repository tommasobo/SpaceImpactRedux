package spaceimpact.model;

/** Spaceship (User controlled)
 * <br>
 * Define Spaceship User driven.
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
}
