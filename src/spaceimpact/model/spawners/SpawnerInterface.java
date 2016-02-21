package spaceimpact.model.spawners;

import java.util.List;

/**
 * Generic entity spawner Interface
 * @author Davide
 */
public interface SpawnerInterface<T> {

	/**
	 * Spawn entities
	 */
	List<T> spawn();
	
}
