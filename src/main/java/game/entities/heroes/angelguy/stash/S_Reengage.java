package game.entities.heroes.angelguy.stash;

import game.entities.Hero;
import game.skills.*;

import java.util.List;

public class S_Reengage extends Skill {

    public S_Reengage(Hero hero) {
        super(hero);
        this.iconPath = "entities/heroes/angelguy/icons/reengage.png";
        setToInitial();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.TACTICAL);
        this.targetType = TargetType.SINGLE;
        this.possibleTargetPositions = new int[]{2};
        this.possibleCastPositions = new int[]{0,1};
        this.faithRequirement = 55;
        this.priority = 1;
        this.level = 2;
        this.moveTo = true;
    }

    @Override
    public String getDescriptionFor(Hero hero) {
        return "Move to target position.";
    }

    @Override
    public int getAIRating(Hero target) {
        int rating = 0;
        if (this.hero.getPosition() == this.hero.team.getFirstPosition()) {
            return 1;
        }
        if (target.getPosition() == this.hero.team.getFirstPosition()) {
            rating++;
        }
        rating += target.getMissingLifePercentage() / 25;
        rating -= this.hero.getMissingLifePercentage() / 20;
        if (this.hero.getLastEffectivePosition() < this.hero.getPosition()) {
            rating += 10;
        }
        return rating;
    }

    @Override
    public String getName() {
        return "Reengage";
    }
}
