package spaceimpact.view;

import javafx.scene.image.ImageView;
import spaceimpact.utilities.ImageLoader;

/**
 * Class showing the main logo of space invaders game.
 * 
 * @author Tommaso Bonato
 */
public final class Logo {
    
    private final ImageLoader imageLoader = ImageLoader.getLoader();
    private final ImageView logo = new ImageView(this.imageLoader.getImageFromPath("logo.png"));
    
    public Logo(final double width, final double heigth) {    
        logo.setFitHeight(heigth);
        logo.setFitWidth(width);
    }

    public ImageView getLogo() {
        return this.logo;
    }
        
}
