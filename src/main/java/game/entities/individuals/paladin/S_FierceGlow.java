package game.entities.individuals.paladin;

import game.entities.Hero;
import game.skills.Skill;
import game.skills.SkillTag;
import game.skills.TargetType;
import game.skills.changeeffects.effects.Threatening;

import java.util.List;

public class S_FierceGlow extends Skill {

    public S_FierceGlow(Hero hero) {
        super(hero);
        this.iconPath = "entities/paladin/icons/fierceglow.png";
        addSubscriptions();
        setToInitial();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.TACTICAL);
        this.targetType = TargetType.SELF;
        this.possibleCastPositions = new int[]{0,1,2};
        this.effects = List.of(new Threatening(2));
        this.faithRequirement = 60;
    }

    @Override
    public int getAIRating(Hero target) {
        int rating = 5;
        rating -= this.hero.getMissingLifePercentage() / 40;
        return rating;
    }

    @Override
    public String getName() {
        return "Fierce Glow";
    }
}
