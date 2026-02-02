package game.entities.heroes.divinemage;

import game.entities.Hero;
import game.skills.Skill;
import game.skills.logic.SkillTag;
import game.skills.logic.TargetType;
import game.skills.changeeffects.effects.other.Protected;

import java.util.List;

public class S_Invincibility extends Skill {

    public S_Invincibility(Hero hero) {
        super(hero);
        this.iconPath = "entities/heroes/divinemage/icons/invincibility.png";
        addSubscriptions();
        setToInitial();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.TACTICAL);
        this.targetType = TargetType.SINGLE;
        this.effects = List.of(new Protected(1));
        this.level = 2;
    }

    @Override
    public int getAIRating(Hero target) {
        if (target.getCurrentLifePercentage() < 25) {
            return 4;
        }
        if (target.getCurrentLifePercentage() < 50) {
            return 2;
        }
        return 0;
    }

    @Override
    public String getName() {
        return "Invincibility";
    }
}
