package spaceimpact.model;

import java.util.List;

import spaceimpact.model.entities.Debris;
import spaceimpact.model.entities.Enemy;
import spaceimpact.model.entities.PowerUp;
import spaceimpact.model.spawners.DebrisSpawner;
import spaceimpact.model.spawners.EnemySpawner;
import spaceimpact.model.spawners.PowerUpSpawner;

/**
 * Level of the Game<br>
 * Defines all main variables of the level. 
 * Contains all spawners and define maximum enemy count in the level.
 */
public class Level {
	
	private final int enemycount;
	private final double velocity;	
	private EnemySpawner enemyspawner;
	private DebrisSpawner debrisspawner;
	private PowerUpSpawner powerupspawner;
	
	/**
	 * Level Constructor
	 * @param enemycount The Maximum number of enemy spawn in the level
	 * @param maxenemyperspawn The maximum number of enemy spawn per spawn event
	 * @param enemydelay The delay between each enemy spawn event
	 * @param debrisdelay The delay between each debris spawn event
	 * @param powerupdelay The delay between each powerup spawn event
	 * @param globalvelocity The global default velocity of the game
	 */
	public Level(final int enemycount, final int maxenemyperspawn, final int enemydelay, 
			final int debrisdelay, final int powerupdelay, final double globalvelocity) {
		this.enemycount = enemycount;
		this.velocity = globalvelocity;		
		enemyspawner = new EnemySpawner(this.enemycount, maxenemyperspawn, enemydelay);
		debrisspawner = new DebrisSpawner(debrisdelay, globalvelocity);
		powerupspawner = new PowerUpSpawner(powerupdelay, globalvelocity);
	}
	
	//SPAWNERS GETTERS
		
	/**
	 * Get the level Enemy Spawner
	 * @return enemyspawner The Enemy spawner of the level
	 */
	public EnemySpawner getEnemySpawner() {
		return this.enemyspawner;
	}
	
	/**
	 * Get the level Debris Spawner
	 * @return debrisspawner The Debris spawner of the level
	 */
	public DebrisSpawner getDebrisSpawner() {
		return this.debrisspawner;
	}
	
	/**
	 * Get the level PowerUp Spawner
	 * @return powerupspawner The PowerUp spawner of the level
	 */
	public PowerUpSpawner getPowerUpSpawner() {
		return this.powerupspawner;
	}
	
	/**
	 * Verify if the player has completed the level
	 * @return boolean True if the level is completed
	 */
	public boolean playerWin() {
		return this.enemycount <= this.enemyspawner.getSpawnedEntitiesCount();
	}
	
	/**
	 * Update Internal Spawners
	 */
	public void update() {
		this.enemyspawner.update();
		this.debrisspawner.update();
		this.powerupspawner.update();
	}
		
	/**
	 * Spawn entities and add them to input lists
	 * @param enemylist Current Enemies List
	 * @param debrislist Current Debris List
	 * @param poweruplist Current PowerUps List
	 */
	public void spawn(List<Enemy> enemylist, List<Debris> debrislist, List<PowerUp> poweruplist) {
		if (this.enemyspawner.canSpawn()) {
			enemylist.addAll(this.enemyspawner.spawn());
		}
		if (this.debrisspawner.canSpawn()) {
			debrislist.addAll(this.debrisspawner.spawn());
		}
		if (this.powerupspawner.canSpawn()) {
			poweruplist.addAll(this.powerupspawner.spawn());
		}
	}

	/**
	 * Getter for Global Leve Velocity
	 * @return double As current global level velocity
	 */
	public double getLevelVelocity() {
		return this.velocity;
	}	
}
