package game.skills;

import framework.Logger;
import framework.Property;
import framework.connector.Connection;
import framework.connector.ConnectionType;
import framework.connector.Connector;
import framework.connector.payloads.*;
import framework.graphics.text.Color;
import framework.resources.SpriteLibrary;
import framework.states.Arena;
import game.entities.Hero;
import game.entities.Multiplier;
import game.objects.Equipment;
import game.skills.changeeffects.effects.other.Protected;
import game.skills.logic.*;
import utils.MyMaths;

import java.util.*;

public class Skill {

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

    protected int level = 1;
    protected TargetType targetType = TargetType.SINGLE;
    protected DamageType damageType = null;
    protected double lifeSteal = 0.0;
    protected List<Effect> effects = new ArrayList<>();
    protected List<Effect> casterEffects = new ArrayList<>();
    protected List<Resource> targetResources = new ArrayList<>();

    protected List<Multiplier> dmgMultipliers = new ArrayList<>();
    protected List<Multiplier> healMultipliers = new ArrayList<>();
    protected List<Multiplier> shieldMultipliers = new ArrayList<>();

    protected List<Hero> targets = new ArrayList<>();


    protected int manaCost = 0;
    protected int lifeCost = 0;
    protected int accuracy = 100;
    protected int critChance = 0;
    protected int dmg = 0;
    protected int heal = 0;
    protected int shield = 0;
    protected boolean canMiss = true;
    protected int countAsHits = 1;
    protected int lethality = 0;
    protected int move = 0;
    protected boolean moveTo = false;

    public SkillScripts scripts;

    public Map<ConnectionType, String> triggerMap;

    //AI
    public int getAIRating(Hero target){return 0;}
    public int getAIArenaRating(Arena arena) {return 0;}

