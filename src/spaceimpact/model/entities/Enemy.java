package spaceimpact.model.entities;

import spaceimpact.model.Direction;
import spaceimpact.model.Location;
import spaceimpact.model.spawners.WeaponInterface;

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
		super.location = location;
		super.direction = direction;
		super.isalive = true;
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
	public Enemy(int maxlife, double velocity, Location location, Direction direction, int maxshield, WeaponInterface weapon){
		this(maxlife, velocity,location, direction, maxshield);
		super.weapon = weapon;
	}
}