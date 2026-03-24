package game.entities;

import framework.Logger;
import framework.Property;
import framework.connector.ConnectionPayload;
import framework.connector.Connector;
import framework.graphics.GUIElement;
import framework.graphics.text.Color;
import framework.graphics.text.TextAlignment;
import framework.resources.SpriteLibrary;
import framework.resources.SpriteUtils;
import framework.states.Arena;
import game.effects.Effect;
import game.effects.EffectLibrary;
import game.effects.stat.Brittle;
import game.effects.stat.Mighty;
import game.effects.status.Cleanse;
import game.effects.status.Debuff;
import game.effects.status.Immunity;
import game.effects.status.Bleeding;
import game.objects.Equipment;
import game.skills.Skill;
import game.skills.trees.genericskills.S_Boots;
import game.skills.trees.genericskills.S_Skip;
import game.skills.logic.*;
import utils.CollectionUtils;
import utils.FileWalker;
import utils.MyMaths;

import java.util.*;

public class Hero extends GUIElement {

  public static String DRAFT = "DRAFT";
  public static String ARENA = "ARENA";
  public static String BUILDER = "BUILDER";

  private static final String STAT_PATH = "/data/stats.json";

  public Arena arena;
  public Animator anim;
  public HeroTeam team;
  public HeroTeam enemyTeam;
  protected Map<String, int[][]> sprites = new HashMap<>();
  public String basePath = "";

  protected static int idCounter;
  protected int id;
  protected String name;
  protected List<Skill> skills = new ArrayList<>();
  protected List<Skill> learnableSkillList = new ArrayList<>();
  protected List<Equipment> equipments = new ArrayList<>();
  protected String portraitName;
  public static int draftDimensionX = 64;
  public static int draftDimensionY = 74;

  protected int level = 1;
  protected int exp = 0;
  protected int[] levelCaps = new int[] {0, 5, 10, 15, 20};
  protected int draftChoice = 0;
  protected Map<Stat, Integer> baseStats = new HashMap<>();
  protected Map<Stat, Integer> stats = new HashMap<>();
  protected Map<Stat, Integer> statChanges = new HashMap<>();
  protected Map<Stat, Integer> statCache = new HashMap<>();
  protected List<Effect> effects = new ArrayList<>();
  //    protected boolean movedLast = false;
  protected boolean moved = false;
  protected Stat secondaryResource = Stat.ENERGY;
  protected Role role = Role.NONE;

  private int position;

  private int yf = 0;

  public int effectiveRange = 0;

  public static Hero getForDraft() {
    return null;
  }
  ;

  // for test purposes
  public Hero() {}

  public Hero(HeroDTO dto) {
    this.width = 64;
    this.height = 110;
    this.pixels = new int[this.width * this.height];
    this.basePath = dto.path;
    this.name = dto.name;
    this.role = dto.role;
    this.baseStats = dto.baseStats;
    this.initAnimator(dto);
    this.initSkills(dto);
    this.setLevel(1);
  }

  protected Hero(String name) {
    this.id = idCounter++;
    this.width = 64;
    this.height = 110;
    this.pixels = new int[this.width * this.height];
    this.name = name;
    this.stats.putAll(statBase());
    this.statCache.putAll(this.stats);
  }

  public void setPosition(int position, Arena arena) {
    this.position = position;
    this.arena = arena;
  }

  public void arenaStart() {
    this.resetResources();
    this.startTrigger();
    this.addGenericSkills();
  }

  private void startTrigger() {
    this.equipments.forEach(Equipment::addSubscriptions);
    this.skills.forEach(Skill::addSubscriptions);
  }

  private void addGenericSkills() {
    this.addSkill(new S_Boots());
    this.addSkill(new S_Skip());
  }

  public void resetResources() {
    this.stats.put(Stat.CURRENT_LIFE, this.stats.get(Stat.VITALITY));
    this.stats.put(Stat.CURRENT_ENERGY, this.stats.get(Stat.ENERGY));
  }

  public void leaveArena() {
    this.effects.forEach(Effect::removeFromHero);
    this.equipments.forEach(Equipment::reset);
    this.equipments.forEach(Equipment::removeSubscriptions);
    this.skills.removeIf(s -> s instanceof S_Boots || s instanceof S_Skip);
    this.skills.forEach(Skill::reset);
    this.skills.forEach(Skill::removeSubscriptions);
  }

  public void initBasePath(String name) {
    String base = "entities/heroes/";
    this.basePath = base + name;
  }

  protected void initAnimator() {}
  ;

  protected void initSkills() {}

  private void initAnimator(HeroDTO dto) {
    String idleAnim = dto.idleAnim != null ? dto.idleAnim : "idle_w.png";
    String damagedAnim = dto.damagedAnim != null ? dto.damagedAnim : "damaged_w.png";
    String actionAnim = dto.actionAnim != null ? dto.actionAnim : "action_w.png";
    this.anim = new Animator();
    anim.width = 64;
    anim.height = 64;
    anim.setupAnimation(this.basePath + "/sprites/" + idleAnim, "idle", new int[] {40, 80});
    anim.setupAnimation(this.basePath + "/sprites/" + damagedAnim, "damaged", new int[] {40, 80});
    anim.setupAnimation(this.basePath + "/sprites/" + actionAnim, "action", new int[] {15, 30, 45});

    anim.setDefaultAnim("idle");
    anim.currentAnim = anim.getDefaultAnim();
    anim.onLoop = true;
  }

  private void initSkills(HeroDTO dto) {
    //    dto.learnedSkills.stream().map(SkillLibrary::getSkillDTO).forEach(this::addSkill);
    //
    // dto.learnableSkills.stream().map(SkillLibrary::getSkillDTO).forEach(this::addLearnableSkill);
  }

  public void addSkill(Skill skill) {
    this.skills.add(skill);
    skill.hero = this;
  }

  private void addLearnableSkill(Skill skill) {
    this.learnableSkillList.add(skill);
    skill.hero = this;
  }

  protected void initStats() {
    this.baseStats = loadLevelStats();
  }

