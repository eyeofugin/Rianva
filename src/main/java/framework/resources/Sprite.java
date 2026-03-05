package framework.resources;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

public class Sprite {
  private final String path;
  private final int WIDTH, HEIGHT;
  private final int[] pixels;

  public Sprite(String path, int width, int height) {
    this.WIDTH = width;
    this.HEIGHT = height;
    this.path = path;
    this.pixels = new int[width * height];
    load();
  }

  private void load() {
    try {
      BufferedImage image = ImageIO.read(new File(this.path));
      int width = image.getWidth();
      int height = image.getHeight();
      int[] source = new int[width * height];
      image.getRGB(0, 0, width, height, source, 0, width);
      convert(source, width);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void convert(int[] source, int size) {
    double mult = this.WIDTH / (double) size;

    for (int y = 0; y < this.HEIGHT; y++) {
      for (int x = 0; x < this.WIDTH; x++) {
        this.pixels[x + y * this.WIDTH] = source[(int) (x / mult) + (int) (y / mult) * size];
      }
    }
  }

  public int[] getPixels() {
    return this.pixels;
  }

  public static int[] flipUpDown(int[] p) {

    int[] result = new int[p.length];
    for (int i = 0; i < p.length; i++) {
      result[i] = p[p.length - 1 - i];
    }

    return result;
  }

  public static int[] flipLeftRight(int[] p, int size) {
    int[] result = new int[p.length];
    for (int y = 0; y < size; y++) {
      for (int x = 0; x < size; x++) {
        int reflectedX = size - (x + 1);
        result[reflectedX + y * size] = p[x + y * size];
      }
    }
    return result;
  }

  public static int[] rotateLeft(int[] p, int size) {
    int[] result = new int[p.length];
    for (int y = 0; y < size; y++) {
      for (int x = 0; x < size; x++) {
        result[y + x * size] = p[x + y * size];
      }
    }
    return result;
  }
}
