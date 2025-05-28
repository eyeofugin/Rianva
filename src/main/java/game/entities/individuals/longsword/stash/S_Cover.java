package game.entities.individuals.longsword.stash;

import game.entities.Hero;
import game.skills.Skill;
import game.skills.SkillTag;
import game.skills.TargetType;
import game.skills.changeeffects.effects.Cover;

import java.util.List;

public class S_Cover extends Skill {

    public S_Cover(Hero hero) {
        super(hero);
        this.iconPath = "entities/longsword/icons/cover.png";
        setToInitial();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.targetType = TargetType.SINGLE_OTHER;
        this.possibleCastPositions = new int[]{1,2};
        this.possibleTargetPositions = new int[]{1,2};
        this.effects = List.of(new Cover(3));
        this.cdMax = 4;
        this.tags = List.of(SkillTag.TACTICAL);
    }

    @Override
    public String getName() {
        return "Cover";
    }
}
