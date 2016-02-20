package spaceimpact.model;

/**
 * Enumeration for the 4 possible directions of the entities
 * <br>
 * List of Possible Direction with the relative function:
 * <br>
 * North (y += y*v)
 * South (x -= x*v)
 * East (x += x*v)
 * West (y -= y*v)
 * @author Davide
 */
public enum Direction {
	W, 
	N, 
	E, 
	S;
	
	public void moveLocation(Location loc, double v) {
		if (this.equals(Direction.W)) {
			loc.setX(loc.getX() - loc.getX() * v); 
		} else if (this.equals(Direction.E)) {
			loc.setX(loc.getX() + loc.getX() * v); 
		} else if (this.equals(Direction.N)) {
			loc.setY(loc.getY() + loc.getY() * v); 
		} else if (this.equals(Direction.S)) {
			loc.setY(loc.getY() - loc.getY() * v); 
		}
	};
}
