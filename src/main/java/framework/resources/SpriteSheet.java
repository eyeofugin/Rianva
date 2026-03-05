package framework.resources;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URI;

public class SpriteSheet {

  private final String path;
  public final int WIDTH, HEIGHT;
  public int[] pixels;

  public static SpriteSheet fonts5x8 = new SpriteSheet("fonts/FontsMid.png", 50, 60);
  public static SpriteSheet smallNum = new SpriteSheet("fonts/smallnum.png", 30, 5);
  public static SpriteSheet fonts8x = new SpriteSheet("fonts/8x/8xfonts.png", 64, 32);

  public SpriteSheet(String path, int width, int height) {
    this.HEIGHT = height;
    this.WIDTH = width;
    this.path = path;
    pixels = new int[HEIGHT * WIDTH];
    load();
  }

  private void load() {
    try {
      URI uri = SpriteLibrary.class.getClassLoader().getResource(path).toURI();
      BufferedImage image = ImageIO.read(new File(uri));
      int width = image.getWidth();
      int height = image.getHeight();
      image.getRGB(0, 0, width, height, pixels, 0, width);

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public int[] getSprite(int targetWidth, int targetHeight) {

    double relation = (double) WIDTH / targetWidth;

    int[] result = new int[targetWidth * targetHeight];

    for (int row = 0; row < targetHeight; row++) {
      for (int column = 0; column < targetWidth; column++) {

        int ogRow = (int) (row * relation);
        int ogColumn = (int) (column * relation);

        result[column + row * targetWidth] = this.pixels[ogColumn + ogRow * WIDTH];
      }
    }

    return result;
  }
}
