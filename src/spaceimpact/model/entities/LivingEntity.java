package spaceimpact.model.entities;

import java.util.List;

import spaceimpact.model.Direction;
import spaceimpact.model.Location;
import spaceimpact.model.spawners.Weapon;

/** 
 * Living Entity<br>
 * <b>location</b> as current location<br>
 * <b>currentlife</b> as the current life amount<br>
 * <b>maxlife</b> as the maximum life value reachable<br>
 * <b>currentshield</b> as the current shield amount<br>
 * <b>maxshield</b> as the maximum shield value reachable<br>
 * <b>velocity</b> as the amount of movement in a tick<br>
 * <b>removable</b> as the state of the spaceship<br>
 * <b>direction</b> as the current direction of the entity<br>
 * <b>ID</b> as the entity type<br>
 * <b>weapon</b> as the current equipped weapon<br>
 */
public abstract class LivingEntity implements Entity {

	protected Direction direction; //current entity direction
	protected EntityType ID; //entityType
	protected Location location; //current position
	protected int currentlife = 0; //current life
	protected int maxlife = 0; //max life 
	protected int currentshield = 0; //current shield
	protected int maxshield = 0; //max shield
	protected double velocity = 0; //how much the entity moves in a tick
	protected boolean removable = false; //determine if the spaceship is alive
	protected Weapon weapon; //current weapon
		
	/*ACTIONS*/
		
	/**
	 * Update the entity Location
	 */
	protected void updateLocation() {		
		this.direction.moveLocation(this.location, this.velocity);	
	}
		
	/**
	 * Verify if weapon is ready to shoot
	 * @return boolean True if the weapon does not need cooldown, false if it does.
	 */
	public boolean canShoot() {
		if (this.weapon != null) {
			return this.weapon.isReadyToShoot();
		}
		return false;
	}
		
	/** 
	 * Shoot with the current Weapon
	 * <br>
	 * If current weapon is null ignore the command.
	 * @return projectile Shooted projectile
	 * @throws IllegalStateException if no weapon is defined
	*/
	public List<Projectile> attack() throws IllegalStateException {
		if (this.weapon == null) {
			throw new IllegalStateException("Entity " + this.ID + " cannot shoot without a gun.");			
		}
			
		return this.weapon.shoot(new Location(this.location));
	}
	
	/**
	 * CoolDown Weapon if equipped
	 */
	protected void coolDownWeapon() {
		if (this.weapon != null) {
			this.weapon.coolDown();			
		}
	}
	
	/** 
	 * Damage the entity life
	 * <br>
	 * Decrease current life by the amount of input damage. If currentlife goes below or equal to 0 then 
	 * isalive boolean is set to false and the ship is dead.
	 * <br>
	 * @param damage Amount of damage as integer
	 * @throws IllegalArgumentException If damage value is negative
	*/
	public void looseLife(final int damage) throws IllegalArgumentException {
		if (damage < 0) {
			throw new IllegalArgumentException("The entity cannot receive a negative value of damage.");
		}
		
		this.currentlife -= this.looseShield(damage);
		
		if (this.currentlife <= 0) {
			this.currentlife = 0;
			this.removable = true;
		}			
	}

	/** 
	 * Absorb damage
	 * <br>
	 * The damage is decrease by the currentshield value.
	 * <br>
	 * @param damage Amount of damage as integer
	 * @return damage Amount of remaining damage as integer (maybe some of it was absorbed by the shield)
	*/
	public int looseShield(int damage){	
		
		int originalshield = currentshield;
		int filtereddamage = 0;
		
		currentshield -= damage;
		
		//if shield value is less than 0 return the absolute value of shield and set currentshield to 0
		if (originalshield != currentshield && currentshield < 0) { 
			filtereddamage = Math.abs(currentshield);
			currentshield = 0;
		} else if (originalshield != currentshield && currentshield > 0 ){ //if the shield value is greater that 0 and was decreased
			return 0;
		} else if (originalshield == currentshield && currentshield == 0) { //if there is no shield return full damage
			return damage;
		}
					
		return filtereddamage;
	}
		
