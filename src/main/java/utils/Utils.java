package utils;

import framework.connector.ConnectionPayload;
import framework.connector.Connector;
import game.entities.Hero;
import game.entities.Multiplier;
import game.effects.Effect;
import game.objects.Equipment;
import game.skills.Skill;
import game.skills.logic.DamageType;
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
      if (effectList == null) {
          return result;
      }
    for (Effect effect : effectList) {
      result.add(effect.copy());
    }
    return result;
  }

  public static List<Multiplier> copyMultiplier(List<Multiplier> multipliers) {
    List<Multiplier> result = new ArrayList<>();
      if (multipliers == null) {
          return result;
      }
    for (Multiplier multiplier : multipliers) {
      result.add(multiplier.copy());
    }
    return result;
  }

  public static List<Resource> copyResource(List<Resource> resources) {
    List<Resource> result = new ArrayList<>();
    if (resources == null) {
        return result;
    }
    for (Resource resource : resources) {
      result.add(resource.copy());
    }
    return result;
  }


  public static Map<String, Object> copyKeyValues(Map<String, Object> keyValues) {
      if (keyValues == null) {
          return null;
      }
    return keyValues.entrySet().stream()
        .collect(
            Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (a, b) -> b, HashMap::new));
  }
  public static Map<Stat, Integer> copyStats(Map<Stat, Integer> stats) {
      if (stats == null) {
          return null;
      }
      return stats.entrySet().stream()
        .collect(
            Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (a, b) -> b, HashMap::new));
  }

  public static int chanceChanges(Hero target, Hero origin, int value, Skill skill, Equipment equipment, Effect effect) {
    ConnectionPayload pl = new ConnectionPayload()
            .setTarget(target)
            .setCaster(origin)
            .setValue(value)
            .setSkill(skill)
            .setEquipment(equipment)
            .setEffect(effect);
    Connector.fireTopic(Connector.CHANCE_EFFECT_BASE_CHANGE, pl);
    Connector.fireTopic(Connector.CHANCE_EFFECT_MULT_CHANGE, pl);
    return pl.value;
  }

  public static void onMark(Hero marked) {
    ConnectionPayload pl = new ConnectionPayload()
            .setTarget(marked);
    Connector.fireTopic(Connector.ON_MARK, pl);
  }

  public static int statChangesChanges(Hero target, Hero origin, Stat stat, int value, Skill skill, Equipment equipment, Effect effect) {
    ConnectionPayload pl = new ConnectionPayload()
            .setTarget(target)
            .setCaster(origin)
            .setStat(stat)
            .setValue(value)
            .setSkill(skill)
            .setEquipment(equipment)
            .setEffect(effect);
    Connector.fireTopic(Connector.STAT_BASE_CHANGE_CHANGE, pl);
    Connector.fireTopic(Connector.STAT_MULT_CHANGE_CHANGE, pl);
    return pl.value;
  }
  public static ConnectionPayload.CondEffectImpact condTriggerChanges(Hero origin, Skill skill, Equipment equipment, Effect effect) {
    ConnectionPayload pl = new ConnectionPayload()
            .setCaster(origin)
            .setCondEffectImpact(ConnectionPayload.CondEffectImpact.IGNORE)
            .setSkill(skill)
            .setEquipment(equipment)
            .setEffect(effect);
    Connector.fireTopic(Connector.COND_TRIGGER_CHANGES, pl);
    return pl.condEffectImpact;
  }

  public static Stat getDefenseStatForDt(DamageType dt) {
    switch (dt) {
        case HEAT -> {
          return Stat.HEAT_RESIST;
        }
        case COLD -> {
          return Stat.COLD_RESIST;
        }
        case NORMAL -> {
          return Stat.ARMOR;
        }
        case MENTAL -> {
          return Stat.MENTAL_RESIST;
        }
        case LIGHT -> {
          return Stat.LIGHT_RESIST;
        }
        case DARK -> {
          return Stat.DARK_RESIST;
        }
        case SHOCK -> {
          return Stat.SHOCK_RESIST;
        }
        case TOXIC -> {
          return Stat.TOX_RESIST;
        }
        default -> {
          return null;
        }
    }
  }

    public static void addStats(Map<Stat, Integer> stats, Map<Stat, Integer> statsIncrease, int change) {
      for (Map.Entry<Stat, Integer> stat: statsIncrease.entrySet()) {
          int val = stat.getValue() * change;
          stats.put(stat.getKey(), stats.get(stat.getKey()) + val);
      }
    }
}
