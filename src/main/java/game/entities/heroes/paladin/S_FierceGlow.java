package game.entities.heroes.paladin;

import game.entities.Hero;
import game.skills.Skill;
import game.skills.logic.SkillTag;
import game.skills.logic.TargetType;
import game.skills.changeeffects.effects.other.Threatening;

import java.util.List;

public class S_FierceGlow extends Skill {

    public S_FierceGlow(Hero hero) {
        super(hero);
        this.iconPath = "entities/heroes/paladin/icons/fierceglow.png";
        addSubscriptions();
        setToInitial();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.TACTICAL);
        this.targetType = TargetType.SELF;
        this.effects = List.of(new Threatening(2));
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
