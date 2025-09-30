package game.entities.heroes.darkmage;

import game.entities.Hero;
import game.skills.Skill;
import game.skills.SkillTag;
import game.skills.Stat;
import game.skills.TargetType;
import game.skills.changeeffects.effects.StatEffect;

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
        this.possibleCastPositions = new int[]{0,1,2};
        this.possibleTargetPositions = new int[]{0,1,2};
        this.effects = List.of(new StatEffect(2, Stat.MAGIC, 0.2), new StatEffect(2, Stat.SPEED, 0.2));
        this.manaCost = 3;
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
