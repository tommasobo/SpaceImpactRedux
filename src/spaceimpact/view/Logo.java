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

    /**
     * Constructor of the class. It set ups the size of the logo.
     * 
     * @param width
     *            Width of the logo.
     * @param heigth
     *            Height of the logo.
     */
    Logo(final double width, final double heigth) {
        this.logo.setFitHeight(heigth);
        this.logo.setFitWidth(width);
    }

    /**
     * Getter of the ImageView.
     * 
     * @return An ImageView of the logo.
     */
    ImageView getLogo() {
        return this.logo;
    }

}
