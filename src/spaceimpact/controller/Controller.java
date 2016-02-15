package spaceimpact.controller;

import java.util.List;

import spaceimpact.utilities.Pair;

public class Controller implements ControllerInterface {

	private final HighScoresManager hsManager;
	
	
	private Controller() {
		this.hsManager = new HighScoresManager();
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
