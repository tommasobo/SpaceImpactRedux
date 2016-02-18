package spaceimpact.view;

import java.util.List;

import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import spaceimpact.model.Location;
import spaceimpact.utilities.ImageLoader;
import spaceimpact.utilities.Pair;

public class DrawEntities {
	private final ImageLoader imgl;

	public DrawEntities() {
		this.imgl = ImageLoader.getLoader();
	}

	public void draw(final Scene scene, final List<Pair<String, Location>> listEntities) {
		scene.setOnKeyPressed(InputHandler.getInputHandler());
		final HBox layout = new HBox();
		listEntities.forEach(p -> {
			final ImageView iv = new ImageView(this.imgl.getImageFromPath(p.getFirst()));
			iv.setFitHeight(50);
			iv.setFitWidth(60);
			layout.getChildren().add(iv);
		});

	}

}
