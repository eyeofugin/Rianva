package game.effects;

import game.skills.logic.Condition;
import utils.CollectionUtils;
import utils.FileWalker;

import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.stream.Collectors;

public class EffectLibrary {
  private static Map<String, EffectDTO> heroEffectDTOs;
  private static Map<String, EffectDTO> globalEffectDTOs;
  private static Map<String, EffectDTO> fieldEffectDTOs;
  private static Map<String, EffectDTO> statEffectsDTOs;
  private static Map<String, EffectDTO> statusEffectsDTOs;

  public static void init() {
    Map<String, String> heroEffectJson = FileWalker.loadJsonMap("data/heroEffects.json");
    heroEffectDTOs = loadDTOMap(heroEffectJson);
    Map<String, String> globalEffectJson = FileWalker.loadJsonMap("data/globalEffects.json");
    globalEffectDTOs = loadDTOMap(globalEffectJson);
    Map<String, String> fieldEffectJson = FileWalker.loadJsonMap("data/fieldEffects.json");
    fieldEffectDTOs = loadDTOMap(fieldEffectJson);
    Map<String, String> statEffectsJson = FileWalker.loadJsonMap("data/statEffects.json");
    statEffectsDTOs = loadDTOMap(statEffectsJson);
    Map<String, String> statusEffectsJson = FileWalker.loadJsonMap("data/statusEffects.json");
    statusEffectsDTOs = loadDTOMap(statusEffectsJson);
  }

  public static Effect getRandomAtkEnhance() {
    List<EffectDTO> effects = heroEffectDTOs.values().stream()
            .filter(effectDTO -> effectDTO.subTypes.contains(Effect.SubType.ATK_ENHANCE)).toList();
    EffectDTO random = CollectionUtils.getRandom(effects);
    if (random != null) {
      Effect effect = getEffect(random.name);
      if (effect != null) {
        effect.turns = Effect.Durability.TIME.equals(random.durability) ? 1 : 0;
        effect.stacks = Effect.Durability.STACK.equals(random.durability) ? 1 : 0;
      }
      return effect;
    }
    return null;
  }

  public static Effect getRandomStatusDebuff() {
    List<EffectDTO> effects = statusEffectsDTOs.values().stream()
            .filter(effectDTO -> effectDTO.subTypes.contains(Effect.SubType.DEBUFF)).toList();
    EffectDTO randomEffect = CollectionUtils.getRandom(effects);
    if (randomEffect != null) {
      Effect effect = getEffect(randomEffect.name);
      if (effect != null) {
        effect.turns = Effect.Durability.TIME.equals(randomEffect.durability) ? 1 : 0;
        effect.stacks = Effect.Durability.STACK.equals(randomEffect.durability) ? 1 : 0;
      }
      return effect;
    }
    return null;
  }

  public static Effect getRandomStatDebuff() {
    List<EffectDTO> effects = statEffectsDTOs.values().stream()
            .filter(effectDTO -> effectDTO.subTypes.contains(Effect.SubType.DEBUFF)).toList();
    EffectDTO randomEffect = CollectionUtils.getRandom(effects);

    if (randomEffect != null) {
      Effect effect = getEffect(randomEffect.name);
        if (effect != null) {
            effect.turns = 1;
        }
        return effect;
    }
    return null;
  }

  public static Effect getEffect(String className) {
    try {
      Class<?> effectClass = Class.forName(className);
      return (Effect) effectClass.getDeclaredConstructor().newInstance();
    } catch (ClassNotFoundException
             | InvocationTargetException
             | InstantiationException
             | IllegalAccessException
             | NoSuchMethodException e) {
      return null;
    }
  }

  public static Effect getEffect(String className, int stacks, int turns, Condition condition) {
    Effect effect = getEffect(className);
    if (effect != null) {
      effect.turns = turns;
      effect.stacks = stacks;
      effect.condition = condition;
    }
    return effect;
  }

  public static EffectDTO getEffectDTO(String name) {
    if (heroEffectDTOs.containsKey(name)) {
      return heroEffectDTOs.get(name);
    } else if (globalEffectDTOs.containsKey(name)) {
      return globalEffectDTOs.get(name);
    } else if (fieldEffectDTOs.containsKey(name)) {
      return fieldEffectDTOs.get(name);
    } else if (statEffectsDTOs.containsKey(name)) {
      return statEffectsDTOs.get(name);
    } else if (statusEffectsDTOs.containsKey(name)) {
      return statusEffectsDTOs.get(name);
    }
    return null;
  }

  private static Map<String, EffectDTO> loadDTOMap(Map<String, String> json) {
    return json.entrySet().stream()
        .collect(
            Collectors.toMap(
                Map.Entry::getKey,
                e -> {
                  EffectDTO dto = getEffectDTO(e.getValue());
                  return Objects.requireNonNullElseGet(dto, EffectDTO::new);
                }));
  }

  public static EffectDTO getEffectDTO(String name, Map<String, String> map) {
    return FileWalker.mapJson(EffectDTO.class, map.get(name));
  }
}
