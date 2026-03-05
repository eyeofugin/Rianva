package game.entities;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Animation {
  public Map<Integer, int[]> images = new HashMap<>();
  public int length;
  public String name = "";

  public Animation(int[] ms, int[][] sprite) {
    if (ms.length == sprite.length) {
      for (int i = 0; i < ms.length; i++) {
        images.put(ms[i], sprite[i]);
      }
    }
    this.length = ms[ms.length - 1];
  }

  public int[] getImage(int counter) {
    List<Integer> sortedKeys = images.keySet().stream().sorted().toList();
    for (Integer i : sortedKeys) {
      if (counter <= i) {
        return images.get(i);
      }
    }
    return new int[0];
  }
}
