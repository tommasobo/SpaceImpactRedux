package spaceimpact.model.entities;

import spaceimpact.model.Direction;
import spaceimpact.model.Location;

/**
 * PowerUp<br>
 * If acquired permits the player to enhance some personal statistics or his weapon.<br>
 * Possible Enhancements (Internal Enumeration):<br>
 * <b>AddProjectile</b> Increment by one the number of shooted projectiles by the player weapon.<br>
 * <b>CoolDownDecreased</b> Decrease cooldown time of the player weapon.<br>
 * <b>IncrementDamage</b> Increase damage of the weapon.<br>
 * <b>RestoreShield</b> Restore part of the player's shield.<br>
 * <b>IncrementSpeed</b> Increase player speed.<br>
 * <b>Heal</b> Restore part of the player's life.<br>
 * @author Davide
 *
 */
public class PowerUp implements Entity {
	
	/**
	 * All possible PowerUps enhancements
	 */
	public enum Enhancement {
		AddProjectile,
		CoolDownDecreased,
		IncrementDamage,
		Heal,
		RestoreShield,
		IncrementSpeed;
	}

	private final EntityType ID = EntityType.PowerUp; //entity type identifier
	private final Enhancement plus; //Enhancement type
	private Direction direction = Direction.W; //direction of the entity
	private Location location; //current position
	private double velocity; //current debris velocity
	private boolean removable; //determine if can be removed from gamescreen

	/**
	 * PowerUp Constructor
	 * @param plus Define the effects of the PowerUp
	 * @param location Define the starting location of the PowerUp
	 * @param velocity Define the velocity of the PowerUp
	 */
	public PowerUp(final Enhancement plus, final Location location, final double velocity) {
		this.plus = plus;
		this.location = new Location(location);
		this.velocity = velocity;
		this.removable = false;
	}
	
	/**
	 * Apply PowerUp enhancement to Player Spaceship
	 * @param player As the player Spaceship to enhance
	 * @throws IllegalStateException If player's weapon is null
	 * @throws IllegalArgumentException If player spaceship is null
	 */
	public void applyEnhancement(final Spaceship player) throws IllegalStateException, IllegalArgumentException {
		if (player == null) {
			throw new IllegalArgumentException("PowerUp cannot enhance player if it's null.");
		}
		if (player.getWeapon() == null) {
			throw new IllegalStateException("PowerUp cannot enhance player weapon if it's null.");
		}
		
		if (this.plus.equals(Enhancement.AddProjectile)) {
			player.getWeapon().increaseProjectiles();
		} else if (this.plus.equals(Enhancement.CoolDownDecreased)) {
			player.getWeapon().decreaseCoolDown(2);
		} else if (this.plus.equals(Enhancement.IncrementDamage)) {
			player.getWeapon().increaseDamage(2);
		} else if (this.plus.equals(Enhancement.Heal)) {
			player.acquireLife(10);
		} else if (this.plus.equals(Enhancement.RestoreShield)) {
			player.acquireShield(10);
		} else {
			player.setVelocity(player.getVelocity() * 1.1);
		}
	}
	
	/**
	 * Return PowerUp Effect
	 * @return plus The effects of this PowerUp
	 */
	public Enhancement getEnhancement() {
		return this.plus;		
	}
	
	/**
	 * Method set removable state as true
	 */
	public void setRemovable() {
		this.removable = true;
	}
	
	@Override
	public boolean toRemove() {
		return this.removable;
	}
		
	@Override
	public Location getLocation() {
		return this.location;
	}
	
	@Override
	public void setLocation(Location location) {
		this.location = location;
	}
			
	@Override
	public EntityType getID() {
		return this.ID;
	}
	
	@Override
	public void update() {
		if (location.getX() < -0.30d) {
			this.removable = true;
		} else {
			this.direction.moveLocation(this.location, this.velocity);		
		}
	}
	
	@Override
	public String toString() {
		return "[ " + this.ID + " -> X: " + this.location.getX() + 
				" | Y: " + this.location.getY() + " | Enhancement: " + plus.toString() + " ]";		
	}

}
