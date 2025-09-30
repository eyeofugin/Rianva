package game.entities.heroes.sniper.stash;

import game.entities.Hero;
import game.skills.*;

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
        this.possibleCastPositions = new int[]{0,1};
        this.possibleTargetPositions = new int[]{0,1,2};
        this.cdMax = 1;
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
