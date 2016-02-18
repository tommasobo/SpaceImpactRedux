package spaceimpact.model.entities;

import spaceimpact.model.Location;

public class WeaponImpl implements Weapon {

	private final EntityType parentID;
	private int damage;
	private double projectilesvelocity = 0.5;
	private Location location;
	
	public WeaponImpl(final EntityType shooter, final Location location, final int damage) {
		this.parentID = shooter;
		this.location = location;
		this.damage = damage;
	}
	
	@Override
	public Projectile shoot() {
		return new Projectile(this.parentID, location, projectilesvelocity, damage);
	}

	@Override
	public void enhance(int damageincrement, double shootingfrequency) {
		this.damage += damageincrement;
		this.projectilesvelocity += shootingfrequency;
	}
}
