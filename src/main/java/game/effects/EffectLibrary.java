package game.effects;

import game.skills.logic.Condition;
import utils.FileWalker;

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

  public static Effect getEffect(String name, int stacks, int turns, Condition condition) {
    EffectDTO dto = getEffectDTO(name);
    if (dto != null) {
      Effect effect = new Effect(dto);
      effect.set(dto);
      effect.turns = turns;
      effect.stacks = stacks;
      effect.condition = condition;
      return effect;
    }
    return null;
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
