package utils;

import game.entities.Multiplier;
import game.effects.Effect;
import game.skills.logic.Resource;
import game.skills.logic.Stat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Utils {

  public static List<Effect> copyEffect(List<Effect> effectList) {
    List<Effect> result = new ArrayList<>();
    for (Effect effect : effectList) {
      result.add(effect.copy());
    }
    return result;
  }

  public static List<Multiplier> copyMultiplier(List<Multiplier> multipliers) {
    List<Multiplier> result = new ArrayList<>();
    for (Multiplier multiplier : multipliers) {
      result.add(multiplier.copy());
    }
    return result;
  }

  public static List<Resource> copyResource(List<Resource> resources) {
    List<Resource> result = new ArrayList<>();
    for (Resource resource : resources) {
      result.add(resource.copy());
    }
    return result;
  }

  public static Map<String, Object> copyKeyValues(Map<String, Object> keyValues) {
    return keyValues.entrySet().stream()
        .collect(
            Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (a, b) -> b, HashMap::new));
  }
}
