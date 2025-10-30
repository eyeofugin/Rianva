package game.entities.heroes.longsword.stash;

import game.entities.Hero;
import game.skills.Skill;
import game.skills.logic.SkillTag;
import game.skills.logic.TargetType;
import game.skills.changeeffects.effects.other.StatEffect;

import java.util.List;

public class S_Cover extends Skill {

    public S_Cover(Hero hero) {
        super(hero);
        this.iconPath = "entities/heroes/longsword/icons/cover.png";
        setToInitial();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.targetType = TargetType.SINGLE_OTHER;
        this.effects = List.of(StatEffect.covered.getNew());
        this.tags = List.of(SkillTag.TACTICAL);
    }

    @Override
    public String getName() {
        return "Cover";
    }
}