  protected Map<Stat, Integer> statBase() {
    Map<Stat, Integer> base = new HashMap<>();
    base.put(Stat.MIND, 0);
    base.put(Stat.BODY, 0);
    base.put(Stat.DEXTERITY, 0);
    base.put(Stat.VITALITY, 0);

    // ResourceStats
    base.put(Stat.CURRENT_LIFE, 0);
    base.put(Stat.LIFE_REGAIN, 0);

    base.put(Stat.ENERGY, 0);
    base.put(Stat.CURRENT_ENERGY, 0);

    //        base.put(Stat.MAX_ACTION, 3);
    //        base.put(Stat.CURRENT_ACTION, 3);

    base.put(Stat.CRIT_CHANCE, 0);
    base.put(Stat.ACCURACY, 0);
    base.put(Stat.DODGE, 0);
    base.put(Stat.SHIELD, 0);

    base.put(Stat.ARMOR, 0);
    base.put(Stat.HEAT_RESIST, 0);
    base.put(Stat.COLD_RESIST, 0);
    base.put(Stat.LIGHT_RESIST, 0);
    base.put(Stat.DARK_RESIST, 0);
    base.put(Stat.TOX_RESIST, 0);
    base.put(Stat.MENTAL_RESIST, 0);
    base.put(Stat.SHOCK_RESIST, 0);

    return base;
  }

  //    protected void randomizeSkills() {
  //        this.skills = new Skill[5];
  //        int primaryUpper = this.primary.length-1;
  //        this.skills[0] = primary[MyMaths.getFromToIncl(0,primaryUpper, new ArrayList<>())];
  //        int tacticalUpper = this.tactical.length-1;
  //        int tac1 = MyMaths.getFromToIncl(0, tacticalUpper, new ArrayList<>());
  //        int tac2 = MyMaths.getFromToIncl(0, tacticalUpper, List.of(tac1));
  //        this.skills[1] = tactical[tac1];
  //        this.skills[2] = tactical[tac2];
  //        this.skills[3] = ult;
  //        this.skills[4] = new S_Skip(this);
  //    }

  public int getLastEffectivePosition() {
    return this.team.getFirstPosition() - (effectiveRange * this.team.fillUpDirection);
  }

  public void animate(int frame) {
    this.anim.animate();
  }

  public int[] render(String renderMode) {
    background(Color.VOID);
    renderImage();
    if (renderMode.equals(ARENA)) {
      yf = 65;
      renderBars();
      renderEffects();
    } else if (renderMode.equals(DRAFT)) {
      yf = 65;
      renderDraftInfo();
    }
    return pixels;
  }

  public int[] draftRender() {
    int[] pixels = new int[draftDimensionX * draftDimensionY];
    Arrays.fill(pixels, Color.VOID.VALUE);
    staticFillSize(0, 0, 64, 64, 64, pixels, getImagePixels());
    staticFillSize(0, 64, 64, 10, 64, pixels, getTextLine(this.name, 64, 10, Color.WHITE));
    return pixels;
  }

  private int[] getImagePixels() {
    boolean flipHorizontal = this.team != null && this.team.teamNumber == 2;
    return flipHorizontal ? SpriteUtils.flipHorizontal(this.anim.image, 64) : this.anim.image;
  }

  private void renderImage() {
    fillWithGraphicsSize(0, 0, 64, 64, getImagePixels(), false);
  }

  private void renderBars() {
    fillWithGraphicsSize(
        0,
        yf,
        64,
        3,
        getBar(64, 3, 0, getResourcePercentage(Stat.VITALITY), Color.GREEN, Color.DARKRED),
        false);
    if (this.getStat(Stat.SHIELD) > 0) {
      fillWithGraphicsSize(0, yf, 64, 3, getShieldBar(), false);
    }
    yf += 4;
    if (this.secondaryResource != null) {
      fillWithGraphicsSize(
          0,
          yf,
          64,
          3,
          getBar(
              64,
              3,
              0,
              getResourcePercentage(this.secondaryResource),
              getResourceColor(this.secondaryResource),
              Color.DARKRED),
          false);
      yf += 4;
    }
    if (!this.moved) {
      int[] action = SpriteLibrary.getSprite("action");
      fillWithGraphicsSize(2, 2, 5, 5, action, false);
    }
    //        fillWithGraphicsSize(0, yf, 64, 3, getBar(64, 3, 0,
    // getResourcePercentage(Stat.MAX_ACTION), getResourceColor(Stat.MAX_ACTION), Color.DARKRED),
    // false);
    //        yf+=4;
  }

  private int[] getShieldBar() {
    double currentLifePercentage = getResourcePercentage(Stat.VITALITY);
    int missingLifePercentage = getMissingLifePercentage();
    double shieldPercentage = (double) getStat(Stat.SHIELD) / getStat(Stat.VITALITY);
    int lifeFill = (int) (64 * currentLifePercentage);

    if (missingLifePercentage < shieldPercentage) {
      return getBar(64, 3, 0, 1.0 - shieldPercentage, Color.VOID, Color.DARKYELLOW);
    } else {
      return getBar(64, 3, lifeFill, shieldPercentage, Color.DARKYELLOW, Color.VOID);
    }
  }

