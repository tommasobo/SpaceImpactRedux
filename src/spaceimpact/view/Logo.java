package spaceimpact.view;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Class showing the main logo of space invaders game.
 * 
 * @author Tommaso Bonato
 */
public final class Logo {
    
    private final Image image = new Image("logo.png");
    private final ImageView iv1 = new ImageView();
    
    public Logo(final double width, final double heigth) {
        iv1.setImage(image);
        iv1.setFitHeight(heigth);
        iv1.setFitWidth(width);
    }

    public ImageView getLogo() {
        return this.iv1;
    }
        
}
