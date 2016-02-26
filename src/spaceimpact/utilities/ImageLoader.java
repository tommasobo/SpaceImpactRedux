package spaceimpact.utilities;

import java.util.HashMap;
import java.util.Map;

import javafx.scene.image.Image;

/**
 * Class used to read an image from file. It contains already the most important
 * icons of the game. It is designed using Singleton pattern.
 */
public final class ImageLoader {

	private static ImageLoader il;
	private final Map<String, Image> imagesMap;

	/**
	 * Returns the current Singleton instance of the ImageLoader. If it's the
	 * first call, this creates a new instance (lazy creation).
	 *
	 * @return the current Singleton instance of the ImageLoader.
	 */
	public static ImageLoader getLoader() {
		if (ImageLoader.il == null) {
			ImageLoader.il = new ImageLoader();
		}
		return ImageLoader.il;
	}

	/**
	 * Creates a new ImageLoader.
	 */
	private ImageLoader() {
		this.imagesMap = new HashMap<>();
	}

	/**
	 * Returns the requested image. Images are cached to save time.
	 *
	 * @param path
	 *            The path of the image.
	 * @return Image found in that path.
	 */
	public Image getImageFromPath(final String path) {
		try {
			if (!this.imagesMap.containsKey(path)) {
				this.imagesMap.put(path, new Image(ImageLoader.class.getResourceAsStream("/" + path)));
			}
			return this.imagesMap.get(path);
		} catch (final Exception e) {
			System.out.println("Error while loading " + path);
		}
		return null;
	}

}