  private void renderEffects() {
    int effectsX = 0;
    int paintedEffects = 0;
    int paddingX = 2;
    int paddingY = 5;
    for (Effect effect : this.effects) {
      int[] sprite = SpriteLibrary.getSprite(effect.getClass().getName());

      fillWithGraphicsSize(
          effectsX,
          yf,
          Property.EFFECT_ICON_SIZE,
          Property.EFFECT_ICON_SIZE,
          sprite,
          false,
          null,
          Color.VOID);
      if (effect.stackable) {
        int[] stacks =
            getSmallNumTextLine(
                effect.stacks + "",
                Property.EFFECT_ICON_SIZE,
                this.editor.smallNumHeight,
                TextAlignment.RIGHT,
                Color.VOID,
                Color.BLACK);
        fillWithGraphicsSize(
            effectsX + 1,
            yf + (Property.EFFECT_ICON_SIZE - 2),
            Property.EFFECT_ICON_SIZE,
            this.editor.smallNumHeight,
            stacks,
            false);
      } else if (effect.turns > 0) {
        int[] turns =
            getSmallNumTextLine(
                effect.turns + "",
                Property.EFFECT_ICON_SIZE,
                this.editor.smallNumHeight,
                TextAlignment.RIGHT,
                Color.VOID,
                Color.WHITE);
        fillWithGraphicsSize(
            effectsX + 1,
            yf + (Property.EFFECT_ICON_SIZE - 2),
            Property.EFFECT_ICON_SIZE,
            this.editor.smallNumHeight,
            turns,
            false);
      }

      paintedEffects++;

      if (paintedEffects % 6 == 0) {
        yf += Property.EFFECT_ICON_SIZE + paddingY;
        effectsX = 0;
      } else {
        effectsX += Property.EFFECT_ICON_SIZE + paddingX;
      }
    }
    //        for (Map.Entry<Stat, Integer> statChange : this.statChanges.entrySet()) {
    //            if (Stat.nonResourceStats.contains(statChange.getKey())) {
    //                Color borderColor  = statChange.getValue() > 0 ? Color.GREEN : Color.RED;
    //                int [] stat = getTextLine(statChange.getKey().getIconString(),
    // Property.EFFECT_ICON_SIZE, Property.EFFECT_ICON_SIZE,
    //                       Color.WHITE);
    //                fillWithGraphicsSize(effectsX, yf, Property.EFFECT_ICON_SIZE,
    // Property.EFFECT_ICON_SIZE, stat, borderColor);
    //
    //                paintedEffects++;
    //
    //                if (paintedEffects % 6 == 0) {
    //                    yf += Property.EFFECT_ICON_SIZE + paddingY;
    //                    effectsX = 0;
    //                } else {
    //                    effectsX += Property.EFFECT_ICON_SIZE + paddingX;
    //                }
    //            }
    //        }
  }

  private void renderDraftInfo() {
    if (this.draftChoice != 0) {
      int[] draftChoiceNumberPixels = getTextLine(this.draftChoice + "", 10, 10, Color.RED);
      fillWithGraphicsSize(this.width - 10, 0, 10, 10, draftChoiceNumberPixels, false);
    }

    int roleYf = 2;
    int[] role = getTextLine("[" + this.role.iconKey + "]", 10, 10, Color.WHITE);
    fillWithGraphicsSize(2, roleYf, 10, 10, role, false);
  }

  // StatMagic

  //    public void levelUp() {
  //        int newLevel = this.level + 1;
  //        this.setLevel(newLevel);
  //        List<Skill> newSkills = this.learnableSkillList.stream().filter(s->s.getLevel() ==
  // newLevel).toList();
  //        newSkills.forEach(s-> {
  //            if (!this.skills.contains(s)) {
  //                this.skills.add(s);
  //                sortSkills();
  //            }
  //        });
  //    }

  private void cacheStats() {
    for (Map.Entry<Stat, Integer> stat : statCache.entrySet()) {
      Integer currentValue = this.getStat(stat.getKey());
      stat.setValue(currentValue);
    }
  }

  private void sortSkills() {
    this.skills.sort(Comparator.comparingInt(Skill::getSort));
  }

  public void setLevel(int level) {
    this.level = level;
    this.stats.putAll(this.baseStats);
  }
  public int getStat(Stat stat) {
    if (stat == null) {
      return 0;
    }
    Integer statValue = this.stats.get(stat);
    if (statValue == null) {
      return 0;
    }
    if (stat.equals(Stat.CURRENT_LIFE) || stat.equals(Stat.SHIELD) || stat.equals(Stat.CURRENT_ENERGY)) {
      return statValue;
    }
    return trigger_statChange(stat, statValue);
  }

  public int getCachedStat(Stat stat) {
    if (stat == null) {
      return 0;
    }
    Integer statValue = this.statCache.get(stat);
    if (statValue == null) {
      return 0;
    }
    return statValue;
  }


  public Map<Stat, Integer> getStatChanges() {
    return this.statChanges;
  }

  public int getStatChange(Stat stat) {
    if (this.statChanges.containsKey(stat)) {
      return this.statChanges.get(stat);
    }
    return 0;
  }

  public Map<Stat, Integer> getStats() {
    return this.stats;
  }

  public void addResource(
      Stat currentStat,
      Stat maxStat,
      int value,
      Hero source,
      Skill skill,
      Effect effect,
      Equipment equipment,
      boolean negative) {
    int target = value + this.getStat(currentStat);
    int max = 99999;
    if (maxStat != null) {
      max = this.getStat(maxStat);
    }
    int result = Math.min(target, max);
    int excess = target - max;
    if (!negative) {
      result = Math.max(result, 0);
    }
    this.stats.put(currentStat, result);

    if (excess > 0) {
      trigger_excessResource(currentStat, excess, source, skill, effect, equipment);
    }
  }

  public void addSkillResources(
      List<Resource> resources, Skill skill, Hero source, Equipment equipment) {
    for (Resource resource : resources) {
      addSkillResource(resource, skill, source, equipment);
    }
  }

  public void addSkillResource(Resource resource, Skill skill, Hero source, Equipment equipment) {
    if (resource.resource.equals(Stat.CURRENT_LIFE)) {
      heal(resource.amount, source, skill, null, equipment, false);
    } else if (resource.resource.equals(Stat.CURRENT_ENERGY)) {
      energy(resource.amount, source, skill, null, equipment, false);
    } else if (resource.resource.equals(Stat.SHIELD)) {
      shield(resource.amount, source, skill, null, equipment);
    } else if (resource.resource.equals(Stat.ACCURACY)){
      accuracy(resource.amount, source, skill, null, equipment);
    } else if (resource.resource.equals(Stat.DODGE)) {
      dodge(resource.amount, source, skill, null, equipment);
    }
  }

  public int getSkillAccuracy(Skill skill) {
    if (skill.getAccuracy() == -1 || skill.getTargetType().equals(TargetType.SELF)) {
      return 100;
    }
    return skill.getAccuracy();
  }

