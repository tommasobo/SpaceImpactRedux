package spaceimpact.model.spawners;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import spaceimpact.model.Location;
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
		super(EntityType.Debris, 1, delay);
		this.velocity = initvelocity;
	}

	@Override
	public List<Debris> spawn() {
		
		List<Debris> spawnedentities = new ArrayList<>();		
		Random rnd = new Random();	
		
		if (rnd.nextInt(this.maxperspawn + 1) == 1) {			
				//generate random location
				double x = 1.8d + 0.2d * rnd.nextDouble();
				double y = 0.15d + 0.70d * rnd.nextDouble();
				Location tmploc = new Location(x, y, this.area);

				spawnedentities.add(new Debris(tmploc, this.velocity));
				this.spawncount++;			
		}				
		return spawnedentities;
	}
}
