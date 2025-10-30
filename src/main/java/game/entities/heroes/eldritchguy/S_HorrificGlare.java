package game.entities.heroes.eldritchguy;

import game.entities.Hero;
import game.skills.*;
import game.skills.changeeffects.effects.other.StatEffect;
import game.skills.changeeffects.effects.status.Dazed;
import game.skills.logic.SkillTag;
import game.skills.logic.TargetType;

import java.util.List;

public class S_HorrificGlare extends Skill {

    public S_HorrificGlare(Hero hero) {
        super(hero);
        this.iconPath = "entities/heroes/eldritchguy/icons/horrificglare.png";
        addSubscriptions();
        setToInitial();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.TACTICAL);
        this.targetType = TargetType.SINGLE;
        this.effects = List.of(new Dazed(1), StatEffect.frail.getNew());
    }

    @Override
    public String getName() {
        return "Horrific Glare";
    }
}
