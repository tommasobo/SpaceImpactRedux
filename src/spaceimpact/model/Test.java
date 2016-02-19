package spaceimpact.model;

import static org.junit.Assert.*;
import spaceimpact.model.entities.*;
import java.awt.Rectangle;

/**
 * JUnit test class for Model classes
 * @author Davide
 */
public class Test {

	Spaceship ship = new Spaceship(100, 0.1, new Location(10, 10, new Rectangle(2, 2)), Direction.N);
	Entity enemy = new Enemy(100, 0.1, new Location(8, 8, new Rectangle(4, 2)), Direction.N);
	
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
		ship.setWeapon(new Weapon(ship.getID(), ship.getLocation(), 5));
	}
}
