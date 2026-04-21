package game.skills;

import framework.Logger;
import framework.Property;
import framework.connector.SubscriberSubscriptionConnection;
import framework.connector.ConnectionPayload;
import framework.connector.Connector;
import framework.connector.Subscriber;
import framework.graphics.text.Color;
import framework.graphics.text.TextAlignment;
import framework.graphics.text.TextEditor;
import framework.resources.SpriteLibrary;
import framework.states.Arena;
import framework.connector.Subscription;
import game.effects.Effect;
import game.effects.status.Guarded;
import game.effects.status.Protected;
import game.entities.Hero;
import game.entities.Multiplier;
import game.libraries.SkillLibrary;
import game.objects.Equipment;
import game.skills.logic.*;
import utils.CollectionUtils;
import utils.MyMaths;
import utils.Utils;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class Skill implements Subscriber {

  private static int counter;
  public int id;
  public Hero hero;
  public Equipment equipment;
  public Skill saveState;

  public String name;
  public String description;
  protected int[] iconPixels;
  protected String iconPath;
  protected String animationName = "action_w";

  public List<SkillTag> tags = new ArrayList<>();
  public List<AiSkillTag> aiTags = new ArrayList<>();

  protected TargetType targetType = TargetType.SINGLE;
  protected DamageType damageType = null;
  protected DamageMode damageMode = null;
  protected DamageType staticDamageType = null;
  protected DamageMode staticDamageMode = null;
  protected Double lifeSteal = 0.0;
  protected List<Effect> effects = new ArrayList<>();
  protected List<Effect> casterEffects = new ArrayList<>();
  protected Effect globalEffect = null;
  protected List<Resource> targetResources = new ArrayList<>();
  protected List<Resource> casterResources = new ArrayList<>();

  protected List<Integer> staticDmgTargets = new ArrayList<>();
  protected List<Multiplier> staticDmgMultipliers = new ArrayList<>();
  protected List<Multiplier> dmgMultipliers = new ArrayList<>();
  protected List<Multiplier> healMultipliers = new ArrayList<>();
  protected List<Multiplier> shieldMultipliers = new ArrayList<>();

  protected List<Hero> targets = new ArrayList<>();

  protected Integer accuracy = 100;
  protected Boolean cannotMiss = true;
  protected Integer countAsHits = 1;
  protected Integer move;
  protected Boolean moveTo = false;
  public Integer push = 0;
  protected Integer pull = 0;

  public int[] possibleTargetPositions = new int[0];
  public int[] possibleCastPositions = new int[0];

  protected Integer manaCost = 0;
  protected Integer lifeCost = 0;
  protected Integer dodgeCost = 0;
  protected Integer critChance = 0;
  protected Integer staticDmg = 0;
  protected Integer dmg = 0;
  protected Integer heal = 0;
  protected Integer shield = 0;
  protected Integer lethality = 0;
  protected Integer currentCd = 0;
  protected Integer maxCd = 0;

  public List<Subscription> subscriptions;
  public Map<String, Object> keyValues;

  /*AI
   *
   */
  public int getAIRating(Hero target) {
    return 0;
  }

  public int getAIArenaRating(Arena arena) {
    return 0;
  }

  protected int getRollRating(Hero target) {

    int lastEffectivePosition = this.hero.getLastEffectivePosition();
    int targetLifeAdvantage =
        target.getStat(Stat.CURRENT_LIFE) - this.hero.getStat(Stat.CURRENT_LIFE);

    if (target.getPosition() > this.hero.getPosition()) { // ROll Back
      if (this.hero.getPosition() == lastEffectivePosition) {
        return -10;
      }
      return targetLifeAdvantage / 2;

    } else {
      if (target.getPosition() == lastEffectivePosition) { // ROLL FORWARD
        return 10;
      }
      return targetLifeAdvantage / 2 * -1;
    }
  }

  /*Initiation
   *
   */

  public Skill() {
    this.name = getClassName();
    this.set(SkillLibrary.getSkillDTO(this.name));
  }

  public void set(SkillDTO dto) {
    if (dto == null) {
      return;
    }

    this.name = dto.name;
    this.description = dto.description;
    this.iconPath = dto.iconPath;
    this.animationName = dto.animationName;
    this.tags = dto.tags;
    this.aiTags = dto.aiTags;
    this.staticDmgTargets = dto.staticDmgTargets;
    this.staticDmg = dto.staticDmg;
    this.staticDamageMode = dto.staticDamageMode;
    this.staticDamageType = dto.staticDamageType;
    this.staticDmgMultipliers = dto.staticDmgMultipliers;
    this.targetType = dto.targetType;
    this.damageType = dto.damageType;
    this.damageMode = dto.damageMode;
    this.lifeSteal = dto.lifeSteal;
    this.targetResources = dto.targetResources;
    this.casterResources = dto.casterResources;
    this.dmgMultipliers = dto.dmgMultipliers;
    this.healMultipliers = dto.healMultipliers;
    this.shieldMultipliers = dto.shieldMultipliers;
    this.subscriptions = dto.subscriptions;
    if (this.subscriptions != null) {
      this.subscriptions.forEach(s->s.setSkill(this));
    }
    this.keyValues = Utils.copyKeyValues(dto.keyValues);
    this.manaCost = dto.manaCost;
    this.lifeCost = dto.lifeCost;
    this.dodgeCost = dto.dodgeCost;
    this.accuracy = dto.accuracy;
    this.critChance = dto.critChance;
    this.dmg = dto.dmg;
    this.heal = dto.heal;
    this.shield = dto.shield;
    this.countAsHits = dto.countsAsHits;
    this.lethality = dto.lethality;
    this.move = dto.move;
    this.moveTo = dto.moveTo;
    this.cannotMiss = dto.cannotMiss;
    this.possibleCastPositions = dto.possibleCastPositions;
    this.possibleTargetPositions = dto.possibleTargetPositions;
    this.maxCd = dto.maxCd;
    this.pull = dto.pull;
    this.push = dto.push;

    initEffects(dto);

    if (SpriteLibrary.hasSprite(this.getName())) {
      this.iconPixels = SpriteLibrary.getSprite(this.getName());
    } else {
      this.iconPixels =
              SpriteLibrary.sprite(
                      Property.SKILL_ICON_SIZE,
                      Property.SKILL_ICON_SIZE,
                      Property.SKILL_ICON_SIZE,
                      Property.SKILL_ICON_SIZE,
                      this.iconPath,
                      0);
      SpriteLibrary.addSprite(this.getName(), this.iconPixels);
    }

    saveState = new Skill();
    Skill.writeInitialsFromTo(this, saveState);
  }
  public Skill(SkillDTO dto) {
    this.set(dto);
  }

  public Skill(Hero hero) {
    this.id = ++counter;
    this.hero = hero;
  }

  private void initEffects(SkillDTO dto) {
    try {
      if (dto.effects != null) {
        for (SkillEffectDTO sed : dto.effects) {
          Class<?> effectClass = Class.forName(sed.className);
          Effect effect = (Effect) effectClass.getDeclaredConstructor().newInstance();
          effect.stacks = sed.stacks;
          effect.turns = sed.turns;
          effect.condition = sed.condition;
          this.effects.add(effect);
        }
      }
      if (dto.casterEffects != null) {
        for (SkillEffectDTO sed : dto.casterEffects) {
          Class<?> effectClass = Class.forName(sed.className);
          Effect effect = (Effect) effectClass.getDeclaredConstructor().newInstance();
          effect.stacks = sed.stacks;
          effect.turns = sed.turns;
          effect.condition = sed.condition;
          this.casterEffects.add(effect);
        }
      }
      if (dto.globalEffect != null) {
        Class<?> effectClass = Class.forName(dto.globalEffect.className);
        Effect effect = (Effect) effectClass.getDeclaredConstructor().newInstance();
        effect.turns = dto.globalEffect.turns;
        this.globalEffect = effect;
      }
    } catch (ClassNotFoundException
        | InvocationTargetException
        | InstantiationException
        | IllegalAccessException
        | NoSuchMethodException e) {
      return;
    }
  }
  public int getRank() {
    return 0;
  }

  public int getSpeed() {
    if (this.hero != null) {
      return this.hero.getCachedStat(Stat.DEXTERITY);
    }
    if (this.equipment != null) {
      return this.equipment.getHero().getCachedStat(Stat.DEXTERITY);
    }
    return 0;
  }

  public void getCurrentVersion() {
    this.setToInitial();
    trigger_castChanges();
  }

  public void setToInitial() {
    Logger.logLn("Set to initial");
    Skill.writeInitialsFromTo(this.saveState, this);
  }

  public boolean performCheck(Hero hero) {
    return Arrays.stream(this.possibleCastPositions).anyMatch(i -> i == hero.getTeamPosition());
  }

  public void turn() {}

  public int getLethality() {
    return this.lethality;
  }

  public String getUpperDescriptionFor(Hero hero) {
    return "";
  }

  public String getComboDescription(Hero hero) {
    return "";
  }

  public String getDescription() {
    return description;
  }
  public String getDescriptionFor(Hero hero) {
    return "";
  }

  public void addSubscriptions() {
    if (subscriptions != null) {
      for (Subscription subscription: this.subscriptions) {
        Connector.addSubscription(
            subscription.topicName, new SubscriberSubscriptionConnection(this, subscription));
      }
    }
  }

  public void removeSubscriptions() {
    Connector.removeSubscriptions(this);
  }

  // SKILL LOGIC
  public void perform() {
    this.hero.arena.logCard.addToLog(this.getName() + " performed by " + this.hero.getName() + ".");
    trigger_onPerform();
    this.hero.playAnimation(this.animationName);
    this.hero.payForSkill(this);
  }

  public void clearEffects() {
    this.effects = new ArrayList<>();
    this.casterEffects = new ArrayList<>();
  }

  public void resolve() {
    // init action summary
    this.trigger_onTarget();
    if (this.targetType.equals(TargetType.ARENA)) {
      this.hero.arena.setGlobalEffect(this.globalEffect);
      this.applySkillEffects(this.hero);
    } else {
      oncePerActivationEffect();
      this.trigger_changeTargets();
      for (Hero arenaTarget : targets) {

        if (this.targetType.equals(TargetType.SELF)
            || this.targetType.equals(TargetType.SINGLE_OTHER)
                || this.targetType.equals(TargetType.ALL_OTHER_ALLY)) {
          this.individualResolve(arenaTarget);
        } else {
          if (arenaTarget.hasPermanentEffect(Protected.class) && !arenaTarget.isAlly(this.hero)) {
            this.hero.arena.logCard.addToLog(arenaTarget.getName() + " is shielded!");
            return;
          }
          if (arenaTarget.hasPermanentEffect(Guarded.class) && !arenaTarget.isAlly(this.hero)) {
            arenaTarget = arenaTarget.getPermanentEffect(Guarded.class).origin;
          }
          int evasion = arenaTarget.getStat(Stat.DODGE);
          int hitChance = this.accuracy / 100;
          if (!this.cannotMiss && !MyMaths.success(hitChance - evasion)) {
            this.trigger_onMiss(arenaTarget);
            this.hero.arena.logCard.addToLog("Missed " + arenaTarget.getName() + "!");
            continue;
          }
          this.individualResolve(arenaTarget);
        }
        if (this.moveTo) {
          this.hero.arena.moveTo(this.hero, arenaTarget.getPosition(), false);
        }
      }
    }
  }

  protected void oncePerActivationEffect() {}

  protected void individualResolve(Hero target) {
    int dmg = getDmgWithMulti(target);
    int heal = this.getHealWithMulti(target);
    for (int i = 0; i < getCountsAsHits(); i++) {
      int dmgPerHit = dmg;
      int critChance = this.hero.getStat(Stat.CRIT_CHANCE);
      critChance += this.critChance;
      if (MyMaths.success(critChance)) {
        this.hero.arena.logCard.addToLog("Crit!");
        Logger.logLn("Crit!");
        dmgPerHit = (int) (dmgPerHit * 1.5);
        this.trigger_onCrit(target, this);
      }
      if (dmgPerHit > 0) {
        int dmgDone = target.damage(dmgPerHit, this.damageType, this.damageMode, this.hero, this, null, null, lethality);
        if (this.lifeSteal > 0) {
          this.hero.heal(MyMaths.percentageOf(lifeSteal, dmgDone), this.hero, this, null, this.equipment, false);
        }
      }
      if (heal > 0) {
        target.heal(heal, this.hero, this, null, null, false);
      }
      int shield = this.getShieldWithMulti(target);
      if (shield > 0) {
        target.shield(shield, this.hero, this, null, null);
      }
      this.applySkillEffects(target);
      this.customTargetEffect(target);
    }
  }
  public void customTargetEffect(Hero target){}

  public void applySkillEffects(Hero target) {

    for (Effect effect : this.casterEffects) {
      if (Effect.ChangeEffectType.FIELD.equals(effect.type)) {
        this.hero.arena.addFieldEffect(this.hero.getPosition(), effect, this.hero);
      } else {
        if (effect.condition != null && effect.condition.isMet(this, effect, this.hero, target)) {
          this.hero.addEffect(effect, this.hero, this);
        }
      }
    }
    if (!trigger_moveFailure(target)) {
      if (this.move != 0) {
        this.hero.arena.moveTo(
                target, target.getPosition() + (target.isTeam2() ? this.move : -1 * this.move), false);
      }
      if (this.push != null && this.push > 0) {
        this.hero.arena.push(target, push);
      }
      if (this.pull != null && this.pull > 0) {
        this.hero.arena.pull(target, pull);
      }
    }
    if (this.staticDmg != null || CollectionUtils.isNotEmpty(this.staticDmgMultipliers)) {
      staticDmgTargets.forEach(i -> {
        Hero staticTarget = this.hero.arena.getAtPosition(convertTargetPos(i));
        int dmg = getStaticDmgWithMult(staticTarget);
        staticTarget.damage(dmg, staticDamageType, staticDamageMode, this.hero, this, null, this.equipment, 0);
      });
    }
    for (Effect effect : this.effects) {
      if (Effect.ChangeEffectType.FIELD.equals(effect.type)) {
        this.hero.arena.addFieldEffect(target.getPosition(), effect, this.hero);
      } else {
        if (effect.condition != null && effect.condition.isMet(this, effect, this.hero, target)) {
          target.addEffect(effect, this.hero, this);
        }
      }
    }
    target.addSkillResources(this.targetResources, this, this.hero, this.equipment);
    this.hero.addSkillResources(this.casterResources, this, this.hero, this.equipment);
    // action summary add all effects
  }

  public void changeCurrentCdBy(int change) {
    if (this.maxCd == 0) { return; }
    if (this.currentCd + change < 0) {
      this.currentCd = 0;
    }
    this.currentCd += change;
  }
  protected int getShieldMultiBonus() {
    return this.getMultiplierBonus(this.shieldMultipliers);
  }

  protected int getHealMultiBonus() {
    return this.getMultiplierBonus(this.healMultipliers);
  }

  protected int getDmgMultiBonus() {
    return this.getMultiplierBonus(this.dmgMultipliers);
  }
  protected int getStaticDmgMultiBonus() {
    return this.getMultiplierBonus(this.staticDmgMultipliers);
  }

  protected int getMultiplierBonus(List<Multiplier> multipliers) {
    if (multipliers == null) {
      return 0;
    }
    int result = 0;
    for (Multiplier m : multipliers) {
      result += (int) (m.percentage * this.hero.getStat(m.prof));
    }
    return result;
  }

  public int[] setupTargetMatrix() {
    if (this.targetType == null) {
      return new int[0];
    }
    List<Integer> targetList = new ArrayList<>();

    for (int pos : this.possibleTargetPositions) {
      int targetPos = convertTargetPos(pos);
      if (this.targetType.equals(TargetType.SINGLE_OTHER) && this.hero.getPosition() == targetPos) {
        continue;
      }
      targetList.add(targetPos);
    }
    if (targetType.equals(TargetType.SINGLE) && targetList.stream().anyMatch(i -> i > 2)) {
      targetList.addAll(List.of(0, 1, 2));
    }
    Collections.sort(targetList);
    int[] targets = new int[targetList.size()];
    for (int i = 0; i < targets.length; i++) {
      targets[i] = targetList.get(i);
    }

    return targets;
  }

  public int[] getConvertedTargetPos() {
    return convertTargetPos(this.possibleTargetPositions);
  }

  public int convertTargetPos(int pos) {
    return this.hero.isTeam2() ? Arena.lastEnemyPos - pos : pos;
  }

  public int[] convertTargetPos(int[] pos) {
    for (int i = 0; i < pos.length; i++) {
      pos[i] = convertTargetPos(pos[i]);
    }
    return pos;
  }

  // triggers

  public void trigger_onPerform() {
    ConnectionPayload pl = new ConnectionPayload(1).setSkill(this).setCaster(this.hero);
    Connector.fireTopic(Connector.ON_PERFORM, pl);
  }

  public void trigger_onCrit(Hero target, Skill cast) {
    ConnectionPayload criticalTriggerPayload =
        new ConnectionPayload(1).setTarget(target).setSkill(cast);
    Connector.fireTopic(Connector.CRITICAL_TRIGGER, criticalTriggerPayload);
  }
  public boolean trigger_moveFailure(Hero target) {
    ConnectionPayload pl = new ConnectionPayload(1)
            .setSkill(this)
            .setCaster(this.hero)
            .setTarget(target);
    Connector.fireTopic(Connector.IS_MOVE_FAILURE, pl);
    return pl.failure;
  }
  public void trigger_castChanges() {
    ConnectionPayload payload = new ConnectionPayload(1).setSkill(this).setCaster(this.hero);
    Connector.fireTopic(Connector.CAST_CHANGE, payload);
  }

  public void trigger_onTarget() {
    ConnectionPayload pl = new ConnectionPayload(1)
            .setSkill(this);
    Connector.fireTopic(Connector.ON_TARGET, pl);
  }

  public void trigger_onMiss(Hero target) {
    ConnectionPayload pl = new ConnectionPayload(1)
            .setTarget(target)
            .setCaster(this.hero)
            .setSkill(this);
    Connector.fireTopic(Connector.ON_MISS, pl);
  }
  public void trigger_changeTargets() {
    ConnectionPayload payload = new ConnectionPayload(1).setSkill(this).setCaster(this.hero);
    Connector.fireTopic(Connector.TARGET_CHANGE, payload);
  }

  /*
   *       Getters / Setters
   *
   *
   *
   *
   */

  public int getLifeCost(Hero caster) {
    return lifeCost;
  }

  protected List<Effect> of(Effect[] effects) {
    List<Effect> result = new ArrayList<>();
    Collections.addAll(result, effects);
    return result;
  }

  protected List<Multiplier> of(Multiplier[] multiplier) {
    List<Multiplier> result = new ArrayList<>();
    Collections.addAll(result, multiplier);
    return result;
  }

  public TargetType getTargetType() {
    return targetType;
  }

  public void setTargetType(TargetType targetType) {
    this.targetType = targetType;
  }

  public boolean isPassive() {
    return this.tags.contains(SkillTag.PASSIVE);
  }

  public List<Effect> getEffects() {
    return effects;
  }

  public void setEffects(List<Effect> effects) {
    this.effects = effects;
  }

  public List<Effect> getCasterEffects() {
    return casterEffects;
  }

  public void setCasterEffects(List<Effect> casterEffects) {
    this.casterEffects = casterEffects;
  }

  public List<Multiplier> getDmgMultipliers() {
    return dmgMultipliers;
  }

  public void setDmgMultipliers(List<Multiplier> dmgMultipliers) {
    this.dmgMultipliers = dmgMultipliers;
  }

  public List<Multiplier> getHealMultipliers() {
    return healMultipliers;
  }

  public List<Multiplier> getShieldMultipliers() {
    return shieldMultipliers;
  }

  public void setHealMultipliers(List<Multiplier> healMultipliers) {
    this.healMultipliers = healMultipliers;
  }

  public int getLifeCost() {
    return lifeCost;
  }

  public void setLifeCost(int lifeCost) {
    this.lifeCost = lifeCost;
  }

  public int getAccuracy() {
    return accuracy;
  }

  public void setAccuracy(int accuracy) {
    this.accuracy = accuracy;
  }

  public Integer getDodgeCost() {
    return dodgeCost;
  }

  public void setDodgeCost(Integer dodgeCost) {
    this.dodgeCost = dodgeCost;
  }

  public int getManaCost() {
    return manaCost;
  }

  public void setManaCost(int manaCost) {
    this.manaCost = manaCost;
  }
  public DamageMode getDamageMode() {
    return this.damageMode;
  }
  public int getDmg(Hero target) {
    int level = this.hero != null? this.hero.getLevel() : 1;
    return MyMaths.getLevelStat(dmg, level);
  }
  public int getStaticDmg(Hero target) {
    int level = this.hero != null? this.hero.getLevel() : 1;
    return MyMaths.getLevelStat(staticDmg, level);
  }

  public int getDmgWithMulti(Hero target) {
    return getDmg(target) + getDmgMultiBonus();
  }
  public int getStaticDmgWithMult(Hero target) {
    return getStaticDmg(target) + getStaticDmgMultiBonus();
  }

  public int getShieldWithMulti(Hero target) {
    int baseShield = this.getShield(target);
    int multiBonus = getShieldMultiBonus();
    return baseShield + multiBonus;
  }

  public int getHealWithMulti(Hero target) {
    int baseHeal = this.getHeal(target);
    int multiplierBonus = getHealMultiBonus();
    return baseHeal + multiplierBonus;
  }

  public int getHeal(Hero target) {
    return MyMaths.getLevelStat(heal, this.hero.getLevel());
  }

  public void setPower(int power) {
    this.dmg = power;
  }

  public int getCountsAsHits() {
    return this.countAsHits;
  }

  public void setCountsAsHits(int countsAsHits) {
    this.countAsHits = countsAsHits;
  }

  public String getIcon() {
    return "aa_blaster";
  }

  public int[] getIconPixels() {
    return iconPixels;
  }

  public List<Resource> getTargetResources() {
    return targetResources;
  }

  public void setTargetResources(List<Resource> targetResources) {
    this.targetResources = targetResources;
  }

  public String getName() {
    return this.name;
  }
  public String getClassName() {
    String[] nameSplit = this.getClass().getName().split("\\.");
    return nameSplit[nameSplit.length - 1];
  }
  public DamageType getDamageType() {
    return damageType;
  }

  public Integer getMove() {
    return move;
  }
  public boolean isMoveTo() {
    return moveTo;
  }
  public Integer getMaxCd() {
    return maxCd;
  }

  public Skill setMaxCd(Integer maxCd) {
    this.maxCd = maxCd;
    return this;
  }

  public Integer getCurrentCd() {
    return currentCd;
  }

  public Skill setCurrentCd(Integer currentCd) {
    this.currentCd = currentCd;
    return this;
  }

  public void setCannotMiss(boolean bl) {
    this.cannotMiss = bl;
  }

  public boolean isCannotMiss() {
    return cannotMiss;
  }

  public int getShield(Hero target) {
    return MyMaths.getLevelStat(shield, this.hero.getLevel());
  }

  public String getTargetString() {
    StringBuilder builder = new StringBuilder();
    if (this.isPassive()) {
      return "Passive";
    }
    List<Integer> castPosList = Arrays.stream(this.possibleCastPositions).boxed().toList();
    for (int i = 0; i < Arena.firstEnemyPos; i++) {
      if (castPosList.contains(i)) {
        builder.append("[FTT]");
      } else {
        builder.append("[EMT]");
      }
    }
    builder.append(" ");

    if (this.targetType.equals(TargetType.SELF)) {
      builder.append("Self");
    } else if (targetType.equals(TargetType.ARENA)) {
      builder.append("Arena");
    } else if (targetType.equals(TargetType.ALL)) {
      builder.append("All");
    } else if(this.possibleTargetPositions != null) {
      List<Integer> targetPosList = Arrays.stream(this.possibleTargetPositions).boxed().toList();
      if (targetPosList.stream().anyMatch(i -> i < Arena.firstEnemyPos)) {
        for (int i = 0; i < Arena.numberPositions; i++) {
          if (targetPosList.contains(i)) {
            if (targetType.equals(TargetType.SINGLE_OTHER)) {
              builder.append("[OTT]");
            } else if (targetType.equals(TargetType.SINGLE)) {
              builder.append("[FTT]");
            } else if (targetType.equals(TargetType.ALL_TARGETS)) {
              builder.append("[FTA]");
            }
          } else {
            builder.append("[EMT]");
          }
        }
      } else {
        for (int i = Arena.firstEnemyPos; i <= Arena.lastEnemyPos; i++) {
          if (targetPosList.contains(i)) {
            if (targetType.equals(TargetType.SINGLE)||targetType.equals(TargetType.SINGLE_OTHER)) {
              builder.append("[ETT]");
            } else if (targetType.equals(TargetType.ALL_TARGETS)) {
              builder.append("[ETA]");
            }
          } else {
            builder.append("[EMT]");
          }
        }
      }
    }
    return builder.toString();
  }

  protected String getDmgString() {
    int fullDmg = this.getDmgWithMulti(null);
    int dmg = this.getDmg(null);
    String pureDmg = dmg == 0 ? "" : dmg + "";
    StringBuilder builder = new StringBuilder();
    Iterator<Multiplier> iter = this.dmgMultipliers.iterator();
    while (iter.hasNext()) {
      Multiplier mult = iter.next();
      String profColor = mult.prof.getColorKey();
      builder.append(profColor);
      builder
          .append((int) (mult.percentage * 100))
          .append("%")
          .append(mult.prof.getReference())
          .append("{001}");
      if (iter.hasNext()) {
        builder.append("+");
      }
    }
    String multiDmg = builder.toString();
    StringBuilder resultBuilder = new StringBuilder();
    resultBuilder.append(fullDmg);
    if (!multiDmg.isEmpty()) {
      resultBuilder.append(" (");
      if (!pureDmg.isEmpty()) {
        resultBuilder.append(pureDmg);
        resultBuilder.append("+");
      }
      resultBuilder.append(multiDmg);
      resultBuilder.append(")");
    }
    return resultBuilder.toString();
  }

  protected String getHealString() {
    int fullHeal = this.getHealWithMulti(null);
    int heal = this.getHeal(null);
    String pureHeal = heal == 0 ? "" : heal + "";
    StringBuilder builder = new StringBuilder();
    Iterator<Multiplier> iter = this.healMultipliers.iterator();
    while (iter.hasNext()) {
      Multiplier mult = iter.next();
      String profColor = mult.prof.getColorKey();
      builder.append(profColor);
      builder
          .append((int) (mult.percentage * 100))
          .append("%")
          .append(mult.prof.getReference())
          .append("{001}");
      if (iter.hasNext()) {
        builder.append("+");
      }
    }
    String multiHeal = builder.toString();
    StringBuilder resultBuilder = new StringBuilder();
    resultBuilder.append(fullHeal);
    if (!multiHeal.isEmpty()) {
      resultBuilder.append(" (");
      if (!pureHeal.isEmpty()) {
        resultBuilder.append(pureHeal);
        resultBuilder.append("+");
      }
      resultBuilder.append(multiHeal);
      resultBuilder.append(")");
    }
    return resultBuilder.toString();
  }

  protected String getShieldString() {
    int fullShield = this.getShieldWithMulti(null);
    int shield = this.getShield(null);
    String pureShield = shield == 0 ? "" : shield + "";
    StringBuilder builder = new StringBuilder();
    Iterator<Multiplier> iter = this.shieldMultipliers.iterator();
    while (iter.hasNext()) {
      Multiplier mult = iter.next();
      String profColor = mult.prof.getColorKey();
      builder.append(profColor);
      builder
          .append((int) (mult.percentage * 100))
          .append("%")
          .append(mult.prof.getReference())
          .append("{001}");
      if (iter.hasNext()) {
        builder.append("+");
      }
    }
    String multiShield = builder.toString();
    StringBuilder resultBuilder = new StringBuilder();
    resultBuilder.append(fullShield);
    if (!multiShield.isEmpty()) {
      resultBuilder.append(" (");
      if (!pureShield.isEmpty()) {
        resultBuilder.append(pureShield);
        resultBuilder.append("+");
      }
      resultBuilder.append(multiShield);
      resultBuilder.append(")");
    }
    return resultBuilder.toString();
  }

  public Color getCostColor() {
    Color color = Color.WHITE;
    if (manaCost != 0) {
      color = Color.BLUE;
    }
    if (lifeCost != 0) {
      color = Color.RED;
    }
    return color;
  }
  public String getCostStringGUI() {
    String amount = "0";
    if (manaCost != 0) {
      amount = manaCost + "";
    }
    if (lifeCost != 0) {
      amount = lifeCost + "";
    }
    return amount;
  }
  public String getDmgStringGUI() {
    StringBuilder builder = new StringBuilder();
    if (this.getDmg(null) != 0 || !this.dmgMultipliers.isEmpty()) {
      builder.append(getDmgString());
    }
    return builder.toString();
  }

  public String getHealStringGUI() {
    StringBuilder builder = new StringBuilder();
    if ((this.getHeal(null) != 0 || !this.healMultipliers.isEmpty())) {
      if (this.getHeal(null) != 0 || !this.healMultipliers.isEmpty()) {
        builder.append(getHealString());
      }
    }
    return builder.toString();
  }

  public String getShieldStringGUI() {
    StringBuilder builder = new StringBuilder();
    if ((this.getShield(null) != 0 || !this.shieldMultipliers.isEmpty())) {
      if (this.getShield(null) != 0 || !this.shieldMultipliers.isEmpty()) {
        builder.append(getShieldString());
      }
    }
    return builder.toString();
  }

  public String getEffectString() {
    return getEffectStringForEffectList(this.effects, "Target Effects: ");
  }

  public String getCasterEffectString() {
    return getEffectStringForEffectList(this.casterEffects, "Caster Effects: ");
  }

  public String getEffectStringForEffectList(List<Effect> effectList, String listPrefix) {
    if (effectList.isEmpty()) {
      return "";
    }
    StringBuilder effectString = new StringBuilder();
    effectString.append(listPrefix);
    Iterator<Effect> iterator = effectList.iterator();
    while (iterator.hasNext()) {
      Effect effect = iterator.next();
      int value = effect.stackable ? effect.stacks : effect.turns;
      effectString
          .append(effect.getIconString())
          .append("(")
          .append(value == -1 ? "~" : value)
          .append(")");
      if (iterator.hasNext()) {
        effectString.append(", ");
      }
    }

    return effectString.toString();
  }

  public static void writeInitialsFromTo(Skill from, Skill to) {
    to.effects = Utils.copyEffect(from.effects);
    to.casterEffects = Utils.copyEffect(from.casterEffects);
    to.targetResources = Utils.copyResource(from.targetResources);
    to.casterResources = Utils.copyResource(from.casterResources);
    to.dmgMultipliers = Utils.copyMultiplier(from.dmgMultipliers);
    to.healMultipliers = Utils.copyMultiplier(from.healMultipliers);
    to.shieldMultipliers = Utils.copyMultiplier(from.shieldMultipliers);
    to.subscriptions = from.subscriptions;
    if (to.subscriptions != null) {
      to.subscriptions.forEach(s->s.setSkill(to));
    }
    to.manaCost = from.manaCost;
    to.lifeCost = from.lifeCost;
    to.dodgeCost = from.dodgeCost;
    to.accuracy = from.accuracy;
    to.critChance = from.critChance;
    to.damageType = from.damageType;
    to.damageMode = from.damageMode;
    to.dmg = from.dmg;
    to.heal = from.heal;
    to.shield = from.shield;
    to.maxCd = from.maxCd;
    to.cannotMiss = from.cannotMiss;
    to.lethality = from.lethality;
    to.countAsHits = from.countAsHits;
    to.move = from.move;
    to.moveTo = from.moveTo;
  }

  public void setTargets(Hero[] entitiesAt) {
    this.targets = List.of(entitiesAt);
  }

  public List<Hero> getTargets() {
    return this.targets;
  }

  public int getSort() {
    if (this.tags.contains(SkillTag.PRIMARY)) {
      return 1;
    }
    if (this.tags.contains(SkillTag.TACTICAL)) {
      return 2;
    }
    if (this.tags.contains(SkillTag.ULT)) {
      return 3;
    }
    if (this.equipment != null) {
      return 4;
    }
    return 5;
  }

  public void reset() {}

  public List<Effect> getTargetEffects() {
    return this.effects;
  }
}
