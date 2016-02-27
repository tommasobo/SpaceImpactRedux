package spaceimpact.model.entities;

import java.util.List;

import spaceimpact.model.Direction;
import spaceimpact.model.Location;
import spaceimpact.model.spawners.Weapon;

/** 
 * Living Entity.<br>
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

	private Direction direction = null; //current entity direction
	private EntityType id = null; //entityType
	private Location location = null; //current position
	private int currentlife = 0; //current life
	private int maxlife = 0; //max life 
	private double velocity = 0; //how much the entity moves in a tick
	private double initvel = 0; //initial entity velocity
	private boolean removable = false; //determine if the spaceship is alive
	private Weapon weapon = null; //current weapon
		
	/**
	 * Generic Living Entity Constructor.
	 * @param initid EntityType of the Living Entity
	 * @param initmaxlife Maximum life value of the Living Entity
	 * @param initvelocity Velocity of the Living Entity
	 */
	public LivingEntity(final EntityType initid, final int initmaxlife, final double initvelocity) {
	    this.id = initid;
	    this.maxlife = initmaxlife;
	    this.currentlife = initmaxlife;
	    this.initvel = initvelocity;
	    this.velocity = initvelocity;
	    this.removable = false;
	}
	
	/*ACTIONS*/
		
	/**
	 * Update the entity Location.
	 */
	protected void updateLocation() {		
		this.direction.moveLocation(this.location, this.velocity);	
	}
		
	/**
	 * Verify if weapon is ready to shoot.
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
			throw new IllegalStateException("Entity " + this.id + " cannot shoot without a gun.");			
		}
		
		return this.weapon.shoot(new Location(this.location));
	}
	
	/**
	 * CoolDown Weapon if equipped.
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
		
		this.currentlife -= damage;
		
		if (this.currentlife <= 0) {
			this.currentlife = 0;
			this.removable = true;
		}			
	}
		
	/** 
	 * Increment entity life
	 * <br>
	 * Increase current life by the increment amount. If currentlife is greater than the maxlife value then 
	 * the currentlife is set to maxlife.
	 * @param increment Amount of life to add as integer
	 * @throws IllegalArgumentException If increment value is negative
	*/
	public void acquireLife(final int increment) {
		if (increment < 0) {
			throw new IllegalArgumentException("The entity cannot acquire negative amount of life.");
		}
			
		this.currentlife += increment;
			
		if (this.currentlife > this.maxlife) {
			this.currentlife = this.maxlife;
		}
	}
			
	/*SETTERS*/
	
	/** 
	 * Setter for Entity Weapon.
	 * @param newweapon Weapon to equip
	 * @throws IllegalArgumentException if the input weapon is null
	*/
	public void setWeapon(final Weapon newweapon) throws IllegalArgumentException {
		if (newweapon == null) {
			throw new IllegalArgumentException("Entity's weapon cannot be set if the new one is null.");
		}
		this.weapon = newweapon;
	}
				
	/** 
	 * Setter method for entity velocity.
	 * @param newvelocity velocity of the entity as double
	 * @throws IllegalArgumentException if the input value is negative
	 */
	public void setVelocity(final double newvelocity) throws IllegalArgumentException {
		if (newvelocity < 0) {
			throw new IllegalArgumentException("Entity's velocity cannot be set below 0.");
		}
		if ((newvelocity / this.initvel) <= 3) {
			this.velocity = newvelocity;
		}		
	}
	
	/** 
	 * Setter method for entity direction.
	 * @param newdirection as new entity direction
	 * @throws IllegalArgumentException if the input value is null
	 */
	public void setDirection(final Direction newdirection) throws IllegalArgumentException {
		if (newdirection == null) {
			throw new IllegalArgumentException("Entity's direction cannot be set as null");
		}
		this.direction = newdirection;	
	}
	
	@Override
	public void setLocation(final Location newlocation) throws IllegalArgumentException {
		if (newlocation == null) {
			throw new IllegalArgumentException("Entity's location cannot be set as null");
		}
		this.location = newlocation;		
	}
	
	@Override
	public void setRemovable() {
		this.removable = true;
	}
				
	/*GETTERS*/
			
	/** 
	 * Getter method to get remaining life.
	 * @return amount of remaining life as integer
	 */
	public int getRemainingLife() {
		return this.currentlife;
	}
		
	/** 
	 * Getter method for Direction.
	 * @return Direction
	 */
	public Direction getDirection() {
		return this.direction;	
	}
	
	/**
	 * Getter for current equipped weapon.
	 * @return weapon Current equipped weapon
	 */
	public Weapon getWeapon() {
		return this.weapon;
	}
	
	/**
	 * Getter for maximum entity life.
	 * @return maxlife maximum entity life
	 */
	public int getMaximumLife() {
		return this.maxlife;
	}
	
	/**
     * Getter method to get velocity.
     * @return velocity Current spaceship Velocity
     */
    public double getVelocity() {
        return this.velocity;
    }
    
	@Override	
	public Location getLocation() {
		return this.location;
	}
				
	@Override
	public EntityType getID() {
		return this.id;
	}
	
	/*OTHER METHODS*/
		
	@Override
	public boolean toRemove() {
		return this.removable;
	}
		
	@Override
	public String toString() {
	    return "[ " + this.id + " -> X: " + this.location.getX() + " | Y: " + this.location.getY() + " | Direction: " + this.getDirection() + " | Life: " + this.getRemainingLife() + " ]";		
	}
}

