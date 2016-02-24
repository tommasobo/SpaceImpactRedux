package spaceimpact.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import spaceimpact.model.entities.Debris;
import spaceimpact.model.entities.Enemy;
import spaceimpact.model.entities.Entity;
import spaceimpact.model.entities.EntityType;
import spaceimpact.model.entities.PowerUp;
import spaceimpact.model.entities.Projectile;
import spaceimpact.model.entities.Spaceship;
import spaceimpact.model.spawners.Weapon;

/**
 * Model Implementation <br>
 * The cartesian plane of the model is defined in such a way: x = from 0 to 16/9
 * y = from 0 to 1 <br>
 * The model represent the game and all of active entities. It controls
 * collisions and call level spawners to add new entities. <br>
 * <i>Fields</i> <b>gamestatus</b> Current game Status <b>framerate</b> Current
 * framerate <b>playerscores</b> Current player scores <b>lvl</b> Current
 * Playing Level
 * 
 * @author Davide
 */
public class Model implements ModelInterface {

	// game variables
	private GameStatus gamestatus = GameStatus.Running;
	private final int framerate;
	private int playerscores = 0;
	private Level lvl = null;

	// entities collections
	static Spaceship player = null;
	List<Enemy> enemylist = null;
	List<Debris> debrislist = null;
	List<Projectile> playerprojectilelist = null;
	List<Projectile> enemiesprojectilelist = null;
	List<Entity> deadentities = null;
	List<PowerUp> poweruplist = null;

	/**
	 * Inizializate all collections and start the level <br>
	 * Level difficulty is defined by the maximum number of enemy spawn
	 * 
	 * @param framerate
	 *            Framerate of the game
	 * @param level
	 *            Level to play
	 * @throws IllegalArgumentException
	 *             If one or more inputs are null
	 */
	public Model(final int framerate, final Level level) throws IllegalArgumentException {

		if ((level == null) || (framerate < 0)) {
			throw new IllegalArgumentException("Model cannot work properly without a level or framerate.");
		}

		// inizializate main variables
		this.framerate = framerate;
		this.gamestatus = GameStatus.Running;
		this.lvl = level;

		// inizializate entities collections
		this.enemylist = new ArrayList<>();
		this.debrislist = new ArrayList<>();
		this.poweruplist = new ArrayList<>();
		this.playerprojectilelist = new ArrayList<>();
		this.enemiesprojectilelist = new ArrayList<>();
		this.deadentities = new ArrayList<>();

		// fill player using level
		if (Model.player == null) {
			final Location tmploc = new Location(0.1, 0.5, new Area(0.125, 0.0972));
			final Weapon tmpweapon = new Weapon(EntityType.Spaceship, Direction.E, 50, 10,
			this.lvl.getLevelVelocity() * 2);
			tmpweapon.setShootedProjectiles(1);
			Model.player = new Spaceship(100, this.lvl.getLevelVelocity() * 1.5, tmploc, Direction.E, 100, tmpweapon);
		}
	}

	/* MAIN METHODS */

	@Override
	public List<Entity> getEntitiesToDraw() {

		// do not update if the game is not running
		if (!this.gamestatus.equals(GameStatus.Running)) {
			System.out.println("The game is finished!");
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
			throw new IllegalStateException("player is NULL!!");
		}

		if (direction.isPresent()) {
			Model.player.move(direction.get());
		}

		if (shoot && Model.player.canShoot()) {
			this.playerprojectilelist.addAll(Model.player.attack());
		}
	}

	@Override
	public void updateAll() {

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

		// verify game status
		if ((this.enemylist.size() <= 0) && this.lvl.playerWin() && this.gamestatus.equals(GameStatus.Running)) {
			this.gamestatus = GameStatus.Won;
		}
	}

	/**
	 * Remove all dead entities from the model
	 */
	private void deadEntityCollector() {
		this.deadentities.forEach(x -> {
			if (x.getID().equals(EntityType.Spaceship)) {
				this.gamestatus = GameStatus.Over;
			} else if (x.getID().equals(EntityType.Enemy)) {
				this.enemylist.remove(x);
			} else if (x.getID().equals(EntityType.Debris)) {
				this.debrislist.remove(x);
			} else if (x.getID().equals(EntityType.PowerUp)) {
				this.poweruplist.remove(x);
			} else if (x.getID().equals(EntityType.Projectile)) {
				final Projectile tmp = (Projectile) x;
				if (tmp.getParentID().equals(EntityType.Spaceship)) {
					this.playerprojectilelist.remove(tmp);
				} else {
					this.enemiesprojectilelist.remove(tmp);
				}
			}
		});
	}

	/**
	 * Control Collisions
	 */
	private void controlCollisions() {

		// player projectiles with enemy
		if ((this.enemylist.size() > 0) && (this.playerprojectilelist.size() > 0)) {
			this.playerprojectilelist.stream().filter(x -> x.toRemove() == false)
					.forEach(x -> this.enemylist.stream().filter(y -> y.toRemove() == false).forEach(y -> {
						if (x.collideWith(y) && !this.deadentities.contains(x)) {
							this.debrislist.add(new Debris(y.getLocation(), 0, 10));
							this.deadentities.add(y);
							this.deadentities.add(x);
							this.playerscores += 10;
						}
					}));
		}

		// enemy projectiles with player
		if (this.enemiesprojectilelist.size() > 0) {
			this.enemiesprojectilelist.stream().filter(x -> x.toRemove() == false).forEach(x -> {
				if (x.collideWith(Model.player)) {
					Model.player.looseLife(x.getDamage());
					if (Model.player.toRemove()) {
						this.debrislist.add(new Debris(Model.player.getLocation(), 0, 10));
						this.gamestatus = GameStatus.Over;
						this.deadentities.add(Model.player);
					}
					this.deadentities.add(x);
				}
			});
		}

		// enemy with player or viceversa
		if ((this.enemylist.size() > 0) && (Model.player.toRemove() == false)) {
			this.enemylist.stream().filter(x -> x.toRemove() == false).forEach(x -> {
				if (x.collideWith(Model.player)) {
					Model.player.looseLife(20);
					x.looseLife(20);
					if (Model.player.toRemove()) {
						this.debrislist.add(new Debris(Model.player.getLocation(), 0, 10));
						this.gamestatus = GameStatus.Over;
						this.deadentities.add(Model.player);
					}
					if (x.toRemove()) {
						this.debrislist.add(new Debris(x.getLocation(), 0, 10));
						this.deadentities.add(x);
					}
				}
			});
		}

		// player with powerup
		if ((this.poweruplist.size() > 0) && (Model.player.toRemove() == false)) {
			this.poweruplist.stream().filter(x -> x.toRemove() == false).forEach(x -> {
				if (x.collideWith(Model.player)) {
					// TOMODIFY!!!!
					// player.getWeapon().enhance(1, 1, 1, 1);
					System.out.println("POWERUP!");
					this.deadentities.add(x);
				}
			});
		}
	}

	/**
	 * Decide if the current enemy can shoot
	 * 
	 * @param enemy
	 *            Enemy entity that could eventually shoot
	 * @return boolean True mean that the enemy can shoot, false that it cannot.
	 */
	private boolean enemyShoot(final Enemy enemy) {

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
			return new Location(0, 0, new Area(0.1, 0.1));
		}
		return Model.player.getLocation();
	}

	@Override
	public int getScores() {
		return this.playerscores;
	}

	@Override
	public GameStatus getGameStatus() {
		return this.gamestatus;
	}
}
