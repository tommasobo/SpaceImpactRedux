package spaceimpact.model.spawners;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import spaceimpact.model.entities.Debris;
import spaceimpact.model.entities.EntityType;

/**
 * Debris Spanwer<br>
 * Define the Debris Spawner inside the Level.
 */
public class DebrisSpawner extends Spawner {
	
	private final double velocity;
	
	/**
	 * Debris Spawner Constructor.
	 * <br>
	 * @param delay Delay between each spawn
	 * @param initvelocity Velocity of the debris
	 */
	public DebrisSpawner(final int delay, final double initvelocity) {
		super(EntityType.Debris, delay);
		this.velocity = initvelocity;
	}

	@Override
	public List<Debris> spawn() {
		
		List<Debris> spawnedentities = new ArrayList<>();		
		Random rnd = new Random();	
		
		if (rnd.nextInt(2) == 1) {			
				spawnedentities.add(new Debris(this.generateRandomLocation(), this.velocity));
				this.incrementSpawnCount();			
		}				
		return spawnedentities;
	}
}