    protected int getRollRating(Hero target) {

        int lastEffectivePosition = this.hero.getLastEffectivePosition();
        int targetLifeAdvantage = target.getStat(Stat.CURRENT_LIFE) - this.hero.getStat(Stat.CURRENT_LIFE);

        if (target.getPosition() > this.hero.getPosition()) { //ROll Back
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

    public Skill() {}

    public Skill(SkillDTO dto) {
        this.scripts = new SkillScripts(this);
        this.name = dto.name;
        this.description = dto.description;
        this.iconPath = dto.iconPath;
        this.animationName = dto.animationName != null? dto.animationName : "action_w";
        this.tags = dto.tags;
        this.aiTags = dto.aiTags;
        this.level = dto.level;
        this.targetType = dto.targetType;
        this.damageType = dto.damageType;
        this.lifeSteal = dto.lifeSteal;
        this.targetResources = dto.targetResources;
        this.dmgMultipliers = dto.dmgMultipliers;
        this.healMultipliers = dto.healMultipliers;
        this.shieldMultipliers = dto.shieldMultipliers;
        this.manaCost = dto.manaCost;
        this.lifeCost = dto.lifeCost;
        this.accuracy = dto.accuracy != 0 ? dto.accuracy : 100;
        this.critChance = dto.critChance;
        this.dmg = dto.dmg;
        this.heal = dto.heal;
        this.shield = dto.shield;
        this.canMiss = dto.canMiss;
        this.countAsHits = dto.countAsHits;
        this.lethality = dto.lethality;
        this.move = dto.move;
        this.moveTo = dto.moveTo;
        this.triggerMap = dto.triggerMap;

        initEffects(dto);

        if (SpriteLibrary.hasSprite(this.getName())) {
            this.iconPixels = SpriteLibrary.getSprite(this.getName());
        } else {
            this.iconPixels = SpriteLibrary.sprite(Property.SKILL_ICON_SIZE,Property.SKILL_ICON_SIZE,Property.SKILL_ICON_SIZE,Property.SKILL_ICON_SIZE,
                    this.iconPath, 0);
            SpriteLibrary.addSprite(this.getName(), this.iconPixels);
        }

        saveState = new Skill();
        Skill.writeInitialsFromTo(this, saveState);
    }

    private void initEffects(SkillDTO dto) {
        if (dto.effects != null) {
            for (SkillEffectDTO sed: dto.effects) {
                this.effects.add(EffectLibrary.getEffect(sed.name, sed.stacks, sed.turns, sed.condition));
            }
        }
        if (dto.casterEffects != null){
            for (SkillEffectDTO sed : dto.casterEffects) {
                this.casterEffects.add(EffectLibrary.getEffect(sed.name, sed.stacks, sed.turns, sed.condition));
            }
        }
    }

    public Skill(Hero hero) {
        this.id = ++counter;
        this.hero = hero;
    }

    public void getCurrentVersion() {
        this.setToInitial();
        CastChangePayload payload = new CastChangePayload()
                .setSkill(this);
        Connector.fireTopic(Connector.CAST_CHANGE, payload);
    }

    public void setToInitial() {
        Logger.logLn("Set to initial");
        this.effects = new ArrayList<>();
        this.casterEffects = new ArrayList<>();
        this.dmgMultipliers = new ArrayList<>();
        this.lifeCost = 0;
        this.accuracy = 100;
        this.critChance = 0;
        this.dmg = 0;
        this.heal = 0;
        this.manaCost = 0;
        this.shield = 0;
        this.canMiss = true;
        this.countAsHits = 1;
        this.lethality = 0;
        this.move = 0;
        this.level = 1;
        if (SpriteLibrary.hasSprite(this.getName())) {
            this.iconPixels = SpriteLibrary.getSprite(this.getName());
        } else {
            this.iconPixels = SpriteLibrary.sprite(Property.SKILL_ICON_SIZE,Property.SKILL_ICON_SIZE,Property.SKILL_ICON_SIZE,Property.SKILL_ICON_SIZE,
                    this.iconPath, 0);
            SpriteLibrary.addSprite(this.getName(), this.iconPixels);
        }
    }

    public void turn() {
    }

    public int getLethality() {
        return this.lethality;
    }

    public String getUpperDescriptionFor(Hero hero) {
        return "";
    }

    public String getComboDescription(Hero hero) {
        return "";
    }

    public String getDescriptionFor(Hero hero) {
        return "";
    };
    public void addSubscriptions() {
        if (triggerMap != null) {
            for (Map.Entry<ConnectionType, String> entry : this.triggerMap.entrySet()) {
                ConnectionType ct = entry.getKey();
                Connector.addSubscription(ct.name, new Connection(scripts, ct.payloadClass, entry.getValue()));
            }
        }
    }
    public void removeSubscriptions() {
        Connector.removeSubscriptions(this);
    }
    //SKILL LOGIC
    public void baseDamageChanges(Hero target, Hero caster){
        if (this.dmg > 0 || !this.dmgMultipliers.isEmpty()) {
            DmgChangesPayload dmgChangesPayload = new DmgChangesPayload()
                    .setDmg(this.dmg)
                    .setSkill(this)
                    .setTarget(target)
                    .setCaster(caster);
            Connector.fireTopic(Connector.BASE_DMG_CHANGES, dmgChangesPayload);
            this.dmg = dmgChangesPayload.dmg;
        }
    }
    public void baseHealChanges(Hero target, Hero caster) {
        if (this.heal > 0 || !this.healMultipliers.isEmpty()) {
            BaseHealChangesPayload baseHealChangesPayload = new BaseHealChangesPayload()
                    .setHeal(this.heal)
                    .setSkill(this)
                    .setTarget(target)
                    .setCaster(caster);
            Connector.fireTopic(Connector.BASE_HEAL_CHANGES, baseHealChangesPayload);
            this.heal = baseHealChangesPayload.heal;
        }
    }
    public void perform() {
        this.hero.arena.logCard.addToLog(this.getName() + " performed by " + this.hero.getName() + ".");
        OnPerformPayload pl = new OnPerformPayload()
                .setSkill(this);
        Connector.fireTopic(Connector.ON_PERFORM, pl);
        this.hero.playAnimation(this.animationName);
        this.hero.payForSkill(this);
    }
    public void clearEffects() {
        this.effects = new ArrayList<>();
        this.casterEffects = new ArrayList<>();
    }

    public void resolve() {
        //init action summary
        if (this.targetType.equals(TargetType.ARENA)) {
            this.applySkillEffects(this.hero);
        } else {
            oncePerActivationEffect();
            for (Hero arenaTarget : targets) {

                if (this.targetType.equals(TargetType.SELF) || this.targetType.equals(TargetType.SINGLE_OTHER)) {
                    this.individualResolve(arenaTarget);
                } else {
                    if (arenaTarget.hasPermanentEffect(Protected.class) > 0
                        && arenaTarget.isTeam2() != this.hero.isTeam2()) {
                        this.hero.arena.logCard.addToLog(arenaTarget.getName()+" is shielded!");
                        return;
                    }
                    int evasion = arenaTarget.getStat( Stat.EVASION);
                    int acc = hero.getStat(Stat.ACCURACY);
                    int hitChance = this.accuracy * acc / 100;
                    if (this.canMiss && !MyMaths.success(hitChance - evasion)) {
                        this.hero.arena.logCard.addToLog("Missed "+arenaTarget.getName()+"!");
                        continue;
                    }
                    this.individualResolve(arenaTarget);
                }
                if (this.moveTo) {
                    this.hero.arena.moveTo(this.hero, arenaTarget.getPosition());
                }
            }
        }
    }
    protected void oncePerActivationEffect() {}
    protected void individualResolve(Hero target) {
        this.baseDamageChanges(target, this.hero);
        this.baseHealChanges(target, this.hero);
        int dmg = getDmgWithMulti(target);
        int lethality = this.hero.getStat(Stat.LETHALITY);
        for (int i = 0; i < getCountsAsHits(); i++) {
            int dmgPerHit = dmg;
            int critChance = this.hero.getStat(Stat.CRIT_CHANCE);
            critChance += this.critChance;
            if (MyMaths.success(critChance)) {
                this.hero.arena.logCard.addToLog("Crit!");
                Logger.logLn("Crit!");
                dmgPerHit= (int)(dmgPerHit * 1.5);
                this.fireCritTrigger(target, this);
            }
            if (dmgPerHit>0) {
                int doneDamage = target.damage(this.hero, dmgPerHit, lethality, this);
                this.fireDmgTrigger(target,this, doneDamage);
            }
            int heal = this.getHealWithMulti(target);
            if (heal > 0) {
                target.heal(this.hero, heal, this, false);
            }
            int shield = this.getShieldWithMulti(target);
            if (shield > 0) {
                target.shield(shield, this.hero);
            }
            this.applySkillEffects(target);
        }
    }
    public void fireCritTrigger(Hero target, Skill cast) {
        CriticalTriggerPayload criticalTriggerPayload = new CriticalTriggerPayload()
                .setTarget(target)
                .setCast(cast);
        Connector.fireTopic(Connector.CRITICAL_TRIGGER, criticalTriggerPayload);
    }

    public void fireDmgTrigger(Hero target, Skill cast, int damageDone) {
        DmgTriggerPayload dmgTriggerPayload = new DmgTriggerPayload()
                .setTarget(target)
                .setCast(cast)
                .setDmgDone(damageDone);
        Connector.fireTopic(Connector.DMG_TRIGGER, dmgTriggerPayload);
    }
    public void applySkillEffects(Hero target) {

        for (Effect effect : this.getCasterEffects()) {
            if ( effect.condition != null && effect.condition.isMet(this.hero, target)) {
                this.hero.addEffect(effect, this.hero);
            }
        }
        if (this.move != 0) {
            this.hero.arena.moveTo(target, target.getPosition() + (target.isTeam2()?this.move:-1*this.move));
        }
        for (Effect effect : this.getEffects()) {
            if (effect.condition != null && effect.condition.isMet(this.hero, target)) {
                target.addEffect(effect, this.hero);
            }
        }
        target.addResources(this.targetResources, this.hero);

        //action summary add all effects
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
    protected int getMultiplierBonus(List<Multiplier> multipliers) {
        if(multipliers ==null) {
            return 0;
        }
        int result = 0;
        for(Multiplier m : multipliers) {
            result +=(int)( m.percentage * this.hero.getStat(m.prof));
        }
        return result;
    }
    public int[] setupTargetMatrix() {
        if (this.targetType == null) {
            return new int[0];
        }
        List<Integer> targetList = new ArrayList<>(Arrays.stream(Arena.allPos).boxed().toList());
        if (this.targetType.equals(TargetType.SINGLE_OTHER)) {
            targetList.remove(this.hero.getPosition());
        }
        Collections.sort(targetList);
        int[] targets = new int[targetList.size()];
        for (int i = 0; i < targets.length; i++) {
            targets[i] = targetList.get(i);
        }

        return targets;
    }

    public int getLifeCost(Hero caster) {
        return lifeCost;
    }
    protected List<Effect> of(Effect[] effects){
        List<Effect> result = new ArrayList<>();
        Collections.addAll(result, effects);
        return result;
    }
    protected List<Multiplier> of(Multiplier[] multiplier){
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

    public int getManaCost() {
        return manaCost;
    }

    public Skill setManaCost(int manaCost) {
        this.manaCost = manaCost;
        return this;
    }
    public int getDmg(Hero target) {
        return MyMaths.getLevelStat(dmg, this.hero.getLevel());
    }
    public int getDmgWithMulti(Hero target) {
        return getDmg(target) + getDmgMultiBonus();
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

    public int getShield(Hero target) {
        return MyMaths.getLevelStat(shield, this.hero.getLevel());
    }

    public int getLevel() {
        return level;
    }

    public String getTargetString() {
        StringBuilder builder = new StringBuilder();
        if (this.isPassive()) {
            return "Passive";
        }
        builder.append(this.targetType.getTranslation());
        return builder.toString();
    }

    protected String getDmgString() {
        int fullDmg = this.getDmgWithMulti(null);
        int dmg = this.getDmg(null);
        String pureDmg = dmg == 0? "": dmg + "";
        StringBuilder builder = new StringBuilder();
        Iterator<Multiplier> iter = this.dmgMultipliers.iterator();
        while (iter.hasNext()) {
            Multiplier mult = iter.next();
            String profColor = mult.prof.getColorKey();
            builder.append(profColor);
            builder.append((int)(mult.percentage*100))
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
        String pureHeal = heal == 0? "": heal + "";
        StringBuilder builder = new StringBuilder();
        Iterator<Multiplier> iter = this.healMultipliers.iterator();
        while (iter.hasNext()) {
            Multiplier mult = iter.next();
            String profColor = mult.prof.getColorKey();
            builder.append(profColor);
            builder.append((int)(mult.percentage*100))
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
        String pureShield = shield == 0? "": shield + "";
        StringBuilder builder = new StringBuilder();
        Iterator<Multiplier> iter = this.shieldMultipliers.iterator();
        while (iter.hasNext()) {
            Multiplier mult = iter.next();
            String profColor = mult.prof.getColorKey();
            builder.append(profColor);
            builder.append((int)(mult.percentage*100))
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

    public String getDmgStringGUI() {
        StringBuilder builder = new StringBuilder();
        if (this.getDmg(null) != 0 || !this.dmgMultipliers.isEmpty()) {
            builder.append("DMG: ");
            builder.append(getDmgString());
        }
        return builder.toString();
    }
    public String getHealStringGUI() {
        StringBuilder builder = new StringBuilder();
        if ((this.getHeal(null) != 0 || !this.healMultipliers.isEmpty())){
            if (this.getHeal(null) != 0 || !this.healMultipliers.isEmpty()){
                builder.append("HEAL: ");
                builder.append(Color.WHITE.getCodeString());
                builder.append(getHealString());
            }
        }
        return builder.toString();
    }
    public String getShieldStringGUI() {
        StringBuilder builder = new StringBuilder();
        if ((this.getShield(null) != 0 || !this.shieldMultipliers.isEmpty())){
            if (this.getShield(null) != 0 || !this.shieldMultipliers.isEmpty()) {
                builder.append("SHIELD: ");
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
            int value = effect.stackable  ? effect.stacks : effect.turns;
            effectString.append(effect.getIconString()).append("(").append(value == -1 ? "~" : value).append(")");
            if (iterator.hasNext()) {
                effectString.append(", ");
            }
        }

        return effectString.toString();
    }
    @Override
    public String toString() {
        return "\nSkill{" +
                "id=" + id +
                ", Hero=" + hero.getName() +
                ", name='" + getName() + '\'' +
                ", targetType=" + targetType +
                ", effects=" + effects +
                ", casterEffects=" + casterEffects +
                ", dmgMultipliers=" + dmgMultipliers +
                ", healMultipliers=" + healMultipliers +
                ", lifeCost=" + lifeCost +
                ", accuracy=" + accuracy +
                ", dmg=" + dmg +
                ", heal=" + heal +
                ", canMiss=" + canMiss +
                ", countAsHits=" + countAsHits +
                ", tags=" + tags +
                '}';
    }

    public static void writeInitialsFromTo(Skill from, Skill to) {
        to.effects = from.effects;
        to.casterEffects = from.casterEffects;
        to.targetResources = from.targetResources;
        to.dmgMultipliers = from.dmgMultipliers;
        to.healMultipliers = from.healMultipliers;
        to.shieldMultipliers = from.shieldMultipliers;
        to.manaCost = from.manaCost;
        to.lifeCost = from.lifeCost;
        to.accuracy = from.accuracy;
        to.critChance = from.critChance;
        to.dmg = from.dmg;
        to.heal = from.heal;
        to.shield = from.shield;
        to.canMiss = from.canMiss;
        to.lethality = from.lethality;
        to.countAsHits = from.countAsHits;
        to.move = from.move;
        to.moveTo = from.moveTo;
    }

    public void setTargets(Hero[] entitiesAt) {
        this.targets = List.of(entitiesAt);
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
    public void reset() {
    }
}
