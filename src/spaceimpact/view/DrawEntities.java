package spaceimpact.view;

import java.util.List;

import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import spaceimpact.model.Location;
import spaceimpact.utilities.ImageLoader;
import spaceimpact.utilities.Pair;

public class DrawEntities {
	private final ImageLoader imgl;

	public DrawEntities() {
		this.imgl = ImageLoader.getLoader();
	}

	public void draw(final Pane layer, final List<Pair<String, Location>> listEntities) {
		layer.getChildren().clear();
		listEntities.forEach(p -> {
			final ImageView iv = new ImageView(this.imgl.getImageFromPath(p.getFirst()));
			iv.setFitHeight(50);
			iv.setFitWidth(60);
			layer.getChildren().add(iv);
			// TODO Make screenheight a param!
			iv.setX(p.getSecond().getX() * 800.0);
			iv.setY(p.getSecond().getY() * 800.0);
		});
	}

}