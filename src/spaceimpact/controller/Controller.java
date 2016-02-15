package spaceimpact.controller;

import java.util.List;

import spaceimpact.utilities.Pair;

public class Controller implements ControllerInterface {
	private static final String HS_FILENAME = "hiscores";
	private static final int HS_NSCORES = 10;
	
	
	private final HighScoresManager hsManager;
	
	
	private Controller() {
		this.hsManager = new HighScoresManager(Controller.HS_FILENAME, Controller.HS_NSCORES);
	}
	
	
	@Override
	public void startGameLoop() throws IllegalStateException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void abortGameLoop() throws IllegalStateException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Pair<String, Integer>> getCurrentHighScores() {
		// TODO Auto-generated method stub
		return null;
	}

	
}
