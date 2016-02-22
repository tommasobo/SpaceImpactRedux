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
 
	N, 
	NE,
	E,
	SE,
	S,
	SW,
	W,
	NW;
	
	private final double diagonalvel = 0.7071; //diagonal velocity
	
	public void moveLocation(Location loc, double v) {
		if (this.equals(Direction.N)) {
			loc.setY(loc.getY() - v); 
		} else if (this.equals(Direction.NE)) {
			Direction.N.moveLocation(loc, v * diagonalvel);
			Direction.E.moveLocation(loc, v * diagonalvel);  
		} else if (this.equals(Direction.S)) {
			loc.setY(loc.getY() + v); 
		} else if (this.equals(Direction.SE)) {
			Direction.S.moveLocation(loc, v * diagonalvel); 
			Direction.E.moveLocation(loc, v * diagonalvel);
		} else if (this.equals(Direction.E)) {
			loc.setX(loc.getX() + v); 
		} else if (this.equals(Direction.SW)) {
			Direction.S.moveLocation(loc, v * diagonalvel);
			Direction.W.moveLocation(loc, v * diagonalvel);  
		} else if (this.equals(Direction.W)) {
			loc.setX(loc.getX() - v); 
		} else if (this.equals(Direction.NW)) {
			Direction.N.moveLocation(loc, v * diagonalvel);
			Direction.W.moveLocation(loc, v * diagonalvel);  
		}
	};
}
