package spaceimpact.model.spawners;

import java.util.ArrayList;
import java.util.List;

import spaceimpact.model.Area;
import spaceimpact.model.Direction;
import spaceimpact.model.Location;
import spaceimpact.model.entities.EntityType;
import spaceimpact.model.entities.Projectile;

/**
 * Weapon Class
 * Factory to generate Projectile entities with a defined damage, location, direction<br>
 * <b>damage</b> Projectiles damages<br>
 * <b>projectilesvelocity</b> Projectiles velocity<br>
 * <b>parentID</b> The EntityType of the shooter<br>
 * <b>direction</b> Direction where the projectiles need to be spawned<br>
 * <b>projectilescount</b> Number of shooted projectiles<br>
 * <b>cooldowntime</b> Total time (ticks) of Weapon's cooldown<br>
 * <b>cooldown</b> Current cooldown's countdown<br>
 * @author Davide
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
	 * Weapon's Constructor (Shooter Entity Type, Direction, Cooldown time, Damage, velocity)
	 * @param shooter EntityType of who the owner of the weapon
	 * @param direction Direction of the shooted projectiles
	 * @param damage value of the damage
	 * @param velocity projectile velocity
	 * @param cooldowntime total countdown time for cooldown
	 */
	public Weapon(final EntityType shooter, final Direction direction, final int cooldowntime, final int damage, final double velocity) {
		this.parentID = shooter;
		this.direction = direction;
		this.damage = damage;
		this.projectilesvelocity = velocity;
		this.projectilescount = 1;
		this.cooldowntime = cooldowntime;
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
		
		if (projectilescount == 3) {
			projectilesdir.add(this.direction.moveLeft());
			projectilesdir.add(this.direction.moveRight());			
		} else if (projectilescount == 2) {
			projectilesdir.add(this.direction.flip());		
		}
		
		projectilesdir.stream().forEach(x -> {
			projectiles.add(new Projectile(this.parentID, new Location(newlocarea), x, this.projectilesvelocity, this.damage));
		});
		
		return projectiles;		
	}

	@Override
	public void enhance(final int damageincrement, final double shootingvelocity, final int cooldowntime, final int projectilesshooted) {
		this.damage += damageincrement;
		this.projectilesvelocity += shootingvelocity;
		this.cooldowntime -= cooldowntime;
		this.projectilescount = projectilesshooted;
	}

	@Override
	public boolean isReadyToShoot() {
		if (this.cooldown == 0) {
			return true;
		} else {
			return false;	
		}
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
	public void setShootedProjectiles(int count) {
		this.projectilescount = count;		
	}	
}
