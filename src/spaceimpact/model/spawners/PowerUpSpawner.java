package spaceimpact.model.spawners;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import spaceimpact.model.Location;
import spaceimpact.model.entities.EntityType;
import spaceimpact.model.entities.PowerUp;
import spaceimpact.model.entities.PowerUp.Enhancement;

/**
 * PowerUp Spanwer
 * <br>
 * Define the PowerUp Spawner inside the Level.
 * @author Davide
 */
public class PowerUpSpawner extends Spawner {
	
	private double velocity = 1;
		
	/**
	 * PowerUp Spawner Constructor
	 * <br>
	 * @param delay Delay between each spawn
	 * @param velocity Velocity of the Powerup
	 */
	public PowerUpSpawner(final int delay, final double velocity) {
		super(EntityType.PowerUp, 1, delay);
		this.velocity = velocity;
	}

	@Override
	public List<PowerUp> spawn() {
		
		List<PowerUp> spawnedentities = new ArrayList<>();		
		Random rnd = new Random();	
		
		if (rnd.nextInt(maxperspawn + 1) == 1) {			
				//generate random location
				double x = 1.8d + 0.2d * rnd.nextDouble();
				double y = 0.15d + 0.70d * rnd.nextDouble();												
				Location tmploc = new Location(x, y, area);
				
				//generate powerup effects RANDOMLY
				int rndplus = rnd.nextInt(Enhancement.values().length);
				
				Enhancement tmpplus = Enhancement.AddProjectile;			
				if (rndplus == 0) {
					tmpplus = Enhancement.CoolDownDecreased;
				} else if (rndplus == 1) {
					tmpplus = Enhancement.RestoreShield;
				} else if (rndplus == 2) {
					tmpplus = Enhancement.IncrementDamage;
				} else if (rndplus == 3) {
					tmpplus = Enhancement.IncrementSpeed;
				} else if (rndplus == 4) {
					tmpplus = Enhancement.Heal;
				}
				
				spawnedentities.add(new PowerUp(tmpplus, tmploc, velocity));
				spawncount++;			
		}				
		return spawnedentities;
	}	
}
