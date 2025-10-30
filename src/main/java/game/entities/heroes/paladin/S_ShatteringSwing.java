package game.entities.heroes.paladin;

import game.entities.Hero;
import game.entities.Multiplier;
import game.skills.*;
import game.skills.changeeffects.effects.status.Dazed; 
import game.skills.logic.SkillTag;
import game.skills.logic.Stat;
import game.skills.logic.TargetType;
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
        this.dmgMultipliers = List.of(new Multiplier(Stat.ATTACK, 0.5));
        this.targetType = TargetType.SINGLE;  
    }



    @Override
    public void applySkillEffects(Hero target) {
        super.applySkillEffects(target);
//        if (MyMaths.success(this.hero.getStat(Stat.CURRENT_FAITH))) {
//            target.addEffect(new Dazed(1), this.hero);
//        }
    }

    @Override
    public String getDescriptionFor(Hero hero) {
        return "";//return this.hero.getStat(Stat.CURRENT_FAITH)+"(100%"+Stat.FAITH.getIconString()+")% chance to give " + Dazed.getStaticIconString() + "(1).";
    }


    @Override
    public String getName() {
        return "Shattering Swing";
    }
}
