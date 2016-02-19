package spaceimpact.controller;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.junit.Test;

import spaceimpact.utilities.Pair;

public class TestHighScoresManager {

	@Test
	public void test1() {
		// if last test failed remove "test1" file!!!
		HighScoresManager h;
		
		System.out.println("try to create a manager with invalid arguments");
		try {
			h = new HighScoresManager("", 1);
			assertTrue("Created with wrong String!", false);
		} catch (Exception e) {}
		try {
			h = new HighScoresManager("test1", -24);
			assertTrue("Created with wrong int!", false);
		} catch (Exception e) {}
		
		System.out.println("\nload an empty list");
		h = new HighScoresManager("test1", 3);
		List<Pair<String, Integer>> comparisonList = new LinkedList<>();
		System.out.println("Current list: " + h.getScores() + "\nExpected: " + comparisonList + "\n\n");
		assertTrue("The newly created score list must be empty!",
				h.getScores().toString().equals(comparisonList.toString()));
		
		System.out.println("add a new score");
		Pair<String, Integer> p = new Pair<>("Giorgio", 10);
		comparisonList.add(p);
		h.addScore(p);
		System.out.println("Current list: " + h.getScores() + "\nExpected: " + comparisonList + "\n\n");
		assertTrue("The score list is different from the expected one",
				h.getScores().toString().equals(comparisonList.toString()));
		
		System.out.println("add two more scores (max capacity reached)");
		p = new Pair<>("Luca", 7);
		comparisonList.add(p);
		h.addScore(p);
		p = new Pair<>("Anna", 15);
		comparisonList.add(0, p);
		h.addScore(p);
		System.out.println("Current list: " + h.getScores() + "\nExpected: " + comparisonList + "\n\n");
		assertTrue("The score list is not correctly sorted",
				h.getScores().toString().equals(comparisonList.toString()));
		
		System.out.println("add another score (the lowest is removed)");
		p = new Pair<>("Mattia", 12);
		comparisonList.remove(2);
		comparisonList.add(1, p);
		h.addScore(p);
		System.out.println("Current list: " + h.getScores() + "\nExpected: " + comparisonList + "\n\n");
		assertTrue("Luca should be out of the list!",
				h.getScores().toString().equals(comparisonList.toString()));
		
		System.out.println("clear the list");
		h.emptyScores();
		comparisonList.clear();
		System.out.println("Current list: " + h.getScores() + "\nExpected: " + comparisonList + "\n\n");
		assertTrue("The list should be empty again!",
				h.getScores().toString().equals(comparisonList.toString()));
		
		System.out.println("save a non-empty list");
		p = new Pair<>("Genoveffa", 7);
		comparisonList.add(p);
		h.addScore(p);
		p = new Pair<>("Gianni", 6);
		comparisonList.add(p);
		h.addScore(p);
		p = new Pair<>("Giuseppe", 2);
		h.addScore(p);
		comparisonList.add(p);
		try {
			h.saveData();
		} catch (IllegalStateException | IOException e) {
			assertTrue("Error while saving!", false);
		}
		
		System.out.println("\n\nload again");
		h = new HighScoresManager("test1", 3);
		System.out.println("Current list: " + h.getScores() + "\nExpected: " + comparisonList + "\n\n");
		assertTrue("The list should be as before!", h.getScores().toString().equals(comparisonList.toString()));
		
		System.out.println("load a list with a lower score limit");
		comparisonList.remove(2);
		h = new HighScoresManager("test1", 2);
		System.out.println("Current list: " + h.getScores() + "\nExpected: " + comparisonList + "\n\n");
		assertTrue("Only Gianni and Genoveffa should be in the list!",
				h.getScores().toString().equals(comparisonList.toString()));
		
		System.out.println("not saved, load again with higher score limit");
		h = new HighScoresManager("test1", 15);
		comparisonList.add(p);
		System.out.println("Current list: " + h.getScores() + "\nExpected: " + comparisonList + "\n\n");
		assertTrue("Giuseppe should be in the list!",
				h.getScores().toString().equals(comparisonList.toString()));
		
		System.out.println("cleanup for next tests");
		h.emptyScores();
		comparisonList.clear();
		try {
			h.saveData();
		} catch (IllegalStateException | IOException e) {
			assertTrue("Error while saving!", false);
		}
	}

}
