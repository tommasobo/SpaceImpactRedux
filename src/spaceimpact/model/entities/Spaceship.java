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
		
	/** 
	 * Spaceship's Constructor (Maximum Life, Velocity, Location, Direction, Max Shield, Weapon)
	 * <br>
	 * @param maxlife Spaceship's Maximum Life Value
	 * @param velocity Spaceship's Velocity
	 * @param location Spaceship's start location
	 * @param direction Spaceship's start direction
	 * @param maxshield Spaceship's Maximum Shield Value
	 * @param weapon Spaceship's Weapon
	*/
	public Spaceship(int maxlife, double velocity, Location location, Direction direction, int maxshield, Weapon weapon){
		super.ID = EntityType.Spaceship;
		super.currentshield = maxshield;
		super.maxshield = maxshield;
		super.currentlife = maxlife;
		super.maxlife = maxlife;
		super.velocity = velocity;
		super.initvel = velocity;
		super.location = location;
		super.direction = direction;
		super.removable = false;
		super.weapon = weapon; 
	}
	
	/* MAIN METHODS */
	
	@Override
	public void update() throws IllegalStateException {	
		if (this.direction == null) {
			throw new IllegalStateException("Cannot update projectile if his direction is undefined.");
		}
		if(this.location == null) {
			throw new IllegalStateException("Cannot update projectile if his location is undefined.");
		}
		if(this.weapon == null) {
			throw new IllegalStateException("Cannot update projectile if his location is undefined.");
		}	
		coolDownWeapon();			
	}
	
	/** 
	 * Move the entity in the specified direction
	 * @param direction Direction of the movement
	 * @throws IllegalArgumentException if the input direction is null
	 */
	public void move(final Direction direction) throws IllegalArgumentException {
		if (direction == null) {
			throw new IllegalArgumentException("Cannot move spaceship if the direction of the movement is undefined.");
		}
			
		this.direction = direction;			
		super.updateLocation();
		this.boundaryControl();
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
	 * @return currentshield Amount of remaining shield as integer
	 */
	public int getRemainingShield() {
		return this.currentshield;
	}
	
	/**
	 * Getter method to get velocity
	 * @return velocity Current spaceship Velocity
	 */
	public double getVelocity() {
		return this.velocity;
	}

}
