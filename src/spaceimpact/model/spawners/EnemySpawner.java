package spaceimpact.model.spawners;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import spaceimpact.model.Direction;
import spaceimpact.model.Location;
import spaceimpact.model.entities.Enemy;
import spaceimpact.model.entities.EntityType;

/**
 * Enemy Spanwer<br>
 * Define the Enemy Spawner inside the Level.
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
	 * Constructor for EnemySpawner.
	 * @param spawnlimit Maximum Spawn Counts
	 * @param maxperspawn Max Concurrent Entity in a Spawn
	 * @param spawndelay Delay between each spawn (ticks)
	 */
	public EnemySpawner(final int spawnlimit, final int maxperspawn, final int spawndelay) {
		super(EntityType.Enemy, maxperspawn, spawndelay);
		super.maxspawn = spawnlimit;
	}
	
	/**
	 * Complete Constructor for EnemySpawner.
	 * @param max Maximum Spawn Counts
	 * @param maxperspawn Max Concurrent Entity in a Spawn
	 * @param spawndelay Delay between each spawn (ticks)
	 * @param initminlife Spanwed Entity Mimimum Life
	 * @param initmaxlife Spanwed Entity Maximum Life
	 * @param initminvel Spanwed Entity Mimimum Velocity
	 * @param initmaxvel Spanwed Entity Maximum Velocity
	 * @param initmindamage Spawned Entity Mimimum Damage
	 * @param initmaxdamage Spawned Entity Maximum Damage
	 * @param initweaponcooldown Spawned Entity Weapon's CoolDown
	 */
	public EnemySpawner(final int max, final int maxperspawn, final int spawndelay, final int initminlife, 
	        final int initmaxlife, final double initminvel, final double initmaxvel, final int initmindamage, 
	        final int initmaxdamage, final int initweaponcooldown) {
		this(max, maxperspawn, spawndelay);
		this.minlife = initminlife;
		this.maxlife = initmaxlife;
		this.minvel = initminvel;
		this.maxvel = initmaxvel;
		this.mindamage = initmindamage;
		this.maxdamage = initmaxdamage;
		this.weaponcooldown = initweaponcooldown;
	}

	@Override
	public List<Enemy> spawn() {
		List<Enemy> spawnedentities = new ArrayList<>();
		
		Random rnd = new Random();	
		int tospawn = rnd.nextInt(this.maxperspawn) + 1;
		
		for (int i = 0; i < tospawn; i++) {	
			if (this.spawncount < this.maxspawn) {			
				//generate random life in range
				int newlife = this.minlife + rnd.nextInt(this.maxlife - this.minlife + 1);
				//generate random velocity
				double vel = this.minvel + (this.maxvel - this.minvel) * rnd.nextDouble();			
				//random damage in range
				int newdamage = this.mindamage + rnd.nextInt(this.maxdamage - this.mindamage + 1);	
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
				Location tmploc = new Location(x, y, this.area);
				//random weapon
				Weapon tmpweapon = new Weapon(this.type, dir, this.weaponcooldown, newdamage, this.maxvel * 1.5);
				
				spawnedentities.add(new Enemy(newlife, vel, tmploc, dir, 0, tmpweapon));
				this.spawncount++;			
			} else {
				return spawnedentities;
			}
		}				
		return spawnedentities;
	}
	
	/**
	 * Set spawned entity life range.
	 * @param newminlife Minimum life of the spawned entities
	 * @param newmaxlife Maximum life of the spawned entities
	 * @throws IllegalArgumentException if input values are negative
	 */
	public void setEntityLifeRange(final int newminlife, final int newmaxlife) throws IllegalArgumentException {
		if (newminlife < 0) {
			throw new IllegalArgumentException("Enemy Spawner minimum spawn entity life cannot be set as negative.");
		}
		if (newmaxlife < 0) {
			throw new IllegalArgumentException("Enemy Spawner maximum spawn entity life cannot be set as negative.");
		}	
		this.minlife = newminlife;
		this.maxlife = newmaxlife;	
	}
		
	/**
	 * Set spawned entity velocity range.
	 * @param initminvel MiniVelocity of the spawned entities
	 * @param initmaxvel Maximum Velocity of the spawned entities
	 * @throws IllegalArgumentException if input values are negative
	 */
	public void setEntityVelocityRange(final double initminvel, final double initmaxvel) throws IllegalArgumentException {
		if (initminvel < 0) {
			throw new IllegalArgumentException("Enemy Spawner minimum spawn entity velocity cannot be set as negative.");
		}
		if (initmaxvel < 0) {
			throw new IllegalArgumentException("Enemy Spawner maximum spawn entity velocity cannot be set as negative.");
		}
		this.minvel = initminvel;
		this.maxvel = initmaxvel;	
	}
	
	/**
	 * Set spawned entity damage range.
	 * @param initmindamage Minimum amount of damage that a spawned entity can inflict
	 * @param initmaxdamage Maximum amount of damage that a spawned entity can inflict
	 * @throws IllegalArgumentException if input values are negative
	 */
	public void setEntityDamageRange(final int initmindamage, final int initmaxdamage) throws IllegalArgumentException {
		if (initmindamage < 0) {
			throw new IllegalArgumentException("Enemy Spawner minimum entity damage cannot be set as negative.");
		}
		if (initmaxdamage < 0) {
			throw new IllegalArgumentException("Enemy Spawner maximum entity damage velocity cannot be set as negative.");
		}
		this.mindamage = initmindamage;
		this.maxdamage = initmaxdamage;
	}
	
	/**
	 * Set Weapon cooldown time for spawned entity.
	 * @param cooldown Cooldown time as number of ticks
	 * @throws IllegalArgumentException if input value is negative
	 */
	public void setCoolDownEntityWeapon(final int cooldown) throws IllegalArgumentException {
		if (cooldown < 0) {
			throw new IllegalArgumentException("Enemy Spawner entity weapon's cooldown cannot be set as negative.");
		}
		this.weaponcooldown = cooldown;	
	}

}
