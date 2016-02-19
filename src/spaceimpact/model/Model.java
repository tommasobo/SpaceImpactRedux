package spaceimpact.model;

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
    	player = new Spaceship(100, 0.1); 
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
		if (player == null) {
			throw new IllegalStateException();
		}		
		userinputs.forEach(x -> player.moveOrAttack(x));	
	}

	@Override
	public void updateAll() {
		// TODO Auto-generated method stub
		//aggiorna tutte le entita' in tutte le liste e controlla collisioni (solo se necessario)  
		//se un nemico è morto causa proiettile player allora aumenta gli score del giocatore
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
