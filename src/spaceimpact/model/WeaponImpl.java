package spaceimpact.model;

public class WeaponImpl implements Weapon {

	private int damage;
	private double projectilesvelocity;
	private Location location;
	
	@Override
	public Projectile shoot() {
		return new Projectile(location, projectilesvelocity, damage);
	}

	@Override
	public void enhance(int damageincrement, double shootingfrequency) {
		this.damage += damageincrement;
		this.projectilesvelocity += shootingfrequency;
	}

}
