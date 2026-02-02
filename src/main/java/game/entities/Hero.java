package game.entities;

import framework.Logger;
import framework.Property;
import framework.connector.Connector;
import framework.connector.payloads.*;
import framework.graphics.GUIElement;
import framework.graphics.text.Color;
import framework.graphics.text.TextAlignment;
import framework.resources.SpriteLibrary;
import framework.resources.SpriteUtils;
import framework.states.Arena;
import game.objects.Equipment;
import game.objects.equipments.skills.S_WingedBoots;
import game.skills.*;
import game.skills.changeeffects.effects.other.Immunity;
import game.skills.changeeffects.effects.other.StatEffect;
import game.skills.changeeffects.effects.status.Injured;
import game.skills.genericskills.S_Skip;
import game.skills.logic.*;
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
    protected int[] levelCaps = new int[]{0, 5, 10, 15, 20};
    protected int draftChoice = 0;
    protected Map<Stat, Integer> baseStats = new HashMap<>();
    protected Map<Stat, Integer> stats = new HashMap<>();
    protected Map<Stat, Integer> statChanges = new HashMap<>();
    protected List<Effect> effects = new ArrayList<>();
//    protected boolean movedLast = false;
    protected boolean moved = false;
    protected Stat secondaryResource = Stat.MANA;
    protected Role role = Role.NONE;

    private int position;

    private int yf = 0;

    public int effectiveRange = 0;

    public static Hero getForDraft(){return null;};

    public Hero(HeroDTO dto) {
        this.width = 64;
        this.height = 110;
        this.pixels = new int[this.width*this.height];
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
        this.pixels = new int[this.width*this.height];
        this.name = name;
        this.stats.putAll(statBase());
    }

    public void enterArena(int position, Arena arena) {
        this.position = position;
        this.arena = arena;
        this.resetResources();
        this.equipments.forEach(Equipment::addSubscriptions);
        this.skills.forEach(Skill::addSubscriptions);
        this.skills.add(new S_WingedBoots(this));
        this.skills.add(new S_Skip(this));
    }

    public void resetResources() {
        this.stats.put(Stat.CURRENT_LIFE, this.stats.get(Stat.LIFE));
        this.stats.put(Stat.CURRENT_MANA, this.stats.get(Stat.MANA));
    }
    public void leaveArena() {
        this.effects.forEach(Effect::removeEffect);
        this.equipments.forEach(Equipment::reset);
        this.equipments.forEach(Equipment::removeSubscriptions);
        this.skills.removeIf(s->s instanceof S_WingedBoots || s instanceof S_Skip);
        this.skills.forEach(Skill::reset);
        this.skills.forEach(Skill::removeSubscriptions);
    }

    public void initBasePath(String name) {
        String base = "entities/heroes/";
        this.basePath = base + name;
    }

    protected void initAnimator(){};
    protected void initSkills(){};

    private void initAnimator(HeroDTO dto) {
        String idleAnim = dto.idleAnim != null? dto.idleAnim : "idle_w.png";
        String damagedAnim = dto.damagedAnim != null? dto.damagedAnim: "damaged_w.png";
        String actionAnim = dto.actionAnim != null? dto.actionAnim: "action_w.png";
        this.anim = new Animator();
        anim.width = 64;
        anim.height = 64;
        anim.setupAnimation(this.basePath + "/sprites/" + idleAnim, "idle", new int[]{40,80});
        anim.setupAnimation(this.basePath + "/sprites/" + damagedAnim, "damaged", new int[]{40,80});
        anim.setupAnimation(this.basePath + "/sprites/" + actionAnim, "action", new int[]{15, 30, 45});

        anim.setDefaultAnim("idle");
        anim.currentAnim = anim.getDefaultAnim();
        anim.onLoop = true;
    }

    private void initSkills(HeroDTO dto) {
        dto.learnedSkills.stream().map(SkillLibrary::getSkill).forEach(this::addSkill);
        dto.learnableSkills.stream().map(SkillLibrary::getSkill).forEach(this::addLearnableSkill);
    }

    private void addSkill(Skill skill) {
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
        base.put(Stat.MAGIC, 0);
        base.put(Stat.ATTACK, 0);
        base.put(Stat.STAMINA, 0);
        base.put(Stat.SPEED, 0);

        //ResourceStats
        base.put(Stat.LIFE, 0);
        base.put(Stat.CURRENT_LIFE, 0);
        base.put(Stat.LIFE_REGAIN, 0);

        base.put(Stat.MANA, 0);
        base.put(Stat.CURRENT_MANA, 0);

//        base.put(Stat.MAX_ACTION, 3);
//        base.put(Stat.CURRENT_ACTION, 3);

        base.put(Stat.CRIT_CHANCE, 0);
        base.put(Stat.ACCURACY, 100);
        base.put(Stat.EVASION, 0);
        base.put(Stat.SHIELD,0);
        base.put(Stat.LETHALITY,0);

        base.put(Stat.NORMAL_RESIST,0);
        base.put(Stat.HEAT_RESIST,0);
        base.put(Stat.COLD_RESIST,0);
        base.put(Stat.LIGHT_RESIST,0);
        base.put(Stat.DARK_RESIST,0);
        base.put(Stat.TOX_RESIST,0);
        base.put(Stat.MIND_RESIST,0);
        base.put(Stat.SHOCK_RESIST,0);

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

    @Override
    public void update(int frame) {
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
        int[] pixels = new int[draftDimensionX*draftDimensionY];
        Arrays.fill(pixels, Color.VOID.VALUE);
        staticFillSize(0,0,64,64,64,pixels,getImagePixels());
        staticFillSize(0,64,64,10,64,pixels,getTextLine(this.name,64,10,Color.WHITE));
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
        fillWithGraphicsSize(0, yf, 64, 3, getBar(64, 3, 0, getResourcePercentage(Stat.LIFE), Color.GREEN, Color.DARKRED), false);
        if (this.getStat(Stat.SHIELD)>0) {
            fillWithGraphicsSize(0,yf,64,3,getShieldBar(), false);
        }
        yf+=4;
        if (this.secondaryResource != null) {
            fillWithGraphicsSize(0, yf, 64, 3, getBar(64, 3, 0, getResourcePercentage(this.secondaryResource), getResourceColor(this.secondaryResource), Color.DARKRED), false);
            yf+=4;
        }
        if (!this.moved) {
            int[] action = SpriteLibrary.getSprite("action");
            fillWithGraphicsSize(2, 2, 5,5,action,false);
        }
//        fillWithGraphicsSize(0, yf, 64, 3, getBar(64, 3, 0, getResourcePercentage(Stat.MAX_ACTION), getResourceColor(Stat.MAX_ACTION), Color.DARKRED), false);
//        yf+=4;
    }
    private int[] getShieldBar() {
        double currentLifePercentage = getResourcePercentage(Stat.LIFE);
        int missingLifePercentage = getMissingLifePercentage();
        double shieldPercentage = (double)getStat(Stat.SHIELD) / getStat(Stat.LIFE);
        int lifeFill = (int)(64 * currentLifePercentage);

        if (missingLifePercentage < shieldPercentage) {
            return getBar(64,3,0,1.0-shieldPercentage,Color.VOID,Color.DARKYELLOW);
        } else {
            return getBar(64,3,lifeFill,shieldPercentage,Color.DARKYELLOW,Color.VOID);
        }
    }
    private void renderEffects() {
        int effectsX = 0;
        int paintedEffects = 0;
        int paddingX = 2;
        int paddingY = 5;
        for (Effect effect : this.effects) {
            int[] sprite = new int[0];
            if (effect instanceof StatEffect) {
                Stat stat = effect.stat;
                SpriteLibrary.getSprite(stat.name());
            } else {
                sprite = SpriteLibrary.getSprite(effect.getClass().getName());
            }

            fillWithGraphicsSize(effectsX, yf, Property.EFFECT_ICON_SIZE, Property.EFFECT_ICON_SIZE,
                    sprite, false, null, Color.VOID);
            if (effect.stackable) {
                int[] stacks = getSmallNumTextLine(effect.stacks+"", Property.EFFECT_ICON_SIZE, this.editor.smallNumHeight, TextAlignment.RIGHT, Color.VOID, Color.BLACK);
                fillWithGraphicsSize(effectsX +1, yf + (Property.EFFECT_ICON_SIZE-2), Property.EFFECT_ICON_SIZE, this.editor.smallNumHeight,
                        stacks, false);
            } else if (effect.turns > 0) {
                int[] turns = getSmallNumTextLine(effect.turns+"", Property.EFFECT_ICON_SIZE, this.editor.smallNumHeight, TextAlignment.RIGHT, Color.VOID, Color.WHITE);
                fillWithGraphicsSize(effectsX +1, yf + (Property.EFFECT_ICON_SIZE-2), Property.EFFECT_ICON_SIZE, this.editor.smallNumHeight,
                        turns, false);
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
//                int [] stat = getTextLine(statChange.getKey().getIconString(), Property.EFFECT_ICON_SIZE, Property.EFFECT_ICON_SIZE,
//                       Color.WHITE);
//                fillWithGraphicsSize(effectsX, yf, Property.EFFECT_ICON_SIZE, Property.EFFECT_ICON_SIZE, stat, borderColor);
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
            int[] draftChoiceNumberPixels = getTextLine(this.draftChoice+"", 10,10, Color.RED);
            fillWithGraphicsSize(this.width-10,0,10,10,draftChoiceNumberPixels, false);
        }

        int roleYf = 2;
        int[] role = getTextLine("["+this.role.iconKey+"]", 10,10,Color.WHITE);
        fillWithGraphicsSize(2, roleYf, 10,10,role,false);
    }

//StatMagic

//    public void levelUp() {
//        int newLevel = this.level + 1;
//        this.setLevel(newLevel);
//        List<Skill> newSkills = this.learnableSkillList.stream().filter(s->s.getLevel() == newLevel).toList();
//        newSkills.forEach(s-> {
//            if (!this.skills.contains(s)) {
//                this.skills.add(s);
//                sortSkills();
//            }
//        });
//    }

    private void sortSkills() {
        this.skills.sort(Comparator.comparingInt(Skill::getSort));
    }

    public void setLevel(int level) {
        this.level = level;
        for (Map.Entry<Stat, Integer> entry : this.baseStats.entrySet()) {
            this.stats.put(entry.getKey(), entry.getValue());
        }
    }
    public int getStat(Stat stat) {
        if (stat == null) {
            return 0;
        }
        Integer statValue = this.stats.get(stat);
        if (statValue == null) {
            return 0;
        }
        return this.stats.get(stat);
    }

    public void changeStatTo(Stat stat, int value) {
        int val = Math.max(value, 0);
        int oldValue = this.stats.get(stat);
        this.stats.put(stat, val);
        int change = val - oldValue;
        this.statChanges.put(stat, change + this.statChanges.getOrDefault(stat, 0));
        StatChangePayload pl = new StatChangePayload().setStat(stat).setOldValue(oldValue).setNewValue(val);
        Connector.fireTopic(Connector.STAT_CHANGE, pl);
    }

    public void addToStat(Stat stat, int value) {
        int val = Math.max(this.stats.get(stat) + value, 0);
        int oldValue = this.stats.get(stat);
        int change = val - oldValue;
        this.statChanges.put(stat, change + this.statChanges.getOrDefault(stat, 0));
        this.stats.put(stat, val);

        StatChangePayload pl = new StatChangePayload().setStat(stat).setOldValue(oldValue).setNewValue(val);
        Connector.fireTopic(Connector.STAT_CHANGE, pl);
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
    public void applyStatChanges(Map<Stat, Integer> changes) {
        this.stats.putAll(changes);
    }
    public void addToStats(Map<Stat, Integer> changes) {
        for (Map.Entry<Stat, Integer> entry : changes.entrySet()) {
            if (Stat.nonResourceStats.contains(entry.getKey())) {
                 addToStat(entry.getKey(), entry.getValue());
            }
        }
    }

    public void addResource(Stat currentStat, Stat maxStat, int value, Hero source) {
        int target = value + this.getStat(currentStat);
        int max = this.getStat(maxStat);
        int result = Math.min(target, max);
        int excess = target - max;
        this.changeStatTo(currentStat, Math.max(result, 0));

        if (excess > 0) {
            ExcessResourcePayload pl = new ExcessResourcePayload()
                    .setResource(currentStat)
                    .setExcess(excess)
                    .setSource(source)
                    .setTarget(this);
            Connector.fireTopic(Connector.EXCESS_RESOURCE, pl);
        }
    }

    public void addResources(List<Resource> resources, Hero source) {
        for (Resource resource: resources) {
            addResource(resource, source);
        }
    }

    public void addResource(Resource resource, Hero source) {
        this.addResource(resource.resource, resource.maxResource, resource.amount, source);
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
            case LIFE -> ((double) this.getStat(Stat.CURRENT_LIFE)) / this.getStat(Stat.LIFE);
            case MANA -> ((double) this.getStat(Stat.CURRENT_MANA)) / this.getStat(Stat.MANA);
//            case MAX_ACTION -> ((double) this.getStat(Stat.CURRENT_ACTION)) / this.getStat(Stat.MAX_ACTION);
            default -> 0.0;
        };
    }
    public int getCurrentLifePercentage() {
        return this.stats.get(Stat.CURRENT_LIFE) * 100 / this.stats.get(Stat.LIFE);
    }
    public int getMissingLifePercentage() {
        return (100 - getCurrentLifePercentage());
    }
    public int getCurrentManaPercentage() {
        return this.stats.get(Stat.CURRENT_MANA) * 100 / this.stats.get(Stat.MANA);
    }

//TurnMagic
    public void prepareCast() {
        for (Skill skill : this.skills) {
            if (skill != null) {
                skill.getCurrentVersion();
            }
        }
    }
    public void startOfTurn() {
        StartOfTurnPayload startOfTurnPayload = new StartOfTurnPayload();
        Connector.fireTopic(this.id + Connector.START_OF_TURN, startOfTurnPayload);
    }
    public void endOfTurn() {
        skillTurn();
        equipmentTurn();
        if (this.getStat(Stat.CURRENT_LIFE) < 1) {
            return;
        }
        int heal = getStat(Stat.LIFE_REGAIN);
        if (this.hasPermanentEffect(Injured.class) > 0) {
            return;
        }
        heal(this, heal, null, true);
//        refreshAction();
        if (this.secondaryResource != null && this.secondaryResource.equals(Stat.MANA)) {
            addResource(Stat.CURRENT_MANA, Stat.MANA, this.getStat(Stat.MANA_REGAIN), this);
        }
        effectTurn();
    }
//    public void refreshAction() {
//        if (this.hasPermanentEffect(Exhausted.class) == 0 && (!this.movedLast || getStat(Stat.CURRENT_ACTION) == 0)) {
//            this.changeStatTo(Stat.CURRENT_ACTION, this.getStat(Stat.MAX_ACTION));
//        }
//    }

//Equipment Magic
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
    private void equipmentTurn() {
        for (Equipment equipment: this.equipments) {
            equipment.turn();
        }
    }
//EffectMagic
    private void effectTurn() {
        for (Effect effect : effects) {
            effect.turn();
        }
        cleanUpEffect();
    }
    private void cleanUpEffect() {
        List<Effect> toRemove = this.effects.stream()
                .filter(e->e.turns == 0 || (e.stackable && e.stacks <= 0))
                .toList();
        for (Effect effectToRemove : toRemove) {
            removeEffect(effectToRemove);
        }
    }
    private void removeEffect(Effect effect) {
        effect.removeEffect();
        this.effects.remove(effect);
    }
    public <T extends Effect> void removeStack(Class<T> clazz) {
        for (Effect effect : this.effects) {
            if (effect.getClass().equals(clazz)) {
                effect.removeStack();
            }
        }
    }
    public <T extends Effect> int getPermanentEffectStacks(Class<T> clazz) {
        int amount = 0;
        for (Effect currentEffect : effects) {
            if (currentEffect.getClass().equals(clazz)) {
                amount+=currentEffect.stacks;
            }
        }
        return amount;
    }
    public void addAllEffects(List<Effect> effects, Hero caster) {
        for (Effect effect : effects) {
            this.addEffect(effect, caster);
        }
    }
    public void addEffect(Effect effect, Hero caster) {

        if (getEffectFailure(effect, caster)) {
            return;
        }
        boolean added = false;
        boolean newlyAdded = false;
        if (effect.negates != null) {
            boolean negates = this.effects.stream().anyMatch(e->e.name.equals(effect.negates));
            if (negates) {
                this.removeEffectByName(effect.negates);
                added = true;
            }
        } else {
            for (Effect effectHave : effects) {
                if (effectHave.getClass().equals(effect.getClass())) {
                    if (effect.stackable) {
                        added = true;
                        newlyAdded = true;
                        effectHave.addStack(effect.stacks);
                    } else {
                        effectHave.turns = effect.turns;
                        added = true;
                    }
                }
            }
        }
        if (!added && this.effects.size() != 12) {
            Effect newEffect = effect.getNew();
            newEffect.origin = caster;
            newEffect.hero = this;
            this.effects.add(newEffect);
            newEffect.addToHero();
            newlyAdded = true;
        }
        if (newlyAdded) {
            EffectAddedPayload effectAddedPayload = new EffectAddedPayload()
                    .setEffect(effect)
                    .setCaster(caster);
            Connector.fireTopic(Connector.EFFECT_ADDED, effectAddedPayload);
        }
        this.arena.logCard.addToLog(this.getName() + " received " + effect.getIconString() + "("+(effect.stackable?effect.stacks:effect.turns)+").");
    }
    private boolean getEffectFailure(Effect effect, Hero caster) {
        if (hasPermanentEffect(Immunity.class) > 0) {
            return true;
        }
        EffectFailurePayload payload = new EffectFailurePayload()
                .setEffect(effect)
                .setCaster(caster);
        Connector.fireTopic(Connector.EFFECT_FAILURE, payload);
        return payload.isFailure();
    }
    public int hasPermanentEffect(String effectName) {
        int amount = 0;
        for (Effect currentEffect : effects) {
            if (currentEffect.name.equals(effectName)) {
                amount+=currentEffect.stacks;
            }
        }
        return amount;
    }
    public <T extends Effect> int hasPermanentEffect(Class<T> clazz) {
        int amount = 0;
        for (Effect currentEffect : effects) {
            if (currentEffect.getClass().equals(clazz)) {
                amount+=currentEffect.stacks;
            }
        }
        return amount;
    }
    public <T extends Effect> void decreaseEffectStack(Class<T> clazz) {
        if (this.hasPermanentEffect(clazz) > 0) {
            this.getPermanentEffect(clazz).stacks--;
            this.cleanUpEffect();
        }
    }
    public void removeEffectByName(String name) {
        Logger.logLn(this.name + ".removePermanentEffect:" +name);
        List<Effect> toRemove = this.effects.stream()
                .filter(e->e.name.equals(name))
                .toList();
        for (Effect effect : toRemove) {
            removeEffect(effect);
        }
    }
    public <T extends Effect> void removePermanentEffectOfClass(Class<T> clazz) {
        Logger.logLn(this.name + ".removePermanentEffect:" + clazz.getName());
        List<Effect> toRemove = this.effects.stream()
                .filter(e->e.getClass().equals(clazz))
                .toList();
        for (Effect effect : toRemove) {
            removeEffect(effect);
        }
    }
    public <T extends Effect> Effect getPermanentEffect(Class<T> clazz) {
        for (Effect currentEffect : effects) {
            if (currentEffect.getClass().equals(clazz)) {
                return currentEffect;
            }
        }
        return null;
    }

//SkillMagic
    private void skillTurn() {
        for (Skill s : this.skills) {
            if (s != null) {
                s.turn();
            }
        }
    }
    public <T extends Skill> boolean hasSkill(Class<T> clazz) {
        for (Skill s : this.skills) {
            if (s!= null && s.getClass().equals(clazz)) {
                return true;
            }
        }
        return false;
    }
    public boolean canPerform(Skill s, int[] targetPositions) {

        if (!canPerformResourceCheck(s)) {
            return false;
        }
        CanPerformPayload canPerformPayload = new CanPerformPayload()
                .setSkill(s)
                .setTargetPositions(targetPositions);
        Connector.fireTopic(Connector.CAN_PERFORM, canPerformPayload);
        return canPerformPayload.success;
    }
    private boolean canPerformResourceCheck(Skill s) {
        if (s == null ){
            return false;
        }
        return this.stats.get(Stat.CURRENT_LIFE) > s.getLifeCost() &&
                this.stats.get(Stat.CURRENT_MANA) > s.getManaCost() && !s.isPassive();
    }
    public void payForSkill(Skill s) {
        addToStat(Stat.CURRENT_LIFE, -1*s.getLifeCost(this));
        addToStat(Stat.CURRENT_MANA, -1*s.getManaCost());
        Logger.logLn("Paid life:"+s.getLifeCost(this));
    }

    public void removeNegativeEffects() {
        List<Effect> toRemove = this.effects.stream().filter(e-> e.type.equals(Effect.ChangeEffectType.DEBUFF)).toList();
        for (Effect effect : toRemove) {
            this.removeEffect(effect);
        }
    }
    public void removePositiveEffects() {
        for (Effect effect : this.effects) {
            if (effect.type.equals(Effect.ChangeEffectType.BUFF)) {
                this.removeEffect(effect);
            }
        }
    }

    public int simulateHealInPercentages(Hero caster, int heal, Skill skill) {
        HealChangesPayload healChangesPayload = new HealChangesPayload()
                .setCaster(caster)
                .setTarget(this)
                .setSkill(skill)
                .setHeal(heal);
        Connector.fireTopic(Connector.HEAL_CHANGES, healChangesPayload);
        int pureHeal = healChangesPayload.heal;
        int actualHeal = Math.min(pureHeal, this.stats.get(Stat.LIFE) - this.stats.get(Stat.CURRENT_LIFE));
        return actualHeal * 100 / this.stats.get(Stat.LIFE);
    }
    public void heal(Hero caster, int heal, Skill skill, boolean regen) {
        HealChangesPayload healChangesPayload = new HealChangesPayload()
                .setCaster(caster)
                .setTarget(this)
                .setSkill(skill)
                .setHeal(heal)
                .setRegen(regen);
        Connector.fireTopic(Connector.HEAL_CHANGES, healChangesPayload);
        int resultHeal = healChangesPayload.heal;
        if (resultHeal >0) {
            this.arena.logCard.addToLog(this.getName() + " was healed by " + resultHeal + ".");
            addResource(Stat.CURRENT_LIFE, Stat.LIFE, resultHeal, caster);
        }
    }

    public void shield(int shield, Hero source) {
        ShieldChangesPayload pl = new ShieldChangesPayload()
                .setShield(shield)
                .setSource(source)
                .setTarget(this);
        Connector.fireTopic(Connector.SHIELD_CHANGES, pl);
        addResource(Stat.SHIELD, Stat.LIFE, pl.shield, source);
    }
    public int trueDamage(Hero caster, int damage) {
        playAnimation("damaged", true);
        addResource(Stat.CURRENT_LIFE, Stat.LIFE, -1*damage, caster);
        return damage;
    }
    public int damage(Hero caster, int damage,  int lethality, Skill skill) {

        int def = getStat(Stat.STAMINA);
        int result = MyMaths.getDamage(damage, def, lethality);
        DmgChangesPayload dmgChangesPayload = new DmgChangesPayload()
                .setCaster(caster)
                .setTarget(this)
                .setSkill(skill)
                .setDmg(result)
                .setSimulate(false);
        Connector.fireTopic(Connector.DMG_CHANGES, dmgChangesPayload);
        System.out.println("dmg:"+result);
        Logger.logLn("1play dmg animation of " + this.name + "/"+this.id);
        playAnimation("damaged", true);
        int shield = getStat(Stat.SHIELD);
        if (shield > 0) {
            int dmgToShield;
            boolean broken = false;
            if (shield < result) {
                this.changeStatTo(Stat.SHIELD, 0);
                result -= shield;
                dmgToShield = shield;
                broken = true;
            } else {
                this.shield(-1*result, caster);
                dmgToShield = result;
                result = 0;
            }

            DmgToShieldPayload dmgToShieldPayload = new DmgToShieldPayload()
                    .setTarget(this)
                    .setDmg(dmgToShield);
            Connector.fireTopic(Connector.DMG_TO_SHIELD, dmgToShieldPayload);

            if (broken) {
                ShieldBrokenPayload pl = new ShieldBrokenPayload()
                        .setTarget(this);
                Connector.fireTopic(Connector.SHIELD_BROKEN, pl);
            }
        }
        this.arena.logCard.addToLog(this.getName() + " was dealt " + result + " damage.");
        addResource(Stat.CURRENT_LIFE, Stat.LIFE, -1*result, caster);
        return result;
    }

    public int simulateDamageInPercentages(Hero caster, int damage, int lethality, Skill skill) {
        int def = getStat(Stat.STAMINA);
        int result = MyMaths.getDamage(damage, def, lethality);
        DmgChangesPayload dmgChangesPayload = new DmgChangesPayload()
                .setCaster(caster)
                .setTarget(this)
                .setSkill(skill)
                .setDmg(result)
                .setSimulate(true);
        Connector.fireTopic(Connector.DMG_CHANGES, dmgChangesPayload);
        return result * 100 / this.stats.get(Stat.LIFE);
    }

    public void effectDamage(int damage, Effect effect) {
        EffectDmgChangesPayload dmgChangesPayload = new EffectDmgChangesPayload()
                .setTarget(this)
                .setEffect(effect)
                .setDmg(damage);
        Connector.fireTopic(Connector.EFFECT_DMG_CHANGES, dmgChangesPayload);
        damage = dmgChangesPayload.dmg;
        Logger.logLn("2play dmg animation of " + this.name + " at pos " + this.position);
        this.anim.playAnimation("damaged", true);
        addToStat(Stat.CURRENT_LIFE, -1*damage);
        DmgTriggerPayload dmgTriggerPayload = new DmgTriggerPayload()
                .setDmgDone(damage)
                .setTarget(this)
                .setEffect(effect);
        Connector.fireTopic(Connector.EFFECT_DMG_TRIGGER, dmgTriggerPayload);
    }
//GUI
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
            case LIFE -> getHealthString();
            case MANA -> getManaString();
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

//Loading

    protected Map<Stat, Integer> loadLevelStats() {

        Map<Stat, Integer> statJson = FileWalker.getStatJson(this.basePath + STAT_PATH);
        if (statJson != null) {
            return statJson;
        }
        return new HashMap<>();
    }


//GetterSetter

    private String getManaString() {
        return this.stats.get(Stat.CURRENT_MANA) + "(+" + this.stats.get(Stat.MANA_REGAIN) + ")/" + this.stats.get(Stat.MANA);
    }

    public String getHealthString() {
        return this.stats.get(Stat.CURRENT_LIFE) + "(+" + this.stats.get(Stat.LIFE_REGAIN) + ")/" + this.stats.get(Stat.LIFE);
    }

    public static Color getResourceColor(Stat stat) {
        if (stat == null) {
            return Color.BLACK;
        }
        return switch (stat) {
            case LIFE -> Color.GREEN;
            case MANA -> Color.BLUE;
            default -> Color.WHITE;
        };
    }
    public List<Hero> getAllies() {
        List<Hero> allies = new ArrayList<>();
        for (Hero hero: this.team.heroes) {
            if (hero != this) {
                allies.add(hero);
            }
        }
        return allies;
    }
    public List<Hero> getEnemies() {
        if (this.isTeam2()) {
            return Arrays.stream(this.arena.teams.get(0).heroes).filter(Objects::nonNull).toList();
        } else {
            return Arrays.stream(this.arena.teams.get(1).heroes).filter(Objects::nonNull).toList();
        }
    }

//GETTERS SETTERS

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
        return this.team.teamNumber==2;
    }

    public List<Skill> getSpecificSkills(SkillTag tag) {
        return this.skills.stream().filter(s->s.tags.contains(tag)).toList();
    }

//DEV

    public void devDMGTestSkill(int index, Hero target) {
        if (this.skills.size() > index) {
            Skill skill = this.skills.get(index);
            System.out.print(skill.getName()+":");
            skill.setTargets(new Hero[]{target});
            skill.resolve();
        }
    }

    public int getCasterPosition() {
        return this.isTeam2() ? Arena.lastEnemyPos - this.position : this.position;
    }

    public Role getRole() {
        return this.role;
    }

    public int getLevel() {
        return level;
    }
}
