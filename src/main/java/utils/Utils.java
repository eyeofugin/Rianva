package utils;

import framework.connector.ConnectionPayload;
import framework.connector.Connector;
import game.entities.Hero;
import game.entities.Multiplier;
import game.effects.Effect;
import game.objects.Equipment;
import game.skills.Skill;
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

  public static int chanceChanges(Hero target, Hero origin, int value, Skill skill, Equipment equipment, Effect effect, int depth) {
    ConnectionPayload pl = new ConnectionPayload(++depth)
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

  public static void onMark(Hero marked, int depth) {
    ConnectionPayload pl = new ConnectionPayload(++depth)
            .setTarget(marked);
    Connector.fireTopic(Connector.ON_MARK, pl);
  }

  public static int statChangesChanges(Hero target, Hero origin, Stat stat, int value, Skill skill, Equipment equipment, Effect effect, int depth) {
    ConnectionPayload pl = new ConnectionPayload(++depth)
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
  public static ConnectionPayload.CondEffectImpact condTriggerChanges(Hero origin, Skill skill, Equipment equipment, Effect effect, int depth) {
    ConnectionPayload pl = new ConnectionPayload(++depth)
            .setCaster(origin)
            .setCondEffectImpact(ConnectionPayload.CondEffectImpact.IGNORE)
            .setSkill(skill)
            .setEquipment(equipment)
            .setEffect(effect);
    Connector.fireTopic(Connector.COND_TRIGGER_CHANGES, pl);
    return pl.condEffectImpact;
  }
}
