package utils;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class CollectionUtils {

  public static <T> T getRandom(Collection<T> collection) {
    if (collection.isEmpty()) {
      return null;
    }
    Random random = new Random();
    int index = random.nextInt(collection.size());

    Iterator<T> iterator = collection.iterator();
    for (int i = 0; i < index; i++) {
      iterator.next();
    }

    return iterator.next();
  }
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
