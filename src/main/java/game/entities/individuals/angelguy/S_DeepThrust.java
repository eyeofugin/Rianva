package game.entities.individuals.angelguy;

import framework.graphics.text.Color;
import game.entities.Hero;
import game.entities.Multiplier;
import game.skills.*;
import game.skills.changeeffects.effects.Burning;
import game.skills.changeeffects.statusinflictions.Bleeding;
import utils.MyMaths;

import java.util.List;

public class S_DeepThrust extends Skill {

    public S_DeepThrust(Hero hero) {
        super(hero);
        this.iconPath = "entities/angelguy/icons/deepthrust.png";
        setToInitial();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.PRIMARY);
        this.targetType = TargetType.SINGLE;
        this.possibleTargetPositions = new int[]{3};
        this.possibleCastPositions = new int[]{1,2};
        this.effects = List.of(new Bleeding(1));
        this.damageMode = DamageMode.PHYSICAL;
        this.dmg = 2;
        this.dmgMultipliers = List.of(new Multiplier(Stat.POWER, 0.4));
        this.faithRequirement = 0;
        this.faithGain = 25;
    }
    @Override
    public void applySkillEffects(Hero target) {
        super.applySkillEffects(target);
        int success = this.hero.getStat(Stat.MAGIC) + 30;
        if (MyMaths.success(success)) {
            target.addEffect(new Bleeding(1), this.hero);
        }
    }

    @Override
    public String getDescriptionFor(Hero hero) {
        return this.hero.getStat(Stat.MAGIC) + 30 +" ("+Stat.MAGIC.getColorKey() + this.hero.getStat(Stat.MAGIC) + Stat.MAGIC.getIconString()+ Color.WHITE.getCodeString()+"+30)% chance to give " + Burning.getStaticIconString() + "(1).";
    }

    @Override
    public String getName() {
        return "Deep Thrust";
    }
}
