package spaceimpact.model;

/** <i>Location</i> 
 * <p>
 * Point in a cartesian plane with x and y values.
 * @author Davide
 */
public class Location {
	
	private double x;
	private double y;
	private Area area;
	
	/** Costructor with one parameters
	 * Create a new Location with the same fields as the input Location
	 * @param loc input location
	 */
	public Location(final Location loc) {	
		this.x = loc.x;
		this.y = loc.y;
		this.area = new Area(loc.area);
	}
		
	/** Costructor with params
	 * Set location with input parameters
	 * @param x abscissa value
	 * @param y ordinate value
	 * @param area Area occupied by the entity
	 */
	public Location(final double x, final double y, final Area area) {		
		this.x = x;
		this.y = y;
		this.area = area;
	}
	
	/** Getter of x coordinate of the Location
	 * @return x abscissa value
	 */
	public double getX(){
		return this.x;
	}
	
	/** Getter of y coordinate of the Location
	 * @return y ordinate value
	 */
	public double getY(){
		return this.y;
	}
	
	/** Getter of the Area of the Location
	 * @return area as an Area
	 */
	public Area getArea(){
		return this.area;
	}
	
	/** Setter of x coordinate of the Location
	 * @param x abscissa value
	 */
	public void setX(final double x){
		this.x = x;
	}
	
	/** Setter of y coordinate of the Location
	 * @param y ordinate value
	 */
	public void setY(final double y){
		this.y = y;
	}
}
