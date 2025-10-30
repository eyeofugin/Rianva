package game.entities.heroes.angelguy.stash;

import game.entities.Hero;
import game.skills.*;
import game.skills.logic.SkillTag;
import game.skills.logic.Stat;
import game.skills.logic.TargetType;

import java.util.List;

public class S_AngelicBlessing extends Skill {

    public S_AngelicBlessing(Hero hero) {
        super(hero);
        this.iconPath = "entities/heroes/angelguy/icons/piercinglight.png";
        setToInitial();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.TACTICAL);
        this.targetType = TargetType.SINGLE_OTHER;
    }

    @Override
    public void applySkillEffects(Hero target) {
        super.applySkillEffects(target);
    }

//    @Override
//    public int getFaithRequirement() {
//        return this.hero.getStat(Stat.CURRENT_FAITH) / 2;
//    }

    @Override
    public String getDescriptionFor(Hero hero) {
        return "Spend to give target ally the same amount of .";
    }

    @Override
    public String getName() {
        return "Angelic Blessing";
    }
}
