package spaceimpact.controller;

import java.util.List;
import java.util.Optional;

import spaceimpact.model.Direction;
import spaceimpact.utilities.Input;
import spaceimpact.utilities.Pair;

/**
 * Functional interface for a generic input parser.
 */
public interface InputParserInterface {

    /**
     * Parses the "concrete" inputs and produces an optional direction (where
     * the player wants to go) and a boolean (whether the player wants to shoot
     * or not).
     *
     * @param inputList
     *            The list of "concrete" inputs.
     * @return A Pair<Optional<Direction>, Boolean> (the "abstract" input). The
     *         boolean is true if the player pressed the key that allows to
     *         shoot, false otherwise. The Optional is empty if the player
     *         doesn't want to move (hasn't pressed a motion key), or contains a
     *         direction.
     */
    Pair<Optional<Direction>, Boolean> parseInputs(List<Input> inputList);
}