	/** 
	 * Increment entity life
	 * <br>
	 * Increase current life by the increment amount. If currentlife is greater than the maxlife value then 
	 * the currentlife is set to maxlife.
	 * @param increment Amount of life to add as integer
	 * @throws IllegalArgumentException If increment value is negative
	*/
	public void acquireLife(final int increment){
		if (increment < 0) {
			throw new IllegalArgumentException("The entity cannot acquire negative amount of life.");
		}
			
		this.currentlife += increment;
			
		if (this.currentlife > this.maxlife) {
			this.currentlife = this.maxlife;
		}
	}
		
	/** Increment entity shield
	 * <br>
	 * Increase current shield by the increment amount. If currentshield is greater than the maxshield then 
	 * the currentshield is set to maxshield.
	 * @param increment Amount of shield to add as integer
	 * @throws IllegalArgumentException If increment value is negative
	*/
	public void acquireShield(final int increment) throws IllegalArgumentException {
		if (increment < 0) {
			throw new IllegalArgumentException("The entity cannot acquire negative amount of shield.");
		}
			
		this.currentshield += increment;
			
		if (this.currentshield > this.maxshield) {
			this.currentshield = this.maxshield;
		}
	}
	
	/*SETTERS*/
	
	/** 
	 * Setter for Entity Weapon
	 * @param weapon Weapon to equip
	 * @throws IllegalArgumentException if the input weapon is null
	*/
	public void setWeapon(final Weapon weapon) throws IllegalArgumentException {
		if (weapon == null) {
			throw new IllegalArgumentException("Entity's weapon cannot be set if the new one is null.");
		}
		this.weapon = weapon;
	}
			
	/** 
	 * Setter method for entity shield
	 * @param maxvalueshield the maximum shield value
	 * @throws IllegalArgumentException if the input value is negative
	 */
	public void setShield(final int maxvalueshield) throws IllegalArgumentException {
		if (maxvalueshield < 0) {
			throw new IllegalArgumentException("Entity's maximum shield cannot be set below 0.");
		}
		this.maxshield = maxvalueshield;
	}
	
	/** 
	 * Setter method for entity velocity
	 * @param velocity velocity of the entity as double
	 * @throws IllegalArgumentException if the input value is negative
	 */
	public void setVelocity(final double velocity) throws IllegalArgumentException {
		if (velocity < 0) {
			throw new IllegalArgumentException("Entity's velocity cannot be set below 0.");
		}
		this.velocity = velocity;
	}
	
	/** 
	 * Setter method for entity direction
	 * @param direction as new entity direction
	 * @throws IllegalArgumentException if the input value is null
	 */
	public void setDirection(Direction direction) throws IllegalArgumentException {
		if (direction == null) {
			throw new IllegalArgumentException("Entity's direction cannot be set as null");
		}
		this.direction = direction;	
	}
	
	@Override
	public void setLocation(final Location location) throws IllegalArgumentException {
		if (location == null) {
			throw new IllegalArgumentException("Entity's location cannot be set as null");
		}
		this.location = location;		
	}
				
	/*GETTERS*/
			
	/** 
	 * Getter method to get remaining life
	 * @return amount of remaining life as integer
	 */
	public int getRemainingLife() {
		return this.currentlife;
	}
		
	/** 
	 * Getter method for Direction
	 * @return Direction
	 */
	public Direction getDirection() {
		return this.direction;	
	}
	
	/**
	 * Getter for current equipped weapon
	 * @return weapon Current equipped weapon
	 */
	public Weapon getWeapon() {
		return this.weapon;
	}
	
	/**
	 * Getter for maximum entity life
	 * @return maxlife maximum entity life
	 */
	public int getMaximumLife() {
		return this.maxlife;
	}
	
	@Override	
	public Location getLocation() {
		return this.location;
	}
				
	@Override
	public EntityType getID() {
		return this.ID;
	}
	
	/*OTHER METHODS*/
		
	@Override
	public boolean toRemove() {
		return this.removable;
	}
		
	@Override
	public String toString() {
		if (this.ID.equals(EntityType.Spaceship)){
			Spaceship tmp = (Spaceship) this;
			return "[ " + tmp.ID + " -> X: " + tmp.location.getX() + " | Y: " + tmp.location.getY() + " | Direction: " + tmp.getDirection() + " | Life: " + tmp.getRemainingLife() + " | Shield: " + tmp.getRemainingShield() + " ]";			
		} else {
			return "[ " + this.ID + " -> X: " + this.location.getX() + " | Y: " + this.location.getY() + " | Direction: " + this.getDirection() + " | Life: " + this.getRemainingLife() + " ]";		
		}
	}
}

