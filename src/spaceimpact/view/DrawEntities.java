package spaceimpact.view;

import java.awt.Rectangle;
import java.util.List;

import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import spaceimpact.model.Location;
import spaceimpact.utilities.ImageLoader;
import spaceimpact.utilities.Pair;

/**
 * Class used to print game entities on a layer. The draw method must be called
 * each tic.
 */
public class DrawEntities {
	private final ImageLoader imgl;

	/**
	 * Constructor for DrawEntities
	 */
	public DrawEntities() {
		this.imgl = ImageLoader.getLoader();
	}

	/**
	 * This method prints the list of images provided on the given layer
	 *
	 * @param layer
	 *            The Pane used for printing
	 * @param listEntities
	 *            A list of Pair<String, Location> that describes the entities
	 *            to be drawn. The Location is the entity Location, the String
	 *            is the path to the image (in URL form, starting from the res
	 *            folder)
	 * @param heightGame
	 *            The height of the game window
	 */
	public void draw(final Pane layer, final List<Pair<String, Location>> listEntities, final double heightGame) {
		layer.getChildren().clear();
		listEntities.forEach(p -> {
			final ImageView iv = new ImageView(this.imgl.getImageFromPath(p.getFirst()));
			iv.setPreserveRatio(true);
			final Rectangle area = p.getSecond().getArea();
			iv.setFitHeight((heightGame * area.getHeight()) / 100);
			layer.getChildren().add(iv);
			iv.setX((p.getSecond().getX() - (area.getWidth() / 200)) * heightGame);
			iv.setY((p.getSecond().getY() - (area.getHeight() / 200)) * heightGame);
		});
	}
}