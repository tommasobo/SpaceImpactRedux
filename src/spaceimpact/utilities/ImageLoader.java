package spaceimpact.utilities;

import javafx.scene.image.Image;
import java.util.*;

/**
 * Class used to read an image from file. It contains already the most
 * important icons of the game. It is designed using Singleton pattern.
 *
 * @author Tommaso Bonato
 */
public class ImageLoader {

        private static final String LOGO = "/res/icon.jpg";

        private Map<String, Image> imagesMap = new HashMap<>();
        private static ImageLoader IMAGELOADER = null;
        

        /**
         * Creates a new ImageLoader.
         */
        private ImageLoader() {
                this.imagesMap = new HashMap<>();
                this.imagesMap.put(LOGO, this.getImageFromPath(LOGO));
        }

        /**
         * Returns the current Singleton instance of the ImageLoader.
         * If it's the first call, this creates a new instance.
         *
         * @return the current Singleton instance of the ImageLoader.
         */
        public static ImageLoader getLoader() {
                if (IMAGELOADER == null) {
                    IMAGELOADER = new ImageLoader();
                }
                return IMAGELOADER;
        }

        /**
         * Returns the logo of the game.
         * 
         * @return an Image representing the logo of the game
         */
        public Image getLogoImage() {
                return this.imagesMap.get(LOGO);
        }


        /**
         * Given the path, it adds the image to the map and returns it.
         * 
         * @param path - The path of the image.
         * @return Image found in that path.
         */
        public Image getImageFromPath(String path) {
            if (!this.imagesMap.containsKey(path)) {
                Image image  = new Image(this.getClass().getResourceAsStream(path));
                this.imagesMap.put(path, image);
            }
            return this.imagesMap.get(path);
        }

}
