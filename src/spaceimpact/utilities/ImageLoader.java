package spaceimpact.utilities;

import java.util.HashMap;
import java.util.Map;

import javafx.scene.image.Image;

/**
 * Class used to read an image from file. It contains already the most important
 * icons of the game. It is designed using Singleton pattern.
 *
 * @author Tommaso Bonato
 */
public class ImageLoader {
	private static final String SEP = System.getProperty("file.separator");
	private static final String LOGO = "icon.png";

	private static ImageLoader IMAGELOADER = null;

	/**
	 * Returns the current Singleton instance of the ImageLoader. If it's the
	 * first call, this creates a new instance.
	 *
	 * @return the current Singleton instance of the ImageLoader.
	 */
	public static ImageLoader getLoader() {
		if (ImageLoader.IMAGELOADER == null) {
			ImageLoader.IMAGELOADER = new ImageLoader();
		}
		return ImageLoader.IMAGELOADER;
	}

	private Map<String, Image> imagesMap = new HashMap<>();

	/**
	 * Creates a new ImageLoader.
	 */
	private ImageLoader() {
		this.imagesMap = new HashMap<>();
		System.out.println(ImageLoader.LOGO);
		this.getImageFromPath(ImageLoader.LOGO);
	}

	/**
	 * Given the path, it adds the image to the map and returns it.
	 *
	 * @param path
	 *            - The path of the image.
	 * @return Image found in that path.
	 */
	public Image getImageFromPath(final String path) {
		if (!this.imagesMap.containsKey(path)) {
			this.imagesMap.put(path, new Image("file:res/" + path));
		}
		return this.imagesMap.get(path);
	}

	/**
	 * Returns the logo of the game.
	 *
	 * @return an Image representing the logo of the game
	 */
	public Image getLogoImage() {
		return this.imagesMap.get(ImageLoader.LOGO);
	}

}
