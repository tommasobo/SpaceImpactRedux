package spaceimpact.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import spaceimpact.model.entities.Debris;
import spaceimpact.model.entities.Debris.DebrisType;
import spaceimpact.model.entities.PowerUp.Enhancement;
import spaceimpact.model.entities.Enemy;
import spaceimpact.model.entities.Entity;
import spaceimpact.model.entities.EntityType;
import spaceimpact.model.entities.PowerUp;
import spaceimpact.model.entities.Projectile;
import spaceimpact.model.entities.Spaceship;
import spaceimpact.model.spawners.Weapon;

/**
 * Model Implementation<br>
 * The cartesian plane of the model is defined in such a way:<br> 
 * x = from 0 to 16/9<br>
 * y = from 0 to 1<br>
 * The model represent the game and all of active entities. It controls
 * collisions and call level spawners to add new entities.<br>
 * <i>Fields:</i><br>
 * <b>gamestatus</b> Current game Status<br> 
 * <b>framerate</b> Current framerate<br>
 * <b>playerscores</b> Current player scores<br>
 * <b>lvl</b> Current Playing Level<br>
 */
public class Model implements ModelInterface {

	//DEBUG prints if TRUE
	private final boolean debug = false;
	
	// game variables
	private GameStatus gamestatus = GameStatus.Running;
	private Optional<String> latestpowerup = Optional.empty();
	private final int framerate;
	private int playerscores;
	private Level lvl;
	
	// entities collections
	private static Spaceship player;
	private List<Enemy> enemylist;
	private List<Debris> debrislist;
	private List<Projectile> playerprojectilelist;
	private List<Projectile> enemiesprojectilelist;
	private List<Entity> deadentities;
	private List<PowerUp> poweruplist;
	
	//static entities lifetime
	private final int hitlifetime;
	private final int explosionlifetime;
	private final int sparklelifetime;
	
	/**
	 * Inizializate all collections and start the level.<br>
	 * Level difficulty is defined by the maximum number of enemy spawn 
	 * @param initframerate Frame rate of the game
	 * @param initlevelId The current level identified
	 * @param initdiff Difficulty level of the game
	 * @throws IllegalArgumentException If frame rate is negative or levelId is below 1 or diff is not a valid value.
	 */
	public Model(final int initframerate, final int initlevelId, final int initdiff) throws IllegalArgumentException {

		if (!Arrays.asList(1, 2, 4, 8).contains(initdiff)) {
			throw new IllegalArgumentException("Model cannot work properly if specified difficulty has an unexpected value.");
		}
		if (initlevelId < 0) {
			throw new IllegalArgumentException("Model cannot create a level with a levelid below one.");
		}
		if (initframerate < 0) {
			throw new IllegalArgumentException("Model cannot work properly with a negative framerate.");					
		}

		// inizializate main variables
		this.gamestatus = GameStatus.Running;
		this.latestpowerup = Optional.empty();
		this.framerate = initframerate;
		this.playerscores = 0;	
		this.lvl = LevelCreator.createLevel(initframerate, initlevelId, initdiff);	
		
		// inizializate entities collections
		this.enemylist = new ArrayList<>();
		this.debrislist = new ArrayList<>();
		this.poweruplist = new ArrayList<>();
		this.playerprojectilelist = new ArrayList<>();
		this.enemiesprojectilelist = new ArrayList<>();
		this.deadentities = new ArrayList<>();
		
		//static entities lifetime
		this.hitlifetime = initframerate / 4;
		this.explosionlifetime = initframerate / 2;
		this.sparklelifetime = initframerate * 2;
		
		// fill player using level
		if (Model.player == null || initlevelId == 1) {
			final Location tmploc = new Location(0.1, 0.5, new Area(0.125, 0.0972));
			final Weapon tmpweapon = new Weapon(EntityType.Spaceship, Direction.E, initframerate, 10, this.lvl.getLevelVelocity() * 2);
			Model.player = new Spaceship(100, this.lvl.getLevelVelocity() * 1.8, tmploc, Direction.E, 100, tmpweapon);
		}
	}

	/* MAIN METHODS */

