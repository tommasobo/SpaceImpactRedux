package spaceimpact.model.entities;

import java.util.Arrays;
import java.util.List;

import spaceimpact.utilities.Pair;

/**
 * Enumeration of possible Entity Types <br>
 * List of possible types:<br>
 * <b>Spaceship</b> As the player Entity<br>
 * <b>Enemy</b> The entities which the player has to destroy<br>
 * <b>Debris</b> The entities spawned in a collision or randomly as asteroids<br>
 * <b>PowerUp</b> The entities with which the player can obtain enhancement<br>
 * <b>Projectile</b> The entities spawned by a weapon. Can damage other living entities<br>
 *
 * @author Davide
 */
public enum EntityType {
	
	Spaceship, Enemy, Debris, PowerUp, Projectile;

	private static List<Pair<String, Integer>> projectilesRange = Arrays.asList(new Pair<>("diagonal-green.png", 13),
			new Pair<>("beam_blue.png", 25), new Pair<>("beam_red.png", 35), new Pair<>("fireball.png", 50));

	private static List<Pair<String, Integer>> enemyDmgRange = Arrays.asList(new Pair<>("A", 15), new Pair<>("B", 30),
			new Pair<>("C", 45));

	private static List<Pair<String, Integer>> enemyLifeRange = Arrays.asList(new Pair<>("1.png", 12),
			new Pair<>("2.png", 24), new Pair<>("3.png", 36), new Pair<>("4.png", 48), new Pair<>("5.png", 60));

	/**
	 * Returns data to print the image of the entity.<br>
	 * @param e The given entity.
	 * @return pair A Pair of String, Double. The string is the image URL from 
	 * the "res" folder, the double is the rotation (degrees).
	 */
	public static Pair<String, Double> getImage(final Entity e) {
		final StringBuilder s = new StringBuilder("/Entities/");
		double rotation = 0;
		if (e instanceof Projectile) {
			s.append("Projectiles/");
			s.append(EntityType.textChooser(((Projectile) e).getDamage(), EntityType.projectilesRange));
			rotation = ((Projectile) e).getDirection().getRotation();
		} else if (e instanceof Enemy) {
			s.append("Enemies/");
			s.append(EntityType.textChooser(((Enemy) e).getWeapon().getDamage(), EntityType.enemyDmgRange));
			s.append(EntityType.textChooser(((Enemy) e).getMaximumLife(), EntityType.enemyLifeRange));
		} else if (e instanceof Debris) {
			s.append("Debris/");
			s.append(((Debris) e).getType().getImageName());
		} else if (e instanceof PowerUp) {
			s.append("Powerups/");
			s.append(((PowerUp) e).getEnhancement().getImageName());
		}
		return new Pair<String, Double>(s.toString(), Double.valueOf(rotation));
	}

	/**
	 * Return the filename of the texture to use with the specified entity, current state of the entity can 
	 * @param param as the parameters to search inside the range collection 
	 * @param ranges range of pair that needs to be controlled
	 * @return string as the filename of the texture to use
	 */
	private static String textChooser(final int param, final List<Pair<String, Integer>> ranges) {
		for (final Pair<String, Integer> p : ranges) {
			if (param < p.getSecond()) {
				return p.getFirst();
			}
		}
		return ranges.get(ranges.size() - 1).getFirst();

	}
}
