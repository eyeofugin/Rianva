package game.effects;

import framework.Property;
import framework.connector.SubscriberSubscriptionConnection;
import framework.connector.ConnectionPayload;
import framework.connector.Connector;
import framework.connector.Subscriber;
import framework.graphics.GUIElement;
import framework.graphics.text.Color;
import framework.states.Arena;
import framework.connector.Subscription;
import game.entities.Hero;
import game.libraries.EffectLibrary;
import game.skills.logic.Condition;
import game.skills.logic.DamageType;
import game.skills.logic.Stat;
import utils.Utils;

import java.util.*;

public class Effect implements Subscriber {

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
  public DamageType damageType;

  public List<Subscription> subscriptions = new ArrayList<>();
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
      List<Subscription> subscriptions,
      int position,
      Durability durability,
      DamageType damageType,
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
    this.damageType = damageType;
    this.stackable = stackable;
    this.type = type;
    this.position = position;
    this.subscriptions = subscriptions;
    if (this.subscriptions != null) {
      this.subscriptions.forEach(s->{
        s.setEffect(this);
        s.setPosition(this.position);
      });
    }
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
    this.damageType = dto.damageType;
    this.subscriptions = dto.subscriptions;
    if (this.subscriptions != null) {
      this.subscriptions.forEach(s->{
        s.setEffect(this);
        s.setPosition(this.position);
      });
    }
    this.subTypes = dto.subTypes;
    this.keyValues = Utils.copyKeyValues(dto.keyValues);
  }

  public int getRank() {
    return 0;
  }

  public int getSpeed() {
    if (ChangeEffectType.ARENA.equals(this.type)) {
      return 0;
    }
    if (this.origin != null) {
      return this.origin.getCachedStat(Stat.DEXTERITY);
    }
    if (this.hero != null) {
      return this.hero.getCachedStat(Stat.DEXTERITY);
    }
    return 0;
  }

  public void addSubscriptions() {
    if (subscriptions != null) {
      for (Subscription subscription : subscriptions) {
        Connector.addSubscription(subscription.topicName, new SubscriberSubscriptionConnection(this, subscription));
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
    Connector.fireTopic(Connector.EFFECT_REMOVED, new ConnectionPayload(1).setEffect(this));
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

  public void randomSpread() {
    if (this.arena == null) {
      return;
    }
    Effect copy = this.copy();
    int dir = Math.random() < 0.5 ? -1 : 1;

    boolean success = tryToSpread(this.position + dir, copy);
    if (success) {
      return;
    }
    tryToSpread(this.position-dir, copy);
  }

  private boolean tryToSpread(int target, Effect copy) {
    if (target >= 0 && target <= 7) {
      Hero heroAt = this.arena.getAtPosition(target);
      if (heroAt != null && heroAt.team == this.arena.getAtPosition(this.position).team) {
        this.arena.addFieldEffect(target, copy, this.origin);
        return true;
      }
    }
    return false;
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
        + damageType
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
        subscriptions,
        position,
        durability,
            damageType,
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
