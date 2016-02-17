package spaceimpact.controller;

import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

import spaceimpact.utilities.Pair;
import spaceimpact.view.View;

public class Controller implements ControllerInterface {
        private static final String HS_FILENAME = "hiscores";
        private static final int HS_NSCORES = 10;
        private static final int FPS = 30;
        
        private final HighScoresManager hsManager;
        private Optional<GameLoop> gl;
        
        private Controller() {
                this.hsManager = new HighScoresManager(Controller.HS_FILENAME, Controller.HS_NSCORES);
                this.gl = Optional.empty();
        }
        
        @Override
        public void startGameLoop() throws IllegalStateException {
                this.gl.ifPresent(g -> {
                        throw new IllegalStateException();
                });
                GameLoop game = new GameLoop(Controller.FPS);
                this.gl = Optional.of(game);
                game.start();
                
        }

        @Override
        public void abortGameLoop() throws IllegalStateException {
                this.gl.orElseThrow( () -> new IllegalStateException());
                this.gl.get().abort();
        }

        @Override
        public List<Pair<String, Integer>> getCurrentHighScores() {
                return this.hsManager.getScores();
        }

        /**
         * Start a new application
         */
        public static void main(String args[]) {
                Controller c = new Controller();
                View v = new View(c);
        }
        
}