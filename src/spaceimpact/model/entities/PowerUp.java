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
 */
public class PowerUp implements Entity {
	
	/**
	 * All possible PowerUps enhancements.
	 */
	public enum Enhancement {
	    /**
	     * Increment number of weapon's shooted projectile per fire.
	     */
		AddProjectile {
			@Override
			public String getImageName() {
				return "addprojectile.gif";
			}
		},
		/**
		 * Decrease CoolDown time of the Weapon.
		 */
		CoolDownDecreased {
			@Override
			public String getImageName() {
				return "cooldown.gif";
			}
		},
		/**
		 * Increment Weapon damage.
		 */
		IncrementDamage {
			@Override
			public String getImageName() {
				return "damage.gif";
			}
		},
		/**
		 * Heal Player.
		 */
		Heal {
			@Override
			public String getImageName() {
				return "heal.gif";
			}
		},
		/**
		 * Restore Player's Shield.
		 */
		RestoreShield {
			@Override
			public String getImageName() {
				return "shield.gif";
			}
		},
		/**
		 * Increment Player's Speed.
		 */
		IncrementSpeed {
			@Override
			public String getImageName() {
				return "speed.gif";
			}
		};
		
		/**
		 * Return the Description of the Enhancement.
		 * @return string Effect Description
		 */
		public String getString() {
			if (this.equals(Enhancement.AddProjectile)) {
				return "Weapon enhanced!";
			} else if (this.equals(Enhancement.CoolDownDecreased)) {
				return "More firerate!";
			} else if (this.equals(Enhancement.IncrementDamage)) {
				return "Damage increased!";
			} else if (this.equals(Enhancement.Heal)) {
				return "Hull repaired!";
			} else if (this.equals(Enhancement.RestoreShield)) {
				return "Shields recharged!";
			} else {
				return "Faster engines!";
			}			
		}
		
		/**
		 * Return the texture filename referred to the enhancement.
		 * @return string As the filename of the image linked to the enhancement
		 */
		public abstract String getImageName();
	}

	private final EntityType id = EntityType.PowerUp; //entity type identifier
	private final Enhancement plus; //Enhancement type
	private final double velocity; //current debris velocity
	private Direction direction = Direction.W; //direction of the entity
	private Location location; //current position
	private boolean removable; //determine if can be removed from gamescreen

	/**
	 * PowerUp Constructor.
	 * @param newplus Define the effects of the PowerUp
	 * @param newlocation Define the starting location of the PowerUp
	 * @param newvelocity Define the velocity of the PowerUp
	 */
	public PowerUp(final Enhancement newplus, final Location newlocation, final double newvelocity) {
		this.plus = newplus;
		this.location = new Location(newlocation);
		this.velocity = newvelocity;
		this.removable = false;
	}
	
	/**
	 * Apply PowerUp enhancement to Player Spaceship.
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
			player.acquireShield(20);
		} else {
			player.setVelocity(player.getVelocity() * 1.15);
		}
	}
	
	/**
	 * Return PowerUp Effect.
	 * @return plus The effects of this PowerUp
	 */
	public Enhancement getEnhancement() {
		return this.plus;		
	}
	
	/**
	 * Method set removable state as true.
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
	public void setLocation(final Location newlocation) throws IllegalArgumentException {
		if (newlocation == null) {
			throw new IllegalArgumentException("Entity's location cannot be set as null");
		}
		this.location = newlocation;
	}
			
	@Override
	public EntityType getID() {
		return this.id;
	}
	
	@Override
	public void update() throws IllegalStateException {
		if (this.direction == null) {
			throw new IllegalStateException("Cannot update powerup if his direction is undefined");
		}
		if (this.location == null) {
			throw new IllegalStateException("Cannot update powerup if his location is undefined");
		}
			
		if (location.getX() < -0.30d) {
			this.removable = true;
		} else {
			this.direction.moveLocation(this.location, this.velocity);		
		}
	}
	
	@Override
	public String toString() {
		return "[ " + this.id + " | Effect: " + this.plus + " -> X: " + this.location.getX() 
		       + " | Y: " + this.location.getY() + " | Enhancement: " + plus.toString() + " ]";		
	}

}
