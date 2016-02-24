package spaceimpact.model.entities;

import spaceimpact.model.Direction;
import spaceimpact.model.entities.Debris.DebrisType;
import spaceimpact.utilities.Pair;

/**
 * Enumeration of possible Entity Types <br>
 * List of possibile types:<br>
 * <b>Spaceship</b> As the player Entity<br>
 * <b>Enemy</b> The entities which the player has to destroy<br>
 * <b>Debris</b> The entities spawned in a collision or randomly as asteroids<br>
 * <b>PowerUp</b> The entities with which the player can obtain ehnacements<br>
 * <b>Projectile</b> The entities spawned by a weapon. Can damage other living entities<br>
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
		double rotation = 0;
		if (e instanceof Projectile) {
			s.append("Projectiles/");
			final Projectile p = (Projectile) e;
			boolean ignoreOrizRot = true;
			if (p.getDamage() <= 5) {
				s.append("diagonal-green.png");
				ignoreOrizRot = false;
			} else if (p.getDamage() <= 10) {
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
			} else if (p.getDirection() == Direction.SE) {
				rotation = 45;
			} else if (p.getDirection() == Direction.SW) {
				rotation = 135;
			} else if (p.getDirection() == Direction.NW) {
				rotation = 225;
			} else if (p.getDirection() == Direction.NE) {
				rotation = 315;
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
			final Debris d = (Debris) e;
			s.append("Debris/");
			if (d.getType() == DebrisType.Explosion) {
				s.append("explosion.gif");
			} else if (d.getType() == DebrisType.Hit) {
				s.append("hit.gif");
			} else if (d.getType() == DebrisType.Sparkle) {
				s.append("sparkle.gif");
			} else {
				s.append("asteroid.gif");
			}
		} else if (e instanceof PowerUp) {
			final PowerUp p = (PowerUp) e;
			s.append("Powerups/");
			if (p.getEnhancement() == Enhancement.AddProjectile) {
				s.append("addprojectile.gif");
			} else if (p.getEnhancement() == Enhancement.CoolDownDecreased) {
				s.append("cooldown.gif");
			} else if (p.getEnhancement() == Enhancement.IncrementDamage) {
				s.append("damage.gif");
			} else if (p.getEnhancement() == Enhancement.RestoreShield) {
				s.append("shield.gif");
			} else if (p.getEnhancement() == Enhancement.IncrementSpeed) {
				s.append("speed.gif");
			} else {
				s.append("heal.gif");
			}
		}
		return new Pair<String, Double>(s.toString(), Double.valueOf(rotation));
	}
}
