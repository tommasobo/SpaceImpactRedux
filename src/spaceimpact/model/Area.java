package spaceimpact.model;

/**
 * Area occupied by an entity
 * <br>
 * Defined by 2 doubles: Width and Height.
 * @author Davide
 */
public class Area {
	
	private final double w;
	private final double h;
	
	/**
	 * Constructor that do a protective copy of the input area
	 * @param area Input Area to copy
	 */
	public Area(final Area area) {
		this.w = area.w;
		this.h = area.h;
	}
	
	/**
	 * Constructor to create a new Area
	 * @param width Width of the Area as double
	 * @param height Height of the Area as double
	 */
	public Area(final double width, final double height) {
		this.w = width;
		this.h = height;
	}
	
	/**
	 * Getter for Area's Width 
	 * @return w Width of the Area
	 */
	public double getWidth() {
		return this.w;
	}
	
	/**
	 * Getter for Area's Height 
	 * @return h Height of the Area
	 */
	public double getHeight() {
		return this.h;
	}
	
}
