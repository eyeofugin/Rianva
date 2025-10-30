package game.entities.heroes.firedancer;

import game.entities.Hero;
import game.entities.Multiplier;
import game.skills.*; 
import game.skills.logic.SkillTag;
import game.skills.logic.Stat;
import game.skills.logic.TargetType;

import java.util.List;

public class S_FlameLasso extends Skill {

    public S_FlameLasso(Hero hero) {
        super(hero);
        this.iconPath = "entities/heroes/firedancer/icons/flamelasso.png";
        addSubscriptions();
        setToInitial();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.TACTICAL);
        this.dmgMultipliers = List.of(new Multiplier(Stat.MAGIC, 0.5));
        this.targetType = TargetType.SINGLE;  
        this.move = -1;
    }



    @Override
    public int getAIRating(Hero target) {
        int rating = 0;
        rating+=target.getMissingLifePercentage() / 25;
        Hero targetInFront = this.hero.arena.getAtPosition(target.getPosition()+1);
        if (targetInFront != null) {
            rating += targetInFront.getCurrentLifePercentage() < 75 ? 1: 0;
            if (targetInFront.getPosition() == targetInFront.getLastEffectivePosition()) {
                rating +=5;
            }
        }

        return rating;
    }

    @Override
    public String getDescriptionFor(Hero hero) {
        return "Pull 1";
    }

    @Override
    public String getName() {
        return "Flame Lasso";
    }
}
