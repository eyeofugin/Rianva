package game.entities.heroes.thehealer;

import game.entities.Hero;
import game.entities.Multiplier;
import game.skills.Skill;
import game.skills.logic.SkillTag;
import game.skills.logic.Stat;
import game.skills.logic.TargetType;

import java.util.List;

public class S_ImbueWithLight extends Skill {

    public S_ImbueWithLight(Hero hero) {
        super(hero);
        this.iconPath = "entities/heroes/thehealer/icons/imbuewithlight.png";
        addSubscriptions();
        setToInitial();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.ULT);
        this.targetType = TargetType.ALL_ENEMY;
        this.healMultipliers = List.of(new Multiplier(Stat.MAGIC, 0.2));
        this.shieldMultipliers = List.of(new Multiplier(Stat.LIFE, 0.2));
        this.level = 5;
    }
    @Override
    public int getAIRating(Hero target) {
        return target.getMissingLifePercentage() / 50;
    }

    @Override
    public String getName() {
        return "Imbue with light";
    }
}
