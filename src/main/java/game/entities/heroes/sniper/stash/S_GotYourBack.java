package game.entities.heroes.sniper.stash;

import game.entities.Hero;
import game.skills.*;
import game.skills.logic.SkillTag;
import game.skills.logic.Stat;
import game.skills.logic.TargetType;

import java.util.List;

public class S_GotYourBack extends Skill {

    public S_GotYourBack(Hero hero) {
        super(hero);
        this.iconPath = "entities/heroes/sniper/icons/gotyourback.png";
        addSubscriptions();
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
        target.addToStat(Stat.SPEED, 3);
    }

    @Override
    public String getDescriptionFor(Hero hero) {
        return "Gives +3" + Stat.SPEED.getIconString();
    }


    @Override
    public String getName() {
        return "Got Your Back";
    }
}
