package spaceimpact.model;

/**
 * Enumeration to define the status of the Model.
 * @author Davide
 */
public enum GameStatus {
    /**
     * Player win if maximum enemy spanws is reached and there are no more living enemy.
     */
	Won,
	/**
     * Player loose if his life reach 0 or below.
     */
	Over,
	/**
	 * The game is running.
	 */
	Running;
}