  public double getResourcePercentage(Stat resource) {
    if (resource == null) {
      return 0.0;
    }
    return switch (resource) {
      case VITALITY -> ((double) this.getStat(Stat.CURRENT_LIFE)) / this.getStat(Stat.VITALITY);
      case ENERGY -> ((double) this.getStat(Stat.CURRENT_ENERGY)) / this.getStat(Stat.ENERGY);
      //            case MAX_ACTION -> ((double) this.getStat(Stat.CURRENT_ACTION)) /
      // this.getStat(Stat.MAX_ACTION);
      default -> 0.0;
    };
  }

  public int getCurrentLifePercentage() {
    return this.stats.get(Stat.CURRENT_LIFE) * 100 / this.stats.get(Stat.VITALITY);
  }

  public int getMissingLifePercentage() {
    return (100 - getCurrentLifePercentage());
  }

  public int getCurrentManaPercentage() {
    return this.stats.get(Stat.CURRENT_ENERGY) * 100 / this.stats.get(Stat.ENERGY);
  }

  // TurnMagic
  public void startOfTurn() {
    trigger_startOfTurn();
    cacheStats();
  }

  public void endOfTurn() {
    regain();
    tickEffects();
    cleanUpEffect();
    if (!this.isAlive()) {
      return;
    }
    trigger_endOfTurn();
  }

  private void regain() {
    int heal = getStat(Stat.LIFE_REGAIN);
    if (!(this.hasPermanentEffect(Bleeding.class))) {
      heal(heal, this, null, null, null, true);
    }
    if (this.secondaryResource != null && this.secondaryResource.equals(Stat.ENERGY)) {
      energy(this.getStat(Stat.ENERGY_REGAIN), this, null, null, null, true);
    }
  }

  //    public void refreshAction() {
  //        if (this.hasPermanentEffect(Exhausted.class) == 0 && (!this.movedLast ||
  // getStat(Stat.CURRENT_ACTION) == 0)) {
  //            this.changeStatTo(Stat.CURRENT_ACTION, this.getStat(Stat.MAX_ACTION));
  //        }
  //    }

  // Equipment Magic
  public void equip(Equipment equipment) {
    if (!this.equipments.contains(equipment)) {
      this.equipments.add(equipment);
    }
    if (equipment.getSkill() != null && this.skills.size() < 8 && this.skills.size() > 1) {
      equipment.getSkill().hero = this;
      this.skills.add(equipment.getSkill());
      sortSkills();
      System.out.println("equip " + equipment.getName() + " " + this.name);
    }
  }

  public void unequip(Equipment equipment) {
    this.equipments.remove(equipment);
    if (equipment.getSkill() != null) {
      this.skills.remove(equipment.getSkill());
    }
  }

  // EffectMagic
  private void tickEffects() {
    for (Effect effect : effects) {
      effect.tick();
    }
  }

  private void cleanUpEffect() {
    List<Effect> toRemove =
        this.effects.stream()
            .filter(
                e ->
                    (Effect.Durability.TIME.equals(e.durability) && e.turns < 1)
                        || (e.stackable && e.stacks <= 0)
                        || (e.durability.equals(Effect.Durability.ONCE) && e.used))
            .toList();
    for (Effect effectToRemove : toRemove) {
      removeEffect(effectToRemove);
    }
  }

  private void removeEffect(Effect effect) {
    effect.removeFromHero();
    this.effects.remove(effect);
  }

  public void addAllEffects(List<Effect> effects, Hero caster) {
    for (Effect effect : effects) {
      this.addEffect(effect, caster);
    }
  }

  public void addEffect(Effect effect, Hero caster) {
    addEffect(effect, caster, null);
  }
  public void addEffect(Effect effect, Hero caster, Skill skill) {

    if (getEffectFailure(effect, caster, skill)) {
      return;
    }
    if (effect instanceof Cleanse) {
      this.removeNegativeEffects();
    }else if (effect instanceof Debuff) {
      this.removePositiveEffects();
    } else {
      boolean added = false;
      boolean newlyAdded = false;
      if (effect.negates != null) {
        boolean negates = this.effects.stream().anyMatch(e -> effect.negates.contains(e.name));
        if (negates) {
          effect.negates.forEach(this::removeEffectByName);
          added = true;
        }
      } else {
        for (Effect effectHave : effects) {
          if (effectHave.getClass().equals(effect.getClass())) {
            if (effect.stackable) {
              added = true;
              newlyAdded = true;
              effectHave.addStacks(effect.stacks);
            } else {
              effectHave.turns += effect.turns;
              added = true;
            }
          }
        }
      }
      if (!added && this.effects.size() != 12) {
        Effect newEffect = effect.copy();
        newEffect.origin = caster;
        newEffect.hero = this;
        this.effects.add(newEffect);
        newEffect.addSubscriptions();
        newlyAdded = true;
      }
      trigger_effectAdded(effect, caster, skill, newlyAdded);
    }
    this.arena.logCard.addToLog(
        this.getName()
            + " received "
            + effect.getIconString()
            + "("
            + (effect.stackable ? effect.stacks : effect.turns)
            + ").");
  }

  private boolean getEffectFailure(Effect effect, Hero caster, Skill skill) {
    if (hasPermanentEffect(Immunity.class)) {
      return true;
    }
    return trigger_effectFailure(effect, caster, skill);
  }

  public boolean hasPermanentEffect(String name) {
    return effects.stream().anyMatch(e -> e.name.equals(name));
  }

  public <T extends Effect> boolean hasPermanentEffect(Class<T> clazz) {
    return effects.stream().anyMatch(e -> e.getClass().equals(clazz));
  }

  public int getPermanentEffectStacks(String effectName) {
    int amount = 0;
    for (Effect currentEffect : effects) {
      if (currentEffect.name.equals(effectName)) {
        amount += currentEffect.stacks;
      }
    }
    return amount;
  }

  public <T extends Effect> int getPermanentEffectStacks(Class<T> clazz) {
    int amount = 0;
    for (Effect currentEffect : effects) {
      if (currentEffect.getClass().equals(clazz)) {
        amount += currentEffect.stacks;
      }
    }
    return amount;
  }

