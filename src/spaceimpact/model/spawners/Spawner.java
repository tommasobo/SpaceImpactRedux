package spaceimpact.model.spawners;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import spaceimpact.model.Area;
import spaceimpact.model.Direction;
import spaceimpact.model.Location;
import spaceimpact.model.entities.Enemy;
import spaceimpact.model.entities.Entity;
import spaceimpact.model.entities.EntityType;
import spaceimpact.utilities.Pair;

/**
 * Spawner implementation
 * <br>
 * <b>spawncount</b> Spawn Count<br>
 * <b>maxspawn</b> Maximum Spawn Count<br>
 * <b>type</b> Entity Type to Spawn<br>
 * <b>velocity</b> Velocity of the spawned entities<br>
 * <b>area</b> Area occupied by the spawned entities<br>
 * <b>damage</b> Entities Projectiles damage<br>
 * <b>weaponcooldown</b> Cooldown time of the entity weapon<br>
 * 
 * @author Davide
 */
public class Spawner implements SpawnerInterface {

	//il costruttore 1 vuole tipo di unità da spawnare e la quantità (debris o powerup)
	//il costruttore 2 vuole tipo di unità da spawnare, quantità, quantità per tick, danno, velocità, cooldown weapon (enemies)
	//ritorna lista entità o null
	//campi settabili con vari parametri unità
	//quando inizializzato divide il numero massimo di entità spawnabili per ondate e aspetta il completamento di ognuna per spawnare la successiva
	
	//spawn delay
	private int spawndelay = 0;
	private int countdown = 0;
	
	//spawn management
	private int spawncount = 0;
	private int maxperspawn = 0;
	private int maxspawn = 0;
	
	//entity definition
	private EntityType type = null;
	private Area area = null;	
	private int damage = 0;
	private int weaponcooldown = 0;
	private double velocity = 0.1;
	
	//batch
	private List<Pair<Integer,Entity>> batch = null;
	
	/**
	 * Constructor for Generic Spawner
	 * @param type Entity Type to Spawn 
	 * @param max Maximum Spawn Counts
	 * @param maxperspawn Max Concurrent Entity in a Spawn
	 * @param spawndelay Delay between each spawn (ticks)
	 */
	public Spawner(EntityType type, int max, int maxperspawn, int spawndelay) {
		this.type = type;
		this.maxspawn = max;
		this.maxperspawn = maxperspawn;
		this.spawndelay = spawndelay;
		this.spawncount = 0;
		this.countdown = 0;
	}
	
	/**
	 * Constructor for Enemy Spawner
	 * @param type Entity Type to Spawn 
	 * @param max Maximum Spawn Counts
	 * @param maxperspawn Max Concurrent Entity in a Spawn
	 * @param spawndelay Delay between each spawn (ticks)
	 * @param area Spawned Entity Area
	 * @param damage Spawned Entity Damage
	 * @param weaponcooldown Spawned Entity Weapon's CoolDown
	 * @param velocity Spawned Entity Velocity
	 */
	public Spawner(EntityType type, int max, int maxperspawn, int spawndelay, Area area, int damage, int weaponcooldown, double velocity) {
		this(type, max, maxperspawn, spawndelay);
		this.area = area;
		this.damage = damage;
		this.weaponcooldown = damage;
		this.velocity = velocity;
	}
		
	public List<Entity> spawn() {
		List<Entity> spawnedentities = new ArrayList<>();
		
		Random rnd = new Random();	
		int tospawn = rnd.nextInt(maxperspawn - 1) + 1;
		
		for(int i = 0; i < tospawn; i++) {	
			if (spawncount <= maxspawn) {	
				
				//spawn enemies in 900x720 res aka from 0.53 to 16/9			
				double x = 0.53d + (1.7d - 0.53d) * rnd.nextDouble();
				double y = rnd.nextDouble();
							
				Location tmploc = new Location(x, y, area);
				Weapon tmpweapon = new Weapon(type, Direction.W, weaponcooldown, damage, velocity * 1.5);
				Enemy tmp = new Enemy(1, velocity, tmploc, Direction.W, 0, tmpweapon);
				
				spawnedentities.add(tmp);
				spawncount++;
				
			} else {
				return spawnedentities;
			}
		}				
		return spawnedentities;
	}
	
	@Override
	public int getSpawnedEntitiesCount() {
		return this.spawncount;
	}
	
	@Override
	public void update() {
		this.countdown++;
		if (this.countdown >= this.spawndelay) {
			this.countdown = 0;
		}
	}
	
	@Override
	public boolean canSpawn() {
		return countdown == 0;
	}

	@Override
	public void setMaxEntitySpawns(final int max) {
		this.maxspawn = max;
	}
	
	@Override
	public void setMaxEntityVelocity(final double velocity) {
		this.velocity = velocity;
	}
	
	@Override
	public void setSpawnedEntityType(final EntityType type) {
		this.type = type;
	}
	
	@Override
	public void setSpawnedEntityArea(final Area area) {
		this.area = area;
	}
	
	@Override
	public void setSpawnedEntityDamage(final int damage) {
		this.damage = damage;
	}
	
	@Override
	public void setCoolDownEntityWeapon(final int cooldown) {
		this.weaponcooldown = cooldown;
	}
}
