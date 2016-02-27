package spaceimpact.model.entities;

import spaceimpact.model.Direction;
import spaceimpact.model.Location;

/** 
 * Debris<br>
 * There are four different debris types:<br>
 * <b>Explosion</b> that it's spawned when an entity dies, has a 
 * limited amount of life defined by a countdown.<br>
 * <b>Hit</b> that it's spawned when an entity gets damage, has a 
 * limited amount of life defined by a countdown.<br>
 * <b>Sparkle</b> that it's spawned when the Spaceship get a PowerUp, 
 * has a limited amount of life defined by a countdown.<br>
 * <b>Asteroid</b> that it's spawned randomly and moves across 
 * the gamescreen. He dies as soon as his x location is less than -0.30.<br>
 */
public class Debris implements Entity {
    
    //Movement Limits
    private static final double WLIMIT = -0.30d;
	
	/**
	 * The possible debris types.
	 */
	public enum DebrisType {
	    /**
	     * Explosion Debris.
	     */
		Explosion {
		    /**
             * Return texture filename to the debris type.
             * @return String Texture Filename
             */
			public String getImageName() {
				return "explosion.gif";
			}
		},
		/**
         * Hit Debris, spawned when an entity is damaged but it's still alive.
         */
		Hit {
		    /**
             * Return texture filename to the debris type.
             * @return String Texture Filename
             */
			public String getImageName() {
				return "hit.gif";
			}
		},
		/**
         * Sparkle Debris, spawned when player gets powerup.
         */
		Sparkle {
		    /**
             * Return texture filename to the debris type.
             * @return String Texture Filename
             */
			public String getImageName() {
				return "sparkle.gif";
			}
		},
		/**
         * Asteroid Debris, spawned randomly moves to West with a constant velocity.
         */
		Asteroid {
		    /**
		     * Return texture filename to the debris type.
		     * @return String Texture Filename
		     */
			public String getImageName() {
				return "asteroid.gif";
			}
		};
		
		/**
		 * Return the texture filename referred to the debris type.
		 * @return string As the filename of the image linked to the debris type
		 */
		public abstract String getImageName();
	}

	private final EntityType id = EntityType.Debris; //entity type identifier
	private final DebrisType type; //Debris Type
	private Direction direction = Direction.W; //direction of the entity
	private Location location; //current position
	private double velocity; //current debris velocity
	private int countdown; //current countdown to death
	private boolean removable; //determine if can be removed from gamescreen

	/**
	 * Constructor For Asteroid.
	 * @param startlocation Starting Location of the Asteroid
	 * @param initvelocity Velocity of the Asteroid
	 */
	public Debris(final Location startlocation, final double initvelocity) {
		this.type = DebrisType.Asteroid;
		this.removable = false;
		this.location = new Location(startlocation);
		this.velocity = initvelocity;
		this.countdown = 0;
	}
	
	/**
	 * Constructor for Static Debris.
	 * @param inittype DebrisType of the Debris
	 * @param startlocation Starting Location of the Debris
	 * @param initcountdown Lifetime of the Debris
	 */
	public Debris(final DebrisType inittype, final Location startlocation, final int initcountdown) {
		this.type = inittype;
		this.removable = false;
		this.location = new Location(startlocation);
		this.velocity = 0;
		this.countdown = initcountdown;
	}
	
	/**
	 * Getter for DebrisType.
	 * @return DebrisType As the type of the debris
	 */
	public DebrisType getType() {
		return this.type;
	}
	
	@Override
	public void setRemovable() {
		this.removable = true;	
	}
	
	@Override
	public boolean toRemove() {
		return this.removable;
	}
		
	@Override
	public Location getLocation() {
		return this.location;
	}
	
	@Override
	public void setLocation(final Location newlocation) throws IllegalArgumentException {
		if (newlocation == null) {
			throw new IllegalArgumentException("Cannot set debris location as null");
		}	
		this.location = newlocation;
	}
			
	@Override
	public EntityType getID() {
		return this.id;
	}
	
	@Override
	public void update() throws IllegalStateException {
		if (this.direction == null) {
			throw new IllegalStateException("Cannot update debris if his direction is undefined");
		}
		if (this.location == null) {
			throw new IllegalStateException("Cannot update debris if his location is undefined");
		}
			
		if (!this.type.equals(DebrisType.Asteroid)) {
			this.countdown--;
			if (this.countdown <= 0) {
				this.removable = true;
			} 
		} else {
			if (location.getX() < WLIMIT) {
				this.removable = true;
			} else if (this.velocity > 0) {		
				this.direction.moveLocation(this.location, this.velocity);
			}	
		}
	}
	
	@Override
	public String toString() {
		return "[ " + this.id + " | Type: " + this.type + " -> X: " + this.location.getX() + " | Y: " + this.location.getY() + " ]";		
	}
}
