package game.entities.goons.bowdude;

import game.entities.Hero;
import game.skills.Skill;
import game.skills.logic.SkillTag;
import game.skills.logic.TargetType;
import game.skills.changeeffects.effects.other.StatEffect;

import java.util.List;

public class S_TakeAim extends Skill {

    public S_TakeAim(Hero hero) {
        super(hero);
        this.iconPath = "entities/goons/bowdude/icons/takeaim.png";
        setToInitial();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.TACTICAL);
        this.targetType = TargetType.SELF;
        this.effects = List.of(StatEffect.lucky.getNew());
    }

    @Override
    public String getName() {
        return "Take Aim";
    }
}
