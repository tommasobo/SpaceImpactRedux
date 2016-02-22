package spaceimpact.model;

import java.util.ArrayList;
import spaceimpact.model.entities.*;
import spaceimpact.model.spawners.Spawner;
import spaceimpact.model.spawners.Weapon;

import java.util.List;
import java.util.Optional;
import java.util.Random;

/** 
 * Model Implementation
 * <br>
 * The cartesian plane of the model is defined in such a way:
 *  x = from 0 to 16/9 
 *  y = from 0 to 1
 * 
 * @author Davide
 */
public class Model implements ModelInterface {
	
	//game variables
	GameStatus gamestatus = GameStatus.Running; //boolean to see if the game is running or ended
	int playerscores = 0; //current player scores
	private double globalvelocity = 0; //constant for entities velocity
	int levelmaxenemyspawn = 0; //curent max spawnable enemy in this level
	int framerate = 30; //game framerate
		
	//entities collections
	Spaceship player = null; //nave del giocatore
	List<Entity> enemylist = null; //lista di nemici
	List<Debris> debrislist = null; //lista detriti o elementi visivi, asteroidi e altro
	List<Projectile> playerprojectilelist = null; //lista proiettili giocatore
    List<Projectile> enemiesprojectilelist = null; //lista proiettili nemici    
    List<Entity> deadentities = null; //lista entita' morte da rimuovere
    //List<PowerUp> poweruplist = null; (list di powerup)
    
    //DEBUG
    Spawner spawner = null;
          
    /* CONSTRUCTOR */
    /**
     * Inizializate all collections and start the level
     * <br>
     * Level difficulty is defined by the maximum number of enemy spawn
     * @param framerate Framerate of the game
     * @param maxspawnableenemy Maximum number of spawns
     */
    public Model(final int framerate, final int maxspawnableenemy) {
    	
    	//inizializate main variables
    	this.framerate = framerate;
    	this.globalvelocity = (double)(1 /(double)(4 * framerate));
    	this.levelmaxenemyspawn = maxspawnableenemy;
    	this.gamestatus = GameStatus.Running;
    	
    	//DEBUG (AREA - X: 1280:16/9=90:x (0.125) - Y: 720:1=70:y (0.0972))
    	Location tmploc = new Location(0.1, 0.5, new Area(0.125, 0.0972));
    	Weapon tmpweapon = new Weapon(EntityType.Spaceship, Direction.E, 50, 10, globalvelocity * 1.5);
    	tmpweapon.setShootedProjectiles(3);
    	player = new Spaceship(100, globalvelocity, tmploc, Direction.E, 100, tmpweapon); 
    	
    	//inizializate entities collections
    	enemylist = new ArrayList<>();
    	debrislist = new ArrayList<>();
    	playerprojectilelist = new ArrayList<>();
    	enemiesprojectilelist = new ArrayList<>();
    	deadentities = new ArrayList<>();
    	
    	//set spawner
		spawner = new Spawner(EntityType.Enemy, maxspawnableenemy, 2, 30, new Area(0.125, 0.0972), 10, 50, 30, globalvelocity * 0.70, globalvelocity);
    }
    
    /* MAIN METHODS */
    	
	@Override
	public List<Entity> getEntitiesToDraw() {
		
		//do not update if the game is not running
		if (!this.gamestatus.equals(GameStatus.Running)) {
			return null;
		}
		
		updateAll(); //verify collisions and update positions;
		deadEntityCollector(); //remove dead entities;
		
		List<Entity> entitylist = new ArrayList<>();

		entitylist.addAll(enemylist);
		entitylist.addAll(debrislist);
		entitylist.addAll(playerprojectilelist);
		entitylist.addAll(enemiesprojectilelist);
		
		return entitylist;
	}

	@Override
	public void informInputs(Optional<Direction> direction, boolean shoot) throws IllegalStateException {
		if (player == null) {
			throw new IllegalStateException("player is NULL!!");
		}	
		
		if (direction.isPresent()) {
			player.move(direction.get());
		}
		
		if (shoot && player.canShoot()) { 
			playerprojectilelist.addAll(player.attack());
		}
	}	
	
