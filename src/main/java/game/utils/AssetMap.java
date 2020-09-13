package game.utils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

public class AssetMap {
	private static final HashMap<String, BufferedImage> map = new HashMap<>();
	
	public static BufferedImage getImage(String name) {
		return map.get(name);
	}
	
	public static BufferedImage loadImage(String name) throws IOException {
		InputStream stream = Projectile.class.getClassLoader().getResourceAsStream(name);
		BufferedImage img = ImageIO.read(stream);
		stream.close();
		map.put(name,img);
		return img;
	}
	
	public static BufferedImage getOrLoad(String name) throws IOException {
		if (map.containsKey(name)) {
			return getImage(name);
		} else {
			return loadImage(name);
		}
	}
}
