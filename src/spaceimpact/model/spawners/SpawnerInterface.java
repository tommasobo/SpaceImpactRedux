package spaceimpact.model.spawners;

import java.util.List;

import spaceimpact.model.Area;
import spaceimpact.model.entities.Entity;
import spaceimpact.model.entities.EntityType;

/**
 * Generic entity spawner Interface
 * @author Davide
 */
public interface SpawnerInterface {

	/**
	 * Spawn entities
	 * @return list List of generated Entity
	 */
	List<Entity> spawn();
	
	/**
	 * Update internal spawn countdown
	 * if countdown equals spawndelay the countdown is set to 0 and the spawner can spawn new entities
	 */
	public void update();
	
	/**
	 * Determine if spawner can spawn new entities
	 * @return boolean True if spawner can spawn.
	 */
	public boolean canSpawn();
		
	/**
	 * Get count of total entity spawned
	 * @return entitycount Currently total entities spawned
	 */
	public int getSpawnedEntitiesCount();
	
	/**
	 * Set maximum entity spawn count
	 * @param max Maximum spawn count
	 */
	public void setMaxEntitySpawns(final int max);
	
	/**
	 * Set spawned entity velocity
	 * @param velocity Velocity of the spawned entities
	 */
	public void setMaxEntityVelocity(final double velocity);
	
	/**
	 * Set spawned entity type
	 * @param type Type of entity to spawn
	 */
	public void setSpawnedEntityType(final EntityType type);
	
	/**
	 * Set spawned entity area
	 * @param area Area occupied by the spawned entities
	 */
	public void setSpawnedEntityArea(final Area area);
	
	/**
	 * Set spawned entity damage
	 * @param damage Amount of damage that the spawned entities can inflict
	 */
	public void setSpawnedEntityDamage(final int damage);
	
	/**
	 * Set Weapon cooldown time for spawned entity
	 * @param cooldown Cooldown time as number of ticks
	 */
	public void setCoolDownEntityWeapon(final int cooldown);
}
