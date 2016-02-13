package spaceimpact.model;

import java.util.List;

/** Model
 * il piano cartesiano del model è così definito:
 *  x = da 0 a 16/9 
 *  y = da 0 a 1
 * 
 * @author Davide
 *
 */
public class Model {

	//Game game = null; (Genera interfaccia e classe game)
	Spaceship player = null; //nave del giocatore
	List<Enemy> enemylist = null; //lista di nemici
	List<Debris> debrislist = null; //lista detriti o elementi visivi, asteroidi e altro
	List<Projectile> playerprojectilelist = null; //lista proiettili giocatore
	List<Projectile> enemiesprojectilelist = null; //lista proiettili nemici	
	List<Entity> deadentities = null; //lista entita' morte da rimuovere
	//List<PowerUp> poweruplist = null; (list di powerup)
		
	public void deadEntityCollector() {
		//pulisce in modo ignorante tutte le entita' morte
	}
	
	public void UpdateAll() {
		//aggiorna tutte le entita' in tutte le liste e controlla collisioni (solo se necessario)		
	}
}
