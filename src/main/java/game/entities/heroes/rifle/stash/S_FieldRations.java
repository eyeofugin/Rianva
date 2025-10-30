package game.entities.heroes.rifle.stash;

import game.entities.Hero;
import game.entities.Multiplier;
import game.skills.*;
import game.skills.logic.SkillTag;
import game.skills.logic.Stat;
import game.skills.logic.TargetType;

import java.util.List;

public class S_FieldRations extends Skill {

    public S_FieldRations(Hero hero) {
        super(hero);
        this.iconPath = "entities/heroes/rifle/icons/fieldrations.png";
        addSubscriptions();
        setToInitial();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.TACTICAL);
        this.targetType = TargetType.SINGLE;
        this.healMultipliers = List.of(new Multiplier(Stat.LIFE, 0.5));
    }
    @Override
    public int getAIRating(Hero target) {
        if (target.getResourcePercentage(Stat.CURRENT_LIFE) < 25) {
            return 4;
        }
        if (target.getResourcePercentage(Stat.CURRENT_LIFE) < 50) {
            return 2;
        }
        return 0;
    }

    @Override
    public String getName() {
        return "Field Rations";
    }
}
