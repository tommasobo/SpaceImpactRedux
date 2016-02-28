package spaceimpact.model.spawners;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import spaceimpact.model.Direction;
import spaceimpact.model.entities.Enemy;
import spaceimpact.model.entities.EntityType;

/**
 * Enemy Spanwer<br>
 * Define the Enemy Spawner inside the Level.
 */
public class EnemySpawner extends Spawner {
    
    private static final double PROJECTILEMULTIPLIER = 1.5;
    private static final int DEFAULTCOOLDOWN = 30;
	
    //spawner limits
    private int maxperspawn;
    private int maxspawn;

    //enemy spawn variables
	private int minlife = 1;
	private int maxlife = 1;
	private int mindamage = 1;
	private int maxdamage = 10;
	private double minvel = 1;
	private double maxvel = 1;
	private int weaponcooldown = DEFAULTCOOLDOWN;
		
	/* CONSTRUCTORS */
	
    /**
     * Constructor for EnemySpawner.
     * @param spawnlimit Maximum Spawn Counts
     * @param initmaxperspawn Max Concurrent Entity in a Spawn
     * @param initspawndelay Delay between each spawn (ticks)
     */
    public EnemySpawner(final int spawnlimit, final int initmaxperspawn, final int initspawndelay) {
        super(EntityType.Enemy, initspawndelay);
        this.maxspawn = spawnlimit;
        this.maxperspawn = initmaxperspawn;
    }
	
	/**
	 * Complete Constructor for EnemySpawner.
	 * @param max Maximum Spawn Counts
	 * @param initmaxperspawn Max Concurrent Entity in a Spawn
	 * @param spawndelay Delay between each spawn (ticks)
	 * @param initminlife Spanwed Entity Mimimum Life
	 * @param initmaxlife Spanwed Entity Maximum Life
	 * @param initminvel Spanwed Entity Mimimum Velocity
	 * @param initmaxvel Spanwed Entity Maximum Velocity
	 * @param initmindamage Spawned Entity Mimimum Damage
	 * @param initmaxdamage Spawned Entity Maximum Damage
	 * @param initweaponcooldown Spawned Entity Weapon's CoolDown
	 */
	public EnemySpawner(final int max, final int initmaxperspawn, final int spawndelay, final int initminlife, 
	        final int initmaxlife, final double initminvel, final double initmaxvel, final int initmindamage, 
	        final int initmaxdamage, final int initweaponcooldown) {
		this(max, initmaxperspawn, spawndelay);
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
			if (this.getSpawnedEntitiesCount() < this.maxspawn) {			
				//generate random life in range
				int newlife = this.minlife + rnd.nextInt(this.maxlife - this.minlife + 1);
				//generate random velocity
				double vel = this.minvel + (this.maxvel - this.minvel) * rnd.nextDouble();			
				//random damage in range
				int newdamage = this.mindamage + rnd.nextInt(this.maxdamage - this.mindamage + 1);	
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
				//random weapon
				Weapon tmpweapon = new Weapon(this.getType(), dir, this.weaponcooldown, newdamage, this.maxvel * PROJECTILEMULTIPLIER);
				
				spawnedentities.add(new Enemy(newlife, vel, this.generateRandomLocation(), dir, tmpweapon));
				this.incrementSpawnCount();			
			} else {
				return spawnedentities;
			}
		}				
		return spawnedentities;
	}
	
	/**
     * Set maximum entity spawn count.
     * @param max Maximum spawn count
     * @throws IllegalArgumentException if max input value is not greater than zero
     */
    public void setMaxEntitySpawns(final int max) throws IllegalArgumentException {
        if (max <= 0) {
            throw new IllegalArgumentException("Spawner maximum spawn count must be set with a value greater than zero.");
        }
        this.maxspawn = max;
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