	@Override
	public List<Entity> getEntitiesToDraw() {

		// do not update if the game is not running
		if (!this.gamestatus.equals(GameStatus.Running)) {
			printDBG("The game is not running. No mode entities to draw.");
			return new ArrayList<>();
		}

		this.updateAll(); // verify collisions and update positions;
		this.deadEntityCollector(); // remove dead entities;

		final List<Entity> entitylist = new ArrayList<>();

		entitylist.addAll(this.enemylist);
		entitylist.addAll(this.debrislist);
		entitylist.addAll(this.poweruplist);
		entitylist.addAll(this.playerprojectilelist);
		entitylist.addAll(this.enemiesprojectilelist);

		return entitylist;
	}

	@Override
	public void informInputs(final Optional<Direction> direction, final boolean shoot) throws IllegalStateException {
		if (Model.player == null) {
			throw new IllegalStateException("Player cannot receive inputs because it's null.");
		}

		if (direction.isPresent()) {
			this.printDBG("Player change direction: " + direction.get().toString());
			Model.player.move(direction.get());	
			printDBG(Model.player.toString());
		}

		if (shoot && Model.player.canShoot()) {
			this.printDBG("Player shoot.");
			this.playerprojectilelist.addAll(Model.player.attack());
			this.printDBG(this.playerprojectilelist.get(0).toString());
		}
	}

	@Override
	public void updateAll() throws IllegalStateException {

		if (Model.player == null) {
			throw new IllegalStateException("Model cannot update player if it's null.");
		}
		if (this.lvl == null) {
			throw new IllegalStateException("Model cannot update level if it's null.");
		}
		
		// update player
		Model.player.update();

		// update spawners
		this.lvl.update();

		// move all entities
		this.enemylist.forEach((x) -> {
			x.update();
			if (this.enemyShoot(x)) {
				this.enemiesprojectilelist.addAll(x.attack());
			}
		});
		
		this.playerprojectilelist.forEach(x -> x.update());
		this.enemiesprojectilelist.forEach(x -> x.update());
		this.debrislist.forEach(x -> x.update());
		this.poweruplist.forEach(x -> x.update());

		// remove useless entities
		if (this.playerprojectilelist.size() > 0) {
			this.playerprojectilelist.forEach(x -> {
				if (x.toRemove()) {
					this.deadentities.add(x);
				}
			});
		}
		if (this.enemiesprojectilelist.size() > 0) {
			this.enemiesprojectilelist.forEach(x -> {
				if (x.toRemove()) {
					this.deadentities.add(x);
				}
			});
		}
		if (this.debrislist.size() > 0) {
			this.debrislist.forEach(x -> {
				if (x.toRemove()) {
					this.deadentities.add(x);
				}
			});
		}
		if (this.poweruplist.size() > 0) {
			this.poweruplist.forEach(x -> {
				if (x.toRemove()) {
					this.deadentities.add(x);
				}
			});
		}

		// control collisions
		this.controlCollisions();

		// spawn new entities if possible
		this.lvl.spawn(this.enemylist, this.debrislist, this.poweruplist);
		
		printDBG("No. Enemy: " + this.enemylist.size() + " | Debris: " + this.debrislist.size() 
		+ " | PowerUp: " + this.poweruplist.size() +  " | Proj P: " + this.playerprojectilelist.size() 
		+ " | Proj E: " + this.enemiesprojectilelist.size() + " | Dead: " + this.deadentities.size());

		// verify game status
		if ((this.enemylist.size() <= 0) && this.lvl.playerWin() && this.gamestatus.equals(GameStatus.Running)) {
			this.gamestatus = GameStatus.Won;
		}
	}

	/**
	 * Remove all dead entities from the model
	 * @throws IllegalStateException If the model's deadentities collection is null
	 */
	private void deadEntityCollector() throws IllegalStateException {
		
		if (this.deadentities == null) {
			throw new IllegalStateException("Model cannot clean dead entities because the list is null.");
		}
		
		this.deadentities.forEach(x -> {
			if (x.getID().equals(EntityType.Spaceship)) {
				this.gamestatus = GameStatus.Over;
				this.printDBG("Cleaning: " + x.toString());
			} else if (x.getID().equals(EntityType.Enemy)) {
				this.enemylist.remove(x);
				this.printDBG("Cleaning: " + x.toString());
			} else if (x.getID().equals(EntityType.Debris)) {
				this.debrislist.remove(x);
				this.printDBG("Cleaning: " + x.toString());
			} else if (x.getID().equals(EntityType.PowerUp)) {
				this.printDBG("Cleaning: " + x.toString());
				this.poweruplist.remove(x);
			} else if (x.getID().equals(EntityType.Projectile)) {
				final Projectile tmp = (Projectile) x;
				this.printDBG("Cleaning: " + x.toString());
				if (tmp.getParentID().equals(EntityType.Spaceship)) {
					this.playerprojectilelist.remove(tmp);
				} else {
					this.enemiesprojectilelist.remove(tmp);
				}
			}
		});
		
		//clean dead entites list
		this.deadentities = new ArrayList<>();
	}

