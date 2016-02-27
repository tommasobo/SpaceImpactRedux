package spaceimpact.model.spawners;

import java.util.List;

import spaceimpact.model.Area;
import spaceimpact.model.entities.Entity;
import spaceimpact.model.entities.EntityType;

/**
 * Generic Entity Spawner Interface.
 */
public interface SpawnerInterface {

	/**
	 * Spawn entities.
	 * @return list List of generated Entity
	 */
	List<? extends Entity> spawn();
	
	/**
	 * Update internal spawn countdown.
	 * if countdown equals spawndelay the countdown is set to 0 and the spawner can spawn new entities
	 */
	void update();
	
	/**
	 * Determine if spawner can spawn new entities.
	 * @return boolean True if spawner can spawn.
	 */
	boolean canSpawn();
		
	/**
	 * Determine if spawner can spawn new entities.
	 * @param delay Delay from each spawn
	 * @throws IllegalArgumentException if delay input value is negative
	 */
	void setSpawnDelay(final int delay) throws IllegalArgumentException;
	
	/**
	 * Get count of total entity spawned.
	 * @return entitycount Currently total entities spawned
	 */
	int getSpawnedEntitiesCount();
	
	/**
	 * Set maximum entity spawn count.
	 * @param max Maximum spawn count
	 * @throws IllegalArgumentException if max input value is not greater than zero
	 */
	void setMaxEntitySpawns(final int max) throws IllegalArgumentException;
	
	/**
	 * Set spawned entity type.
	 * @param type Type of entity to spawn
	 * @throws IllegalArgumentException if input type is null
	 */
	void setSpawnedEntityType(final EntityType type) throws IllegalArgumentException;
	
	/**
	 * Set spawned entity area.
	 * @param area Area occupied by the spawned entities
	 * @throws IllegalArgumentException if input area is null
	 */
	void setSpawnedEntityArea(final Area area) throws IllegalArgumentException;
}
