package spaceimpact.model;

import java.util.Objects;

public class WeaponImpl implements Weapon {

	private int damage;
	private double projectilesvelocity;
	private Location location;
	
	@Override
	public Projectile shoot(final EntityType parentID) {
		return new Projectile(Objects.requireNonNull(parentID), location, projectilesvelocity, damage);
	}

	@Override
	public void enhance(int damageincrement, double shootingfrequency) {
		this.damage += damageincrement;
		this.projectilesvelocity += shootingfrequency;
	}
}
