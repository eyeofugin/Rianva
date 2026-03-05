package game.effects;

import game.skills.logic.Stat;

import java.util.List;
import java.util.Map;

public class EffectDTO {
  public String name;
  public String iconString;
  public String description;
  public boolean stackable;
  public Effect.ChangeEffectType type;
  public List<Effect.SubType> subTypes;
  public List<String> negates;
  public Map<String, String> triggerMap;
  public Map<String, Object> keyValues;

  public Effect.Durability durability;
}
