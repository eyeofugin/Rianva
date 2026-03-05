package utils;

import java.util.List;

public class CollectionUtils {

  public static boolean isNotEmpty(List<?> list) {
    if (list == null) {
      return false;
    }
    return !list.isEmpty();
  }
}
