package spaceimpact.model;

import static org.junit.Assert.*;
import java.awt.Rectangle;

public class Test {

	@org.junit.Test
	public void testCollision() {
		
		Entity ship = new Spaceship(100, 0.1, 100);	
		ship.setLocation(new Location(10, 10, new Rectangle(2, 2)));
		
		Entity enemy = new Enemy(100, 0.1, 100);
		enemy.setLocation(new Location(8, 8, new Rectangle(4, 2)));
			
		assertFalse(ship.collideWith(enemy));
		enemy.update();	
		assertTrue(enemy.collideWith(ship));
		ship.update();
		assertFalse(ship.collideWith(enemy));
	}
}
