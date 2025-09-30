package game.entities.heroes.paladin;

import game.entities.Hero;
import game.entities.Multiplier;
import game.skills.*;
import game.skills.changeeffects.effects.Dazed;
import utils.MyMaths;

import java.util.List;

public class S_ShatteringSwing extends Skill {

    public S_ShatteringSwing(Hero hero) {
        super(hero);
        this.iconPath = "entities/heroes/paladin/icons/shatteringswing.png";
        addSubscriptions();
        setToInitial();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.PRIMARY);
        this.dmgMultipliers = List.of(new Multiplier(Stat.POWER, 0.5));
        this.targetType = TargetType.SINGLE;
        this.possibleCastPositions = new int[]{1,2};
        this.possibleTargetPositions = new int[]{3};
        this.faithGain = 20;
        this.damageMode = DamageMode.PHYSICAL;
    }



    @Override
    public void applySkillEffects(Hero target) {
        super.applySkillEffects(target);
        if (MyMaths.success(this.hero.getStat(Stat.CURRENT_FAITH))) {
            target.addEffect(new Dazed(1), this.hero);
        }
    }

    @Override
    public String getDescriptionFor(Hero hero) {
        return this.hero.getStat(Stat.CURRENT_FAITH)+"(100%"+Stat.FAITH.getIconString()+")% chance to give " + Dazed.getStaticIconString() + "(1).";
    }


    @Override
    public String getName() {
        return "Shattering Swing";
    }
}
