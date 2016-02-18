package spaceimpact.model;

/** Enemy (AI Controlled)
 * <br>
 * Enemy Ships Computer driven.
 * 
 * @author Davide
 */
public class Enemy extends LivingEntity {
	
	/*CONSTRUCTORS*/
	
	/** Enemy's Constructor (Maximum Life, Velocity)<br>
	 *  Set isalive boolean as true.
	 *  Set currentlife with maximumlife value.
	 *  Set currentshield with maximumshield value.
	 * @param maxlife Enemy's Maximum Life 
	 * @param velocity Enemy's Velocity
	*/
	public Enemy(int maxlife, double velocity){
		super.ID = EntityType.Enemy;
		this.currentlife = maxlife;
		this.maxlife = maxlife;
		this.velocity = velocity;
		this.isalive = true;
	}
		
	/** Enemy's Constructor (Maximum Life, Velocity, Weapon)<br>
	 *  Set isalive boolean as true.
	 *  Set currentlife with maximumlife value.
	 *  Set currentshield with maximumshield value.
	 * @param maxlife Enemy's Maximum Life Value
	 * @param velocity Enemy's Velocity
	 * @param maxshield Enemy's Maximum Shield Value
	*/
	public Enemy(int maxlife, double velocity, int maxshield){
		this(maxlife, velocity);
		super.currentshield = maxshield;
		super.maxshield = maxshield;
	}
	
	/** Enemy's Constructor (Maximum Life, Velocity, Weapon)<br>
	 *  Set isalive boolean as true.
	 *  Set currentlife with maximumlife value.
	 *  Set currentshield with maximumshield value.
	 * @param maxlife Enemy's Maximum Life Value
	 * @param velocity Enemy's Velocity
	 * @param maxshield Enemy's Maximum Shield Value
	 * @param weapon Enemy's Weapon
	*/
	public Enemy(int maxlife, double velocity, int maxshield, Weapon weapon){
		this(maxlife, velocity);
		this.maxshield = maxshield;
	}
	
	/** Enemy's Constructor (Maximum Life, Velocity, Weapon, Location)<br>
	 *  Set isalive boolean as true.
	 *  Set currentlife with maximumlife value.
	 *  Set currentshield with maximumshield value.
	 * @param maxlife Enemy's Maximum Life 
	 * @param velocity Enemy's Velocity
	 * @param maxshield Enemy's Maximum Shield Value
	 * @param weapon Enemy's Weapon
	 * @param location Location of the Spaceship
	*/
	public Enemy(int maxlife, double velocity, int maxshield, Weapon weapon, Location location) {
		this(maxlife, velocity, maxshield, weapon);
		this.location = location;
	}
}