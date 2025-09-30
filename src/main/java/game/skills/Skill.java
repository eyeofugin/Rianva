package game.skills;

import framework.Logger;
import framework.Property;
import framework.connector.Connector;
import framework.connector.payloads.*;
import framework.graphics.text.Color;
import framework.graphics.text.TextEditor;
import framework.resources.SpriteLibrary;
import framework.states.Arena;
import game.entities.Hero;
import game.entities.Multiplier;
import game.objects.Equipment;
import game.skills.changeeffects.effects.Protected;
import utils.MyMaths;

import java.util.*;

public abstract class Skill {

    private static int counter;
    public int id;
    public String name;
    public Hero hero;
    public Equipment equipment;
    public String description;
    protected int[] iconPixels;
    protected String iconPath;
    protected String animationName = "action_w";

    public List<SkillTag> tags = new ArrayList<>();
    public List<AiSkillTag> aiTags = new ArrayList<>();

    protected int level = 1;
    protected TargetType targetType = TargetType.SINGLE;
    protected DamageMode damageMode = null;
    protected List<Effect> effects = new ArrayList<>();
    protected List<Effect> casterEffects = new ArrayList<>();

    protected List<Resource> targetResources = new ArrayList<>();

    protected List<Multiplier> dmgMultipliers = new ArrayList<>();
    protected List<Multiplier> healMultipliers = new ArrayList<>();
    protected List<Multiplier> shieldMultipliers = new ArrayList<>();

    protected List<Hero> targets = new ArrayList<>();

    protected int manaCost = 0;
    protected int lifeCost = 0;
    protected int faithRequirement = 0;
    protected int faithGain = 0;
    protected int faithCost = 0;
    protected int actionCost = 1;
    protected int accuracy = 100;
    protected int critChance = 0;
    protected int dmg = 0;
    protected int heal = 0;
    protected int shield = 0;
    protected int cdMax = 0;
    protected int cdCurrent = 0;
    protected boolean canMiss = true;
    protected int countAsHits = 1;
    protected int lethality = 0;
    protected int move = 0;
    protected boolean moveTo = false;

    public int priority = 0; // 0 = normal, 1 = fast move, 2 = fastest move, 5 = shield moves

    public int[] possibleTargetPositions = new int[0];
    public int[] possibleCastPositions = new int[0];

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

