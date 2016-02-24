package spaceimpact.model.spawners;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import spaceimpact.model.Location;
import spaceimpact.model.entities.Debris;
import spaceimpact.model.entities.EntityType;

/**
 * Debris Spanwer
 * <br>
 * Define the Debris Spawner inside the Level.
 * @author Davide
 */
public class DebrisSpawner extends Spawner {
	
	private double velocity = 1;
	
	/**
	 * Debris Spawner Constructor
	 * <br>
	 * @param delay Delay between each spawn
	 * @param velocity Velocity of the debris
	 */
	public DebrisSpawner(final int delay, final double velocity) {
		super(EntityType.Debris, 1, delay);
		this.velocity = velocity;
	}

	@Override
	public List<Debris> spawn() {
		
		List<Debris> spawnedentities = new ArrayList<>();		
		Random rnd = new Random();	
		
		if (rnd.nextInt(maxperspawn + 1) == 1) {			
				//generate random location
				double x = 1.8d + 0.2d * rnd.nextDouble();
				double y = 0.15d + 0.70d * rnd.nextDouble();													
				Location tmploc = new Location(x, y, area);

				spawnedentities.add(new Debris(tmploc, velocity));
				spawncount++;			
		}				
		return spawnedentities;
	}
}
