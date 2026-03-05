package game.effects;

import framework.Property;
import framework.connector.Connection;
import framework.connector.ConnectionPayload;
import framework.connector.Connector;
import framework.graphics.GUIElement;
import framework.graphics.text.Color;
import framework.states.Arena;
import game.entities.Hero;
import game.skills.logic.Condition;
import utils.Utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Effect {

  public final String name;
  public static String ICON_STRING = "%%%";
  public String iconString;
  public int turns = -1;
  public int stacks = 1;
  public boolean used = false;

  public Hero origin;
  public Hero hero;
  public Arena arena;

  public int position;

  public Condition condition;

  public List<String> negates;
  public String description;
  public boolean stackable;
  public ChangeEffectType type;
  public List<SubType> subTypes;
  public Durability durability;

  public Map<String, String> triggerMap = new HashMap<>();
  public Map<String, Object> keyValues = new HashMap<>();

  public Effect() {
    this.name = getName();
    this.set(EffectLibrary.getEffectDTO(name));
  }

  public Effect(EffectDTO dto) {
    this.name = dto.name;
    this.set(dto);
  }

  public Effect(
      String iconString,
      int turns,
      int stacks,
      Hero origin,
      Hero hero,
      Condition condition,
      List<String> negates,
      String name,
      String description,
      boolean stackable,
      ChangeEffectType type,
      Map<String, String> triggerMap,
      int position,
      Durability durability,
      List<SubType> subTypes,
      Map<String, Object> keyValues) {
    this.iconString = iconString;
    this.turns = turns;
    this.stacks = stacks;
    this.origin = origin;
    this.hero = hero;
    this.condition = condition;
    this.negates = negates;
    this.name = name;
    this.description = description;
    this.stackable = stackable;
    this.type = type;
    this.triggerMap = triggerMap;
    this.position = position;
    this.durability = durability;
    this.subTypes = subTypes;
    this.keyValues = Utils.copyKeyValues(keyValues);
  }

  public void set(EffectDTO dto) {
    if (dto == null) {
      return;
    }
    this.iconString = dto.iconString;
    this.description = dto.description;
    this.stackable = dto.stackable;
    this.type = dto.type;
    this.negates = dto.negates;
    this.durability = dto.durability;
    this.triggerMap = dto.triggerMap;
    this.subTypes = dto.subTypes;
    this.keyValues = Utils.copyKeyValues(dto.keyValues);
  }

  public void addSubscriptions() {
    if (triggerMap != null && !triggerMap.isEmpty()) {
      for (Map.Entry<String, String> entry : triggerMap.entrySet()) {
        Connector.addSubscription(entry.getKey(), new Connection(this, entry.getValue()));
      }
    }
  }

  public void addStack() {
    this.addStacks(1);
  }
  public void addStacks(int stacks) {
    this.stacks += stacks;
  }

  public void removeFromHero() {
    Connector.fireTopic(Connector.EFFECT_REMOVED, new ConnectionPayload().setEffect(this));
    Connector.removeSubscriptions(this);
  }

  public void tick() {
    if (turns != -1) {
      this.turns--;
    }
  }

  public void addStacksToSprite(int[] effectSprite) {
    if (stackable) {
      int x = Property.EFFECT_ICON_SIZE - 2;
      for (int i = 0; i < this.stacks; i++) {
        GUIElement.verticalLine(
            x,
            Property.EFFECT_ICON_SIZE - 3,
            Property.EFFECT_ICON_SIZE - 1,
            Property.EFFECT_ICON_SIZE,
            effectSprite,
            Color.RED);
        x -= 2;
      }
    }
  }

  public String getDetailInfo() {
    if (stackable) {
      return "Stacks:" + this.stacks + " ";
    }
    if (turns > 0) {
      return "Turns:" + this.turns + " ";
    }
    return "";
  }

  public String getIconString() {
    return "[" + this.iconString + "]";
  }

  public static String getStaticIconString() {
    return "[" + ICON_STRING + "]";
  }

  @Override
  public String toString() {
    return "Effect{"
        + "turns="
        + turns
        + ", stacks="
        + stacks
        + ", name='"
        + name
        + '\''
        + ", type="
        + type
        + '}';
  }

  //    public static Effect getRdmDebuff() {
  //        List<Effect> effectList = List.of(new Burning(1), new Injured(1), new Bleeding(1), new
  // Dazed(1), new Disenchanted(1), new Taunted(1));
  //        Random r = new Random();
  //        return effectList.get(r.nextInt(effectList.size()));
  //    }

  public String getName() {
    String[] nameSplit = this.getClass().getName().split("\\.");
    return nameSplit[nameSplit.length - 1];
  }

  public Effect copy() {
    return new Effect(
        iconString,
        turns,
        stacks,
        origin,
        hero,
        condition,
        negates,
        name,
        description,
        stackable,
        type,
        triggerMap,
        position,
        durability,
        subTypes,
        keyValues);
  }


  public enum ChangeEffectType {
    ARENA,
    FIELD,
    HERO,
    STATUS,
    STAT;
  }

  public enum SubType {
    ATK_ENHANCE,
    COSMIC,
    HEAT,
    COLD,
    TOXIC,
    SHOCK,
    LIGHT,
    DARK,
    MENTAL,
    NORMAL,
    BONUS,
    STAT,
    UNIQUE,
    WEAKNESS,
    BUFF,
    DEBUFF;
  }

  public enum Durability {
    ONCE,
    TIME,
    ON_ENTER,
    STACK,
    PERSISTENT;
  }

}
