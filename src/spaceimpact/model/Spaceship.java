package spaceimpact.model;

/** Spaceship (User controlled)
 * <br>
 * <b>location</b> as current location<br>
 * <b>currentlife</b> as the current life amount<br>
 * <b>maxlife</b> as the maximum life value reachable<br>
 * <b>velocity</b> as the amount of movement in a tick<br>
 * <b>isalive</b> as the state of the spaceship<br>
 * <b>weapon</b> as the current equipped weapon<br>
 * 
 * @author Davide
 */
public class Spaceship implements Entity {
	
	private Location location; //current position
	private int currentlife; //current life
	private int maxlife; //max life 
	private double velocity; //how much the entity moves in a tick
	private boolean isalive; //determine if spaceship is alive or dead
	private Weapon weapon; //current weapon
	
	/*CONSTRUCTORS*/
	
	/** Spaceship's Constructor (Maximum Life, Velocity)<br>
	 *  Set alive boolean as true.
	 * @param maxlife Spaceship's Maximum Life 
	 * @param velocity Spaceship's Velocity
	*/
	public Spaceship(int maxlife, double velocity){
		this.maxlife = maxlife;
		this.velocity = velocity;
		this.isalive = true;
	}
	
	/** Spaceship's Constructor (Maximum Life, Velocity, Weapon)<br>
	 * Set alive boolean as true.
	 * @param maxlife Spaceship's Maximum Life 
	 * @param velocity Spaceship's Velocity
	 * @param weapon Spaceship's Weapon
	*/
	public Spaceship(int maxlife, double velocity, Weapon weapon){
		this(maxlife, velocity);
		this.weapon = weapon;
	}
	
	/** Spaceship's Constructor (Maximum Life, Velocity, Weapon, Location)<br>
	 * Set alive boolean as true.
	 * @param maxlife Spaceship's Maximum Life 
	 * @param velocity Spaceship's Velocity
	 * @param weapon Spaceship's Weapon
	 * @param location Location of the Spaceship
	*/
	public Spaceship(int maxlife, double velocity, Weapon weapon, Location location) {
		this(maxlife, velocity, weapon);
		this.location = location;
	}
	
	/*ACTIONS*/
	
	/** Shoot with the current Weapon
	*/
	public void attack(){
		this.weapon.shoot();
	}
	
	/** Damage the spaceship life
	 * 
	 * Decrease current life by the amount of input damage. If currentlife goes <= 0 then 
	 * isalive boolean is set to false and the ship is dead.
	 * 
	 * @param damage Amount of damage as integer
	 * @throws IllegalArgumentException If damage value is negative
	*/
	public void looseLife(final int damage){
		if (damage < 0) {
			throw new IllegalArgumentException("The ship cannot receive a negative value of damage");
		}
		
		this.currentlife -= damage;
		
		if (this.currentlife < 0) {
			this.currentlife = 0;
			this.isalive = false;
		}			
	}

	/** Increment Spaceship life
	 * 
	 * Increase current life by the increment amount. If currentlife goes > maxlife then 
	 * the currentlife is set to maxlife.
	 * 
	 * @param increment Amount of life to add as integer
	 * @throws IllegalArgumentException If increment value is negative
	*/
	public void acquireLife(final int increment){
		if (increment < 0) {
			throw new IllegalArgumentException("The ship cannot acquire negative amount of life");
		}
			
		this.currentlife += increment;
			
		if (this.currentlife > this.maxlife) {
			this.currentlife = this.maxlife;
		}
	}
	
	/*SETTERS*/
	
	/** Setter for Spaceship Weapon
	 * @param weapon Weapon to equip
	*/
	public void setWeapon(final Weapon weapon) {
		this.weapon = weapon;
	}
		
	/** Setter method for entity location
	 * @param location
	 */
	public void setLocation(final Location location) {
		this.location = location;		
	}
	
	/** Setter method for entity velocity
	 * @param velocity the velocity of the entity
	 */
	public void setVelocity(final double velocity) {
		this.velocity = velocity;
	}

	/*GETTERS*/
		
	/** Getter method to get remaining life
	 * @return amount of remaining life as integer
	 */
	public int getRemainingLife() {
		return this.currentlife;
	}
	
	@Override	
	public Location getLocation() {
		return this.location;
	}
	
	@Override
	public Boolean collideWith(Entity otherEntity) {
		return otherEntity.getLocation().equals(this.location);
	}

	@Override
	public void Update() {
		this.location.setX(this.location.getX() + this.location.getX() * velocity);
		this.location.setY(this.location.getY() + this.location.getY() * velocity);
	}

}
