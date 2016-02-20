package spaceimpact.model.spawners;

import java.util.List;
import spaceimpact.model.entities.Entity;

/**
 * Generic entity spawner Interface
 * @author Davide
 */
public interface SpawnerInterface {

	/**
	 * Spawn entities
	 */
	List<Entity> spawn();
	
}
