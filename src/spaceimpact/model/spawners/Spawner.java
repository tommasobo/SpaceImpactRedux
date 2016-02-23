package spaceimpact.model.spawners;

import spaceimpact.model.Area;
import spaceimpact.model.entities.EntityType;

/**
 * Spawner implementation
 * <br>
 * <b>spawndelay</b> Delay between each Spawn<br>
 * <b>countdown</b> Internal timer for the spawn delay<br>
 * <b>spawncount</b> Spawn Count<br>
 * <b>maxperspawn</b> Max Spawns count per Spawn
 * <b>maxspawn</b> Maximum Spawn Count<br>
 * <b>type</b> Entity Type to Spawn<br>
 * <b>area</b> Area occupied by the spawned entities<br>
 * <b>damage</b> Entities Projectiles damage<br>
 * 
 * @author Davide
 */
public abstract class Spawner implements SpawnerInterface {

	//spawn delay
	protected int spawndelay = 0;
	protected int countdown = 0;
	
	//spawn management
	protected int spawncount = 0;
	protected int maxperspawn = 0;
	protected int maxspawn = 0;
	
	//entity definition
	protected EntityType type = null;
	protected Area area = null;		

	/**
	 * Constructor for Generic Spawner
	 * @param type Entity Type to Spawn 
	 * @param max Maximum Spawn Counts
	 * @param maxperspawn Max Concurrent Entity in a Spawn
	 * @param spawndelay Delay between each spawn (ticks)
	 */
	public Spawner(EntityType type, int maxperspawn, int spawndelay) {
		this.type = type;
		this.maxperspawn = maxperspawn;
		this.spawndelay = spawndelay;
		this.spawncount = 0;
		this.countdown = 0;
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
	public int getSpawnedEntitiesCount() {
		return this.spawncount;
	}
	
	@Override
	public void setMaxEntitySpawns(final int max) {
		this.maxspawn = max;
	}
	
	@Override
	public void setSpawnDelay(final int delay) {
		this.spawndelay = delay;
	}
	
	@Override
	public void setSpawnedEntityType(final EntityType type) {
		this.type = type;
	}
	
	@Override
	public void setSpawnedEntityArea(final Area area) {		
		this.area = area;
	}
	
}
