package spaceimpact.view;

import java.util.List;

import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import spaceimpact.model.Area;
import spaceimpact.model.Location;
import spaceimpact.utilities.ImageLoader;
import spaceimpact.utilities.Pair;

/**
 * Class used to print game entities on a layer. The draw method must be called
 * each tic.
 */
public class DrawEntities {
	private final ImageLoader imgl;
	private final ImageView bg;
	private final ImageView bg2;
	private static final double BACKGROUND_SPEED = 150 / View.getController().getFPS();
	private static final double GAME_WIDTH = 1280;

	/**
	 * Constructor for DrawEntities
	 */
	public DrawEntities() {
		this.imgl = ImageLoader.getLoader();
		this.bg = new ImageView(this.imgl.getImageFromPath("images/gameBackground.png"));
		this.bg2 = new ImageView(this.imgl.getImageFromPath("images/gameBackground.png"));
		this.bg.setFitWidth(GAME_WIDTH);
		this.bg2.setFitWidth(GAME_WIDTH);
		this.bg2.relocate(GAME_WIDTH, 0);
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
	public void draw(final Pane layer, final List<Pair<Pair<String, Double>, Location>> listEntities,
			final double heightGame) {
		layer.getChildren().clear();
		this.translateBackground();
		layer.getChildren().addAll(this.bg, this.bg2);
		listEntities.forEach(p -> {
			final ImageView iv = new ImageView(this.imgl.getImageFromPath(p.getFirst().getFirst()));
			iv.setPreserveRatio(true);
			final Area area = p.getSecond().getArea();
			iv.setFitHeight((heightGame * area.getHeight()));
			layer.getChildren().add(iv);
			iv.setX((p.getSecond().getX() - (area.getWidth() / 2)) * heightGame);
			iv.setY((p.getSecond().getY() - (area.getHeight() / 2)) * heightGame);
			if (p.getFirst().getSecond().doubleValue() != 0d) {
				iv.setRotate(p.getFirst().getSecond().doubleValue());
			}
		});
		
	}
	
	private void translateBackground() {
	    final double x = this.bg.getLayoutX() - BACKGROUND_SPEED;
	    if (x <= (-GAME_WIDTH)) {
	        this.bg.relocate(GAME_WIDTH, 0);
	    } else {
	        this.bg.setLayoutX(x);
	    }
	    final double x2 = this.bg2.getLayoutX() - BACKGROUND_SPEED;
	    if (x2 <= (-GAME_WIDTH)) {
                this.bg2.relocate(GAME_WIDTH, 0);
            } else {
                this.bg2.setLayoutX(x2);
            }
	}
}