  public <T extends Effect> void decreaseEffectStack(Class<T> clazz) {
    if (this.getPermanentEffectStacks(clazz) > 0) {
      this.getPermanentEffect(clazz).stacks--;
    }
  }

  public void removeRdmEffectOfTypes(List<Effect.SubType> subType) {
    List<Effect> effectsOfType = this.effects.stream()
            .filter(e-> new HashSet<>(e.subTypes).containsAll(subType))
            .toList();
    if (effectsOfType.isEmpty()) {
      return;
    }
    Random random = new Random();
    Effect effect = effectsOfType.get(random.nextInt(effectsOfType.size()));
    this.removeEffect(effect);
  }

  public void removeEffectByName(String name) {
    Logger.logLn(this.name + ".removePermanentEffect:" + name);
    List<Effect> toRemove = this.effects.stream().filter(e -> e.name.equals(name)).toList();
    for (Effect effect : toRemove) {
      removeEffect(effect);
    }
  }

  public <T extends Effect> void removePermanentEffectOfClass(Class<T> clazz) {
    Logger.logLn(this.name + ".removePermanentEffect:" + clazz.getName());
    List<Effect> toRemove = this.effects.stream().filter(e -> e.getClass().equals(clazz)).toList();
    for (Effect effect : toRemove) {
      removeEffect(effect);
    }
  }

  public Effect getPermanentEffectByName(String name) {
    for (Effect effect : this.effects) {
      if (effect.getName().equals(name)) {
        return effect;
      }
    }
    return null;
  }
  public <T extends Effect> Effect getPermanentEffect(Class<T> clazz) {
    for (Effect currentEffect : effects) {
      if (currentEffect.getClass().equals(clazz)) {
        return currentEffect;
      }
    }
    return null;
  }

  // SkillMagic
  private void skillTurn() {
    for (Skill s : this.skills) {
      if (s != null) {
        s.turn();
      }
    }
  }

  public void changeRandomActiveCdBy(int change) {
    List<Skill> cdSkills = this.skills.stream().filter(s->s.getMaxCd() > 0).toList();
    if (CollectionUtils.isEmpty(cdSkills)) {
      return;
    }
    Random random = new Random();
    cdSkills.get(random.nextInt(cdSkills.size())).changeCurrentCdBy(change);
  }

  public <T extends Skill> boolean hasSkill(Class<T> clazz) {
    for (Skill s : this.skills) {
      if (s != null && s.getClass().equals(clazz)) {
        return true;
      }
    }
    return false;
  }

  public boolean canPerform(Skill s, int[] targetPositions) {

    if (!canPerformResourceCheck(s)) {
      return false;
    }
    if (trigger_performFailure(s, targetPositions)) {
      return false;
    }
    return s.performCheck(this);
  }

  private boolean canPerformResourceCheck(Skill s) {
    if (s == null) {
      return false;
    }
    return this.stats.get(Stat.CURRENT_LIFE) > s.getLifeCost()
        && this.stats.get(Stat.CURRENT_ENERGY) >= s.getManaCost()
        && this.stats.get(Stat.DODGE) >= s.getDodgeCost()
        && !s.isPassive();
  }

  public void payForSkill(Skill s) {
    payResource(Stat.CURRENT_LIFE, Stat.VITALITY, -1 * s.getLifeCost(this));
    payResource(Stat.CURRENT_ENERGY, Stat.ENERGY, -1 * s.getManaCost());
    payResource(Stat.DODGE, null, -1 * s.getDodgeCost());
    Logger.logLn("Paid life:" + s.getLifeCost(this));
  }

  public boolean hasFieldEffect() {
    return this.arena.fieldEffects.stream().anyMatch(fe->fe.position == this.position);
  }
  public boolean hasStatus() {
    for (Effect effect : this.effects) {
      if (Effect.ChangeEffectType.STATUS.equals(effect.type)) {
        return true;
      }
    }
    return false;
  }

  public boolean hasBuff() {
    for (Effect effect : this.effects) {
      if (effect.subTypes.contains(Effect.SubType.BUFF)) {
        return true;
      }
    }
    return false;
  }

  public boolean hasDebuff() {
    for (Effect effect : this.effects) {
      if (effect.subTypes.contains(Effect.SubType.DEBUFF)) {
        return true;
      }
    }
    return false;
  }

  public boolean hasStatusDebuff() {
    for (Effect effect : this.effects) {
      if (Effect.ChangeEffectType.STATUS.equals(effect.type)
          && effect.subTypes.contains(Effect.SubType.DEBUFF)) {
        return true;
      }
    }
    return false;
  }

  public boolean hasStatusBuff() {
    for (Effect effect : this.effects) {
      if (Effect.ChangeEffectType.STATUS.equals(effect.type)
          && effect.subTypes.contains(Effect.SubType.BUFF)) {
        return true;
      }
    }
    return false;
  }

  public void revertStatEffects(Hero origin) {
    List<String> effectNames = this.effects. stream().map(Effect::getName).toList();
    effectNames.forEach(name -> {
      Effect remove = this.getPermanentEffectByName(name);
      Effect add = EffectLibrary.getEffect(remove.negates.getFirst(), remove.stacks, remove.turns, null);
      this.removeEffect(remove);
      this.addEffect(add,origin);
    });
  }
  public void removeNegativeEffects() {
    List<Effect> toRemove =
        this.effects.stream().filter(e -> e.subTypes.contains(Effect.SubType.DEBUFF)).toList();
    for (Effect effect : toRemove) {
      this.removeEffect(effect);
    }
  }

  public void removePositiveEffects() {
    for (Effect effect : this.effects) {
      if (effect.subTypes.contains(Effect.SubType.BUFF)) {
        this.removeEffect(effect);
      }
    }
  }

  public int simulateHealInPercentages(
      int heal, Hero caster, Skill skill, Effect effect, Equipment equipment) {
    int pureHeal = trigger_healChanges(heal, caster, skill, effect, equipment, false);

    int actualHeal =
        Math.min(pureHeal, this.stats.get(Stat.VITALITY) - this.stats.get(Stat.CURRENT_LIFE));
    return actualHeal * 100 / this.stats.get(Stat.VITALITY);
  }

