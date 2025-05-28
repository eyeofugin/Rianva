package game.entities.individuals.divinemage;

import game.entities.Hero;
import game.skills.Skill;
import game.skills.SkillTag;
import game.skills.TargetType;
import game.skills.changeeffects.effects.Invincible;
import game.skills.changeeffects.effects.Protected;

import java.util.List;

public class S_Invincibility extends Skill {

    public S_Invincibility(Hero hero) {
        super(hero);
        this.iconPath = "entities/divinemage/icons/invincibility.png";
        addSubscriptions();
        setToInitial();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.TACTICAL);
        this.targetType = TargetType.SINGLE;
        this.faithRequirement = 30;
        this.faithGain = 5;
        this.effects = List.of(new Protected(1));
        this.possibleCastPositions = new int[]{0,1,2};
        this.possibleTargetPositions = new int[]{0,1,2};
        this.level = 2;
    }


    @Override
    public int getAIRating(Hero target) {
        if (target.getCurrentLifePercentage() < 25) {
            return 4;
        }
        if (target.getCurrentLifePercentage() < 50) {
            return 2;
        }
        return 0;
    }

    @Override
    public String getName() {
        return "Invincibility";
    }
}
