package spaceimpact.view;

import javafx.scene.image.ImageView;
import spaceimpact.utilities.ImageLoader;

/**
 * Class showing the main logo of space invaders redux.
 *
 */
public final class Logo {

	private final ImageLoader imageLoader = ImageLoader.getLoader();
	private final ImageView logo = new ImageView(this.imageLoader.getImageFromPath("images/logo.png"));

	public Logo(final double width, final double heigth) {
		this.logo.setFitHeight(heigth);
		this.logo.setFitWidth(width);
	}

	public ImageView getLogo() {
		return this.logo;
	}

}
