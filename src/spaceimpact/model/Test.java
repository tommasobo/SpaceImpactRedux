package spaceimpact.model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Assert;
import java.util.List;

import spaceimpact.model.entities.Enemy;
import spaceimpact.model.entities.EntityType;
import spaceimpact.model.entities.Projectile;
import spaceimpact.model.entities.Spaceship;
import spaceimpact.model.spawners.Weapon;

/**
 * JUnit test for Model.
 */
public class Test {
	private Weapon tmpweapon = new Weapon(EntityType.Spaceship, Direction.E, 10, 5, 1);
	private Spaceship ship = new Spaceship(100, 0.65, new Location(0.2, 0.4, new Area(0.125, 0.0972)), Direction.E, 100, tmpweapon);
	private Enemy enemy = new Enemy(100, 0.1, new Location(0.8, 0.4, new Area(0.125, 0.0972)), Direction.E);
	
	/**
	 * JUnit Test for general entities.
	 */
	@org.junit.Test
	public void testEntities() {
		enemy.acquireLife(10);
		assertTrue(enemy.getRemainingLife() == 100);
		ship.acquireShield(50);
		assertFalse(ship.getRemainingShield() == 150);
		ship.acquireLife(50);
		assertTrue(ship.getRemainingLife() == 100);
		assertFalse(ship.collideWith(enemy));
	}
	
	/**
     * JUnit Test for general methods in model.
     */
	@org.junit.Test
	public void testGeneral() {
		assertFalse(ship.getID().equals(enemy.getID()));
		assertTrue(ship.getID().equals(EntityType.Spaceship));
		ship.setWeapon(new Weapon(ship.getID(), Direction.E, 10, 5, 1));
		ship.looseLife(5);
		assertTrue(ship.getRemainingLife() == 100);
		assertTrue(ship.getRemainingShield() == 95);
		assertFalse(ship.toRemove());
		
		System.out.println(ship.toString());
		System.out.println(enemy.toString());
	}
	
	/**
     * JUnit Test for spaceship shoot and collisions.
     */
	@org.junit.Test
	public void testShoot() {
		ship.setLocation(new Location(0.1, 0.4, new Area(0.125, 0.0972)));
		enemy.setLocation(new Location(1.6, 0.4, new Area(0.125, 0.0972)));
		
		System.out.println(ship.toString());
		System.out.println(enemy.toString());
		
		try {
		ship.attack();
		} catch (IllegalStateException ex) {
		} catch (Exception ex) {
		    Assert.fail("Ship must throw IllegalStateException if weapon is not set.");
		}
		
		ship.setWeapon(new Weapon(ship.getID(), Direction.E, 10, 5, 0.3));
		
		List<Projectile> proj = ship.attack();
		proj.get(0).update();
		System.out.println(proj.toString());
		proj.get(0).update();
		System.out.println(proj.toString());
		proj.get(0).update();
		System.out.println(proj.toString());
		proj.get(0).update();
		System.out.println(proj.toString());
		proj.get(0).update();
		System.out.println(proj.toString());
		assertTrue(enemy.collideWith(proj.get(0)));
		enemy.looseLife(proj.get(0).getDamage());
		assertFalse(enemy.getRemainingLife() == 100);
		assertFalse(enemy.toRemove());
	}
}
