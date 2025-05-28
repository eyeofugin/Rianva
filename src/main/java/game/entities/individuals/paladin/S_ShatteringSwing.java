package game.entities.individuals.paladin;

import game.entities.Hero;
import game.entities.Multiplier;
import game.skills.*;
import game.skills.changeeffects.statusinflictions.Dazed;
import game.skills.changeeffects.statusinflictions.Disenchanted;
import game.skills.changeeffects.statusinflictions.Injured;
import utils.MyMaths;

import java.util.List;

public class S_ShatteringSwing extends Skill {

    public S_ShatteringSwing(Hero hero) {
        super(hero);
        this.iconPath = "entities/paladin/icons/shatteringswing.png";
        addSubscriptions();
        setToInitial();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.PRIMARY);
        this.dmgMultipliers = List.of(new Multiplier(Stat.POWER, 0.1));
        this.targetType = TargetType.SINGLE;
        this.possibleCastPositions = new int[]{1,2};
        this.possibleTargetPositions = new int[]{3};
        this.dmg = 5;
        this.faithGain = 15;
        this.damageMode = DamageMode.PHYSICAL;
    }



    @Override
    public void applySkillEffects(Hero target) {
        super.applySkillEffects(target);
        this.hero.addToStat(Stat.CURRENT_FAITH, 2);
        if (MyMaths.success(this.hero.getStat(Stat.CURRENT_FAITH))) {
            target.addEffect(new Dazed(1), this.hero);
        }
    }

    @Override
    public String getDescriptionFor(Hero hero) {
        return "Gain +15"+Stat.FAITH.getIconString()+". "+ 2*this.hero.getStat(Stat.FAITH)+"(100%"+Stat.FAITH.getIconString()+")% chance to give " + Dazed.getStaticIconString() + "(1).";
    }


    @Override
    public String getName() {
        return "Shattering Swing";
    }
}
