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
	private double gameWidth = 1280;
	private double gameHeight = 720;

	/**
	 * Constructor for DrawEntities
	 * @param inGameHeight 
	 * @param inGameWidth 
	 */
	public DrawEntities(double inGameWidth, double inGameHeight) {
	    this.gameWidth = inGameWidth;
	    this.gameHeight = inGameHeight;
		this.imgl = ImageLoader.getLoader();
		this.bg = new ImageView(this.imgl.getImageFromPath("images/gameBackground.png"));
		this.bg2 = new ImageView(this.imgl.getImageFromPath("images/gameBackground.png"));
		this.bg.setFitWidth(this.gameWidth);
		this.bg.setFitHeight(this.gameHeight);
		this.bg2.setFitWidth(this.gameWidth);
		this.bg2.setFitHeight(this.gameHeight);
		this.bg2.relocate(this.gameWidth, 0);
	}

	/**
	 * This method prints on the given layer the list of images provided.
	 *
	 * @param layer
	 *            The Pane used for printing
	 * @param listEntities
	 *            A list of Pair<Pair<String, Double>,Location> that describes
	 *            the entities to be drawn. The Location is the entity Location,
	 *            the Pair<String, Double> describes the image chosen (the
	 *            String is the image path relative from res folder (in URL
	 *            form), the Double is the image rotation required).
	 */
	public void draw(final Pane layer, final List<Pair<Pair<String, Double>, Location>> listEntities) {
		layer.getChildren().clear();
		this.translateBackground();
		layer.getChildren().addAll(this.bg, this.bg2);
		listEntities.forEach(p -> {
			final ImageView iv = new ImageView(this.imgl.getImageFromPath(p.getFirst().getFirst()));
			iv.setPreserveRatio(true);
			final Area area = p.getSecond().getArea();
			iv.setFitHeight(this.gameHeight * area.getHeight());
			layer.getChildren().add(iv);
			iv.setX((p.getSecond().getX() - area.getWidth() / 2) * this.gameHeight);
			iv.setY((p.getSecond().getY() - area.getHeight() / 2) * this.gameHeight);
			if (p.getFirst().getSecond().doubleValue() != 0d) {
				iv.setRotate(p.getFirst().getSecond().doubleValue());
			}
		});

	}

	private void translateBackground() {
		final double x = this.bg.getLayoutX() - DrawEntities.BACKGROUND_SPEED;
		if (x <= -this.gameWidth) {
			this.bg.relocate(this.gameWidth, 0);
		} else {
			this.bg.setLayoutX(x);
		}
		final double x2 = this.bg2.getLayoutX() - DrawEntities.BACKGROUND_SPEED;
		if (x2 <= -this.gameWidth) {
			this.bg2.relocate(this.gameWidth, 0);
		} else {
			this.bg2.setLayoutX(x2);
		}
	}

}