package spaceimpact.model;

/**
 * Level Creator<br>
 * Create a new level at request. Implemeted with Factory Pattern.
 */
public class LevelCreator {

	/**
	 * Static method that creates a new Level (the difficulty depends on the number
	 * of levels completed before). In increases the levelId count at each creation 
	 * @param fps Framerate of the level.
	 * @param levelId The number of level (i.e. 1 for first level, 2 for second...)
	 * @param diff Difficulty of the level.
	 * @return level The created level.
	 */
	static public Level createLevel(final int fps, final int levelId, final int diff) {

		final int totalEnemiesToSpawn = 5 * (2 * levelId + diff);
		final int maxEnemyPerSpawn = diff + (levelId - 1) / 2;
		final int enemyDelay = (int) ((9 - 0.36666 * Math.min(10, levelId)) * fps / diff);
		final int debrisDelay = (int) ((diff * (levelId + 1) * 0.02 + 2) * fps);
		final int powerupDelay = (int) ((7.5 + diff * levelId) * fps);
		final double tmpvel = (0.135 + 0.015 * levelId) / fps;
		
		final Level tmp = new Level(totalEnemiesToSpawn, maxEnemyPerSpawn, enemyDelay, debrisDelay, powerupDelay, tmpvel);
		tmp.getEnemySpawner().setSpawnedEntityArea(new Area(0.125, 0.0972));
		tmp.getDebrisSpawner().setSpawnedEntityArea(new Area(0.125, 0.0972));
		tmp.getPowerUpSpawner().setSpawnedEntityArea(new Area(0.125, 0.0972));
		tmp.getEnemySpawner().setCoolDownEntityWeapon((int) ((1 - 0.1 * Math.min(5, levelId / 2)) * fps));
		tmp.getEnemySpawner().setEntityDamageRange(5 * levelId, 10 * levelId);
		tmp.getEnemySpawner().setEntityLifeRange(5 * levelId, 5 + 10 * levelId);
		tmp.getEnemySpawner().setEntityVelocityRange(tmpvel, tmpvel * 1.5);
		
		return tmp;
	}
}
