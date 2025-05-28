package game.entities.individuals.dragonbreather;

import game.entities.Hero;
import game.entities.Multiplier;
import game.skills.*;
import game.skills.changeeffects.effects.AxeSwingCounter;
import game.skills.changeeffects.effects.Burning;
import game.skills.changeeffects.effects.Combo;
import game.skills.changeeffects.statusinflictions.Bleeding;
import utils.MyMaths;

import java.util.List;

public class S_BlazingCleaver extends Skill {

    public S_BlazingCleaver(Hero hero) {
        super(hero);
        this.iconPath = "entities/dragonbreather/icons/blazingcleaver.png";
        addSubscriptions();
        setToInitial();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.PRIMARY);
        this.dmgMultipliers = List.of(new Multiplier(Stat.POWER, 0.1), new Multiplier(Stat.MAGIC, 0.3));
        this.targetType = TargetType.SINGLE;
        this.possibleCastPositions = new int[]{1,2};
        this.possibleTargetPositions = new int[]{3,4};
        this.effects = List.of(new Burning(1));
        this.dmg = 14;
        this.damageMode = DamageMode.PHYSICAL;
    }

    @Override
    public void applySkillEffects(Hero target) {
        super.applySkillEffects(target);
        if (this.hero.hasPermanentEffect(Combo.class) > 0) {
            this.hero.removePermanentEffectOfClass(Combo.class);
            int burnStacks = target.getPermanentEffectStacks(Burning.class);
            target.effectDamage(burnStacks, new Burning(0));
        }
    }

    @Override
    public int getAIRating(Hero target) {
        if (this.hero.hasPermanentEffect(AxeSwingCounter.class) == 1) {
            return 2;
        }
        return 0;
    }



    @Override
    public String getComboDescription(Hero hero) {
        return "Activates the target's "+Burning.getStaticIconString()+" stacks";
    }

    @Override
    public String getName() {
        return "Blazing Cleaver";
    }
}
