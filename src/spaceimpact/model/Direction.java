package spaceimpact.model;

/**
 * Enumeration for the 4 possible directions of the entities
 * <br>
 * List of Possible Direction with the relative function:
 * @author Davide
 */
public enum Direction {
 
	N(0), 
	NE(1),
	E(2),
	SE(3),
	S(4),
	SW(5),
	W(6),
	NW(7);
	
	static private final double diagonalvel = 0.7071; //diagonal velocity
	private int index; //internal index
	
	/**
	 * Constructor
	 * @param index index of the Direction
	 */
	private Direction(final int index) {
		this.index = index;
	}
	
	/**
	 * Moves the input Location in the current direction
	 * @param loc Current location
	 * @param v Current velocity
	 */
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
	
	/**
	 * Get the index of the direction
	 * @return index the index of the direction
	 */
	private int getIndex() {
		return this.index;
	}
	
	/**
	 * Move Direction to Left
	 * @return Direction Return the current direction moved to left
	 */
	public Direction moveLeft() {
		return Direction.values()[(this.getIndex() + 1) % 8];
		
	}
	
	/**
	 * Move Direction to Right
	 * @return Direction Return the current direction moved to right
	 */
	public Direction moveRight() {
		return Direction.values()[(this.getIndex() + 7) % 8];
		
	}
	
	/**
	 * Flip the direction by 180 degrees
	 * @return Direction Return the current direction flipped by 180 degrees
	 */
	public Direction flip() {
		return Direction.values()[(this.getIndex() + 4) % 8];		
	}
}
