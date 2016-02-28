package spaceimpact.model;

/**
 * Enumeration for the 4 possible directions of the entities.<br>
 * List of Possible Direction:<br>
 * <b>North (N)</b><br>
 * <b>NorthEast (NE)</b><br>
 * <b>East (E)</b><br>
 * <b>SouthEast (SE)</b><br>
 * <b>South (S)</b><br>
 * <b>SouthWest (SW)</b><br>
 * <b>West (W)</b><br>
 * <b>NorthWest (NW)</b><br>
 */
public enum Direction {
    
    /**
     * North.
     */
	N(0) {
		@Override
		public void moveLocation(final Location loc, final double v) {
			loc.setY(loc.getY() - v);
		}
	},
    /**
     * North East.
     */
	NE(1) {
		@Override
		public void moveLocation(final Location loc, final double v) {
			Direction.N.moveLocation(loc, v * DIAGONALVELOCITY);
			Direction.E.moveLocation(loc, v * DIAGONALVELOCITY);
		}
	},
    /**
     * East.
     */
	E(2) {
		@Override
		public void moveLocation(final Location loc, final double v) {
			loc.setX(loc.getX() + v);
		}
	},
	 /**
     * South East.
     */
	SE(3) {
		@Override
		public void moveLocation(final Location loc, final double v) {
			Direction.S.moveLocation(loc, v * DIAGONALVELOCITY);
			Direction.E.moveLocation(loc, v * DIAGONALVELOCITY);
		}
	},
	 /**
     * South.
     */
	S(4) {
		@Override
		public void moveLocation(final Location loc, final double v) {
			loc.setY(loc.getY() + v);
		}
	},
    /**
     * South West.
     */
	SW(5) {
		@Override
		public void moveLocation(final Location loc, final double v) {
			Direction.S.moveLocation(loc, v * DIAGONALVELOCITY);
			Direction.W.moveLocation(loc, v * DIAGONALVELOCITY);
		}
	},
	 /**
     * West.
     */
	W(6) {
		@Override
		public void moveLocation(final Location loc, final double v) {
			loc.setX(loc.getX() - v);
		}
	},
	 /**
     * North West.
     */
	NW(7) {
		@Override
		public void moveLocation(final Location loc, final double v) {
			Direction.N.moveLocation(loc, v * DIAGONALVELOCITY);
			Direction.W.moveLocation(loc, v * DIAGONALVELOCITY);
		}
	};
    
    //velocity of diagonal movement
    private static final double DIAGONALVELOCITY = 0.7071;
    //internal index
    private int index; 
    

	/**
	 * Constructor
	 * @param initindex index of the Direction
	 */
	Direction(final int initindex) {
		this.index = initindex;
	}

	/**
	 * Move the Input location (loc) in the specified direction from which this method is called.<br>
	 * If the Direction is oblique, the movement velocity used is DIAGONALVEL.
	 * @param loc Location to move
	 * @param d Velocity of the movement
	 */
	public abstract void moveLocation(final Location loc, final double d);
	
	/**
	 * Return the angle of the direction as degrees value.
	 * @return degrees angle of the direction as degrees value
	 */
	public double getRotation() {
		return ((this.getIndex() + 6) % 8) * 45.0;
	}

	/**
	 * Get the index of the direction
	 * @return index the index of the direction
	 */
	private int getIndex() {
		return this.index;
	}

	/**
	 * Move Direction to Left by 45 degrees.
	 * @return Direction Return the current direction moved to left by 45 degrees
	 */
	public Direction moveLeft() {
		return Direction.values()[(this.getIndex() + 1) % 8];
	}

	/**
	 * Move Direction to Right by 45 degrees.
	 * @return Direction Return the current direction moved to right by 45 degrees
	 */

	public Direction moveRight() {
		return Direction.values()[(this.getIndex() + 7) % 8];
	}

	/**
	 * Flip the direction by 180 degrees.
	 * @return Direction Return the current direction flipped by 180 degrees
	 */
	public Direction flip() {
		return Direction.values()[(this.getIndex() + 4) % 8];
	}
}