	/**
	 * Control Collisions between player, projectiles, enemies and powerups
	 */
	private void controlCollisions() {

		//new debris list to avoid concurrent modification in foreach
		List<Debris> newdebris = new ArrayList<Debris>();
		
		//player projectiles with enemy
		if ((this.enemylist.size() > 0) && (this.playerprojectilelist.size() > 0)) {
			this.playerprojectilelist.stream()
			.filter(x -> !x.toRemove())
			.forEach(x -> this.enemylist.stream()
					.filter(y -> !y.toRemove())
					.forEach(y -> {
						if (x.collideWith(y) && !this.deadentities.contains(x)) {
							y.looseLife(x.getDamage());
							x.setRemovable();
							if (y.toRemove()) { //KILL
								newdebris.add(new Debris(DebrisType.Explosion, y.getLocation(), this.explosionlifetime));
								this.deadentities.add(y);
								this.playerscores += 10;
								this.printDBG("Player kill enemy: " + y.toString());
							} else { //HIT
								newdebris.add(new Debris(DebrisType.Hit, y.getLocation(), this.hitlifetime));
								this.printDBG("Player hit enemy: " + y.toString());
							}
							this.deadentities.add(x);
					}
			}));
		}

		//enemy projectiles with player
		if (this.enemiesprojectilelist.size() > 0) {
			this.enemiesprojectilelist.stream()
			.filter(x -> !x.toRemove())
			.forEach(x -> {
				if (x.collideWith(Model.player)) {
					Model.player.looseLife(x.getDamage());
					x.setRemovable();
					if (Model.player.toRemove()) { //KILL
						newdebris.add(new Debris(DebrisType.Explosion, Model.player.getLocation(), this.explosionlifetime));
						this.gamestatus = GameStatus.Over;
						this.deadentities.add(Model.player);
						this.printDBG("Player is dead!");
					} else { //HIT
						newdebris.add(new Debris(DebrisType.Hit, Model.player.getLocation(), this.hitlifetime));
						this.printDBG("Player is hit!");
					}
					this.deadentities.add(x);
				}
			});
		}

		//enemy with player or viceversa
		if ((this.enemylist.size() > 0) && !Model.player.toRemove()) {
			this.enemylist.stream()
			.filter(x -> !x.toRemove())
			.forEach(x -> {
				if (x.collideWith(Model.player)) {
					Model.player.looseLife(20);
					x.looseLife(20);
					if (Model.player.toRemove()) {
						newdebris.add(new Debris(DebrisType.Explosion, Model.player.getLocation(), this.explosionlifetime));
						this.gamestatus = GameStatus.Over;
						this.deadentities.add(Model.player);
						this.printDBG("Player is dead!");
					} else {
						newdebris.add(new Debris(DebrisType.Hit, Model.player.getLocation(), this.hitlifetime));
						this.printDBG("Player has collided with an enemy!");
					}
					if (x.toRemove()) {
						newdebris.add(new Debris(DebrisType.Explosion, x.getLocation(), this.explosionlifetime));
						this.deadentities.add(x);
						this.printDBG("Enemy has collided with the player and he's dead!");
					} else {
						newdebris.add(new Debris(DebrisType.Hit, x.getLocation(), this.hitlifetime));
						this.printDBG("Enemy has collided with the player!");
					}
				}
			});
		}
		
		//player projectiles with asteroids
		if ((this.debrislist.size() > 0) && (this.playerprojectilelist.size() > 0)) {
			this.playerprojectilelist.stream()
			.filter(x -> !x.toRemove())
			.forEach(x -> this.debrislist.stream()
					.filter(y -> !y.toRemove())
					.filter(y -> y.getType().equals(DebrisType.Asteroid))
					.forEach(y -> {
						if (x.collideWith(y) && !this.deadentities.contains(x) && !this.deadentities.contains(y)) {							
							x.setRemovable();
							y.setRemovable();
							newdebris.add(new Debris(DebrisType.Explosion, x.getLocation(), this.explosionlifetime));
							this.deadentities.add(x);
							this.deadentities.add(y);							
					}
			}));
		}

		//player with powerup
		if ((this.poweruplist.size() > 0) && !Model.player.toRemove()) {
			this.poweruplist.stream()
			.filter(x -> !x.toRemove())
			.forEach(x -> {
				if (x.collideWith(Model.player)) {			
					x.applyEnhancement(Model.player);
					x.setRemovable();
					newdebris.add(new Debris(DebrisType.Sparkle, Model.player.getLocation(), this.sparklelifetime));
					this.latestpowerup = Optional.of(x.getEnhancement().getString());
					this.printDBG("Player get PowerUp: " + x.toString());						
					this.deadentities.add(x);
				}
			});
		}
				
		//player with asteroids
		if ((this.debrislist.size() > 0) && !Model.player.toRemove()) {
			this.debrislist.stream()
			.filter(x -> !x.toRemove())
			.filter(x -> x.getType().equals(DebrisType.Asteroid))
			.forEach(x -> {
				if (x.collideWith(Model.player)) {
					Model.player.looseLife(10);
					x.setRemovable();
					if (Model.player.toRemove()) { //KILL
						newdebris.add(new Debris(DebrisType.Explosion, Model.player.getLocation(), this.explosionlifetime));
						this.gamestatus = GameStatus.Over;
						this.deadentities.add(Model.player);
						this.printDBG("Player is dead!");
					} else { //HIT
						newdebris.add(new Debris(DebrisType.Hit, Model.player.getLocation(), this.hitlifetime));
						this.printDBG("Player is hit!");
					}
					this.deadentities.add(x);
				}
			});
		}	
				
		//add new spawned debris to the model list
		this.debrislist.addAll(newdebris);
	}

