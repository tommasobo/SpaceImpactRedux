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
 * <b>
 * <b>
 * @author Davide
 */
public class Spawner implements SpawnerInterface<Enemy> {

	private int spawnedentitiescount;
	private int maxspawableentities;
	private EntityType typetospawn;
	private double spawnedetitiesvelocity = 0.1;	
	private Area entityarea = null;
	private int entitiesdamage = 0;
	
	/**
	 * Constructor
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
		
		for(int i = 0; i< tospawn; i++) {	
			if (spawnedentitiescount <= maxspawableentities) {	
				
				//spawn enemies in 900x720 res aka from 0.53 to 16/9
				
				double x = 0.53d + (1.7d - 0.53d) * rnd.nextDouble();
				double y = rnd.nextDouble();
							
				Location tmploc = new Location(x, y, entityarea);
				Weapon tmpweapon = new Weapon(typetospawn, entitiesdamage, spawnedetitiesvelocity);
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
	 * Get count of total entity spawned
	 * @return entitycount Currently total entities spawned
	 */
	public int getSpawnedEntities() {
		return this.spawnedentitiescount;
	}

}
