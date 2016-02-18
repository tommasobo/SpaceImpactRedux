package spaceimpact.view;

import java.util.List;

import spaceimpact.model.Location;
import spaceimpact.utilities.Input;
import spaceimpact.utilities.Pair;

public interface ViewInterface {

    List<Input> getInput();

    void draw(List<Pair<String, Location>> listEntities);

}