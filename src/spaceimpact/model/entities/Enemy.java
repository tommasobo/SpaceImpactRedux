package spaceimpact.model.entities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import spaceimpact.model.Direction;
import spaceimpact.model.Location;
import spaceimpact.model.spawners.Weapon;

/** 
 * Enemy<br>
 * Defines Computer driven Enemy Ship.
 */
public class Enemy extends LivingEntity {
	
    //Movement Limits
    private static final double NLIMIT = 1.3; //north limit
    private static final double NMAX = 1.25; //north max value if exceed limit
    private static final double ELIMIT = 2; //east limit
    private static final double EMAX = 1.95; //east west max value if exceed limit
    private static final double SWLIMIT = -0.3; //south west limit
    private static final double SWMAX = -0.25; //south west value if exceed limit
    private static final double RANDOMMOVEMENTPROBABILITY = 0.0070;
    
	/*CONSTRUCTORS*/
	
	/** 
	 * Enemy's Constructor (Maximum Life, Velocity, Location, Direction, Weapon)
	 * <br>
	 *  Set isalive boolean as true.
	 *  Set currentlife with maximumlife value.
	 *  Set currentshield with maximumshield value.
	 * @param maxlife Enemy's Maximum Life 
	 * @param velocity Enemy's Velocity
	 * @param location Location of the Enemy Ship
	 * @param direction Direction of the Enemy Ship
	 * @param weapon Weapon of the Enemy Ship
	*/
	public Enemy(final int maxlife, final double velocity, final Location location, final Direction direction, final Weapon weapon) {
		super(EntityType.Enemy, maxlife, velocity);
		this.setLocation(new Location(location));
		this.setDirection(direction);
		this.setWeapon(weapon);
	}
				
	/*MAIN METHODS */
	
	@Override
	public void update() throws IllegalStateException {			
		if (this.getDirection() == null) {
			throw new IllegalStateException("Cannot update enemy if his direction is undefined");
		}
		if (this.getLocation() == null) {
			throw new IllegalStateException("Cannot update enemy if his location is undefined");
		}
		if (this.getWeapon() == null) {
			throw new IllegalStateException("Cannot update enemy if his location is undefined");
		}		
		this.coolDownWeapon();
		this.generateRandomMovement();
		this.updateLocation();
		this.boundaryControl();
	}
	
	/**
	 * Generate Random movement.<br>
	 * The change in the direction must be rational (max 45°)<br>
	 */
	private void generateRandomMovement() {
		
		Random rnd = new Random();	
		
		if (rnd.nextDouble() < RANDOMMOVEMENTPROBABILITY) {
			
			int dirrnd = rnd.nextInt(2);
			if (dirrnd == 0) {
				this.setDirection(this.getDirection().moveLeft());
			} else if (dirrnd == 1) {
				this.setDirection(this.getDirection().moveRight());
			}		
		}
	}
	
	/**
	 * Control that the ship does not go over the screen boundaries.
	 */
	public void boundaryControl() {	
				
		if (this.getLocation().getX() < SWLIMIT) {
			this.getLocation().setX(SWMAX);
			this.setDirection(this.getRandomDirection(this.getDirection()));
		}
		if (this.getLocation().getY() > NLIMIT) {
			this.getLocation().setY(NMAX);
			this.setDirection(this.getRandomDirection(this.getDirection()));
		}
		if (this.getLocation().getY() < SWLIMIT) {
			this.getLocation().setY(SWMAX);
			this.setDirection(this.getRandomDirection(this.getDirection()));
		}
		if (this.getLocation().getX() > ELIMIT) {
			this.getLocation().setX(EMAX);
			this.setDirection(this.getRandomDirection(this.getDirection()));
		}
	}
	
	/**
	 * Generate new random direction
	 * <br>
	 * Return one directions excluding currdirection
	 * @param currdirection Current direction that must not be returned
	 * @return direction New Random Direction
	 * @throws IllegalArgumentException if the input currdirection is null
	 */
	Direction getRandomDirection(final Direction currdirection) throws IllegalArgumentException {
		
		if (currdirection == null) {
			throw new IllegalArgumentException("Cannot randomly generate a new direction if the input currentdirection is null.");
		}
		
		List<Direction> dirlist = new ArrayList<Direction>(Arrays.asList(Direction.values()));
		dirlist.remove(currdirection);			
		Random rnd = new Random();
		
		return dirlist.get(rnd.nextInt(Direction.values().length - 1));	
	}
}