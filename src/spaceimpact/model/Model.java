package spaceimpact.model;

import java.awt.Rectangle;
import java.util.ArrayList;
import spaceimpact.model.entities.*;
import java.util.List;

import spaceimpact.utilities.Input;

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
	
	boolean gameisover = false; //boolean to see if the game is running or ended
	int playerscores = 0; //current player scores
	
	Spaceship player = null; //nave del giocatore
	
	List<Enemy> enemylist = null; //lista di nemici
	List<Debris> debrislist = null; //lista detriti o elementi visivi, asteroidi e altro
	List<Projectile> playerprojectilelist = null; //lista proiettili giocatore
    List<Projectile> enemiesprojectilelist = null; //lista proiettili nemici    
    List<Entity> deadentities = null; //lista entita' morte da rimuovere
    //List<PowerUp> poweruplist = null; (list di powerup)
          
    /* CONSTRUCTOR */
    /**
     * Inizializate all collections
     */
    public Model() {
    	player = new Spaceship(100, 0.1, new Location(0.5,0.5, new Rectangle(10,10)), Direction.N); 
    	enemylist = new ArrayList<>();
    	debrislist = new ArrayList<>();
    	playerprojectilelist = new ArrayList<>();
    	enemiesprojectilelist = new ArrayList<>();
    	deadentities = new ArrayList<>();
    }
    
    /* MAIN METHODS */
    	
	@Override
	public List<Entity> getEntitiesToDraw() {
		
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
	public void informInputs(List<Input> userinputs) throws IllegalStateException {
		if (player == null || userinputs == null) {
			throw new IllegalStateException("player or userinput are NULL!!");
		}		
		userinputs.forEach(x -> { 
			if (x.equals(Input.SPACE)) {
				playerprojectilelist.add(player.attack());
			} else {
				player.move(x);	
			}
		});
	}	
	
	@Override
	public void updateAll() {
		
		//move all entities
		enemylist.forEach((x) -> x.update());
		playerprojectilelist.forEach((x) -> x.update());
		enemiesprojectilelist.forEach((x) -> x.update());	
		debrislist.forEach((x) -> x.update());
		
		//control collisions
		
		//player projectiles with enemy
		playerprojectilelist.forEach(x -> enemylist.forEach(y -> {
			if (x.collideWith(y)) { 
				//spawn debris in the dead location of the enemy
				deadentities.add(y);
				deadentities.add(x);
				playerscores += 10;
			} 
		}));
		
		//enemy projectiles with player
		enemiesprojectilelist.forEach(x -> {
			if (x.collideWith(player)) {			
				player.looseLife(x.getDamage());			
				if (!player.isAlive()) {
					//spawn debris in the dead location of the enemy
					gameisover = true;
					deadentities.add(player);
					deadentities.add(x);
				}				
			} 
		});
	}

	
	/** 
     * Remove all dead entities from the model
     */
    private void deadEntityCollector() {
    	deadentities.forEach( x -> {
    		if (x.getID().equals(EntityType.Spaceship)) {
    			player = null;
    			gameisover = true;
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
    
	/*GETTER FOR PLAYER INFOS */
    
	@Override
	public int getPlayerLife() {
		return player.getRemainingLife();
	}
	
	@Override
	public int getPlayerShield() {
		return player.getRemainingShield();
	}

	@Override
	public Location getPlayerLocation() {
		return player.getLocation();
	}
	
	/** Getter method to get player score
	 * @return amount of remaining shield as integer
	 */
	public int getScores() {
		return this.playerscores;
	}
}
