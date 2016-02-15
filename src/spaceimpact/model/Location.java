package spaceimpact.model;

/** <i>Location</i> 
 * <p>
 * Point in a cartesian plane with x and y values.
 * 
 * @author Davide
 *
 */
public class Location {
	
	private double x;
	private double y;
	
	/** Costructor with no params
	 * Set location as (0,0)
	 */
	public Location() {		
		this.x = 0;
		this.y = 0;
	}
	
	/** Costructor with params
	 * Set location with input parameters
	 * 
	 * @param x abscissa value
	 * @param y ordinate value
	 */
	public Location(final double x, final double y) {		
		this.x = x;
		this.y = y;
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
