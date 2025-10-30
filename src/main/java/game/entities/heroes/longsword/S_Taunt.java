package game.entities.heroes.longsword;

import game.entities.Hero;
import game.skills.Skill;
import game.skills.logic.SkillTag;
import game.skills.logic.TargetType;
import game.skills.changeeffects.effects.status.Taunted;

import java.util.List;

public class S_Taunt extends Skill {

    public S_Taunt(Hero hero) {
        super(hero);
        this.iconPath = "entities/heroes/longsword/icons/taunt.png";
        addSubscriptions();
        setToInitial();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.TACTICAL);
        this.targetType = TargetType.SINGLE;
        this.effects = List.of(new Taunted(2));
    }

    @Override
    public int getAIRating(Hero target) {
        return 5;
    }

    @Override
    public String getName() {
        return "Taunt";
    }
}
