package spaceimpact.model.spawners;

import spaceimpact.model.Area;
import spaceimpact.model.entities.EntityType;

/**
 * Generic Spawner implementation<br>
 * <b>spawndelay</b> Delay between each Spawn<br>
 * <b>countdown</b> Internal timer for the spawn delay<br>
 * <b>spawncount</b> Spawn Count<br>
 * <b>maxperspawn</b> Max Spawns count per Spawn
 * <b>maxspawn</b> Maximum Spawn Count<br>
 * <b>type</b> Entity Type to Spawn<br>
 * <b>area</b> Area occupied by the spawned entities<br>
 * <b>damage</b> Entities Projectiles damage<br>
 */
public abstract class Spawner implements SpawnerInterface {

	//spawn delay
	protected int spawndelay;
	protected int countdown;
	
	//spawn management
	protected int spawncount;
	protected int maxperspawn;
	protected int maxspawn;
	
	//entity definition
	protected EntityType type;
	protected Area area = new Area(0.125, 0.0972); //default spawn area	

	/**
	 * Constructor for Generic Spawner
	 * @param type Entity Type to Spawn 
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
		return this.countdown == 0;
	}

	@Override
	public int getSpawnedEntitiesCount() {
		return this.spawncount;
	}
	
	@Override
	public void setMaxEntitySpawns(final int max) throws IllegalArgumentException {
		if (max <= 0) {
			throw new IllegalArgumentException("Spawner maximum spawn count must be set with a value greater than zero.");
		}
		this.maxspawn = max;
	}
	
	@Override
	public void setSpawnDelay(final int delay) throws IllegalArgumentException {
		if (delay < 0) {
			throw new IllegalArgumentException("Spawner spawn delay cannot be set as negative.");
		}
		this.spawndelay = delay;
	}
	
	@Override
	public void setSpawnedEntityType(final EntityType type) throws IllegalArgumentException {
		if (type == null) {
			throw new IllegalArgumentException("Spawner spawn entity type cannot be set as undefined.");
		}
		this.type = type;
	}
	
	@Override
	public void setSpawnedEntityArea(final Area area) throws IllegalArgumentException {		
		if (area == null) {
			throw new IllegalArgumentException("Spawner spawn entity area cannot be set as undefined.");
		}
		this.area = area;
	}
	
}