  public void payResource(Stat resource, Stat max, int amount) {
    addResource(resource, max, amount, this, null, null, null, false);
  }


  public void percentageEnergy(int percentage, Hero caster, Skill skill, Effect effect, Equipment equipment, boolean regen) {
    int nrg = MyMaths.percentageOf(percentage, this.getStat(Stat.ENERGY));
    energy(nrg, caster, skill, effect, equipment, regen);
  }
  public void energy(
      int energy, Hero caster, Skill skill, Effect effect, Equipment equipment, boolean regen) {
    energy = trigger_energyChanges(energy, caster, skill, effect, equipment, regen);
    addResource(Stat.CURRENT_ENERGY, Stat.ENERGY, energy, caster, skill, effect, equipment, false);
  }

  public void percentageHeal(
      int percentage, Hero caster, Skill skill, Effect effect, Equipment equipment, boolean regen) {
    int heal = MyMaths.percentageOf(percentage, this.getStat(Stat.VITALITY));
    heal(heal, caster, skill, effect, equipment, regen);
  }

  public void heal(
      int heal, Hero caster, Skill skill, Effect effect, Equipment equipment, boolean regen) {
    heal = trigger_healChanges(heal, caster, skill, effect, equipment, regen);
    addResource(Stat.CURRENT_LIFE, Stat.VITALITY, heal, caster, skill, effect, equipment, false);
    trigger_onHeal(heal, caster, skill, effect,equipment, regen);
  }

  public void shield(int shield, Hero caster, Skill skill, Effect effect, Equipment equipment) {
    shield = trigger_shieldChanges(shield, caster, skill, effect, equipment);
    addResource(Stat.SHIELD, Stat.VITALITY, shield, caster, skill, effect, equipment, false);
  }

  public void dodge(int dodge, Hero caster, Skill skill, Effect effect, Equipment equipment) {
    dodge = trigger_permanentDodgeChanges(dodge, caster, skill, effect, equipment);
    addResource(Stat.DODGE, null, dodge, caster, skill, effect, equipment, true);
  }

  public void accuracy(int accuracy, Hero caster, Skill skill, Effect effect, Equipment equipment) {
    accuracy = trigger_permanentAccuracyChanges(accuracy, caster, skill, effect, equipment);
    addResource(Stat.ACCURACY, null, accuracy, caster, skill, effect, equipment, true);
  }

  public int percentageDamage(
      double percentageDamage,
      DamageType damageType,
      DamageMode mode,
      Hero caster,
      Skill skill,
      Effect effect,
      Equipment equipment,
      int lethality) {
    int damage = MyMaths.percentageOf(percentageDamage, this.getStat(Stat.VITALITY));
    return damage(damage, damageType, mode, caster, skill, effect, equipment, lethality);
  }

  public int damage(
      int damage,
      DamageType damageType,
      DamageMode mode,
      Hero caster,
      Skill skill,
      Effect effect,
      Equipment equipment,
      int lethality) {

    damage = trigger_dmgChanges(damage, damageType, mode, caster, skill, effect, equipment);

    int def = getStat(Stat.ARMOR);
    int result = MyMaths.getDamage(damage, def, lethality);

    System.out.println("dmg:" + result);
    Logger.logLn("1play dmg animation of " + this.name + "/" + this.id);
    playAnimation("damaged", true);

    int dmgToShield = 0;
    int shield = getStat(Stat.SHIELD);
    if (shield > 0) {
      boolean broken = false;
      if (shield < result) {
        this.stats.put(Stat.SHIELD, 0);
        result -= shield;
        dmgToShield = shield;
        broken = true;
      } else {
        this.addResource(Stat.SHIELD, null, -1 * result, caster, skill, effect, equipment, false);
        dmgToShield = result;
        result = 0;
      }
      if (broken) {
        trigger_shieldBroken(this);
      }
    }
    this.arena.logCard.addToLog(this.getName() + " was dealt " + result + " damage.");
    addResource(Stat.CURRENT_LIFE, Stat.VITALITY, -1 * result, caster, skill, effect, equipment, false);
    trigger_onDamage(result, dmgToShield, damageType, mode, skill, effect, caster);
    return result;
  }

  public int simulateDamageInPercentages(
      Hero caster, int damage, DamageType type, DamageMode mode, int lethality, Skill skill) {
    damage = trigger_dmgChanges(damage, type, mode, caster, skill, null, null);
    int def = getStat(Stat.ARMOR);
    int result = MyMaths.getDamage(damage, def, lethality);
    return result * 100 / this.stats.get(Stat.VITALITY);
  }

  // GUI
  public void playAnimation(String anim) {
    this.playAnimation(anim, true);
  }

  public void playAnimation(String anim, boolean waitfor) {
    if (this.anim != null) {
      Logger.logLn("play skill animation of " + this.name);
      this.anim.playAnimation(anim, waitfor);
    }
  }

  public String getResourceString(Stat resource) {
    if (resource == null) {
      return "";
    }
    return switch (resource) {
      case VITALITY -> getHealthString();
      case ENERGY -> getManaString();
      default -> "";
    };
  }

  public void draft(int draftChoice) {
    this.draftChoice = draftChoice;
  }

  public void removeFromDraft() {
    this.draftChoice = 0;
  }

  public int getDraftChoice() {
    return this.draftChoice;
  }

  // Loading

  protected Map<Stat, Integer> loadLevelStats() {

    Map<Stat, Integer> statJson = FileWalker.getStatJson(this.basePath + STAT_PATH);
    if (statJson != null) {
      return statJson;
    }
    return new HashMap<>();
  }

  // Trigger

