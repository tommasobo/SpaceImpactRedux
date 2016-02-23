package spaceimpact.model.entities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import spaceimpact.model.Direction;
import spaceimpact.model.Location;
import spaceimpact.model.spawners.Weapon;

/** 
 * Enemy (AI Controlled) Class
 * <br>
 * Enemy Ships Computer driven.
 * @author Davide
 */
public class Enemy extends LivingEntity {
	
	/*CONSTRUCTORS*/
	
	/** 
	 * Enemy's Constructor (Maximum Life, Velocity, Location, Direction)
	 * <br>
	 *  Set isalive boolean as true.
	 *  Set currentlife with maximumlife value.
	 *  Set currentshield with maximumshield value.
	 * @param maxlife Enemy's Maximum Life 
	 * @param velocity Enemy's Velocity
	 * @param location Location of the Enemy Ship
	 * @param direction Direction of the Enemy Ship
	*/
	public Enemy(int maxlife, double velocity, Location location, Direction direction){
		super.ID = EntityType.Enemy;
		super.currentlife = maxlife;
		super.maxlife = maxlife;
		super.velocity = velocity;
		super.location = new Location(location);
		super.direction = direction;
		super.removable = false;
	}
		
	/** 
	 * Enemy's Constructor (Maximum Life, Velocity, Location, Direction, Maximum Shield)
	 * <br>
	 *  Set isalive boolean as true.
	 *  Set currentlife with maximumlife value.
	 *  Set currentshield with maximumshield value.
	 * @param maxlife Enemy's Maximum Life Value
	 * @param velocity Enemy's Velocity
	 * @param location Location of the Enemy Ship
	 * @param direction Direction of the Enemy Ship
	 * @param maxshield Enemy's Maximum Shield Value
	*/
	public Enemy(int maxlife, double velocity, Location location, Direction direction, int maxshield){
		this(maxlife, velocity, location, direction);
		super.currentshield = maxshield;
		super.maxshield = maxshield;	
	}
	
	/** 
	 * Enemy's Constructor (Maximum Life, Velocity, Location, Direction, Maximum Shield, Weapon)
	 * <br>
	 *  Set isalive boolean as true.
	 *  Set currentlife with maximumlife value.
	 *  Set currentshield with maximumshield value.
	 * @param maxlife Enemy's Maximum Life Value
	 * @param velocity Enemy's Velocity
	 * @param location Location of the Enemy Ship
	 * @param direction Direction of the Enemy Ship
	 * @param maxshield Enemy's Maximum Shield Value
	 * @param weapon Enemy's Weapon
	*/
	public Enemy(int maxlife, double velocity, Location location, Direction direction, int maxshield, Weapon weapon){
		this(maxlife, velocity,location, direction, maxshield);
		super.weapon = weapon;
	}
	
	/*MAIN METHODS */
	
	@Override
	public void update() {	
				
		if (this.weapon != null) {
			coolDownWeapon();			
		}	
		
		generateRandomMovement();
		updateLocation();
		boundaryControl();
	}
	
	/**
	 * Generate Random movement
	 * <br>
	 * The change in the direction must be rational (max 45°)<br>
	 */
	private void generateRandomMovement() {
		
		Random rnd = new Random();	
		
		if (rnd.nextDouble() < 0.0070) {
			
			int dirrnd = rnd.nextInt(2);
			if (dirrnd == 0) {
				this.direction = this.direction.moveLeft();
			} else if (dirrnd == 1) {
				this.direction = this.direction.moveRight();
			}		
		}
	}
	
	/**
	 * Control that the ship does not go over the screen boundaries
	 */
	public void boundaryControl() {	
		
		//se è in un angolo non farlo bloccare setta una direzione specificata che non lo blocchi		
		if (this.location.getX() < -0.3d) {
			this.location.setX(-0.25d);
			this.setDirection(getRandomDirection(this.direction));
		}
		if (this.location.getY() > 1.3d) {
			this.location.setY(1.25d);
			this.setDirection(getRandomDirection(this.direction));
		}
		if(this.location.getY() < -0.3d) {
			this.location.setY(-0.25d);
			this.setDirection(getRandomDirection(this.direction));
		}
		if (this.location.getX() > 2d) {
			this.location.setX(1.95);
			this.setDirection(getRandomDirection(this.direction));
		}
	}
	
	/**
	 * Generate new random direction
	 * <br>
	 * Return one directions excluding currdirection
	 * @param currdirection Current direction that must not be returned
	 * @return direction New Random Direction
	 */
	Direction getRandomDirection(final Direction currdirection) {
		List<Direction> dirlist = new ArrayList<Direction>(Arrays.asList(Direction.values()));
		dirlist.remove(currdirection);			
		Random rnd = new Random();
		
		return dirlist.get(rnd.nextInt(7));	
	}
}