package game.entities.heroes.angelguy.stash;

import game.entities.Hero;
import game.skills.*;
import game.skills.changeeffects.effects.other.Protected;
import game.skills.logic.SkillTag;
import game.skills.logic.TargetType;

import java.util.List;

public class S_HolyShield extends Skill {

    public S_HolyShield(Hero hero) {
        super(hero);
        this.iconPath = "entities/heroes/angelguy/icons/holyshield.png";
        setToInitial();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.TACTICAL);
        this.targetType = TargetType.SELF;
        this.effects = List.of(new Protected(1));
    }

    @Override
    public String getName() {
        return "Holy Shield";
    }
}
