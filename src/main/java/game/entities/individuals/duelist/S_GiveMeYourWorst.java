package game.entities.individuals.duelist;

import game.entities.Hero;
import game.skills.Skill;
import game.skills.SkillTag;
import game.skills.TargetType;
import game.skills.changeeffects.statusinflictions.Taunted;

import java.util.List;

public class S_GiveMeYourWorst extends Skill {

    public S_GiveMeYourWorst(Hero hero) {
        super(hero);
        this.iconPath = "entities/duelist/icons/givemeyourworst.png";
        addSubscriptions();
        setToInitial();
    }
    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.TACTICAL);
        this.targetType = TargetType.SINGLE;
        this.possibleCastPositions = new int[]{1,2};
        this.possibleTargetPositions = new int[]{3,4};
        this.cdMax = 3;
        this.effects = List.of(new Taunted(2));
        this.level = 2;
    }

    @Override
    public String getName() {
        return "Give me your worst";
    }
}
