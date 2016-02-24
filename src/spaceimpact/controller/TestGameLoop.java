package spaceimpact.controller;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import spaceimpact.model.Location;
import spaceimpact.utilities.Input;
import spaceimpact.utilities.Pair;
import spaceimpact.view.ViewInterface;

public class TestGameLoop {
	private volatile int var;

	// Test GameLoop start and stop
	@Test
	public void test1() {
		this.var = 0;
		final GameLoop gl = new GameLoop(10, this.dummyController, this.dummyView);
		gl.start();
		try {
			Thread.sleep(1000);
		} catch (final InterruptedException e) {
			Assert.assertTrue("Interrupted", false);
		}
		Assert.assertTrue("Draw method is not called", this.var > 0);
		gl.abort();
		final int lastVar = this.var;
		try {
			Thread.sleep(1000);
		} catch (final InterruptedException e) {
			Assert.assertTrue("Interrupted", false);
		}
		System.out.println("Test1: " + lastVar + " when stopped, now is " + this.var);
		Assert.assertTrue("GameLoop not stopped", this.var <= (lastVar + 1));
	}

	// Test GameLoop framerate
	@Test
	public void test2() {
		this.var = 0;
		final GameLoop gl = new GameLoop(60, this.dummyController, this.dummyView);
		gl.start();
		try {
			Thread.sleep(10000);
		} catch (final InterruptedException e) {
			Assert.assertTrue("Interrupted", false);
		}
		gl.abort();
		final double x = (double) (600 - Math.abs(600 - this.var)) / 6;
		System.out.println("Test2: expected 600, got " + this.var + " (" + x + "% accuracy)");
		Assert.assertTrue("Not enough accurate", x > 90);
	}

	// Test GameLoop pause/unpause
	@Test
	public void test3() {
		this.var = 0;
		final GameLoop gl = new GameLoop(50, this.dummyController, this.dummyView);
		gl.start();
		try {
			Thread.sleep(1000);
		} catch (final InterruptedException e) {
			Assert.assertTrue("Interrupted", false);
		}
		System.out.print("Test3: " + this.var + " before pause, ");
		gl.pause();
		try {
			Thread.sleep(1000);
		} catch (final InterruptedException e) {
			Assert.assertTrue("Interrupted", false);
		}
		System.out.print(this.var + " after pause,");
		Assert.assertTrue("GameLoop not paused", this.var < 55);
		gl.unPause();
		try {
			Thread.sleep(1000);
		} catch (final InterruptedException e) {
			Assert.assertTrue("Interrupted", false);
		}
		gl.abort();
		System.out.println(" resumed, finally is " + this.var);
		Assert.assertTrue("GameLoop not resumed", this.var >= 100);
	}

	private final ViewInterface dummyView = new ViewInterface() {

		@Override
		public List<Input> getInput() {
			return new ArrayList<>();
		}

		@Override
		public void startView() {
		}

		@Override
		public void draw(final List<Pair<Pair<String, Double>, Location>> listEntities) {
			TestGameLoop.this.var++;
		}

		@Override
		public void updateInfo(final int hp, final int shields, final int score) {
		}

		@Override
		public void won(final int nLevel) {
		}
	};

	private final ControllerInterface dummyController = new ControllerInterface() {

		@Override
		public void startGameLoop() throws IllegalStateException {
		}

		@Override
		public void setCurrentPlayerName(final String s) {
		}

		@Override
		public void resumeGameLoop() throws IllegalStateException {
		}

		@Override
		public void pauseGameLoop() throws IllegalStateException {
		}

		@Override
		public boolean isGameLoopRunning() {
			return false;
		}

		@Override
		public boolean isGameLoopPaused() {
			return false;
		}

		@Override
		public List<Pair<String, Integer>> getCurrentHighScores() {
			return null;
		}

		@Override
		public void emptyHighScores() {
		}

		@Override
		public void abortGameLoop() throws IllegalStateException {
		}

		@Override
		public void setScore(final int score) {
		}
	};
}
