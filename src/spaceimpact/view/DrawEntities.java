package spaceimpact.view;

import java.awt.Rectangle;
import java.util.List;

import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import spaceimpact.model.Area;
import spaceimpact.model.Location;
import spaceimpact.utilities.ImageLoader;
import spaceimpact.utilities.Pair;

public class DrawEntities {
	private final ImageLoader imgl;

	public DrawEntities() {
		this.imgl = ImageLoader.getLoader();
	}

	public void draw(final Pane layer, final List<Pair<String, Location>> listEntities, final double heightGame) {
		layer.getChildren().clear();
		listEntities.forEach(p -> {
			final ImageView iv = new ImageView(this.imgl.getImageFromPath(p.getFirst()));
			iv.setPreserveRatio(true);
			final Area area = p.getSecond().getArea();
			iv.setFitHeight((heightGame * area.getHeight()));
			layer.getChildren().add(iv);
			iv.setX((p.getSecond().getX() - (area.getWidth() / 2)) * heightGame);
			iv.setY((p.getSecond().getY() - (area.getHeight() / 2)) * heightGame);
		});
	}
}