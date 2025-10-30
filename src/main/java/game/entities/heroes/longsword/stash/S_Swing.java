package game.entities.heroes.longsword.stash;

import game.entities.Hero;
import game.entities.Multiplier;
import game.skills.*;
import game.skills.changeeffects.effects.status.Injured; 
import game.skills.logic.SkillTag;
import game.skills.logic.Stat;
import game.skills.logic.TargetType;
import utils.MyMaths;

import java.util.List;

public class S_Swing extends Skill {

    public S_Swing(Hero hero) {
        super(hero);
        this.iconPath = "entities/heroes/longsword/icons/swing.png";
        addSubscriptions();
        setToInitial();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.PRIMARY);
        this.targetType = TargetType.SINGLE;  
        this.dmg = 3;
        this.dmgMultipliers = List.of(new Multiplier(Stat.ATTACK, 0.25));
        this.effects = List.of(new Injured(1));
    }



    @Override
    public String getDescriptionFor(Hero hero) {
        return "25%+"+Stat.ATTACK.getIconString()+" chance to deal additional 10% true damage.";
    }
    @Override
    public void applySkillEffects(Hero target) {
        super.applySkillEffects(target);
        int success = this.hero.getStat(Stat.ATTACK) + 25;
        if (MyMaths.success(success)) {
            target.trueDamage(this.hero, target.getStat(Stat.LIFE)/10);
        }
    }

    @Override
    public String getName() {
        return "Swing";
    }
}