	@Override
	public void updateAll() {
				
		//update player 
		this.player.update();
		
		//update spawner
		if (spawner != null) {
			this.spawner.update();		
		}
		
		//move all entities
		enemylist.forEach((x) -> { 
			Enemy tmp = (Enemy) x;
			tmp.update(); 		
			if (enemyShoot(tmp)) {
				enemiesprojectilelist.addAll(tmp.attack());
			}		
			//System.out.println(x);
			} );	
		playerprojectilelist.forEach((x) -> { 
			x.update(); 
			//System.out.println(x);
			} );		
		enemiesprojectilelist.forEach((x) -> { 
			x.update(); 
			//System.out.println(x);
			} );	
		debrislist.forEach((x) -> { 
			x.update(); 
			//System.out.println(x);
			} );
		
		//remove useless entities
		if (playerprojectilelist.size() > 0) {
			playerprojectilelist.forEach(x -> {
				if (x.toRemove()) { 
					deadentities.add(x);
				} 
			});
		}
		if (enemiesprojectilelist.size() > 0) {
			enemiesprojectilelist.forEach(x -> {
				if (x.toRemove()) { 
					deadentities.add(x);
				} 
			});
		}
		if (debrislist.size() > 0) {
			debrislist.forEach(x -> {
				if (x.toRemove()) { 
					deadentities.add(x);
				} 
			});
		}
						
		//control collisions
		controlCollisions();
			
		//spawn new entities if possible
		if (spawner != null && spawner.canSpawn()) {
			enemylist.addAll(spawner.spawn());			
		}
				
		
		//verify game status
   		if (enemylist.size() <= 0 && 
   			spawner.getSpawnedEntitiesCount() >= this.levelmaxenemyspawn &&
   			this.gamestatus == GameStatus.Running) {
   			
			this.gamestatus = GameStatus.Won;
		}
	}

	/** 
     * Remove all dead entities from the model
     */
    private void deadEntityCollector() {
    	deadentities.forEach( x -> {
    		if (x.getID().equals(EntityType.Spaceship)) {
    			this.gamestatus = GameStatus.Over;
    		} else if (x.getID().equals(EntityType.Enemy)) {
    			enemylist.remove(x);
    		} else if (x.getID().equals(EntityType.Debris)) {
    			debrislist.remove(x);
    		} else if (x.getID().equals(EntityType.Projectile)) {
    			Projectile tmp = (Projectile) x;
    			if (tmp.getParentID().equals(EntityType.Spaceship)) {
    				playerprojectilelist.remove(tmp);
    			} else {
    				enemiesprojectilelist.remove(tmp);
    			}
    		}
    	});
    }
    
    /**
     * Control Collisions 
     */
    private void controlCollisions() {   	
    	
    	
    	//player projectiles with enemy
		if (enemylist.size() > 0 && playerprojectilelist.size() > 0) {
			playerprojectilelist.stream()
			.filter(x -> x.toRemove() == false)
			.forEach(x -> enemylist.stream()
					.filter(y -> y.toRemove() == false)
					.forEach(y -> {
						if (x.collideWith(y) && !deadentities.contains(x)) { 
							debrislist.add(new Debris(y.getLocation(), 10));
							deadentities.add(y);
							deadentities.add(x);
							playerscores += 10;
						} 
			}));
		}
		
		//enemy projectiles with player
		if (enemiesprojectilelist.size() > 0) {
			enemiesprojectilelist.stream()
			.filter(x -> x.toRemove() == false)
			.forEach(x -> {
				if (x.collideWith(player)) {			
					player.looseLife(x.getDamage());			
					if (player.toRemove()) {
						debrislist.add(new Debris(player.getLocation(), 10));
						this.gamestatus = GameStatus.Over;
						deadentities.add(player);
					}	
					deadentities.add(x);
				} 	
			});
		}    
		
		//enemy with player or viceversa
		if (enemylist.size() > 0 && player.toRemove() == false) {
			enemylist.stream()
			.filter(x -> x.toRemove() == false)
			.forEach(x -> {
				Enemy enemy = (Enemy)x;
				if (enemy.collideWith(player)) {			
					player.looseLife(20);						
					enemy.looseLife(20);
					if (player.toRemove()) {
						debrislist.add(new Debris(player.getLocation(), 10));
						this.gamestatus = GameStatus.Over;
						deadentities.add(player);
					}	
					if (enemy.toRemove()) {
						debrislist.add(new Debris(enemy.getLocation(), 10));
						deadentities.add(enemy);
					}
				} 	
			});
		}				    
    }
    
    /**
     * Decide if the current enemy can shoot
     * @param enemy Enemy entity that could eventually shoot
     * @return boolean True mean that the enemy can shoot, false that it cannot.
     */
    private boolean enemyShoot(Enemy enemy) {
    	
    	if (enemy.canShoot()) {
    		Random rnd = new Random();
    		double tmp = rnd.nextDouble();
    		
    		if (tmp < ((1 / enemylist.size() + 0.2) / this.framerate)) {
    			return true;
    		}
    	}	
    	return false;
    }
	
    /*GETTER FOR PLAYER INFOS */
    
	@Override
	public int getPlayerLife() {
		if (player == null) {
			return 0;
		}
		return player.getRemainingLife();
	}
	
	@Override
	public int getPlayerShield() {
		if (player == null) {
			return 0;
		}
		return player.getRemainingShield();
	}

	@Override
	public Location getPlayerLocation() {
		if (player == null) {
			return new Location(0,0, new Area(0.1,0.1));
		}
		return player.getLocation();
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
