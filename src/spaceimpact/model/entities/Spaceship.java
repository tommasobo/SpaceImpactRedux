package spaceimpact.model.entities;

import spaceimpact.model.Direction;
import spaceimpact.model.Location;
import spaceimpact.model.spawners.Weapon;

/** 
 * Spaceship<br>
 * The entity that it's driven by the player. Can be ehnanced.
 * When his life goes equal or below zero the player loose and the game finishes.
 */
public class Spaceship extends LivingEntity {
    
    //Movements limits
    private static final double NLIMIT = 0.91;
    private static final double SLIMIT = 0.05;
    private static final double ELIMIT = 1.70;
    private static final double WLIMIT = 0.05;
		
    private int currentshield = 0; //current shield
    private int maxshield = 0; //max shield
    
	/** 
	 * Spaceship's Constructor. (Maximum Life, Velocity, Location, Direction, Max Shield, Weapon)
	 * <br>
	 * @param maxlife Spaceship's Maximum Life Value
	 * @param velocity Spaceship's Velocity
	 * @param location Spaceship's start location
	 * @param direction Spaceship's start direction
	 * @param initmaxshield Spaceship's Maximum Shield Value
	 * @param weapon Spaceship's Weapon
	*/
	public Spaceship(final int maxlife, final double velocity, final Location location, final Direction direction, final int initmaxshield, final Weapon weapon) {
	    super(EntityType.Spaceship, maxlife, velocity);
		this.currentshield = initmaxshield; 
		this.maxshield = initmaxshield;
		this.setLocation(new Location(location));
		this.setDirection(direction);
		this.setWeapon(weapon); 
	}
	
	/* MAIN METHODS */
	
	@Override
    public void update() throws IllegalStateException { 
        if (this.getDirection() == null) {
             throw new IllegalStateException("Cannot update projectile if his direction is undefined.");
        }
        if (this.getLocation() == null) {
            throw new IllegalStateException("Cannot update projectile if his location is undefined.");
        }
        if (this.getWeapon() == null) {
            throw new IllegalStateException("Cannot update projectile if his location is undefined.");
        }   
        coolDownWeapon();           
    }	  
	    	
    @Override
    public void looseLife(final int damage) throws IllegalArgumentException {
         if (damage < 0) {
             throw new IllegalArgumentException("The entity cannot receive a negative value of damage.");
         }        
         super.looseLife(this.looseShield(damage));             
     }
    
	/** 
	 * Move the entity in the specified direction.
	 * @param direction Direction of the movement
	 * @throws IllegalArgumentException if the input direction is null
	 */
	public void move(final Direction direction) throws IllegalArgumentException {
		if (direction == null) {
			throw new IllegalArgumentException("Cannot move spaceship if the direction of the movement is undefined.");
		}		
		this.setDirection(direction);			
		this.updateLocation();
		this.boundaryControl();
	}

	/**
	 * Control that the ship does not go over the screen boundaries
	 */
	private void boundaryControl() {	
		if (this.getLocation().getX() < WLIMIT) {
			this.getLocation().setX(WLIMIT);
		}
		if (this.getLocation().getY() > NLIMIT) {
			this.getLocation().setY(NLIMIT);
		}
		if (this.getLocation().getY() < SLIMIT) {
			this.getLocation().setY(SLIMIT);
		}
		if (this.getLocation().getX() > ELIMIT) {
			this.getLocation().setX(ELIMIT);
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
    
    /** 
     * Absorb damage
     * <br>
     * The damage is decrease by the currentshield value.
     * <br>
     * @param damage Amount of damage as integer
     * @return damage Amount of remaining damage as integer (maybe some of it was absorbed by the shield)
    */
    public int looseShield(final int damage) {
        
        int originalshield = currentshield;
        int filtereddamage = 0;
        
        currentshield -= damage;
        
        //if shield value is less than 0 return the absolute value of shield and set currentshield to 0
        if (originalshield != currentshield && currentshield < 0) { 
            filtereddamage = Math.abs(currentshield);
            currentshield = 0;
        } else if (originalshield != currentshield && currentshield > 0) { //if the shield value is greater that 0 and was decreased
            return 0;
        } else if (originalshield == currentshield && currentshield == 0) { //if there is no shield return full damage
            return damage;
        }
                    
        return filtereddamage;
    }
	
	/* SETTERS */
	
	/** 
     * Setter method for entity shield.
     * @param maxvalueshield the maximum shield value
     * @throws IllegalArgumentException if the input value is negative
     */
    public void setShield(final int maxvalueshield) throws IllegalArgumentException {
        if (maxvalueshield < 0) {
            throw new IllegalArgumentException("Entity's maximum shield cannot be set below 0.");
        }
        this.maxshield = maxvalueshield;
    }
	    
	/* GETTERS */
	
	/** 
	 * Getter method to get remaining shield.
	 * @return currentshield Amount of remaining shield as integer
	 */
	public int getRemainingShield() {
		return this.currentshield;
	}
		
	/* OTHERS */
	
	@Override
	public String toString() {
	    return "[ " + this.getID() + " -> X: " + this.getLocation().getX() + " | Y: " + this.getLocation().getY() 
	            + " | Direction: " + this.getDirection() + " | Life: " + this.getRemainingLife() + " | Shield: " 
	            + this.getRemainingShield() + " ]";
	}
}
