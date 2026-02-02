package game.entities.heroes.firedancer;

import game.entities.Hero;
import game.skills.*;
import game.skills.changeeffects.effects.other.Burning;
import game.skills.logic.AiSkillTag;
import game.skills.logic.SkillTag;
import game.skills.logic.TargetType;

import java.util.List;

public class S_RushOfHeat extends Skill {

    public S_RushOfHeat(Hero hero) {
        super(hero);
        this.iconPath = "entities/heroes/firedancer/icons/rushofheat.png";
        addSubscriptions();
        setToInitial();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.TACTICAL);
        this.targetType = TargetType.SELF;
        this.casterEffects = List.of(new Burning(3));
        this.aiTags = List.of(AiSkillTag.FAITH_GAIN);
        this.level = 2;
    }



    @Override
    public int getAIRating(Hero target) {
        return -1* this.hero.getMissingLifePercentage() / 25;
    }

    @Override
    public String getName() {
        return "Rush of Heat";
    }
}