  public void trigger_onHeal(
          int heal, Hero caster, Skill skill, Effect effect, Equipment equipment, boolean regen) {
    ConnectionPayload pl = new ConnectionPayload(1)
            .setHeal(heal)
            .setCaster(caster)
            .setTarget(this)
            .setSkill(skill)
            .setEffect(effect)
            .setEquipment(equipment)
            .setRegen(regen);
    Connector.fireTopic(Connector.HEAL_TRIGGER, pl);
  }
  public void trigger_onDamage(
      int dmg,
      int shieldDmg,
      DamageType type,
      DamageMode mode,
      Skill skill,
      Effect effect,
      Hero caster) {
    ConnectionPayload ConnectionPayload =
        new ConnectionPayload(1)
            .setTarget(this)
            .setCaster(caster)
            .setSkill(skill)
            .setEffect(effect)
            .setDamageType(type)
            .setDamageMode(mode)
            .setDmg(dmg)
            .setShieldDmg(shieldDmg);
    Connector.fireTopic(Connector.DMG_TRIGGER, ConnectionPayload);
  }

  public void trigger_shieldBroken(Hero target) {
    ConnectionPayload pl = new ConnectionPayload(1).setTarget(target);
    Connector.fireTopic(Connector.SHIELD_BROKEN, pl);
  }

  public boolean trigger_performFailure(Skill skill, int[] targetPositions) {
    ConnectionPayload canPerformPayload =
        new ConnectionPayload(1).setSkill(skill).setTargetPositions(targetPositions);
    Connector.fireTopic(Connector.CAN_PERFORM, canPerformPayload);
    return canPerformPayload.failure;
  }

  public void trigger_effectAdded(Effect effect, Hero caster, Skill skill, boolean newlyAdded) {
    ConnectionPayload effectAddedPayload =
        new ConnectionPayload(1)
            .setEffect(effect)
            .setCaster(caster)
            .setSkill(skill)
            .setTarget(this)
            .setNewEffect(newlyAdded);
    Connector.fireTopic(Connector.EFFECT_ADDED, effectAddedPayload);
  }

  public boolean trigger_effectFailure(Effect effect, Hero caster, Skill skill) {
    ConnectionPayload payload =
        new ConnectionPayload(1).setSkill(skill).setEffect(effect).setTarget(this).setCaster(caster);
    Connector.fireTopic(Connector.EFFECT_FAILURE_CHECK, payload);
    return payload.failure;
  }

  public void trigger_startOfTurn() {
    ConnectionPayload pl = new ConnectionPayload(1);
    pl.setCaster(this);
    Connector.fireTopic(this.id + Connector.START_OF_TURN, pl);
  }

  public void trigger_endOfTurn() {
    ConnectionPayload pl = new ConnectionPayload(1);
    pl.setCaster(this);
    Connector.fireTopic(this.id + Connector.END_OF_TURN, pl);
  }

  public void trigger_excessResource(
      Stat stat, int value, Hero source, Skill skill, Effect effect, Equipment equipment) {
    ConnectionPayload pl =
        new ConnectionPayload(1)
            .setStat(stat)
            .setValue(value)
            .setCaster(source)
            .setSkill(skill)
            .setEffect(effect)
            .setEquipment(equipment)
            .setTarget(this);
    Connector.fireTopic(Connector.EXCESS_RESOURCE, pl);
  }

  public int trigger_energyChanges(
      int energy, Hero caster, Skill skill, Effect effect, Equipment equipment, boolean regen) {
    ConnectionPayload energyChangesPayload =
        new ConnectionPayload(1)
            .setCaster(caster)
            .setTarget(this)
            .setEffect(effect)
            .setEquipment(equipment)
            .setSkill(skill)
            .setRegen(regen)
            .setEnergy(energy);
    Connector.fireTopic(Connector.BASE_ENERGY_CHANGES, energyChangesPayload);
    Connector.fireTopic(Connector.ENERGY_CHANGES_MULT, energyChangesPayload);
    return energyChangesPayload.energy;
  }

    public int trigger_healChanges(
      int heal, Hero caster, Skill skill, Effect effect, Equipment equipment, boolean regen) {
    ConnectionPayload healChangesPayload =
        new ConnectionPayload(1)
            .setCaster(caster)
            .setTarget(this)
            .setEffect(effect)
            .setEquipment(equipment)
            .setSkill(skill)
            .setRegen(regen)
            .setHeal(heal);
    Connector.fireTopic(Connector.BASE_HEAL_CHANGES, healChangesPayload);
    Connector.fireTopic(Connector.HEAL_CHANGES_MULT, healChangesPayload);
    return healChangesPayload.heal;
  }

  public int trigger_dmgChanges(
      int dmg,
      DamageType damageType,
      DamageMode mode,
      Hero caster,
      Skill skill,
      Effect effect,
      Equipment equipment) {
    ConnectionPayload dmgChangesPayload =
        new ConnectionPayload(1)
            .setCaster(caster)
            .setTarget(this)
            .setSkill(skill)
            .setEffect(effect)
            .setEquipment(equipment)
            .setDamageType(damageType)
            .setDamageMode(mode)
            .setDmg(dmg)
            .setSimulate(false);
    Connector.fireTopic(Connector.BASE_DMG_CHANGES, dmgChangesPayload);
    Connector.fireTopic(Connector.DMG_CHANGES_MULT, dmgChangesPayload);
    return dmgChangesPayload.dmg;
  }

  public int trigger_shieldChanges(
      int shield, Hero caster, Skill skill, Effect effect, Equipment equipment) {
    ConnectionPayload shieldChangesPayload =
        new ConnectionPayload(1)
            .setCaster(caster)
            .setTarget(this)
            .setSkill(skill)
            .setEquipment(equipment)
            .setEffect(effect)
            .setShield(shield)
            .setSimulate(false);
    Connector.fireTopic(Connector.BASE_SHIELD_CHANGES, shieldChangesPayload);
    Connector.fireTopic(Connector.SHIELD_CHANGES_MULT, shieldChangesPayload);
    return shieldChangesPayload.shield;
  }
  public int trigger_permanentAccuracyChanges(
          int shield, Hero caster, Skill skill, Effect effect, Equipment equipment) {
    ConnectionPayload shieldChangesPayload =
            new ConnectionPayload(1)
                    .setCaster(caster)
                    .setTarget(this)
                    .setSkill(skill)
                    .setEquipment(equipment)
                    .setEffect(effect)
                    .setShield(shield)
                    .setSimulate(false);
    Connector.fireTopic(Connector.ACCURACY_PERMANENT_BASE_CHANGES, shieldChangesPayload);
    Connector.fireTopic(Connector.ACCURACY_PERMANENT_MULT_CHANGES, shieldChangesPayload);
    return shieldChangesPayload.value;
  }
  public int trigger_permanentDodgeChanges(
          int shield, Hero caster, Skill skill, Effect effect, Equipment equipment) {
    ConnectionPayload shieldChangesPayload =
            new ConnectionPayload(1)
                    .setCaster(caster)
                    .setTarget(this)
                    .setSkill(skill)
                    .setEquipment(equipment)
                    .setEffect(effect)
                    .setShield(shield)
                    .setSimulate(false);
    Connector.fireTopic(Connector.DODGE_PERMANENT_BASE_CHANGES, shieldChangesPayload);
    Connector.fireTopic(Connector.DODGE_PERMANENT_MULT_CHANGES, shieldChangesPayload);
    return shieldChangesPayload.value;
  }

