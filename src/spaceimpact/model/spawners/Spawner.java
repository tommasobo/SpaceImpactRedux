package spaceimpact.model.spawners;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import spaceimpact.model.Area;
import spaceimpact.model.Direction;
import spaceimpact.model.Location;
import spaceimpact.model.entities.Enemy;
import spaceimpact.model.entities.EntityType;

/**
 * Spawner implementation
 * <br>
 * <b>spawnedentitiescount</b> Number of total entities spawned by the spawner
 * <b>maxspawnableentities</b> Number of maximum spawnable entities
 * <b>typetospawn</b> Entity type to spawn
 * <b>spawnedetitiesvelocity</b> Velocity of the spawned entities
 * <b>entityarea</b> Area occupied by the spawned entities
 * <b>entitiesdamage</b> Entities Projectiles damage
 * <b>cooldownentityweapon</b> Cooldown time of the entityweapon
 * 
 * @author Davide
 */
public class Spawner implements SpawnerInterface<Enemy> {

	private int spawnedentitiescount;
	private int maxspawableentities;
	private EntityType typetospawn;
	private double spawnedetitiesvelocity = 0.1;	
	private Area entityarea = null;
	private int entitiesdamage = 0;
	private int cooldownentityweapon = 0;
	
	/**
	 * Constructor (Entity Type, Maximum spawnable entities)
	 * @param type Spawned entity type 
	 * @param max Max spawnable entities count
	 */
	public Spawner(EntityType type, int max) {
		this.spawnedentitiescount = 0;
		this.maxspawableentities = max;
		this.typetospawn = type;
	}
		
	@Override
	public List<Enemy> spawn() {
		List<Enemy> spawnedentities = new ArrayList<>();
		
		Random rnd = new Random();	
		int tospawn = rnd.nextInt(maxspawableentities);
		
		for(int i = 0; i < tospawn / 4; i++) {	
			if (spawnedentitiescount <= maxspawableentities) {	
				
				//la cosa migliore è avere un array che definisce le ondate
				//per ogni ondata è definito il numero di nemici da spawnare e la loro potenza e tipo
				//per evitare di farli spawnare tutti insieme fai un delay tra uno spawn ed un altro
				
				//spawn enemies in 900x720 res aka from 0.53 to 16/9			
				double x = 0.53d + (1.7d - 0.53d) * rnd.nextDouble();
				double y = rnd.nextDouble();
							
				Location tmploc = new Location(x, y, entityarea);
				Weapon tmpweapon = new Weapon(typetospawn, Direction.W, cooldownentityweapon, entitiesdamage, spawnedetitiesvelocity * 1.5);
				Enemy tmp = new Enemy(1, spawnedetitiesvelocity, tmploc, Direction.W, 0, tmpweapon);	
				spawnedentities.add(tmp);
				spawnedentitiescount++;
			} else {
				return spawnedentities;
			}
		}				
		return spawnedentities;
	}
	
	/**
	 * Set maximum entity spawn count
	 * @param max Maximum spawn count
	 */
	public void setMaxEntitySpawns(final int max) {
		this.maxspawableentities = max;
	}
	
	/**
	 * Set spawned entity velocity
	 * @param velocity Velocity of the spawned entities
	 */
	public void setMaxEntityVelocity(final double velocity) {
		this.spawnedetitiesvelocity = velocity;
	}
	
	/**
	 * Set spawned entity type
	 * @param type Type of entity to spawn
	 */
	public void setSpawnedEntityType(final EntityType type) {
		this.typetospawn = type;
	}
	
	/**
	 * Set spawned entity area
	 * @param area Area occupied by the spawned entities
	 */
	public void setSpawnedEntityArea(final Area area) {
		this.entityarea = area;
	}
	
	/**
	 * Set spawned entity damage
	 * @param damage Amount of damage that the spawned entities can inflict
	 */
	public void setSpawnedEntityDamage(final int damage) {
		this.entitiesdamage = damage;
	}
	
	/**
	 * Set Weapon cooldown time for spawned entity
	 * @param cooldown Cooldown time as number of ticks
	 */
	public void setCoolDownEntityWeapon(final int cooldown) {
		this.cooldownentityweapon = cooldown;
	}
	
	/**
	 * Get count of total entity spawned
	 * @return entitycount Currently total entities spawned
	 */
	public int getSpawnedEntities() {
		return this.spawnedentitiescount;
	}
}
