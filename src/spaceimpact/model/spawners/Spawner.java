package spaceimpact.model.spawners;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import spaceimpact.model.Direction;
import spaceimpact.model.Location;
import spaceimpact.model.entities.Enemy;
import spaceimpact.model.entities.Entity;
import spaceimpact.model.entities.EntityType;

/**
 * Spawner implementation
 * @author Davide
 */
public class Spawner implements SpawnerInterface {

	private int spawnedentitiescount;
	private int maxspawableentities;
	private EntityType typetospawn;
	private double spawnedetitiesvelocity = 0.1;	
	private Rectangle entityarea = null;
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
	public List<Entity> spawn() {
		List<Entity> spawnedentities = new ArrayList<>();
		
		Random rnd = new Random();	
		int tospawn = rnd.nextInt(maxspawableentities);
		
		for(int i = 0; i< tospawn; i++) {	
			if (spawnedentitiescount <= maxspawableentities) {		
				double x = rnd.nextDouble();
				double y = rnd.nextDouble() + rnd.nextDouble();
				
				while (y > (double)16/9) {
					y = rnd.nextDouble() + rnd.nextDouble();
				}
				
				if (x > 0.7d) {
					x -= 0.3d;
				}
				
				Location tmploc = new Location(x, y, entityarea);
				Weapon tmpweapon = new Weapon(typetospawn, tmploc, entitiesdamage);
				Entity tmp = new Enemy(1, spawnedetitiesvelocity, tmploc, Direction.W, 0, tmpweapon);	
				spawnedentities.add(tmp);
				spawnedentitiescount++;
			} else {
				return spawnedentities;
			}
		}				
		return null;
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
	public void setSpawnedEntityArea(final Rectangle area) {
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