  public int trigger_statChange(Stat stat, int statValue) {
    ConnectionPayload bsc =
        new ConnectionPayload(1).setStat(stat).setValue(statValue).setTarget(this);
    Connector.fireTopic(Connector.BASE_STAT_CHANGE, bsc);

    ConnectionPayload scm =
        new ConnectionPayload(1).setStat(stat).setTarget(this).setValue(bsc.value);
    Connector.fireTopic(Connector.STAT_CHANGE_MULT, scm);
    return scm.value;
  }

  public int trigger_statChangeChange(Stat stat, int statValue) {
    ConnectionPayload bsc =
        new ConnectionPayload(1).setStat(stat).setValue(statValue).setTarget(this);
    Connector.fireTopic(Connector.STAT_BASE_CHANGE_CHANGE, bsc);

    ConnectionPayload scm =
        new ConnectionPayload(1).setStat(stat).setTarget(this).setValue(bsc.value);
    Connector.fireTopic(Connector.STAT_MULT_CHANGE_CHANGE, scm);
    return scm.value;
  }

  // GetterSetter

  private String getManaString() {
    return this.stats.get(Stat.CURRENT_ENERGY)
        + "(+"
        + this.stats.get(Stat.ENERGY_REGAIN)
        + ")/"
        + this.stats.get(Stat.ENERGY);
  }

  public String getHealthString() {
    return this.stats.get(Stat.CURRENT_LIFE)
        + "(+"
        + this.stats.get(Stat.LIFE_REGAIN)
        + ")/"
        + this.stats.get(Stat.VITALITY);
  }

  public static Color getResourceColor(Stat stat) {
    if (stat == null) {
      return Color.BLACK;
    }
    return switch (stat) {
      case VITALITY -> Color.GREEN;
      case ENERGY -> Color.BLUE;
      default -> Color.WHITE;
    };
  }

  public List<Hero> getAllies() {
    List<Hero> allies = new ArrayList<>();
    for (Hero hero : this.team.heroes) {
      if (hero != this) {
        allies.add(hero);
      }
    }
    return allies;
  }

  public List<Hero> getEnemies() {
    if (this.isTeam2()) {
      return Arrays.stream(this.arena.teams.getFirst().heroes).filter(Objects::nonNull).toList();
    } else {
      return Arrays.stream(this.arena.teams.get(1).heroes).filter(Objects::nonNull).toList();
    }
  }
  // GETTERS SETTERS

  public int getId() {
    return id;
  }

  public Hero setId(int id) {
    this.id = id;
    return this;
  }

  public String getName() {
    return name;
  }

  public Hero setName(String name) {
    this.name = name;
    return this;
  }

  public List<Skill> getLearnableSkillList() {
    return learnableSkillList;
  }

  public List<Skill> getSkills() {
    return skills;
  }

  public Hero setSkills(List<Skill> skills) {
    this.skills = skills;
    return this;
  }

  public List<Equipment> getEquipments() {
    return equipments;
  }

  public Hero setEquipments(List<Equipment> equipments) {
    this.equipments = equipments;
    return this;
  }

  public String getPortraitName() {
    return portraitName;
  }

  public Hero setPortraitName(String portraitName) {
    this.portraitName = portraitName;
    return this;
  }

  public List<Effect> getEffects() {
    return effects;
  }

  public Hero setEffects(List<Effect> effects) {
    this.effects = effects;
    return this;
  }

  public Stat getSecondaryResource() {
    return secondaryResource;
  }

  public Hero setSecondaryResource(Stat secondaryResource) {
    this.secondaryResource = secondaryResource;
    return this;
  }

  public boolean isAlive() {
    return this.getStat(Stat.CURRENT_LIFE) > 0;
  }

  public boolean isMoved() {
    return moved;
  }

  public Hero setMoved(boolean moved) {
    this.moved = moved;
    return this;
  }

  //    public boolean isMovedLast() {
  //        return movedLast;
  //    }
  //
  //    public Hero setMovedLast(boolean movedLast) {
  //        this.movedLast = movedLast;
  //        return this;
  //    }

  public int getPosition() {
    return position;
  }

  public int getSkillPos() {
    return this.isTeam2() ? Arena.lastEnemyPos - this.getPosition() : this.getPosition();
  }

  public void setPosition(int position) {
    this.position = position;
  }

  public boolean isTeam2() {
    return this.team.teamNumber == 2;
  }

  public List<Skill> getSpecificSkills(SkillTag tag) {
    return this.skills.stream().filter(s -> s.tags.contains(tag)).toList();
  }

  // DEV

  public void devDMGTestSkill(int index, Hero target) {
    if (this.skills.size() > index) {
      Skill skill = this.skills.get(index);
      System.out.print(skill.getName() + ":");
      skill.setTargets(new Hero[] {target});
      skill.resolve();
    }
  }

  public int getTeamPosition() {
    return this.isTeam2() ? Arena.lastEnemyPos - this.position : this.position;
  }

  public Role getRole() {
    return this.role;
  }

  public int getLevel() {
    return level;
  }

  public boolean isAlly(Hero other) {
    return this.team != other.team;
  }
}
