package spaceimpact.model.entities;

import java.util.Arrays;
import java.util.List;

import spaceimpact.model.Direction;
import spaceimpact.utilities.Pair;

/**
 * Enumeration of possible Entity Types <br>
 * List of possibile types:<br>
 * Spaceship<br>
 * Enemy<br>
 * Debris<br>
 * PowerUp<br>
 * Projectile<br>
 *
 * @author Davide
 */
public enum EntityType {
	Spaceship, Enemy, Debris, PowerUp, Projectile;

	/**
	 * Returns data to print the image of the entity. <br>
	 *
	 * @param e
	 *            The given entity.
	 * @return pair A Pair of String, Double. The string is the image URL from
	 *         the "res" folder, the double is the rotation (degrees)
	 */
	public static Pair<String, Double> getImage(final Entity e) {
		final StringBuilder s = new StringBuilder("/Entities/");
		final List<Direction> dirOrtho = Arrays.asList(Direction.N, Direction.E, Direction.S, Direction.W);
		double rotation = 0;
		if (e instanceof Projectile) {
			s.append("Projectiles/");
			final Projectile p = (Projectile) e;
			if (dirOrtho.contains(p.getDirection())) {
				boolean ignoreOrizRot = true;
				if (p.getDamage() <= 10) {
					s.append("beam_blue.png");
				} else if (p.getDamage() < 20) {
					s.append("beam_red.png");
				} else {
					s.append("fireball.png");
					ignoreOrizRot = false;
				}
				if (p.getDirection() == Direction.N) {
					rotation = 270;
				} else if (p.getDirection() == Direction.S) {
					rotation = 90;
				} else if (!ignoreOrizRot && (p.getDirection() == Direction.W)) {
					rotation = 180;
				}
			} else {
				s.append("diagonal-green.png");
				if (p.getDirection() == Direction.SE) {
					rotation = 90;
				} else if (p.getDirection() == Direction.SW) {
					rotation = 180;
				} else if (p.getDirection() == Direction.NW) {
					rotation = 270;
				}
			}
		} else if (e instanceof Enemy) {
			s.append("Enemies/");
			final Enemy en = (Enemy) e;
			final int maxlife = en.getMaximumLife();
			final int dmg = en.getWeapon().getDamage();
			if (dmg <= 10) {
				s.append('A');
			} else if (dmg < 20) {
				s.append('B');
			} else {
				s.append('C');
			}
			if (maxlife <= 10) {
				s.append("1.png");
			} else if (maxlife <= 20) {
				s.append("2.png");
			} else if (maxlife <= 30) {
				s.append("3.png");
			} else if (maxlife <= 40) {
				s.append("4.png");
			} else {
				s.append("5.png");
			}
		} else if (e instanceof Debris) {
			s.append("explosion.gif");
		} else {
			// TODO add powerups
		}
		return new Pair<String, Double>(s.toString(), Double.valueOf(rotation));
	}
}
