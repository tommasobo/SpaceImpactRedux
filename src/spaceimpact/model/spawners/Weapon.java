package spaceimpact.model.spawners;

import java.util.ArrayList;
import java.util.List;

import spaceimpact.model.Area;
import spaceimpact.model.Direction;
import spaceimpact.model.Location;
import spaceimpact.model.entities.EntityType;
import spaceimpact.model.entities.Projectile;

/**
 * Weapon.<br>
 * Factory to generate Projectile entities with a defined damage, location, direction<br>
 * <b>damage</b> Projectiles damages<br>
 * <b>projectilesvelocity</b> Projectiles velocity<br>
 * <b>parentID</b> The EntityType of the shooter<br>
 * <b>direction</b> Direction where the projectiles need to be spawned<br>
 * <b>projectilescount</b> Number of shooted projectiles<br>
 * <b>cooldowntime</b> Total time (ticks) of Weapon's cooldown<br>
 * <b>cooldown</b> Current cooldown's countdown<br>
 */
public class Weapon implements WeaponInterface {

	private final EntityType parentID;
	private final Direction direction;
	private double projectilesvelocity;
	private int damage; 
	private int cooldowntime;
	private int cooldown;
	private int projectilescount;
		
	/**
	 * Weapon's Constructor. (Shooter Entity Type, Direction, Cooldown time, Damage, velocity)
	 * @param shooter EntityType of who the owner of the weapon
	 * @param initdirection Direction of the shooted projectiles
	 * @param initdamage value of the damage
	 * @param velocity projectile velocity
	 * @param initcooldowntime total countdown time for cooldown
	 */
	public Weapon(final EntityType shooter, final Direction initdirection, final int initcooldowntime, final int initdamage, final double velocity) {
		this.parentID = shooter;
		this.direction = initdirection;
		this.damage = initdamage;
		this.projectilesvelocity = velocity;
		this.projectilescount = 1;
		this.cooldowntime = initcooldowntime;
		this.cooldown = 0;
	}
	
	@Override
	public List<Projectile> shoot(final Location loc) {
		
		this.cooldown = this.cooldowntime;
		Location newlocarea = new Location(loc);
		newlocarea.setArea(new Area(loc.getArea().getWidth() * 0.5, loc.getArea().getHeight() * 0.5));
		
		List<Projectile> projectiles = new ArrayList<Projectile>();		
		List<Direction> projectilesdir = new ArrayList<Direction>();
				
		projectilesdir.add(this.direction);
		if (this.projectilescount % 2 == 0 || this.projectilescount > 5) {
			projectilesdir.add(this.direction.flip());
		}
		if (this.projectilescount > 2) {
			projectilesdir.add(this.direction.moveLeft());
			projectilesdir.add(this.direction.moveRight());
			if (this.projectilescount > 4) {
				projectilesdir.add(this.direction.moveLeft().flip());
				projectilesdir.add(this.direction.moveRight().flip());
				if (this.projectilescount > 6) {
					projectilesdir.add(this.direction.moveLeft().moveLeft());
					if (this.projectilescount == 8) {
						projectilesdir.add(this.direction.moveRight().moveRight());
					}
				}
			}
		}
	
		projectilesdir.stream().forEach(x -> {
			projectiles.add(new Projectile(this.parentID, new Location(newlocarea), x, this.projectilesvelocity, this.damage));
		});
		
		return projectiles;		
	}

	@Override
	public boolean isReadyToShoot() {
		return this.cooldown == 0;
	}
	
	@Override
	public void coolDown() {
		if (this.cooldown > 0) {
			this.cooldown--;		
		}
	}

	@Override
	public int getDamage() {
		return this.damage;
	}

	@Override
	public void increaseProjectiles() {
		if (this.projectilescount < 8) {
			this.projectilescount++;
		}		
	}

	@Override
	public void increaseDamage(final int increment) throws IllegalArgumentException {
		if (increment < 0) {
			throw new IllegalArgumentException("Weapon's damage cannot be increase by a negative amount.");
		}	
		if (this.damage + increment <= 60) {
			this.damage += increment;
		}
	}

	@Override
	public void decreaseCoolDown(final int decrement) throws IllegalArgumentException {
		if (decrement < 0) {
			throw new IllegalArgumentException("Weapon's damage cannot be increase by a negative amount.");
		}
		if (this.cooldowntime - decrement >= 0) {
			this.cooldowntime -= decrement;
		}
	}

	@Override
	public int getProjectilesCount() {
		return this.projectilescount;
	}	
}
