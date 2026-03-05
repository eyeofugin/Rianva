package framework.resources;

import framework.graphics.text.Color;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class SpriteUtils {
  public static BufferedImage flipH(BufferedImage bi) {
    AffineTransform at = new AffineTransform();
    at.concatenate(AffineTransform.getScaleInstance(-1, 1));
    at.concatenate(AffineTransform.getTranslateInstance(-bi.getWidth(), 0));
    return createTransformed(bi, at);
  }

  public static BufferedImage flip90(BufferedImage bi) {
    int w = bi.getWidth();
    int h = bi.getHeight();

    BufferedImage res = new BufferedImage(h, w, bi.getType());

    Graphics2D g2 = res.createGraphics();
    g2.translate((h - w) / 2, (h - w) / 2);
    g2.rotate(Math.PI / 2, h / 2, w / 2);
    g2.drawRenderedImage(bi, null);
    return res;
  }

  private static BufferedImage createTransformed(BufferedImage image, AffineTransform at) {
    BufferedImage newImage =
        new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
    Graphics2D g = newImage.createGraphics();
    g.transform(at);
    g.drawImage(image, 0, 0, null);
    g.dispose();
    return newImage;
  }

  public static int[] flipHorizontal(int[] p, int sideSize) {
    if (p == null) {
      return new int[0];
    }
    int[] copy = new int[p.length];
    int index = 0;
    for (int y = 0; y < sideSize; y++) {
      for (int x = sideSize - 1; x >= 0; x--) {
        copy[y * sideSize + x] = p[index++];
      }
    }
    return copy;
  }

  public static int[] scale(int[] p, int target_w, int target_h, int current_width) {

    int mult = target_w / current_width;

    int[] result = new int[(target_w * mult) * (target_h * mult)];

    for (int y = 0; y < target_h; y++) {
      for (int x = 0; x < target_w; x++) {
        result[x + y * target_w] = p[(x / mult) + (y / mult) * current_width];
      }
    }
    return result;
  }

  public static int[] upscale(int[] p, int w, int h, int mult) {

    int target_w = w * mult;
    int target_h = h * mult;
    int[] result = new int[(target_w) * (target_h)];

    for (int y = 0; y < target_h; y++) {
      for (int x = 0; x < target_w; x++) {
        result[x + y * target_w] = p[(x / mult) + (y / mult) * w];
      }
    }
    return result;
  }

  public static int[] mergeInto(int[] original, int[] merge) {
    int[] result = new int[original.length];
    for (int i = 0; i < original.length; i++) {
      if (merge[i] != Color.VOID.VALUE) {
        result[i] = merge[i];
      }
    }
    return result;
  }

  public static int[] mergeIntoSize(
      int[] original, int originalW, int[] merge, int mergeW, int mergeH, int xFrom, int yFrom) {

    int[] result = new int[original.length];
    System.arraycopy(original, 0, result, 0, result.length);
    int yUntil = yFrom + mergeH;
    int xUntil = xFrom + mergeW;
    int index = 0;
    for (int i = yFrom; i < yUntil; i++) {
      for (int j = xFrom; j < xUntil; j++) {

        if (index >= merge.length) {
          continue;
        }
        if (j + i * originalW >= original.length) {
          continue;
        }
        if (merge[index] != Color.VOID.VALUE) {
          int color = merge[index];
          int arrayindex = j + i * originalW;
          if (arrayindex < original.length && arrayindex >= 0) result[arrayindex] = color;
        }
        index++;
      }
    }
    return result;
  }
}
