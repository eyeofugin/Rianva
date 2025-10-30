package game.entities.heroes.darkmage;

import game.entities.Hero;
import game.skills.Skill;
import game.skills.logic.SkillTag;
import game.skills.logic.TargetType;
import game.skills.changeeffects.effects.other.StatEffect;

import java.util.List;

public class S_UnfairAdvantage extends Skill {

    public S_UnfairAdvantage(Hero hero) {
        super(hero);
        this.iconPath = "entities/heroes/darkmage/icons/unfairadvantage.png";
        addSubscriptions();
        setToInitial();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.TACTICAL);
        this.targetType = TargetType.SINGLE_OTHER;
        this.effects = List.of(StatEffect.arcane.getNew(), StatEffect.fast.getNew());
        this.level = 2;
    }


    @Override
    public int getAIRating(Hero target) {
        return 2;
    }



    @Override
    public String getName() {
        return "Unfair Advantage";
    }
}
