package spaceimpact.controller;

import java.util.Optional;

class GameLoop extends Thread {
		
	private volatile boolean stopped;
	private final long ticLenght;
	
	void abort() {
		this.stopped = true;
	}
	
	GameLoop(int fps) {
		this.stopped = false;
		this.ticLenght = 1 / fps;
	}

	@Override
	public void run() {
		while (!this.stopped) {
			long startTime = System.currentTimeMillis();
			/* raccogli i dati dal model
			 * crea thread di stampa a video
			 * chiama metodo del model che aggiorna il tic
			 * attendi la terminazione di stampa a video
			 */
			long timeSpent = System.currentTimeMillis() - startTime;
			if (timeSpent < this.ticLenght) {
				try {
					GameLoop.sleep(this.ticLenght - timeSpent);
				} catch (InterruptedException e) {
					this.stopped = true;
				}
			}
		}
	}
	
	
}
