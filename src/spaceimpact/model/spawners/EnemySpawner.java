package spaceimpact.model.spawners;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import spaceimpact.model.Direction;
import spaceimpact.model.Location;
import spaceimpact.model.entities.Enemy;
import spaceimpact.model.entities.EntityType;

/**
 * Enemy Spanwer
 * <br>
 * Define the Enemy Spawner inside the Level.
 * @author Davide
 */
public class EnemySpawner extends Spawner {
	
	private int minlife = 1;
	private int maxlife = 1;
	private int mindamage = 1;
	private int maxdamage = 10;
	private double minvel = 0.1d;
	private double maxvel = 1;
	private int weaponcooldown = 30;
	
	/**
	 * Constructor for EnemySpawner
	 * @param spawnlimit Maximum Spawn Counts
	 * @param maxperspawn Max Concurrent Entity in a Spawn
	 * @param spawndelay Delay between each spawn (ticks)
	 * @param maxperspawn Max Concurrent Entity in a Spawn
	 */
	public EnemySpawner(final int spawnlimit,int maxperspawn, int spawndelay) {
		super(EntityType.Enemy, maxperspawn, spawndelay);
		super.maxspawn = spawnlimit;
	}
	
	/**
	 * Complete Constructor for EnemySpawner
	 * @param max Maximum Spawn Counts
	 * @param maxperspawn Max Concurrent Entity in a Spawn
	 * @param spawndelay Delay between each spawn (ticks)
	 * @param minlife Spanwed Entity Mimimum Life
	 * @param maxlife Spanwed Entity Maximum Life
	 * @param minvel Spanwed Entity Mimimum Velocity
	 * @param maxvel Spanwed Entity Maximum Velocity
	 * @param mindamage Spawned Entity Mimimum Damage
	 * @param maxdamage Spawned Entity Maximum Damage
	 * @param weaponcooldown Spawned Entity Weapon's CoolDown
	 */
	public EnemySpawner(int max, int maxperspawn, int spawndelay, int minlife, int maxlife, double minvel, double maxvel, int mindamage, int maxdamage, int weaponcooldown) {
		this(max, maxperspawn, spawndelay);
		this.minlife = minlife;
		this.maxlife = maxlife;
		this.minvel = minvel;
		this.maxvel = maxvel;
		this.mindamage = mindamage;
		this.maxdamage = maxdamage;
		this.weaponcooldown = weaponcooldown;
	}

	@Override
	public List<Enemy> spawn() {
		List<Enemy> spawnedentities = new ArrayList<>();
		
		Random rnd = new Random();	
		int tospawn = rnd.nextInt(maxperspawn) + 1;
		
		for(int i = 0; i < tospawn; i++) {	
			if (spawncount < maxspawn) {			
				//generate random life in range
				int newlife = minlife + rnd.nextInt(maxlife - minlife + 1) ;
				//generate random velocity
				double vel = minvel + (maxvel - minvel) * rnd.nextDouble();			
				//random damage in range
				int newdamage = mindamage + rnd.nextInt(maxdamage - mindamage + 1) ;	
				//generate random location
				double x = 1.8d + 0.2d * rnd.nextDouble();
				double y = 0.15d + 0.70d * rnd.nextDouble();
				//random location NW SW W
				Direction dir = null;			
				int rndvalue = rnd.nextInt(3);				
				if (rndvalue == 0) {
					dir = Direction.SW;
				} else if (rndvalue == 1) {
					dir = Direction.W;
				} else {
					dir = Direction.NW;
				}	
				Location tmploc = new Location(x, y, area);
				//random weapon
				Weapon tmpweapon = new Weapon(type, dir, weaponcooldown, newdamage, maxvel * 1.5);
				
				spawnedentities.add(new Enemy(newlife, vel, tmploc, dir, 0, tmpweapon));
				spawncount++;			
			} else {
				return spawnedentities;
			}
		}				
		return spawnedentities;
	}
	
	/**
	 * Set spawned entity life range
	 * @param minlife Minimum life of the spawned entities
	 * @param maxlife Maximum life of the spawned entities
	 * @throws IllegalArgumentException if input values are negative
	 */
	public void setEntityLifeRange(final int minlife, final int maxlife) throws IllegalArgumentException {
		if (minlife < 0) {
			throw new IllegalArgumentException("Enemy Spawner minimum spawn entity life cannot be set as negative");
		}
		if (maxlife < 0) {
			throw new IllegalArgumentException("Enemy Spawner maximum spawn entity life cannot be set as negative");
		}	
		this.minlife = minlife;
		this.maxlife = maxlife;	
	}
		
	/**
	 * Set spawned entity velocity range
	 * @param minvel MiniVelocity of the spawned entities
	 * @param maxvel Maximum Velocity of the spawned entities
	 * @throws IllegalArgumentException if input values are negative
	 */
	public void setEntityVelocityRange(final double minvel, final double maxvel) throws IllegalArgumentException {
		if (minvel < 0) {
			throw new IllegalArgumentException("Enemy Spawner minimum spawn entity velocity cannot be set as negative");
		}
		if (maxvel < 0) {
			throw new IllegalArgumentException("Enemy Spawner maximum spawn entity velocity cannot be set as negative");
		}
		this.minvel = minvel;
		this.maxvel = maxvel;	
	}
	
	/**
	 * Set spawned entity damage range
	 * @param mindamage Minimum amount of damage that a spawned entity can inflict
	 * @param maxdamage Maximum amount of damage that a spawned entity can inflict
	 * @throws IllegalArgumentException if input values are negative
	 */
	public void setEntityDamageRange(final int mindamage, final int maxdamage) throws IllegalArgumentException {
		if (mindamage < 0) {
			throw new IllegalArgumentException("Enemy Spawner minimum entity damage cannot be set as negative");
		}
		if (maxdamage < 0) {
			throw new IllegalArgumentException("Enemy Spawner maximum entity damage velocity cannot be set as negative");
		}
		this.mindamage = mindamage;
		this.maxdamage = maxdamage;
	}
	
	/**
	 * Set Weapon cooldown time for spawned entity
	 * @param cooldown Cooldown time as number of ticks
	 * @throws IllegalArgumentException if input value is negative
	 */
	public void setCoolDownEntityWeapon(final int cooldown) throws IllegalArgumentException {
		if (cooldown < 0) {
			throw new IllegalArgumentException("Enemy Spawner entity weapon's cooldown cannot be set as negative");
		}
		this.weaponcooldown = cooldown;	
	}

}
