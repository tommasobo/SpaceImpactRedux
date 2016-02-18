package spaceimpact.model;

/** Living Entity
 * <br>
 * <b>location</b> as current location<br>
 * <b>currentlife</b> as the current life amount<br>
 * <b>maxlife</b> as the maximum life value reachable<br>
 * <b>currentshield</b> as the current shield amount<br>
 * <b>maxshield</b> as the maximum shield value reachable<br>
 * <b>velocity</b> as the amount of movement in a tick<br>
 * <b>isalive</b> as the state of the spaceship<br>
 * <b>weapon</b> as the current equipped weapon<br>
 * 
 * @author Davide
 */
public abstract class LivingEntity implements Entity{

	protected EntityType ID; //entityType
	protected Location location; //current position
	protected int currentlife; //current life
	protected int maxlife; //max life 
	protected int currentshield; //current shield
	protected int maxshield; //max shield
	protected double velocity; //how much the entity moves in a tick
	protected boolean isalive; //determine if spaceship is alive or dead
	protected Weapon weapon; //current weapon
		
	/*ACTIONS*/
	
	/** Shoot with the current Weapon
	*/
	public void attack(){
		this.weapon.shoot(this.getID());
	}
	
	/** Absorb damage
	 * 
	 * The damage is decrease by the currentshield value.
	 * 
	 * @param damage Amount of damage as integer
	 * @return damage Amount of remaining damage as integer (maybe some of it was absorbed by the shield)
	 * @throws IllegalArgumentException If damage value is negative
	*/
	public int looseShield(int damage){
		
		int filtereddamage = damage - currentshield;
		
		currentshield -= damage;
		
		if (currentshield < 0) {
			currentshield = 0;
		}
					
		return filtereddamage;
	}
	
	/** Damage the entity life
	 * 
	 * Decrease current life by the amount of input damage. If currentlife goes below or equal to 0 then 
	 * isalive boolean is set to false and the ship is dead.
	 * 
	 * @param damage Amount of damage as integer
	 * @throws IllegalArgumentException If damage value is negative
	*/
	public void looseLife(final int damage){
		if (damage < 0) {
			throw new IllegalArgumentException("The entity cannot receive a negative value of damage");
		}
		
		this.currentlife -= looseShield(damage);
		
		if (this.currentlife < 0) {
			this.currentlife = 0;
			this.isalive = false;
		}			
	}

	/** Increment entity life
	 * 
	 * Increase current life by the increment amount. If currentlife is greater than the maxlife value then 
	 * the currentlife is set to maxlife.
	 * 
	 * @param increment Amount of life to add as integer
	 * @throws IllegalArgumentException If increment value is negative
	*/
	public void acquireLife(final int increment){
		if (increment < 0) {
			throw new IllegalArgumentException("The entity cannot acquire negative amount of life");
		}
			
		this.currentlife += increment;
			
		if (this.currentlife > this.maxlife) {
			this.currentlife = this.maxlife;
		}
	}
		
	/** Increment entity shield
	 * 
	 * Increase current shield by the increment amount. If currentshield is greater than the maxshield then 
	 * the currentshield is set to maxshield.
	 * 
	 * @param increment Amount of shield to add as integer
	 * @throws IllegalArgumentException If increment value is negative
	*/
	public void acquireShield(final int increment){
		if (increment < 0) {
			throw new IllegalArgumentException("The entity cannot acquire negative amount of shield");
		}
			
		this.currentshield += increment;
			
		if (this.currentshield > this.maxshield) {
			this.currentshield = this.maxshield;
		}
	}
	
	/*SETTERS*/
	
	/** Setter for Entity Weapon
	 * @param weapon Weapon to equip
	*/
	public void setWeapon(final Weapon weapon) {
		this.weapon = weapon;
	}
			
	/** Setter method for entity shield
	 * @param maxvalueshield the maximum shield value
	 */
	public void setShield(final int maxvalueshield) {		
		this.maxshield = maxvalueshield;
	}
	
	/** Setter method for entity velocity
	 * @param velocity velocity of the entity as double
	 */
	public void setVelocity(final double velocity) {
		this.velocity = velocity;
	}
	
	@Override
	public void setLocation(final Location location) {
		this.location = location;		
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
	public void update() {
		this.location.setX(this.location.getX() + this.location.getX() * velocity);
		this.location.setY(this.location.getY() + this.location.getY() * velocity);	
	}
	
	@Override
	public EntityType getID() {
		return this.ID;
	}
}
