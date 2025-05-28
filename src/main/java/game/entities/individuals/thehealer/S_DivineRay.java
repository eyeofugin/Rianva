package game.entities.individuals.thehealer;

import game.entities.Hero;
import game.entities.Multiplier;
import game.skills.*;

import java.util.List;

public class S_DivineRay extends Skill {

    public S_DivineRay(Hero hero) {
        super(hero);
        this.iconPath = "entities/thehealer/icons/divineray.png";
        addSubscriptions();
        setToInitial();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.PRIMARY);
        this.targetType = TargetType.SINGLE;
        this.heal = 2;
        this.healMultipliers = List.of(new Multiplier(Stat.MANA, 0.3));
        this.possibleCastPositions = new int[]{0,1,2};
        this.possibleTargetPositions = new int[]{1,2,3};
    }

    @Override
    public int getAIRating(Hero target) {
        return target.getMissingLifePercentage() / 25;
    }


    @Override
    public String getName() {
        return "Divine Ray";
    }
}