	/**
	 * Decide if the current enemy can shoot
	 * @param enemy Enemy entity that could eventually shoot
	 * @return boolean True mean that the enemy can shoot, false that it cannot.
	 * @throws IllegalArgumentException If the enemy is null
	 */
	private boolean enemyShoot(final Enemy enemy) throws IllegalArgumentException {

		if (enemy == null) {
			throw new IllegalArgumentException("Enemy cannot shoot because it's null.");
		}
		
		if (enemy.canShoot()) {
			final Random rnd = new Random();
			final double tmp = rnd.nextDouble();

			if (tmp < (((1 / this.enemylist.size()) + 0.2) / this.framerate)) {
				return true;
			}
		}
		return false;
	}

	/* GETTER FOR PLAYER INFOS */

	@Override
	public int getPlayerLife() {
		if (Model.player == null) {
			return 0;
		}
		return Model.player.getRemainingLife();
	}

	@Override
	public int getPlayerShield() {
		if (Model.player == null) {
			return 0;
		}
		return Model.player.getRemainingShield();
	}

	@Override
	public Location getPlayerLocation() {
		if (Model.player == null) {
			return new Location(0.5, 0.5, new Area(0.1, 0.1));
		}
		return Model.player.getLocation();
	}

	@Override
	public int getScores() {
		int tmpvalue = this.playerscores;
		this.playerscores = 0;
		return tmpvalue;
	}

	@Override
	public GameStatus getGameStatus() {
		return this.gamestatus;
	}
	
	@Override
	public Optional<String> getLatestPowerUp() {
		Optional<String> tmp = Optional.empty();
		if (this.latestpowerup.isPresent()) {			
			if (Model.player.getWeapon().getProjectilesCount() >= 8 
			        && this.latestpowerup.get().contains(Enhancement.AddProjectile.getString())) {
				tmp = Optional.of("Weapon maxed out!");
			} else if ((Model.player.getVelocity() / this.lvl.getLevelVelocity()) > 3d 
			        && this.latestpowerup.get().contains(Enhancement.IncrementSpeed.getString())) {
			    tmp = Optional.of("Engines maxed out!");
			} else if ((Model.player.getWeapon().getDamage()) > 60 
			        && this.latestpowerup.get().contains(Enhancement.IncrementDamage.getString())) {
			    tmp = Optional.of("Damage maxed out!");
			} else {
				tmp = this.latestpowerup;
			}
			this.latestpowerup = Optional.empty();
		}
		return tmp;
	}
	
	/*UTILITIES*/
	
	/**
	 * Function to print information during DEBUG
	 * @param str Message to print
	 */
	private void printDBG(final String str) {
		if (debug) { 
		    System.out.println(str);
		}
	}
}
