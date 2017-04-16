package graphics;

/*
							BitMap.java
                                  ,'\
    _.----.        ____         ,'  _\   ___    ___     ____
_,-'       `.     |    |  /`.   \,-'    |   \  /   |   |    \  |`.
\      __    \    '-.  | /   `.  ___    |    \/    |   '-.   \ |  |
 \.    \ \   |  __  |  |/    ,','_  `.  |          | __  |    \|  |
   \    \/   /,' _`.|      ,' / / / /   |          ,' _`.|     |  |
    \     ,-'/  /   \    ,'   | \/ / ,`.|         /  /   \  |     |
     \    \ |   \_/  |   `-.  \    `'  /|  |    ||   \_/  | |\    |
      \    \ \      /       `-.`.___,-' |  |\  /| \      /  | |   |
       \    \ `.__,'|  |`-._    `|      |__| \/ |  `.__,'|  | |   |
        \_.-'       |__|    `-._ |              '-.|     '-.| |   |
                                `'                            '-._|
                                
        
@authors  
Eric Evans
Joey McClanahan
Matt Shaffer
Shawtaroh Granzier-Nakajima

@description
CS 335 Final Project
Implements Pokemon SafariZone image cutting, loading, blending. helper method for rendering graphics
*/

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.IOException;
import java.io.Serializable;

import javax.imageio.ImageIO;

public class BitMap implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4032751192331951262L;
	private BufferedImage image;
	private int[] pixels;
	private int width;
	private int height;

	public BitMap(int w, int h) {
		width = w;
		height = h;
		pixels = new int[w * h];
		image = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
		pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
	}

	public BitMap(int w, int h, int[] p) {
		width = w;
		height = h;
		pixels = p;
	}

	public BufferedImage getBufferedImage() {
		return image;
	}

	public void blit(BitMap bitmap, int x, int y) {
		blit(bitmap, x, y, bitmap.width, bitmap.height);
	}

	// loads images
	public static BitMap load(String filename) {
		try {
			BufferedImage image = ImageIO.read(BitMap.class.getResource(filename));
			return load(image);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	// loads images
	public static BitMap load(BufferedImage image) {
		if (image == null)
			return null;

		int width = image.getWidth();
		int height = image.getHeight();

		return new BitMap(width, height, image.getRGB(0, 0, width, height, null, 0, width));
	}

	// combines and blends new bitmap
	public void blit(BitMap bitmap, int x, int y, int w, int h) {
		if (w == -1)
			w = bitmap.width;
		if (h == -1)
			h = bitmap.height;

		int topLeftCorner_X = x;
		int topLeftCorner_Y = y;
		int bottomRightCorner_X = x + w;
		int bottomRightCorner_Y = y + h;

		if (topLeftCorner_X < 0)
			topLeftCorner_X = 0;
		if (topLeftCorner_Y < 0)
			topLeftCorner_Y = 0;
		if (bottomRightCorner_X > this.width)
			bottomRightCorner_X = this.width;
		if (bottomRightCorner_Y > this.height)
			bottomRightCorner_Y = this.height;

		int blitWidth = bottomRightCorner_X - topLeftCorner_X;

		for (int yy = topLeftCorner_Y; yy < bottomRightCorner_Y; yy++) {
			int tgt = yy * this.width + topLeftCorner_X;
			int src = (yy - y) * bitmap.width + (topLeftCorner_X - x);
			tgt -= src;
			for (int xx = src; xx < src + blitWidth; xx++) {
				int color = bitmap.pixels[xx];
				int alpha = (color >> 24) & 0xFF;
				if (alpha == 0xFF) {
					this.pixels[tgt + xx] = color;
				} else {
					this.pixels[tgt + xx] = blendPixels(this.pixels[tgt + xx], color);
				}
			}
		}
	}

	// blends colors
	private int blendPixels(int bgColor, int blendColor) {
		int alphaBlend = (blendColor >> 24) & 0xFF;
		int alphaBackground = 256 - alphaBlend;

		int bgRed = bgColor & 0xFF0000;
		int bgGreen = bgColor & 0xFF00;
		int bgBlue = bgColor & 0xFF;

		int blendRed = blendColor & 0xFF0000;
		int blendGreen = blendColor & 0xFF00;
		int blendBlue = blendColor & 0xFF;

		int red = ((blendRed * alphaBlend + bgRed * alphaBackground) >> 8) & 0xFF0000;
		int green = ((blendGreen * alphaBlend + bgGreen * alphaBackground) >> 8) & 0xFF00;
		int blue = ((blendBlue * alphaBlend + bgBlue * alphaBackground) >> 8) & 0xFF;

		return 0xFF000000 | red | green | blue;
	}

	// cuts larger png into tile size blocks
	public static BitMap[][] cut(String filename, int w, int h, int clipW, int clipH) {
		try {
			BufferedImage image = ImageIO.read(BitMap.class.getResource(filename));
			int xTiles = (image.getWidth() - clipW) / w;
			int yTiles = (image.getHeight() - clipH) / h;
			BitMap[][] results = new BitMap[xTiles][yTiles];
			if (xTiles == yTiles)
				for (int x = 0; x < xTiles; x++) {
					for (int y = 0; y < yTiles; y++) {
						results[y][x] = new BitMap(w, h);
						image.getRGB(clipW + x * w, clipH + y * h, w, h, results[y][x].pixels, 0, w);
					}
				}
			else
				for (int x = 0; x < xTiles; x++) {
					for (int y = 0; y < yTiles; y++) {
						results[x][y] = new BitMap(w, h);
						image.getRGB(clipW + x * w, clipH + y * h, w, h, results[x][y].pixels, 0, w);
					}
				}
			return results;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/*
	 * Getters adn Setters
	 */
	public int getWidth() {
		return this.width;
	}

	public int getHeight() {
		return this.height;
	}
}