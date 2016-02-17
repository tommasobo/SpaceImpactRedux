package spaceimpact.controller;

/**
 *A "clock" for the game. As an animation, a game is composed of "frames": 
 *every second the GameLoop will generate a fixed number of frames. The
 *GameLoop synchronizes View and Model.
 */
public class GameLoop extends Thread {
		
	private volatile boolean stopped;
	private final long ticLenght;
	
	/**
	 * Constructor for GameLoop
	 * @param fps The number of frames per second
	 */
	public GameLoop(int fps) {
		this.stopped = false;
		this.ticLenght = 1 / fps;
	}
	
	/**
	 * Causes the GameLoop to stop even if the game didn't reach an end.
	 */
	public void abort() {
		this.stopped = true;
	}
	
	/**
	 * This method is called as soon as the GameLoop is started.
	 * The GameLoop asks the View to paint the current game scene
	 * while the Model updates the scene to the next frame.
	 * If necessary the GameLoop waits to keep a constant framerate.
	 */
	@Override
	public void run() {
		while (!this.stopped) {
			long startTime = System.currentTimeMillis();
			// raccogli i dati dal model
			Thread t = new Thread() {
				@Override
				public void run() {
					// ordina la stampa a video dei dati
				}
			};
			t.start();
			// chiama metodo del model che aggiorna il tic
			try {
				t.join();
				long timeSpent = System.currentTimeMillis() - startTime;
				if (timeSpent < this.ticLenght) {
					GameLoop.sleep(this.ticLenght - timeSpent);
				}
			} catch (InterruptedException ex1) {
				this.stopped = true;
			}
		}
	}
	
	
}
