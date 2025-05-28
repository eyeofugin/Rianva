package game.entities.individuals.longsword;

import game.entities.Hero;
import game.skills.Skill;
import game.skills.SkillTag;
import game.skills.TargetType;
import game.skills.changeeffects.statusinflictions.Taunted;

import java.util.List;

public class S_Taunt extends Skill {

    public S_Taunt(Hero hero) {
        super(hero);
        this.iconPath = "entities/longsword/icons/taunt.png";
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
        this.cdMax = 5;
        this.effects = List.of(new Taunted(2));
    }

    @Override
    public int getAIRating(Hero target) {
        return 5;
    }

    @Override
    public String getName() {
        return "Taunt";
    }
}
