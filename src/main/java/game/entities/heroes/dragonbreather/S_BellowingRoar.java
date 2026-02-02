package game.entities.heroes.dragonbreather;

import game.entities.Hero;
import game.entities.Multiplier;
import game.skills.*;
import game.skills.changeeffects.effects.other.Threatening;
import game.skills.logic.SkillTag;
import game.skills.logic.Stat;
import game.skills.logic.TargetType;

import java.util.List;

public class S_BellowingRoar extends Skill {

    public S_BellowingRoar(Hero hero) {
        super(hero);
        this.iconPath = "entities/heroes/dragonbreather/icons/bellowingroar.png";
        addSubscriptions();
        setToInitial();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.ULT);
        this.targetType = TargetType.SELF;
        this.effects = List.of(new Threatening(2));
        this.healMultipliers = List.of(new Multiplier(Stat.LIFE, 0.75));
        this.level = 5;
    }
    @Override
    public int getAIRating(Hero target) {
        if (this.hero.getCurrentLifePercentage() < 50) {
            return 3;
        }
        return 0;
    }

    @Override
    public String getName() {
        return "Bellowing Roar";
    }
}
