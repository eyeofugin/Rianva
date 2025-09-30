package game.entities.heroes.longsword;

import game.entities.Hero;
import game.skills.Skill;
import game.skills.SkillTag;
import game.skills.TargetType;
import game.skills.changeeffects.effects.Threatening;

import java.util.List;

public class S_Challenge extends Skill {

    public S_Challenge(Hero hero) {
        super(hero);
        this.iconPath = "entities/heroes/longsword/icons/challenge.png";
        addSubscriptions();
        setToInitial();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.targetType = TargetType.SINGLE;
        this.possibleCastPositions = new int[]{0,1};
        this.possibleTargetPositions = new int[]{2};
        this.cdMax = 3;
        this.casterEffects = List.of(new Threatening(1));
        this.tags = List.of(SkillTag.TACTICAL);
        this.level = 2;
        this.moveTo = true;
    }
    @Override
    public int getAIRating(Hero target) {
        return getRollRating(target);
    }

    @Override
    public String getDescriptionFor(Hero hero) {
        return "Move to position.";
    }

    @Override
    public String getName() {
        return "Challenge";
    }
}