    public boolean performCheck(Hero hero) {
        return Arrays.stream(this.possibleCastPositions).anyMatch(i -> i == hero.getCasterPosition());
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
        this.manaCost = 0;
        this.lifeCost = 0;
        this.actionCost = 1;
        this.accuracy = 100;
        this.critChance = 0;
        this.dmg = 0;
        this.heal = 0;
        this.cdMax = 0;
        this.shield = 0;
        this.canMiss = true;
        this.countAsHits = 1;
        this.priority = 0;
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
        if (cdCurrent > 0) {
            cdCurrent--;
        }
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
        this.setCdCurrent(this.getCdMax());
        Logger.logLn("Cd now " + this.getCdCurrent());
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
        DamageMode dm = this.getDamageMode();
        int lethality = this.hero.getStat(Stat.LETHALITY);
        for (int i = 0; i < getCountsAsHits(); i++) {
            int dmgPerHit = dmg;
            if (this.damageMode != null && this.damageMode.equals(DamageMode.PHYSICAL)) {
                int critChance = this.hero.getStat(Stat.CRIT_CHANCE);
                critChance += this.critChance;
                if (MyMaths.success(critChance)) {
                    this.hero.arena.logCard.addToLog("Crit!");
                    Logger.logLn("Crit!");
                    dmgPerHit= (int)(dmgPerHit * 1.5);
                    this.fireCritTrigger(target, this);
                }
            }
            if (dmgPerHit>0) {
                int doneDamage = target.damage(this.hero, dmgPerHit, dm, lethality, this);
                this.fireDmgTrigger(target,this, doneDamage, dm);
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

    public void fireDmgTrigger(Hero target, Skill cast, int damageDone, DamageMode dm) {
        DmgTriggerPayload dmgTriggerPayload = new DmgTriggerPayload()
                .setTarget(target)
                .setCast(cast)
                .setDmgDone(damageDone)
                .setDamageMode(dm);
        Connector.fireTopic(Connector.DMG_TRIGGER, dmgTriggerPayload);
    }
    public void applySkillEffects(Hero target) {
        this.hero.addAllEffects(this.getCasterEffects(), this.hero);
        if (this.faithGain > 0) {
            this.hero.addResource(Stat.CURRENT_FAITH, Stat.FAITH, this.faithGain, this.hero);
        }
        if (this.move != 0) {
            this.hero.arena.moveTo(target, target.getPosition() + (target.isTeam2()?this.move:-1*this.move));
        }
        target.addAllEffects(this.getEffects(), this.hero);
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
        List<Integer> targetList = new ArrayList<>();

        for (int pos : this.possibleTargetPositions) {
            int targetPos = convertTargetPos(pos);
            if (this.targetType.equals(TargetType.SINGLE_OTHER) && this.hero.getPosition() == targetPos) {
                continue;
            }
            targetList.add(targetPos);
        }
        if (targetType.equals(TargetType.SINGLE) && targetList.stream().anyMatch(i -> i > 2)) {
            targetList.addAll(List.of(0,1,2));
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

    public DamageMode getDamageMode() {
        return damageMode;
    }

    public void setDamageMode(DamageMode damageMode) {
        this.damageMode = damageMode;
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


    public int getManaCost() {
        return manaCost;
    }


    public void setManaCost(int manaCost) {
        this.manaCost = manaCost;
    }
    public int getFaithRequirement() {
        return faithRequirement;
    }
    public int getFaithCost() {
        return this.faithCost;
    }
    public void setFaithRequirement(int faithRequirement) {
        this.faithRequirement = faithRequirement;
    }
    public int getLifeCost() {
        return lifeCost;
    }

    public void setLifeCost(int lifeCost) {
        this.lifeCost = lifeCost;
    }

    public int getActionCost() {
        return actionCost;
    }

    public void setActionCost(int actionCost) {
        this.actionCost = actionCost;
    }

    public int getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(int accuracy) {
        this.accuracy = accuracy;
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

    public int getCdMax() {
        return cdMax;
    }

    public void setCdMax(int cdMax) {
        this.cdMax = cdMax;
    }

    public int getCdCurrent() {
        return cdCurrent;
    }
    public void setCdCurrent(int cdCurrent) {
        this.cdCurrent = cdCurrent;
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
        List<Integer> castPosList = Arrays.stream(this.possibleCastPositions)
                .boxed()
                .toList();
        List<Integer> targetPosList = Arrays.stream(this.possibleTargetPositions)
                .boxed()
                .toList();
        for (int i = 0; i < Arena.firstEnemyPos; i++) {
            if(castPosList.contains(i)) {
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
        } else if (targetType.equals(TargetType.ANY)) {
            builder.append("Any");
//        } else if (targetType.equals(TargetType.ONE_RDM)){
//            builder.append("1 Rdm");
//        } else if (targetType.equals(TargetType.TWO_RDM)) {
//            builder.append("2 Rdm");
//        } else if (targetType.equals(TargetType.THREE_RDM)) {
//            builder.append("3 Rdm");
        } else if (targetPosList.stream().anyMatch(i-> i < Arena.firstEnemyPos)){
            for (int i = 0; i < Arena.numberPositions; i++) {
                if (targetPosList.contains(i)) {
                    if (targetType.equals(TargetType.SINGLE_OTHER)) {
                        builder.append("[OTT]");
                    } else if (targetType.equals(TargetType.SINGLE)) {
                        builder.append("[FTT]");
                    } else if (targetType.equals(TargetType.ALL_TARGETS)){
                        builder.append("[FTA]");
                    }
                } else {
                    builder.append("[EMT]");
                }
            }
        } else {
            for (int i = Arena.firstEnemyPos; i <= Arena.lastEnemyPos; i++) {
                if (targetPosList.contains(i)) {
                    if (targetType.equals(TargetType.SINGLE)) {
                        builder.append("[ETT]");
                    } else if (targetType.equals(TargetType.ALL_TARGETS)) {
                        builder.append("[ETA]");
                    }
                } else {
                    builder.append("[EMT]");
                }
            }
        }
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
            builder.append(this.damageMode.getColor().getCodeString());
            builder.append("DMG: ");
            builder.append(Color.WHITE.getCodeString());
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
    public String getCostString() {
        if (this.isPassive()) {
            return "";
        }
        StringBuilder costString = new StringBuilder();
        if (this.getManaCost() != 0 || this.getFaithRequirement() != 0 || this.getFaithCost() != 0 || this.getLifeCost() != 0) {
            costString.append("Cost:");
        }
        if (this.getManaCost() != 0) {
            costString.append(Color.BLUE.getCodeString()).append(this.getManaCost()).append("{000}");
            costString.append(Stat.CURRENT_MANA.getIconString());
        }
        if (this.getFaithRequirement() != 0) {
            costString.append(Color.LIGHTYELLOW.getCodeString()).append(this.getFaithRequirement()).append("{000}");
            if (this.getFaithCost() > 0) {
                costString.append(Color.RED.getCodeString()).append(this.getFaithCost()).append("{000}");
            }
            costString.append(Stat.CURRENT_FAITH.getIconString());
        }
        if (this.getLifeCost() != 0) {
            costString.append(Color.RED.getCodeString()).append(this.getLifeCost()).append("{000}");
            costString.append(Stat.CURRENT_LIFE.getIconString());
        }
        if (this.actionCost > 1) {
            costString.append("Action:");
            costString.append(("["+TextEditor.ACTION_KEY+"]").repeat(this.actionCost)).append(" ");
        }
        if (this.cdMax != 0 || this.cdCurrent!=0) {
            costString.append("CD:");
            for (int i = 1; i <= this.cdMax || i <= this.cdCurrent; i++) {
                if (i <= this.cdCurrent) {
                    costString.append("[").append(TextEditor.TURN_CD_KEY).append("]");
                } else {

                    costString.append("[").append(TextEditor.TURN_KEY).append("]");
                }
            }
            costString.append(" ");
        }
        return costString.toString();
    }

    public String getFaithGainString() {
        if (this.faithGain > 0) {
            return "Gain " + this.faithGain + Stat.FAITH.getIconString() + ".";
        }
        return "";
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
            if (effect.stat != null) {
                if (effect.statChange != 0) {
                    effectString.append(effect.statChange > 0? Color.GREEN.getCodeString(): Color.RED.getCodeString());
                    effectString.append(effect.statChange);
                } else if (effect.statChangePercentage != 0) {
                    effectString.append(effect.statChangePercentage > 0? Color.GREEN.getCodeString(): Color.RED.getCodeString());
                    effectString.append(effect.statChangePercentage*100).append("%");
                }
                effectString.append(Color.WHITE.getCodeString());
                effectString.append(effect.getIconString()).append("(").append(value == -1 ? "~" : value).append(")");
            } else {
                effectString.append(effect.getIconString()).append("(").append(value == -1 ? "~" : value).append(")");
            }
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
                ", manaCost=" + manaCost +
                ", lifeCost=" + lifeCost +
                ", actionCost=" + actionCost +
                ", accuracy=" + accuracy +
                ", dmg=" + dmg +
                ", heal=" + heal +
                ", cdMax=" + cdMax +
                ", cdCurrent=" + cdCurrent +
                ", canMiss=" + canMiss +
                ", countAsHits=" + countAsHits +
                ", tags=" + tags +
                '}';
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
        this.cdCurrent = 0;
    }
}
