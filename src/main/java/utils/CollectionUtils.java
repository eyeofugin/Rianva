package utils;

import game.effects.Effect;

import java.util.List;

public class CollectionUtils {

  public static boolean isNotEmpty(List<?> list) {
    if (list == null) {
      return false;
    }
    return !list.isEmpty();
  }

    public static boolean isEmpty(List<?> list) {
      if (list == null) {
        return true;
      }
      return list.isEmpty();
    }
}
