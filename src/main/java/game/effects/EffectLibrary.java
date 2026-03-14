package game.effects;

import game.skills.logic.Condition;
import utils.FileWalker;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

public class EffectLibrary {
  private static Map<String, String> heroEffectJson;
  private static Map<String, String> globalEffectJson;
  private static Map<String, String> fieldEffectJson;
  private static Map<String, String> statEffectsJson;
  private static Map<String, String> statusEffectsJson;

  public static void init() {
    heroEffectJson = FileWalker.loadJsonMap("data/heroEffects.json");
    globalEffectJson = FileWalker.loadJsonMap("data/globalEffects.json");
    fieldEffectJson = FileWalker.loadJsonMap("data/fieldEffects.json");
    statEffectsJson = FileWalker.loadJsonMap("data/statEffects.json");
    statusEffectsJson = FileWalker.loadJsonMap("data/statusEffects.json");
  }

  public static Effect getEffect(String className, int stacks, int turns, Condition condition) {
    try {
      Class<?> effectClass = Class.forName(className);
      Effect effect = (Effect) effectClass.getDeclaredConstructor().newInstance();
      effect.turns = turns;
      effect.stacks = stacks;
      effect.condition = condition;
      return effect;
    } catch (ClassNotFoundException
             | InvocationTargetException
             | InstantiationException
             | IllegalAccessException
             | NoSuchMethodException e) {
      return null;
    }
  }

  public static EffectDTO getEffectDTO(String name) {
    if (heroEffectJson.containsKey(name)) {
      return getEffectDTO(name, heroEffectJson);
    } else if (globalEffectJson.containsKey(name)) {
      return getEffectDTO(name, globalEffectJson);
    } else if (fieldEffectJson.containsKey(name)) {
      return getEffectDTO(name, fieldEffectJson);
    } else if (statEffectsJson.containsKey(name)) {
      return getEffectDTO(name, statEffectsJson);
    } else if (statusEffectsJson.containsKey(name)) {
      return getEffectDTO(name, statusEffectsJson);
    }
    return null;
  }

  public static EffectDTO getEffectDTO(String name, Map<String, String> map) {
    return FileWalker.mapJson(EffectDTO.class, map.get(name));
  }
}
