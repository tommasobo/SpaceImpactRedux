package spaceimpact.model;

import static org.junit.Assert.*;
import spaceimpact.model.entities.*;
import spaceimpact.model.spawners.Weapon;

/**
 * JUnit test class for Model classes
 * @author Davide
 */
public class Test {

	Spaceship ship = new Spaceship(100, 0.1, new Location(0.4, 0.4, new Area(0.159, 0.129)), Direction.E);
	Entity enemy = new Enemy(100, 0.1, new Location(0.2, 0.4, new Area(0.159, 0.129)), Direction.E);
	
	@org.junit.Test
	public void testCollision() {		
		assertFalse(ship.collideWith(enemy));
		enemy.update();	
		assertTrue(enemy.collideWith(ship));
		ship.update();
		assertFalse(ship.collideWith(enemy));
	}
	
	@org.junit.Test
	public void testGeneral() {
		assertFalse(ship.getID().equals(enemy.getID()));
		assertTrue(ship.getID().equals(EntityType.Spaceship));
		ship.setWeapon(new Weapon(ship.getID(), 5, 0.001));
		
		System.out.println(ship.toString());
		System.out.println(enemy.toString());
	}
}
